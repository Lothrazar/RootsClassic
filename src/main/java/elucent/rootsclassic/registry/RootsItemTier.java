package elucent.rootsclassic.registry;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class RootsItemTier {

	public static final Tier LIVING = new SimpleTier(
		RootsTags.INCORRECT_FOR_LIVING_TOOL, 192, 6.0f, 2.0f, 18,
		() -> Ingredient.EMPTY);

	public static final Tier ENGRAVED = new SimpleTier(
		RootsTags.INCORRECT_FOR_ENGRAVED_TOOL, 1050, 5F, 8.0F, 5,
		() -> Ingredient.EMPTY);
}