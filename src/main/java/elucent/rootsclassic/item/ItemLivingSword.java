package elucent.rootsclassic.item;

import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLivingSword extends ItemSword {

  Random random = new Random();

  public ItemLivingSword() {
    super(RegistryManager.livingMaterial);

    setCreativeTab(Roots.tab);
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    if (random.nextInt(80) == 0) {
      if (stack.getItemDamage() > 0) {
        stack.setItemDamage(stack.getItemDamage() - 1);
      }
    }
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }
}
