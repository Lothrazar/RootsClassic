package elucent.rootsclassic.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elucent.rootsclassic.item.SpellPowderItem;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ComponentRecipe implements Recipe<Container> {
  private static final int MAX_INGREDIENTS = 4;
  private final ResourceLocation id;
  private final ResourceLocation effectResult;
  private final String group;
  private final ItemStack recipeOutput;
  private final NonNullList<Ingredient> materials;
  private final boolean needsMixin;

  public ComponentRecipe(ResourceLocation idIn, ResourceLocation effectResult, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn, boolean needsMixin) {
    this.id = idIn;
    this.effectResult = effectResult;
    this.group = groupIn;
    this.recipeOutput = recipeOutputIn;
    this.materials = recipeItemsIn;
    this.needsMixin = needsMixin;
  }

  public ComponentRecipe(ResourceLocation idIn, ResourceLocation effectResult, String groupIn, NonNullList<Ingredient> recipeItemsIn, boolean needsMixin) {
    this(idIn, effectResult, groupIn, new ItemStack(RootsRegistry.SPELL_POWDER.get()), recipeItemsIn, needsMixin);
  }

  @Override
  public ResourceLocation getId() {
    return id;
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
  public ItemStack assemble(Container inventory) {
    ItemStack outputStack = getResultItem();
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
  public ItemStack getResultItem() {
    return recipeOutput.copy();
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return materials;
  }

  @Override
  public RecipeType<?> getType() {
    return RootsRecipes.COMPONENT_RECIPE_TYPE;
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  public boolean needsMixin() {
    return needsMixin;
  }

  public TranslatableComponent getLocalizedName() {
    return new TranslatableComponent("rootsclassic.component." + this.getId());
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Ingredient mat : getIngredients()) {
      if (!mat.isEmpty()) {
        s.append(mat.getItems()[0].getHoverName().getString()).append(" ");
      }
      else {
        s.append("One of the ingredients has no matching ItemStack's");
      }
    }
    return s.toString();
  }

  @Override
  public boolean matches(Container inventory, Level worldIn) {
    java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
    int i = 0;
    for (int j = 0; j < inventory.getContainerSize(); j++) {
      ItemStack stack = inventory.getItem(j);
      if (!stack.isEmpty() && !isSupplementItem(stack)) {
        ++i;
        inputs.add(stack);
      }
    }
    return i == this.materials.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.materials) != null;
  }

  /**
   * True if not part of a recipe but just a recipe booster
   *
   * @param stack
   * @return
   */
  private boolean isSupplementItem(ItemStack stack) {
    if (getResultItem().getItem() instanceof SpellPowderItem) {
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

  public static int getModifierCapacity(Container inventory) {
    int maxCapacity = -1;
    for (int i = 0; i < inventory.getContainerSize(); i++) {
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

  public static int getModifierCount(Container inventory) {
    int count = 0;
    for (int i = 0; i < inventory.getContainerSize(); i++) {
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

  public static class SerializeComponentRecipe extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ComponentRecipe> {

    public ComponentRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
      String s = GsonHelper.getAsString(json, "group", "");
      NonNullList<Ingredient> nonnulllist = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
      if (nonnulllist.isEmpty()) {
        throw new JsonParseException("No ingredients for component recipe");
      }
      else if (nonnulllist.size() > MAX_INGREDIENTS) {
        throw new JsonParseException("Too many ingredients for component recipe the max is " + MAX_INGREDIENTS);
      }
      else {
        boolean needsMixin = GsonHelper.getAsBoolean(json, "needs_mixin", true);
        String effect = GsonHelper.getAsString(json, "effect");
        ResourceLocation effectResult = ResourceLocation.tryParse(effect);
        ItemStack itemstack;
        if (GsonHelper.isValidNode(json, "result")) {
          itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
          return new ComponentRecipe(recipeId, effectResult, s, itemstack, nonnulllist, needsMixin);
        }
        else {
          return new ComponentRecipe(recipeId, effectResult, s, nonnulllist, needsMixin);
        }
      }
    }

    private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
      NonNullList<Ingredient> nonnulllist = NonNullList.create();
      for (int i = 0; i < ingredientArray.size(); ++i) {
        Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
        if (!ingredient.isEmpty()) {
          nonnulllist.add(ingredient);
        }
      }
      return nonnulllist;
    }

    public ComponentRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
      String s = buffer.readUtf(32767);
      int i = buffer.readVarInt();
      NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
      for (int j = 0; j < nonnulllist.size(); ++j) {
        nonnulllist.set(j, Ingredient.fromNetwork(buffer));
      }
      boolean needsMixin = buffer.readBoolean();
      ResourceLocation effectResult = buffer.readResourceLocation();
      ItemStack itemstack = buffer.readItem();
      return new ComponentRecipe(recipeId, effectResult, s, itemstack, nonnulllist, needsMixin);
    }

    public void toNetwork(FriendlyByteBuf buffer, ComponentRecipe recipe) {
      buffer.writeUtf(recipe.group);
      buffer.writeVarInt(recipe.materials.size());
      for (Ingredient ingredient : recipe.materials) {
        ingredient.toNetwork(buffer);
      }
      buffer.writeBoolean(recipe.needsMixin);
      buffer.writeResourceLocation(recipe.effectResult);
      buffer.writeItem(recipe.recipeOutput);
    }
  }
}
