package elucent.rootsclassic.item;

import elucent.rootsclassic.RegistryManager;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
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

  public static double BLOCK_DESTROY_ODDS = 0.3;

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
    IBlockState blockState = worldIn.getBlockState(pos);

    BlockPlanks.EnumType type = null;
    if (blockState.getBlock() == Blocks.LOG) {
      type = blockState.getValue(BlockOldLog.VARIANT);
    }
    else if (blockState.getBlock() == Blocks.LOG2) {
      type = blockState.getValue(BlockNewLog.VARIANT);
    }
    if (type == null) {
      return EnumActionResult.SUCCESS;
    }
    //its a log so it has a type  
    switch (type) {
      case ACACIA:
        itemToDrop = RegistryManager.acaciaTreeBark;
      break;
      case BIRCH:
        itemToDrop = RegistryManager.birchTreeBark;
      break;
      case DARK_OAK:
        itemToDrop = RegistryManager.darkOakTreeBark;
      break;
      case JUNGLE:
        itemToDrop = RegistryManager.jungleTreeBark;
      break;
      case OAK:
        itemToDrop = RegistryManager.oakTreeBark;
      break;
      case SPRUCE:
        itemToDrop = RegistryManager.spruceTreeBark;
      break;
      default:
      break;
    }

    if (itemToDrop != null) {
      playerIn.entityDropItem(new ItemStack(itemToDrop), 1.f);
      stack.damageItem(1, playerIn);
      if (worldIn.rand.nextDouble() < BLOCK_DESTROY_ODDS) {
        worldIn.destroyBlock(pos, false);
      }
    }
    return EnumActionResult.SUCCESS;
  }
}
