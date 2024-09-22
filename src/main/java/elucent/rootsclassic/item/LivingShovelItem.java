package elucent.rootsclassic.item;

import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class LivingShovelItem extends ShovelItem {

  public LivingShovelItem(Tier tier, float attackDamageIn, float attackSpeedIn, Item.Properties properties) {
	  super(tier, properties.attributes(ShovelItem.createAttributes(tier, attackDamageIn, attackSpeedIn)));
  }

  @Override
  public void inventoryTick(ItemStack stack, Level levelAccessor, Entity entity, int slot, boolean selected) {
	  RootsUtil.randomlyRepair(levelAccessor.random, stack);
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
