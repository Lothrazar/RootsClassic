package elucent.rootsclassic.item;

import elucent.rootsclassic.Roots;
import elucent.rootsclassic.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGrowthSalve extends Item {

  public ItemGrowthSalve() {
    super();
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    for (int i = 0; i < 40; i++) {
      double velX = (player.getLookVec().x * 0.75) + 0.5 * (itemRand.nextDouble() - 0.5);
      double velY = (player.getLookVec().y * 0.75) + 0.5 * (itemRand.nextDouble() - 0.5);
      double velZ = (player.getLookVec().z * 0.75) + 0.5 * (itemRand.nextDouble() - 0.5);
      Roots.proxy.spawnParticleMagicFX(world, player.posX + 0.5 * player.getLookVec().x, player.posY + 1.5 + 0.5 * player.getLookVec().y, player.posZ + 0.5 * player.getLookVec().z, velX, velY, velZ, 39, 232, 55);
    }
    BlockPos pos = Util.getRayTrace(world, player, 4);
    for (int i = -2; i < 3; i++) {
      for (int j = -2; j < 3; j++) {
        BlockPos temp = pos.add(i, 0, j);
        if (world.getBlockState(temp).getBlock() == Blocks.DIRT) {
          if (i == 0 && j == 0) {
            world.setBlockState(temp, Blocks.GRASS.getDefaultState());
          }
          else if (itemRand.nextInt(Math.abs(i) + Math.abs(j)) == 0) {
            world.setBlockState(temp, Blocks.GRASS.getDefaultState());
          }
        }
      }
    }
    if (!player.capabilities.isCreativeMode) {
      stack.shrink(1);
    }
    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
  }

}
