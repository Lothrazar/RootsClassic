package elucent.rootsclassic.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.item.powder.SpellPowderItem;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.List;

public class ComponentRecipe implements Recipe<RecipeInput> {
  private static final int MAX_INGREDIENTS = 4;
  private static final MapCodec<ComponentRecipe> CODEC = RecordCodecBuilder.mapCodec(
    instance -> instance.group(
        Identifier.CODEC.fieldOf("effect").forGetter(recipe -> recipe.effectResult),
        Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
        ItemStackTemplate.CODEC.optionalFieldOf("result", new ItemStackTemplate(RootsRegistry.SPELL_POWDER)).forGetter(recipe -> recipe.recipeOutput),
        Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, MAX_INGREDIENTS)).fieldOf("ingredients").forGetter(o -> o.materials),
        Codec.BOOL.optionalFieldOf("need_mixin", true).forGetter(recipe -> recipe.needsMixin)
      )
      .apply(instance, ComponentRecipe::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, ComponentRecipe> STREAM_CODEC = StreamCodec.composite(
    Identifier.STREAM_CODEC,
    o -> o.effectResult,
    ByteBufCodecs.STRING_UTF8,
    o -> o.group,
    ItemStackTemplate.STREAM_CODEC,
    o -> o.recipeOutput,
    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
    o -> o.materials,
    ByteBufCodecs.BOOL,
    o -> o.needsMixin,
    ComponentRecipe::new
  );
  public static final RecipeSerializer<ComponentRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

  private final Identifier effectResult;
  private final String group;
  private final ItemStackTemplate recipeOutput;
  private final List<Ingredient> materials;
  private final boolean needsMixin;

  public ComponentRecipe(Identifier effect, String group, ItemStackTemplate result, List<Ingredient> materials, boolean needsMixin) {
    this.effectResult = effect;
    this.group = group;
    this.recipeOutput = result;
    this.materials = materials;
    this.needsMixin = needsMixin;
  }

	public Identifier getEffectResult() {
    return effectResult;
  }

  @Override
  public RecipeSerializer<ComponentRecipe> getSerializer() {
    return RootsRecipes.COMPONENT_SERIALIZER.get();
  }

  @Override
  public ItemStack assemble(RecipeInput inventory) {
    ItemStack outputStack = recipeOutput.create();
    if (outputStack.getItem() instanceof SpellPowderItem) {
      SpellPowderItem.createData(outputStack, this.getEffectResult(), inventory);
    }
    return outputStack;
  }

  @Override
  public RecipeType<ComponentRecipe> getType() {
    return RootsRecipes.COMPONENT_RECIPE_TYPE.get();
  }

  public List<Ingredient> getIngredients() {
    return materials;
  }

  @Override
  public PlacementInfo placementInfo() {
    return PlacementInfo.NOT_PLACEABLE;
  }

  @Override
  public RecipeBookCategory recipeBookCategory() {
    return RecipeBookCategories.CRAFTING_MISC;
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
  public boolean showNotification() {
    return false;
  }

  @Override
  public String group() {
    return group;
  }

  public boolean needsMixin() {
    return needsMixin;
  }

  @Override
  public boolean matches(RecipeInput recipeInput, Level level) {
    java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
    int i = 0;
    for (int j = 0; j < recipeInput.size(); j++) {
      ItemStack stack = recipeInput.getItem(j);
      if (!stack.isEmpty() && !isSupplementItem(stack, level.registryAccess())) {
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
    if (recipeOutput.create().getItem() instanceof SpellPowderItem) {
      return stack.getItem() == RootsRegistry.OLD_ROOT.get() ||
          stack.getItem() == RootsRegistry.VERDANT_SPRIG.get() ||
          stack.getItem() == RootsRegistry.INFERNAL_BULB.get() ||
          stack.getItem() == RootsRegistry.DRAGONS_EYE.get() ||
          stack.is(RootsTags.POTENCY) ||
          stack.is(RootsTags.EFFICIENCY) ||
          stack.is(RootsTags.SIZE);
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
      if (stack.is(RootsTags.POTENCY)) {
        count++;
      }
      else if (stack.is(RootsTags.EFFICIENCY)) {
        count++;
      }
      else if (stack.is(RootsTags.SIZE)) {
        count++;
      }
    }
    return count;
  }
}
