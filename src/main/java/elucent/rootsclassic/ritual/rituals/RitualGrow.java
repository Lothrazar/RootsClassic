package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;

public class RitualGrow extends SimpleRitualEffect {

  @Override
  public void doEffect(Level level, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    if (!level.isClientSide()) {
      for (int i = -17; i < 18; i++) {
        for (int j = -4; j < 5; j++) {
          for (int k = -17; k < 18; k++) {
            if (level.getBlockState(pos.offset(i, j, k)).getBlock() instanceof BonemealableBlock && level.getRandom().nextInt(12) == 0) {
              ((BonemealableBlock) level.getBlockState(pos.offset(i, j, k)).getBlock()).performBonemeal((ServerLevel) level, level.getRandom(), pos.offset(i, j, k), level.getBlockState(pos.offset(i, j, k)));
            }
          }
        }
      }
    }
  }
}
