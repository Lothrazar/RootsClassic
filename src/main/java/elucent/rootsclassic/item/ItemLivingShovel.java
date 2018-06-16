package elucent.rootsclassic.item;

import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLivingShovel extends ItemSpade {

  Random random = new Random();

  public ItemLivingShovel() {
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


}
