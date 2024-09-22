package elucent.rootsclassic.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.item.powder.SpellPowderItem;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

public class ComponentRecipe implements Recipe<RecipeInput> {

  private static final int MAX_INGREDIENTS = 4;
  private final ResourceLocation effectResult;
  private final String group;
  private final ItemStack recipeOutput;
  private final NonNullList<Ingredient> materials;
  private final boolean needsMixin;

  public ComponentRecipe(ResourceLocation effect, String group, ItemStack result, NonNullList<Ingredient> materials, boolean needsMixin) {
    this.effectResult = effect;
    this.group = group;
    this.recipeOutput = result;
    this.materials = materials;
    this.needsMixin = needsMixin;
  }

	public ResourceLocation getEffectResult() {
    return effectResult;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return RootsRecipes.COMPONENT_SERIALIZER.get();
  }

  @Override
  public String getGroup() {
    return group;
  }

  @Override
  public ItemStack assemble(RecipeInput inventory, HolderLookup.Provider provider) {
    ItemStack outputStack = getResultItem(provider);
    if (outputStack.getItem() instanceof SpellPowderItem) {
      SpellPowderItem.createData(outputStack, this.getEffectResult(), inventory);
    }
    return outputStack;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return false;
  }

  @Override
  public ItemStack getResultItem(HolderLookup.Provider provider) {
    return recipeOutput.copy();
  }

  public ItemStack getResultItem() {
    return recipeOutput.copy();
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return materials;
  }

  @Override
  public RecipeType<?> getType() {
    return RootsRecipes.COMPONENT_RECIPE_TYPE.get();
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  public boolean needsMixin() {
    return needsMixin;
  }

  @Override
  public boolean matches(RecipeInput recipeInput, Level levelAccessor) {
    java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
    int i = 0;
    for (int j = 0; j < recipeInput.size(); j++) {
      ItemStack stack = recipeInput.getItem(j);
      if (!stack.isEmpty() && !isSupplementItem(stack, levelAccessor.registryAccess())) {
        ++i;
        inputs.add(stack);
      }
    }
    return i == this.materials.size() && RecipeMatcher.findMatches(inputs, this.materials) != null;
  }

  /**
   * True if not part of a recipe but just a recipe booster
   *
   * @param stack
   *          The stack to check
   * @return True if the stack is a supplement
   */
  private boolean isSupplementItem(ItemStack stack, RegistryAccess access) {
    if (getResultItem(access).getItem() instanceof SpellPowderItem) {
      return stack.getItem() == RootsRegistry.OLD_ROOT.get() ||
          stack.getItem() == RootsRegistry.VERDANT_SPRIG.get() ||
          stack.getItem() == RootsRegistry.INFERNAL_BULB.get() ||
          stack.getItem() == RootsRegistry.DRAGONS_EYE.get() ||
          stack.getItem() == Items.GLOWSTONE_DUST ||
          stack.getItem() == Items.REDSTONE ||
          stack.getItem() == Items.GUNPOWDER;
    }
    else {
      return false;
    }
  }

  public static int getModifierCapacity(RecipeInput inventory) {
    int maxCapacity = -1;
    for (int i = 0; i < inventory.size(); i++) {
      ItemStack stack = inventory.getItem(i);
      if (stack.getItem() == RootsRegistry.OLD_ROOT.get() && maxCapacity < 0) {
        maxCapacity = 0;
      }
      if (stack.getItem() == RootsRegistry.VERDANT_SPRIG.get() && maxCapacity < 1) {
        maxCapacity = 1;
      }
      if (stack.getItem() == RootsRegistry.INFERNAL_BULB.get() && maxCapacity < 2) {
        maxCapacity = 2;
      }
      if (stack.getItem() == RootsRegistry.DRAGONS_EYE.get() && maxCapacity < 3) {
        maxCapacity = 3;
      }
    }
    return maxCapacity;
  }

  public static int getModifierCount(RecipeInput inventory) {
    int count = 0;
    for (int i = 0; i < inventory.size(); i++) {
      ItemStack stack = inventory.getItem(i);
      if (stack.getItem() == Items.GLOWSTONE_DUST) {
        count++;
      }
      else if (stack.getItem() == Items.REDSTONE) {
        count++;
      }
      else if (stack.getItem() == Items.GUNPOWDER) {
        count++;
      }
    }
    return count;
  }

  public static class SerializeComponentRecipe implements RecipeSerializer<ComponentRecipe> {
	  private static final MapCodec<ComponentRecipe> CODEC = RecordCodecBuilder.mapCodec(
		  instance -> instance.group(
				  ResourceLocation.CODEC.fieldOf("effect").forGetter(recipe -> recipe.effectResult),
				  Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				  ItemStack.STRICT_CODEC.optionalFieldOf("result", RootsRegistry.SPELL_POWDER.toStack()).forGetter(recipe -> recipe.recipeOutput),
				  Ingredient.CODEC_NONEMPTY
					  .listOf()
					  .fieldOf("ingredients")
					  .flatXmap(
						  p_301021_ -> {
							  Ingredient[] aingredient = p_301021_.toArray(Ingredient[]::new); 
							  if (aingredient.length == 0) {
								  return DataResult.error(() -> "No ingredients for component recipe");
							  } else {
								  return aingredient.length > MAX_INGREDIENTS
									  ? DataResult.error(() -> "Too many ingredients for component recipe. The maximum is: %s".formatted(MAX_INGREDIENTS))
									  : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
							  }
						  },
						  DataResult::success
					  )
					  .forGetter(recipe -> recipe.materials),
				  Codec.BOOL.optionalFieldOf("need_mixin", true).forGetter(recipe -> recipe.needsMixin)
			  )
			  .apply(instance, ComponentRecipe::new)
	  );
	  public static final StreamCodec<RegistryFriendlyByteBuf, ComponentRecipe> STREAM_CODEC = StreamCodec.of(
		  SerializeComponentRecipe::toNetwork, SerializeComponentRecipe::fromNetwork
	  );

	  @Override
	  public MapCodec<ComponentRecipe> codec() {
		  return CODEC;
	  }

	  @Override
	  public StreamCodec<RegistryFriendlyByteBuf, ComponentRecipe> streamCodec() {
		  return STREAM_CODEC;
	  }

    public static ComponentRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
	    ResourceLocation effectResult = buffer.readResourceLocation();
      String s = buffer.readUtf();
	    ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
      int i = buffer.readVarInt();
      NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
      for (int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
      }
      boolean needsMixin = buffer.readBoolean();
      return new ComponentRecipe(effectResult, s, itemstack, nonnulllist, needsMixin);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, ComponentRecipe recipe) {
	    buffer.writeResourceLocation(recipe.effectResult);
      buffer.writeUtf(recipe.group);
	    ItemStack.STREAM_CODEC.encode(buffer, recipe.recipeOutput);
      buffer.writeVarInt(recipe.materials.size());
      for (Ingredient ingredient : recipe.materials) {
	      Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
      }
      buffer.writeBoolean(recipe.needsMixin);
    }
  }

	@Override
	public String toString() {
		return "ComponentRecipe [effectResult=" + effectResult + ", recipeOutput=" + recipeOutput + "]";
	}
}
