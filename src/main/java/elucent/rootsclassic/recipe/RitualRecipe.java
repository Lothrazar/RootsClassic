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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RitualRecipe implements Recipe<RecipeInput> {

	private final NonNullList<Ingredient> materials;
	private final NonNullList<Ingredient> incenses;
	public final int level;
	public final String color;
	private final int colorInt;
	public final String secondaryColor;
	private final int secondaryColorInt;
	public final ResourceLocation effectId;
	public final RitualEffect effect;
	@Nullable
	public final CompoundTag effectConfig;

	public RitualRecipe(ResourceLocation effectId, @Nullable CompoundTag effectConfig, NonNullList<Ingredient> materials,
	                    NonNullList<Ingredient> incenses, int level, String color, String secondaryColor) {
		this.materials = materials;
		this.incenses = incenses;
		this.level = level;
		this.effectId = effectId;
		this.effect = RitualBaseRegistry.RITUALS.get(effectId);
		this.effectConfig = effectConfig;
		this.color = color;
		this.colorInt = RootsUtil.intColorFromHexString(color);
		this.secondaryColor = secondaryColor;
		this.secondaryColorInt = RootsUtil.intColorFromHexString(secondaryColor);
	}

	public RitualRecipe(ResourceLocation effectId, Optional<CompoundTag> optionalConfig, NonNullList<Ingredient> materials,
	                    NonNullList<Ingredient> incenses, int level, String color, String secondaryColor) {
		this(effectId, optionalConfig.orElse(null), materials, incenses, level, color, secondaryColor);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RootsRecipes.RITUAL_SERIALIZER.get();
	}

	@Override
	public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
		return getResultItem(provider);
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return effect.getResult(effectConfig, provider).copy();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return materials;
	}

	@Override
	public RecipeType<?> getType() {
		return RootsRecipes.RITUAL_RECIPE_TYPE.get();
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return false;
	}

	@Override
	public boolean matches(RecipeInput recipeInput, Level level) {
		return false;
	}

	public List<Ingredient> getIncenses() {
		return incenses;
	}

	public MutableComponent getInfoText() {
		return effect.getInfoText(effectConfig);
	}

	public int getColorInt() {
		return colorInt;
	}

	public int getSecondaryColorInt() {
		return secondaryColorInt;
	}

	public static class SerializeRitualRecipe implements RecipeSerializer<RitualRecipe> {
		private static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("effect").forGetter(recipe -> recipe.effectId),
					CompoundTag.CODEC.optionalFieldOf("effectConfig").forGetter(recipe -> Optional.ofNullable(recipe.effectConfig)),
					Ingredient.CODEC_NONEMPTY
						.listOf()
						.fieldOf("ingredients")
						.flatXmap(
							p_301021_ -> {
								Ingredient[] aingredient = p_301021_.toArray(Ingredient[]::new);
								if (aingredient.length == 0) {
									return DataResult.error(() -> "No ingredients for ritual recipe");
								} else {
									return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
								}
							},
							DataResult::success
						)
						.forGetter(recipe -> recipe.materials),
					Ingredient.CODEC_NONEMPTY
						.listOf()
						.fieldOf("incenses")
						.flatXmap(
							p_301021_ -> {
								Ingredient[] aingredient = p_301021_.toArray(Ingredient[]::new);
								if (aingredient.length > 4) {
									return DataResult.error(() -> "Too many ingredients for ritual recipe, the max is 4");
								} else {
									return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
								}
							},
							DataResult::success
						)
						.forGetter(recipe -> recipe.incenses),
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
		public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> STREAM_CODEC = StreamCodec.of(
			SerializeRitualRecipe::toNetwork, SerializeRitualRecipe::fromNetwork
		);

		@Override
		public MapCodec<RitualRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static RitualRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			ResourceLocation effect = buffer.readResourceLocation();
			Optional<CompoundTag> effectConfig = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buffer);

			var size = buffer.readVarInt();
			NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
			for (int i = 0; i < size; i++) {
				ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
			}

			size = buffer.readVarInt();
			NonNullList<Ingredient> incenses = NonNullList.create();
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					incenses.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
				}
			}

			var level = buffer.readVarInt();
			var color = buffer.readUtf();
			var secondaryColor = buffer.readUtf();

			return new RitualRecipe(effect, effectConfig, ingredients, incenses, level, color, secondaryColor);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, RitualRecipe recipe) {
			buffer.writeResourceLocation(recipe.effectId);
			ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buffer, Optional.ofNullable(recipe.effectConfig));

			buffer.writeVarInt(recipe.materials.size());
			for (int i = 0; i < recipe.materials.size(); i++) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.materials.get(i));
			}

			buffer.writeVarInt(recipe.incenses.size());
			if (recipe.incenses.size() > 0) {
				for (int i = 0; i < recipe.incenses.size(); i++) {
					Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.incenses.get(i));
				}
			}

			buffer.writeVarInt(recipe.level);
			buffer.writeUtf(recipe.color);
			buffer.writeUtf(recipe.secondaryColor);
		}
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
		return RootsUtil.matchesIngredients(incenseFromNearby, incenses);
	}

	public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
		effect.doEffect(levelAccessor, pos, inventory, incenses, effectConfig);
	}

	@Override
	public String toString() {
		return "RitualRecipe [level=" + level + ", effect=" + RitualBaseRegistry.RITUALS.getKey(effect) + ", config=" + effectConfig + "]";
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