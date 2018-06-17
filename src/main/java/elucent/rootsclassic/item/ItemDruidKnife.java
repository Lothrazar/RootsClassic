package elucent.rootsclassic.item;

import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDruidKnife extends Item {

  Random rnd = new Random();

  public ItemDruidKnife() {
    super();
    this.setMaxDamage(64);
    this.setMaxStackSize(1);
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(hand);
    if (worldIn.getBlockState(pos).getBlock() == Blocks.LOG) {
      if (!worldIn.isRemote) {
        switch (worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos))) {
          case 0:
          case 4:
          case 8:
            playerIn.entityDropItem(new ItemStack(RegistryManager.oakTreeBark), 1.f);
            stack.damageItem(1, playerIn);
            if (rnd.nextDouble() < 0.3) {
              worldIn.destroyBlock(pos, false);
            }
          break;
          case 1:
          case 5:
          case 9:
            playerIn.entityDropItem(new ItemStack(RegistryManager.spruceTreeBark), 1.f);
            stack.damageItem(1, playerIn);
            if (rnd.nextDouble() < 0.3) {
              worldIn.destroyBlock(pos, false);
            }
          break;
          case 2:
          case 6:
          case 10:
            playerIn.entityDropItem(new ItemStack(RegistryManager.birchTreeBark), 1.f);
            stack.damageItem(1, playerIn);
            if (rnd.nextDouble() < 0.3) {
              worldIn.destroyBlock(pos, false);
            }
          break;
          case 3:
          case 7:
          case 11:
            playerIn.entityDropItem(new ItemStack(RegistryManager.jungleTreeBark), 1.f);
            stack.damageItem(1, playerIn);
            if (rnd.nextDouble() < 0.3) {
              worldIn.destroyBlock(pos, false);
            }
          break;
        }
      }
    }
    else if (worldIn.getBlockState(pos).getBlock() == Blocks.LOG2) {
      if (!worldIn.isRemote) {
        switch (worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos))) {
          case 0:
          case 4:
          case 8:
            playerIn.entityDropItem(new ItemStack(RegistryManager.acaciaTreeBark), 1.f);
            stack.damageItem(1, playerIn);
            if (rnd.nextDouble() < 0.3) {
              worldIn.destroyBlock(pos, false);
            }
          break;
          case 1:
          case 5:
          case 9:
            playerIn.entityDropItem(new ItemStack(RegistryManager.darkOakTreeBark), 1.f);
            stack.damageItem(1, playerIn);
            if (rnd.nextDouble() < 0.3) {
              worldIn.destroyBlock(pos, false);
            }
          break;
        }
      }
    }
    return EnumActionResult.SUCCESS;
  }


}
