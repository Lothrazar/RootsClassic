package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;

import java.util.List;

public class RitualGrow extends RitualBase {

  public RitualGrow(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(Level world, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    if(!world.isClientSide) {
      for (int i = -17; i < 18; i++) {
        for (int j = -4; j < 5; j++) {
          for (int k = -17; k < 18; k++) {
            if (world.getBlockState(pos.offset(i, j, k)).getBlock() instanceof BonemealableBlock && world.random.nextInt(12) == 0) {
              ((BonemealableBlock) world.getBlockState(pos.offset(i, j, k)).getBlock()).performBonemeal((ServerLevel) world, world.random, pos.offset(i, j, k), world.getBlockState(pos.offset(i, j, k)));
            }
          }
        }
      }
    }
  }
}
