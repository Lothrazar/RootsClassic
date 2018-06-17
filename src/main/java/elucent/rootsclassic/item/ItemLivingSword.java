package elucent.rootsclassic.item;

import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemLivingSword extends ItemSword {

  public ItemLivingSword() {
    super(RegistryManager.livingMaterial);
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    Util.randomlyRepair(world.rand, stack);
  }
}
