package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualGrow extends RitualBase {

  Random random = new Random();

  public RitualGrow(String name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    for (int i = -17; i < 18; i++) {
      for (int j = -4; j < 5; j++) {
        for (int k = -17; k < 18; k++) {
          if (world.getBlockState(pos.add(i, j, k)).getBlock() instanceof IGrowable && random.nextInt(12) == 0) {
            ((IGrowable) world.getBlockState(pos.add(i, j, k)).getBlock()).grow(world, random, pos.add(i, j, k), world.getBlockState(pos.add(i, j, k)));
          }
        }
      }
    }
  }
}
