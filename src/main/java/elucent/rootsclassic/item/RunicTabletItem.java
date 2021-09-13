package elucent.rootsclassic.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class RunicTabletItem extends Item {

  public RunicTabletItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (hand == InteractionHand.MAIN_HAND && world.isClientSide) {
      elucent.rootsclassic.client.screen.TabletScreen.openScreen(player);
    }
    return new InteractionResultHolder<>(InteractionResult.PASS, stack);
  }
}
