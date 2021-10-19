package elucent.rootsclassic.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public class PestleItem extends Item {

  public PestleItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack getContainerItem(ItemStack stack) {
    return new ItemStack(this, 1);
  }

  @Override
  public boolean hasContainerItem(ItemStack stack) {
    return true;
  }
}
