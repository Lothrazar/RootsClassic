package elucent.rootsclassic.item;

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

  // TODO: config
  private static final double BLOCK_DESTROY_ODDS = 0.3;

  public ItemDruidKnife() {
    super();
    this.setMaxDamage(64);
    this.setMaxStackSize(1);
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.isRemote) {
      return EnumActionResult.SUCCESS;
    }
    ItemStack stack = playerIn.getHeldItem(hand);
    Item itemToDrop = null;
    if (worldIn.getBlockState(pos).getBlock() == Blocks.LOG) {
      switch (worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos))) {
        case 0:
        case 4:
        case 8:
          itemToDrop = RegistryManager.oakTreeBark;
        break;
        case 1:
        case 5:
        case 9:
          itemToDrop = RegistryManager.spruceTreeBark;
        break;
        case 2:
        case 6:
        case 10:
          itemToDrop = RegistryManager.birchTreeBark;
        break;
        case 3:
        case 7:
        case 11:
          itemToDrop = RegistryManager.jungleTreeBark;
        break;
      }
    }
    else if (worldIn.getBlockState(pos).getBlock() == Blocks.LOG2) {
      switch (worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos))) {
        case 0:
        case 4:
        case 8:
          itemToDrop = RegistryManager.acaciaTreeBark;
        break;
        case 1:
        case 5:
        case 9:
          itemToDrop = RegistryManager.darkOakTreeBark;
        break;
      }
    }
    if (itemToDrop != null) {
      playerIn.entityDropItem(new ItemStack(itemToDrop), 1.f);
      stack.damageItem(1, playerIn);
      if (worldIn.rand.nextDouble() < BLOCK_DESTROY_ODDS) {
        worldIn.destroyBlock(pos, false);
      }
      return EnumActionResult.SUCCESS;
    }
    return EnumActionResult.PASS;
  }
}
