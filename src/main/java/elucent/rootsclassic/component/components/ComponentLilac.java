package elucent.rootsclassic.component.components;

import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentLilac extends ComponentBase {

  Random random = new Random();

  public ComponentLilac() {
    super("lilac", Blocks.DOUBLE_PLANT, 1, 14);
  }

  public boolean growBlockSafe(World world, BlockPos pos, int potency) {
    if (world.getBlockState(pos).getBlock() instanceof IGrowable && random.nextInt(5 - potency) < 2) {
      ((IGrowable) world.getBlockState(pos).getBlock()).grow(world, random, pos, world.getBlockState(pos));
      return true;
    }
    if (world.getBlockState(pos).getBlock() == Blocks.NETHER_WART && random.nextInt(5 - potency) < 2) {
      BlockNetherWart wart = (BlockNetherWart) world.getBlockState(pos).getBlock();
      IBlockState state = world.getBlockState(pos);
      int age = state.getValue(wart.AGE).intValue();
      if (age < 3) {
        state = state.withProperty(wart.AGE, Integer.valueOf(age + 1));
        world.setBlockState(pos, state, 2);
        return true;
      }
    }
    return false;
  }

  //  @Override
  //  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
  //    if (type == EnumCastType.SPELL) {
  //      if (caster instanceof EntityPlayer && !world.isRemote) {
  //        //   BlockPos pos = Util.getRayTrace(world, (EntityPlayer) caster, 4 + 2 * (int) size);
  //        // boolean fullEfficiency = growBlockSafe(world, pos, (int) potency) && growBlockSafe(world, pos.east(), (int) potency) && growBlockSafe(world, pos.west(), (int) potency) && growBlockSafe(world, pos.north(), (int) potency) && growBlockSafe(world, pos.south(), (int) potency);
  //        //        if (fullEfficiency) {
  //        //          if (caster instanceof EntityPlayer) {
  //        //            if (!((EntityPlayer) caster).hasAchievement(RegistryManager.achieveSpellGrowth)) {
  //        //              PlayerManager.addAchievement((EntityPlayer) caster, RegistryManager.achieveSpellGrowth);
  //        //            }
  //        //          }
  //        //        }
  //      }
  //    }
  //  }
}
