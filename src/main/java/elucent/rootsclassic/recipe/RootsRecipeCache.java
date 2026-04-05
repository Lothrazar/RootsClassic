package elucent.rootsclassic.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class RootsRecipeCache {
  public static final List<RecipeHolder<ComponentRecipe>> componentRecipes = new ArrayList<>();
  public static final List<RecipeHolder<RitualRecipe>> ritualRecipes = new ArrayList<>();

  public static List<RecipeHolder<ComponentRecipe>> getComponentRecipes() {
    return ImmutableList.copyOf(componentRecipes);
  }

  public static List<RecipeHolder<RitualRecipe>> getRitualRecipes() {
    return ImmutableList.copyOf(ritualRecipes);
  }

  public static void clear() {
    componentRecipes.clear();
    ritualRecipes.clear();
  }
}
