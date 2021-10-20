package elucent.rootsclassic.compat;

//@ZenRegister
//@Name("mods.rootsclassic.Spell")
public class SpellZen {//implements IRecipeManager {
//
//  @Global("spell")
//  public static final SpellZen INSTANCE = new SpellZen();
//
//  private SpellZen() {}
//
//  /**
//   * Invalid spell, names must be one of: rose_bush, dandelion, chorus, nether_wart, peony, sunflower, azure_bluet, allium, lilac, white_tulip, red_tulip, blue_orchid, poppy, poisonous_potato,
//   * orange_tulip, pink_tulip, oxeye_daisy, lily_pad, apple, midnight_bloom, flare_orchid, radiant_daisy
//   *
//   * Starting with rootsclassic (Example: "rootsclassic:rose_bush")
//   */
//  @Method
//  public void setSpellIngredients(ResourceLocation name, IItemStack[] ingredients) {
//    if (ingredients.length == 0 || ingredients.length > 4) {
//      throw new IllegalArgumentException("Invalid spell ingredients, must be in range [1,4]");
//    }
//    ComponentRecipe found = findSpellByName(name);
//    CraftTweakerAPI.logInfo("Changing spell ingredients of " + found.getEffectResult());
//    NonNullList<Ingredient> ingredientList = NonNullList.create();
//    for (IIngredient ingredient : ingredients) {
//      ingredientList.add(ingredient.asVanillaIngredient());
//    }
//    ComponentRecipe newRecipe = new ComponentRecipe(found.getId(), found.getEffectResult(), found.getGroup(), found.getResultItem(), ingredientList, found.needsMixin());
//    CraftTweakerAPI.apply(new ActionRemoveRecipeByName(INSTANCE, found.getId()));
//    CraftTweakerAPI.apply(new ActionAddRecipe(INSTANCE, newRecipe));
//  }
//
//  @Method
//  public void addMortarCrafting(String uniqueName, IItemStack[] items, IItemStack output) {
//    if (items.length == 0 || items.length > 4) {
//      throw new IllegalArgumentException("Invalid ingredient size, must be in range [1,4]");
//    }
//    //		CraftTweakerAPI.logInfo("Adding mortar crafting of " + found.getEffectResult());
//    NonNullList<Ingredient> ingredients = NonNullList.create();
//    for (IItemStack stack : items) {
//      ingredients.add(stack.asVanillaIngredient());
//    }
//    ComponentRecipe craftingRecipe = new ComponentRecipe(new ResourceLocation("crafttweaker", uniqueName),
//        new ResourceLocation(Const.MODID, "none"), "crafttweaker", output.getInternal(), ingredients, true);
//    CraftTweakerAPI.apply(new ActionAddRecipe(INSTANCE, craftingRecipe));
//  }
//
//  private ComponentRecipe findSpellByName(ResourceLocation name) {
//    ComponentRecipe found = ComponentManager.getSpellFromName(CTCraftingTableManager.recipeManager, name);
//    if (found == null) {
//      StringBuilder names = new StringBuilder();
//      for (ComponentRecipe recipe : CTCraftingTableManager.recipeManager.getAllRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE)) {
//        if (name.getNamespace().equals(Const.MODID) && !name.getPath().equals("none")) {
//          names.append(recipe.getEffectResult()).append(", ");
//        }
//      }
//      throw new IllegalArgumentException("Invalid spell [" + name + "], names must be one of: " + names);
//    }
//    return found;
//  }
//
//  @Override
//  public RecipeType<?> getRecipeType() {
//    return RootsRecipes.COMPONENT_RECIPE_TYPE;
//  }
}
