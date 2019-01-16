package elucent.rootsclassic.item;

import elucent.rootsclassic.Roots;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemGrowthSalve extends Item {

  public ItemGrowthSalve() {
    super();
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    spawnGrowthParticle(world, player);
    boolean anySuccess = applyGrowthHere(world, pos);
    if (anySuccess && !player.capabilities.isCreativeMode) {
      player.getHeldItem(hand).shrink(1);
    }
    return EnumActionResult.PASS;
  }

  public static boolean applyGrowthHere(World world, BlockPos pos) {
    IBlockState iblockstate = world.getBlockState(pos);
    if (iblockstate.getBlock() == Blocks.DIRT) {
      world.setBlockState(pos, Blocks.GRASS.getDefaultState());
      return true;
    }
    else if (iblockstate.getBlock() == Blocks.WATER &&
        world.isAirBlock(pos.up())) {
      world.setBlockState(pos.up(), Blocks.WATERLILY.getDefaultState());
    }
    else {
      if (iblockstate.getBlock() instanceof IGrowable) {
        IGrowable igrowable = (IGrowable) iblockstate.getBlock();
        if (igrowable.canGrow(world, pos, iblockstate, world.isRemote)) {
          if (!world.isRemote) {
            if (igrowable.canUseBonemeal(world, world.rand, pos, iblockstate)) {
              igrowable.grow(world, world.rand, pos, iblockstate);
            }
          }
          return true;
        }
      }
    }
    return false;
  }

  public static void spawnGrowthParticle(World world, EntityPlayer player) {
    Vec3d lookVec = player.getLookVec();
    for (int i = 0; i < 40; i++) {
      double velX = (lookVec.x * 0.75) + 0.5 * (itemRand.nextDouble() - 0.5);
      double velY = (lookVec.y * 0.75) + 0.5 * (itemRand.nextDouble() - 0.5);
      double velZ = (lookVec.z * 0.75) + 0.5 * (itemRand.nextDouble() - 0.5);
      Roots.proxy.spawnParticleMagicFX(world, player.posX + 0.5 * lookVec.x, player.posY + 1.5 + 0.5 * lookVec.y, player.posZ + 0.5 * lookVec.z, velX, velY, velZ, 39, 232, 55);
    }
  }
}
