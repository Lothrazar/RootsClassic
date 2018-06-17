package elucent.rootsclassic.item;

import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLivingHoe extends ItemHoe {

  Random random = new Random();

  public ItemLivingHoe() {
    super(RegistryManager.livingMaterial);

  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    if (random.nextInt(80) == 0) {
      if (stack.getItemDamage() > 0) {
        stack.setItemDamage(stack.getItemDamage() - 1);
      }
    }
  }

}
