package elucent.rootsclassic.item;

import com.lothrazar.library.item.ItemFlib;
import net.minecraft.world.item.ItemStack;

public class PestleItem extends ItemFlib {

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
