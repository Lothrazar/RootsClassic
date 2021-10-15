package elucent.rootsclassic.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elucent.rootsclassic.util.RootsUtil;

public class LivingHoeItem extends HoeItem {

  public LivingHoeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
    super(tier, attackDamageIn, attackSpeedIn, builderIn);
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    RootsUtil.randomlyRepair(random, stack);
  }

  @Override
  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
