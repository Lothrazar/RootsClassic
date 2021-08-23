package elucent.rootsclassic.component;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.components.ComponentAllium;
import elucent.rootsclassic.component.components.ComponentApple;
import elucent.rootsclassic.component.components.ComponentAzureBluet;
import elucent.rootsclassic.component.components.ComponentBlueOrchid;
import elucent.rootsclassic.component.components.ComponentChorus;
import elucent.rootsclassic.component.components.ComponentDandelion;
import elucent.rootsclassic.component.components.ComponentFlareOrchid;
import elucent.rootsclassic.component.components.ComponentLilac;
import elucent.rootsclassic.component.components.ComponentLilyPad;
import elucent.rootsclassic.component.components.ComponentMidnightBloom;
import elucent.rootsclassic.component.components.ComponentNetherWart;
import elucent.rootsclassic.component.components.ComponentOrangeTulip;
import elucent.rootsclassic.component.components.ComponentOxeyeDaisy;
import elucent.rootsclassic.component.components.ComponentPeony;
import elucent.rootsclassic.component.components.ComponentPinkTulip;
import elucent.rootsclassic.component.components.ComponentPoisonousPotato;
import elucent.rootsclassic.component.components.ComponentPoppy;
import elucent.rootsclassic.component.components.ComponentRadiantDaisy;
import elucent.rootsclassic.component.components.ComponentRedTulip;
import elucent.rootsclassic.component.components.ComponentRose;
import elucent.rootsclassic.component.components.ComponentSunflower;
import elucent.rootsclassic.component.components.ComponentWhiteTulip;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentManager {

	public static ArrayList<ComponentBase> components = new ArrayList<>();

	public static void reload() {
		components.clear();
		components.add(new ComponentRose().setPrimaryColor(192, 0, 72).setSecondaryColor(0, 200, 48).setTextColor(TextFormatting.GREEN));
		components.add(new ComponentDandelion().setPrimaryColor(255, 217, 102).setSecondaryColor(240, 159, 10).setTextColor(TextFormatting.YELLOW));
		components.add(new ComponentChorus().setPrimaryColor(95, 57, 95).setSecondaryColor(225, 215, 225).setTextColor(TextFormatting.DARK_PURPLE));
		components.add(new ComponentNetherWart().setPrimaryColor(255, 76, 36).setSecondaryColor(255, 174, 0).setTextColor(TextFormatting.GOLD));
		components.add(new ComponentPeony().setPrimaryColor(255, 102, 178).setSecondaryColor(255, 51, 153).setTextColor(TextFormatting.LIGHT_PURPLE));
		components.add(new ComponentSunflower().setPrimaryColor(255, 255, 128).setSecondaryColor(255, 255, 255).setTextColor(TextFormatting.WHITE));
		components.add(new ComponentLilac().setPrimaryColor(112, 80, 112).setSecondaryColor(0, 112, 24).setTextColor(TextFormatting.GREEN));
		components.add(new ComponentAzureBluet().setPrimaryColor(240, 240, 255).setSecondaryColor(86, 86, 96).setTextColor(TextFormatting.GRAY));
		components.add(new ComponentAllium().setPrimaryColor(255, 202, 255).setSecondaryColor(51, 30, 50).setTextColor(TextFormatting.DARK_PURPLE));
		components.add(new ComponentWhiteTulip().setPrimaryColor(255, 255, 255).setSecondaryColor(136, 252, 255).setTextColor(TextFormatting.AQUA));
		components.add(new ComponentRedTulip().setPrimaryColor(128, 16, 16).setSecondaryColor(128, 16, 64).setTextColor(TextFormatting.DARK_RED));
		components.add(new ComponentPoppy().setPrimaryColor(255, 0, 0).setSecondaryColor(50, 50, 50).setTextColor(TextFormatting.RED));
		components.add(new ComponentBlueOrchid().setPrimaryColor(68, 39, 26).setSecondaryColor(162, 153, 150).setTextColor(TextFormatting.GRAY));
		components.add(new ComponentPoisonousPotato().setPrimaryColor(172, 255, 81).setSecondaryColor(81, 181, 255).setTextColor(TextFormatting.YELLOW));
		components.add(new ComponentOrangeTulip().setPrimaryColor(255, 181, 70).setSecondaryColor(255, 255, 0).setTextColor(TextFormatting.GOLD));
		components.add(new ComponentPinkTulip().setPrimaryColor(255, 0, 51).setSecondaryColor(255, 0, 249).setTextColor(TextFormatting.LIGHT_PURPLE));
		components.add(new ComponentOxeyeDaisy().setPrimaryColor(255, 254, 206).setSecondaryColor(52, 0, 74).setTextColor(TextFormatting.WHITE));
		components.add(new ComponentLilyPad().setPrimaryColor(36, 255, 167).setSecondaryColor(8, 0, 255).setTextColor(TextFormatting.BLUE));
		components.add(new ComponentApple().setPrimaryColor(255, 43, 89).setSecondaryColor(255, 43, 43).setTextColor(TextFormatting.DARK_RED));
		components.add(new ComponentMidnightBloom().setPrimaryColor(12, 6, 36).setSecondaryColor(18, 18, 18).setTextColor(TextFormatting.DARK_PURPLE));
		components.add(new ComponentFlareOrchid().setPrimaryColor(255, 60, 18).setSecondaryColor(255, 60, 18).setTextColor(TextFormatting.RED));
		components.add(new ComponentRadiantDaisy().setPrimaryColor(255, 255, 255).setSecondaryColor(255, 255, 255).setTextColor(TextFormatting.WHITE));
	}

	/* //TODO: reject invalid stuff from the bowl??!??! the mortar??
	 *
	 * public static boolean isValidEffectItem(ItemStack stack) { for (int i = 0; i < components.size(); i++) { if (components.get(i).getItem() != null && stack != null) { if
	 * (components.get(i).getItem().getItem() == stack.getItem() && components.get(i).getItem().getMetadata() == stack.getMetadata()) { return true; } } } return false; } */
	public static ComponentRecipe getSpellFromName(RecipeManager mgr, ResourceLocation name) {
		if(name.getNamespace().equals(Const.MODID) && name.getPath().equals("none")) {
			return null;
		}
		List<ComponentRecipe> recipes = mgr.getRecipesForType(RootsRecipes.COMPONENT_RECIPE_TYPE);
		for (ComponentRecipe recipe : recipes) {
			if (recipe.getEffectResult().equals(name)) {
				return recipe;
			}
		}
		return null;
	}

	public static ComponentRecipe getRecipeFromInput(World world, IInventory inventory) {
		Optional<ComponentRecipe> recipe = world.getRecipeManager().getRecipe(RootsRecipes.COMPONENT_RECIPE_TYPE, inventory, world);

		return recipe.orElse(null);
	}

	public static ComponentBase getComponentFromName(ResourceLocation name) {
		for (ComponentBase c : components) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
}
