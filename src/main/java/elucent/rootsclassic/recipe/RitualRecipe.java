package elucent.rootsclassic.recipe;

import com.mojang.datafixers.util.Function7;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.ritual.RitualEffect;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class RitualRecipe implements Recipe<RecipeInput> {
  private static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(
    instance -> instance.group(
        Identifier.CODEC.fieldOf("effect").forGetter(recipe -> recipe.effectId),
        CompoundTag.CODEC.optionalFieldOf("effectConfig").forGetter(o -> o.effectConfig),
        Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, 4)).fieldOf("ingredients").forGetter(o -> o.materials),
        Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, 4)).fieldOf("incenses").forGetter(o -> o.materials),
        Codec.INT.fieldOf("level").validate((level) -> {
          if (level < 0 || level > 2) {
            return DataResult.error(() -> "Level must be between 0 and 3, you tried " + level);
          } else {
            return DataResult.success(level);
          }
        }).forGetter(recipe -> recipe.level),

        Codec.STRING.fieldOf("color").forGetter(recipe -> recipe.color),
        Codec.STRING.optionalFieldOf("secondaryColor", "").forGetter(recipe -> recipe.secondaryColor)
      )
      .apply(instance, RitualRecipe::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> STREAM_CODEC = composite(
    Identifier.STREAM_CODEC,
    o -> o.effectId,
    ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG),
    o -> o.effectConfig,
    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
    o -> o.materials,
    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
    o -> o.incenses,
    ByteBufCodecs.INT,
    o -> o.level,
    ByteBufCodecs.STRING_UTF8,
    o -> o.color,
    ByteBufCodecs.STRING_UTF8,
    o -> o.secondaryColor,
    RitualRecipe::new
  );
  public static final RecipeSerializer<RitualRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

	private final List<Ingredient> materials;
	private final List<Ingredient> incenses;
	public final int level;
	public final String color;
	private final int colorInt;
	public final String secondaryColor;
	private final int secondaryColorInt;
	public final Identifier effectId;
	public final RitualEffect effect;
	public final Optional<CompoundTag> effectConfig;

	public RitualRecipe(Identifier effectId, Optional<CompoundTag> effectConfig, List<Ingredient> materials,
                      List<Ingredient> incenses, int level, String color, String secondaryColor) {
		this.materials = materials;
		this.incenses = incenses;
		this.level = level;
		this.effectId = effectId;
		this.effect = RitualBaseRegistry.RITUALS.getValue(effectId);
		this.effectConfig = effectConfig;
		this.color = color;
		this.colorInt = RootsUtil.intColorFromHexString(color);
		this.secondaryColor = secondaryColor;
		this.secondaryColorInt = RootsUtil.intColorFromHexString(secondaryColor);
	}

	@Override
	public RecipeSerializer<RitualRecipe> getSerializer() {
		return RootsRecipes.RITUAL_SERIALIZER.get();
	}

	@Override
	public ItemStack assemble(RecipeInput recipeInput) {
		return getResultItem();
	}

  public List<Ingredient> getIngredients() {
    return this.materials;
  }

  @Override
  public boolean showNotification() {
    return false;
  }

  @Override
  public String group() {
    return "";
  }

  public ItemStack getResultItem() {
		return effect.getResult(effectConfig.orElse(null)).copy();
	}

	@Override
	public RecipeType<RitualRecipe> getType() {
		return RootsRecipes.RITUAL_RECIPE_TYPE.get();
	}

  @NotNull
  @Override
  public PlacementInfo placementInfo() {
    return PlacementInfo.NOT_PLACEABLE;
  }

  @NotNull
  @Override
  public RecipeBookCategory recipeBookCategory() {
    return RecipeBookCategories.CRAFTING_MISC;
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
	public boolean matches(RecipeInput recipeInput, Level level) {
		return false;
	}

	public List<Ingredient> getIncenses() {
		return incenses;
	}

	public MutableComponent getInfoText() {
		return effect.getInfoText(effectConfig.orElse(null));
	}

	public int getColorInt() {
		return colorInt;
	}

	public int getSecondaryColorInt() {
		return secondaryColorInt;
	}

  public boolean incenseMatches(Level levelAccessor, BlockPos pos) {
		ArrayList<ItemStack> incenseFromNearby = new ArrayList<>();
		List<BrazierBlockEntity> braziers = RitualPillars.getRecipeBraziers(levelAccessor, pos);
		for (BrazierBlockEntity brazier : braziers) {
			if (!brazier.getHeldItem().isEmpty()) {
				//              Roots.logger.info("found brazier item " + brazier.getHeldItem());
				incenseFromNearby.add(brazier.getHeldItem());
			}
		}
    return effect.incenseMatches(incenseFromNearby, this);
	}

	public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
		effect.doEffect(levelAccessor, pos, inventory, incenses, effectConfig.orElse(null));
	}

	public static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(
		final StreamCodec<? super B, T1> pCodec1,
		final Function<C, T1> pGetter1,
		final StreamCodec<? super B, T2> pCodec2,
		final Function<C, T2> pGetter2,
		final StreamCodec<? super B, T3> pCodec3,
		final Function<C, T3> pGetter3,
		final StreamCodec<? super B, T4> pCodec4,
		final Function<C, T4> pGetter4,
		final StreamCodec<? super B, T5> pCodec5,
		final Function<C, T5> pGetter5,
		final StreamCodec<? super B, T6> pCodec6,
		final Function<C, T6> pGetter6,
		final StreamCodec<? super B, T7> pCodec7,
		final Function<C, T7> pGetter7,
		final Function7<T1, T2, T3, T4, T5, T6, T7, C> pFactory
	) {
		return new StreamCodec<B, C>() {
			@Override
			public C decode(B p_330310_) {
				T1 t1 = pCodec1.decode(p_330310_);
				T2 t2 = pCodec2.decode(p_330310_);
				T3 t3 = pCodec3.decode(p_330310_);
				T4 t4 = pCodec4.decode(p_330310_);
				T5 t5 = pCodec5.decode(p_330310_);
				T6 t6 = pCodec6.decode(p_330310_);
				T7 t7 = pCodec7.decode(p_330310_);
				return pFactory.apply(t1, t2, t3, t4, t5, t6, t7);
			}

			@Override
			public void encode(B p_332052_, C p_331912_) {
				pCodec1.encode(p_332052_, pGetter1.apply(p_331912_));
				pCodec2.encode(p_332052_, pGetter2.apply(p_331912_));
				pCodec3.encode(p_332052_, pGetter3.apply(p_331912_));
				pCodec4.encode(p_332052_, pGetter4.apply(p_331912_));
				pCodec5.encode(p_332052_, pGetter5.apply(p_331912_));
				pCodec6.encode(p_332052_, pGetter6.apply(p_331912_));
				pCodec7.encode(p_332052_, pGetter7.apply(p_331912_));
			}
		};

	}
}