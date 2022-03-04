package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.recipe.ComponentRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class RootsRecipeTypes {
	public static final RecipeType<ComponentRecipe> COMPONENT_RECIPE_TYPE = RecipeType.register(new ResourceLocation(Const.MODID, "component").toString());

	public static void init() {
		//For initializing the static final fields
	}
}
