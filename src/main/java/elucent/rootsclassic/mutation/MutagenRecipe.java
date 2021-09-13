package elucent.rootsclassic.mutation;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class MutagenRecipe {

  ArrayList<ItemStack> inputs = new ArrayList<>();
  ResourceLocation name;
  BlockState plantBlock;
  public BlockState result;

  public MutagenRecipe(ResourceLocation name, BlockState state, BlockState resultState) {
    this.name = name;
    this.plantBlock = state;
    this.result = resultState;
  }

  public void onCrafted(Level world, BlockPos pos, Player player) {
    //
  }

  public MutagenRecipe addIngredient(ItemStack stack) {
    inputs.add(stack);
    return this;
  }

  public boolean matches(List<ItemStack> items, Level world, BlockPos pos, Player player) {
    if (world.getBlockState(pos).getBlock() == plantBlock.getBlock()) {
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