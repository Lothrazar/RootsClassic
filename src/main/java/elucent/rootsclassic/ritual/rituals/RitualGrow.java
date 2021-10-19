package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import net.minecraft.block.IGrowable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualGrow extends RitualBase {

  public RitualGrow(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    if(!world.isClientSide) {
      for (int i = -17; i < 18; i++) {
        for (int j = -4; j < 5; j++) {
          for (int k = -17; k < 18; k++) {
            if (world.getBlockState(pos.offset(i, j, k)).getBlock() instanceof IGrowable && world.random.nextInt(12) == 0) {
              ((IGrowable) world.getBlockState(pos.offset(i, j, k)).getBlock()).performBonemeal((ServerWorld) world, world.random, pos.offset(i, j, k), world.getBlockState(pos.offset(i, j, k)));
            }
          }
        }
      }
    }
  }
}
