package elucent.rootsclassic.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPestle extends Item {

  public ItemPestle() {
    super();
    this.setMaxStackSize(1);
  }

  @Override
  public ItemStack getContainerItem(ItemStack stack) {
    return new ItemStack(this, 1);
  }

  @Override
  public boolean hasContainerItem() {
    return true;
  }
}
