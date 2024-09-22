package elucent.rootsclassic.compat;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

@ZenRegister
@Name("mods.rootsclassic.Spell")
public class SpellZen implements IRecipeManager<ComponentRecipe> {

  public static final SpellZen INSTANCE = new SpellZen();

  private SpellZen() {}

  /**
   * Invalid spell, names must be one of: rose_bush, dandelion, chorus, nether_wart, peony, sunflower, azure_bluet, allium, lilac, white_tulip, red_tulip, blue_orchid, poppy, poisonous_potato,
   * orange_tulip, pink_tulip, oxeye_daisy, lily_pad, apple, midnight_bloom, flare_orchid, radiant_daisy
   * <p>
   * Starting with rootsclassic (Example: "rootsclassic:rose_bush")
   */
  @Method
  public void setSpellIngredients(ResourceLocation name, IItemStack[] ingredients) {
    if (ingredients.length == 0 || ingredients.length > 4) {
      throw new IllegalArgumentException("Invalid spell ingredients, must be in range [1,4]");
    }
    RecipeHolder<ComponentRecipe> foundHolder = findSpellByName(name);
		ComponentRecipe recipe = foundHolder.value();

    //    CraftTweakerAPI.LOGGER.info("Changing spell ingredients of " + found.getEffectResult());
    NonNullList<Ingredient> ingredientList = NonNullList.create();
    for (IIngredient ingredient : ingredients) {
      ingredientList.add(ingredient.asVanillaIngredient());
    }
    ComponentRecipe newRecipe = new ComponentRecipe(recipe.getEffectResult(), recipe.getGroup(), recipe.getResultItem(),
	    ingredientList, recipe.needsMixin());
    CraftTweakerAPI.apply(new ActionRemoveRecipeByName<>(INSTANCE, foundHolder.id()));
    CraftTweakerAPI.apply(new ActionAddRecipe<>(INSTANCE,  new RecipeHolder<>(foundHolder.id(), newRecipe)));
  }

  @Method
  public void addMortarCrafting(String uniqueName, IItemStack[] items, IItemStack output) {
    if (items.length == 0 || items.length > 4) {
      throw new IllegalArgumentException("Invalid ingredient size, must be in range [1,4]");
    }
    //		CraftTweakerAPI.logInfo("Adding mortar crafting of " + found.getEffectResult());
    NonNullList<Ingredient> ingredients = NonNullList.create();
    for (IItemStack stack : items) {
      ingredients.add(stack.asVanillaIngredient());
    }
    ComponentRecipe craftingRecipe = new ComponentRecipe(
			Const.modLoc("none"), "crafttweaker", output.getInternal(), ingredients, true);
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath("crafttweaker", uniqueName);
    CraftTweakerAPI.apply(new ActionAddRecipe<>(INSTANCE, new RecipeHolder<>(id, craftingRecipe)));
  }

  private RecipeHolder<ComponentRecipe> findSpellByName(ResourceLocation name) {
    RecipeHolder<ComponentRecipe> found = ComponentRegistry.getSpellFromName(CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(), name);
    if (found == null) {
      StringBuilder names = new StringBuilder();
      for (RecipeHolder<ComponentRecipe> recipe : CraftTweakerAPI.getAccessibleElementsProvider().recipeManager().getAllRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get())) {
        if (name.getNamespace().equals(Const.MODID) && !name.getPath().equals("none")) {
          names.append(recipe.value().getEffectResult()).append(", ");
        }
      }
      throw new IllegalArgumentException("Invalid spell [" + name + "], names must be one of: " + names);
    }
    return found;
  }

  @Override
  public RecipeType<ComponentRecipe> getRecipeType() {
    return RootsRecipes.COMPONENT_RECIPE_TYPE.get();
  }
}
