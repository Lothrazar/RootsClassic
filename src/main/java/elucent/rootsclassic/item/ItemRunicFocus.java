package elucent.rootsclassic.item;

import java.util.Random;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRunicFocus extends Item implements IManaRelatedItem {

  Random random = new Random();

  public ItemRunicFocus() {
    super();
  }

  @Override
  public int getItemStackLimit() {
    return 1;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    if (stack.getItemDamage() == 0) {
      return "runicfocus_0";
    }
    if (stack.getItemDamage() == 1) {
      return "runicfocus_1";
    }
    return "runicfocus_0";
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "_0", "inventory"));
    ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "_1", "inventory"));
  }
}
