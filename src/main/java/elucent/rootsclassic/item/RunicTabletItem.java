package elucent.rootsclassic.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RunicTabletItem extends Item {

  public RunicTabletItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (hand == Hand.MAIN_HAND && world.isRemote) {
      elucent.rootsclassic.client.screen.TabletScreen.openScreen(player);
    }
    return new ActionResult<>(ActionResultType.PASS, stack);
  }
}
