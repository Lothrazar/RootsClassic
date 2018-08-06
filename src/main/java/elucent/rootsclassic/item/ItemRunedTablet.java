package elucent.rootsclassic.item;

import elucent.rootsclassic.Roots;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemRunedTablet extends Item {

  public ItemRunedTablet() {
    super();
    this.setMaxStackSize(1);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (hand == EnumHand.MAIN_HAND) {
      player.openGui(Roots.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
    }
    return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
  }
}
