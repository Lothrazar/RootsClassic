package elucent.rootsclassic.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PestleItem extends Item {

  public PestleItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack getCraftingRemainingItem(ItemStack stack) {
    return new ItemStack(this, 1);
  }

  @Override
  public boolean hasCraftingRemainingItem(ItemStack stack) {
    return true;
  }
}
