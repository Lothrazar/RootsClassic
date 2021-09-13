package elucent.rootsclassic.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.util.RootsUtil;

public class LivingPickaxeItem extends PickaxeItem {

  public LivingPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
    super(tier, attackDamageIn, attackSpeedIn, builder);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    RootsUtil.randomlyRepair(worldIn.random, stack);
  }

  @Override
  public boolean isRepairable(ItemStack stack) {
    return false;
  }
}
