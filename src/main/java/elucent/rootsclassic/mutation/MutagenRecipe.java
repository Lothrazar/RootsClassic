package elucent.rootsclassic.mutation;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class MutagenRecipe {

	final ArrayList<ItemStack> inputs = new ArrayList<>();
	final ResourceLocation name;
	final BlockState plantBlock;
	public final BlockState result;

	public MutagenRecipe(ResourceLocation name, BlockState state, BlockState resultState) {
		this.name = name;
		this.plantBlock = state;
		this.result = resultState;
	}

	public void onCrafted(Level levelAccessor, BlockPos pos, Player player) {
		//Unused?
	}

	public void addIngredient(ItemStack stack) {
		inputs.add(stack);
	}

	public boolean matches(List<ItemStack> items, Level levelAccessor, BlockPos pos, Player player) {
		if (levelAccessor.getBlockState(pos).getBlock() == plantBlock.getBlock()) {
			ArrayList<ItemStack> tempItems = new ArrayList<>(items);
			for (ItemStack input : inputs) {
				boolean endIteration = false;
				for (int j = 0; j < tempItems.size() && !endIteration; j++) {
					if (input.sameItem(tempItems.get(j))) {
						tempItems.remove(j);
						endIteration = true;
					}
				}
			}
			return tempItems.size() == 0;
		}
		return false;
	}
}