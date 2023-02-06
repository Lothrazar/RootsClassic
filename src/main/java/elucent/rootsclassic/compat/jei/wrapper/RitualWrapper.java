package elucent.rootsclassic.compat.jei.wrapper;

import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class RitualWrapper implements IRecipeCategoryExtension {
	private final RitualBase ritual;

	public List<Block> getBlocks() {
		return ritual.getBlocks();
	}

	public List<BlockPos> getPositionsRelative() {
		return ritual.getPositionsRelative();
	}

	public List<ItemStack> getIngredients() {
		return ritual.getIngredients();
	}

	public List<ItemStack> getIncenses() {
		return ritual.getIncenses();
	}

	public ItemStack getResult() {
		if (ritual instanceof RitualCrafting ritualCrafting) {
			return ritualCrafting.result;
		}
		return ItemStack.EMPTY;
	}

	public RitualWrapper(RitualBase ritual) {
		this.ritual = ritual;
	}
}
