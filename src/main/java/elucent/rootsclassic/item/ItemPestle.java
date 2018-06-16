package elucent.rootsclassic.item;

import elucent.rootsclassic.Roots;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPestle extends Item {

  public ItemPestle() {
    super();

    setCreativeTab(Roots.tab);
  }

  @Override
  public ItemStack getContainerItem(ItemStack stack) {
    return new ItemStack(this, 1);
  }

  @Override
  public boolean hasContainerItem() {
    return true;
  }

  @Override
  public int getItemStackLimit() {
    return 1;
  }
}
