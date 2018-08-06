package elucent.rootsclassic.item;

import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLivingPickaxe extends ItemPickaxe {

  public ItemLivingPickaxe() {
    super(RegistryManager.livingMaterial);
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    Util.randomlyRepair(world.rand, stack);
  }
}
