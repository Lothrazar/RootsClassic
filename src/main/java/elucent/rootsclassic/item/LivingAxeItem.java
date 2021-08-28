package elucent.rootsclassic.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elucent.rootsclassic.util.RootsUtil;

public class LivingAxeItem extends AxeItem {

  public LivingAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builder) {
    super(tier, attackDamageIn, attackSpeedIn, builder);
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    RootsUtil.randomlyRepair(random, stack);
  }
}
