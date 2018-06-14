package elucent.rootsclassic.component.components;

import java.util.Random;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentAzureBluet extends ComponentBase {

  Random random = new Random();

  public ComponentAzureBluet() {
    super("azurebluet", "Shatter", Blocks.RED_FLOWER, 6);
  }

  public void destroyBlockSafe(World world, BlockPos pos, int potency) {
    if (world.getBlockState(pos).getBlock().getHarvestLevel(world.getBlockState(pos)) <= 2 + potency && world.getBlockState(pos).getBlock().getBlockHardness(world.getBlockState(pos), world, pos) != -1) {
      world.destroyBlock(pos, true);
    }
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof EntityPlayer && !world.isRemote) {
        BlockPos pos = Util.getRayTrace(world, (EntityPlayer) caster, 4 + 2 * (int) size);
        destroyBlockSafe(world, pos, (int) potency);
        if (random.nextBoolean()) {
          destroyBlockSafe(world, pos.up(), (int) potency);
        }
        if (random.nextBoolean()) {
          destroyBlockSafe(world, pos.down(), (int) potency);
        }
        if (random.nextBoolean()) {
          destroyBlockSafe(world, pos.east(), (int) potency);
        }
        if (random.nextBoolean()) {
          destroyBlockSafe(world, pos.west(), (int) potency);
        }
        if (random.nextBoolean()) {
          destroyBlockSafe(world, pos.north(), (int) potency);
        }
        if (random.nextBoolean()) {
          destroyBlockSafe(world, pos.south(), (int) potency);
        }
      }
    }
  }
}
