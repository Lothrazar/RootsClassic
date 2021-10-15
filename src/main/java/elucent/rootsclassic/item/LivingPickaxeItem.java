package elucent.rootsclassic.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.world.World;
import elucent.rootsclassic.util.RootsUtil;

public class LivingPickaxeItem extends PickaxeItem {

  public LivingPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
    super(tier, attackDamageIn, attackSpeedIn, builder);
  }

  @Override
  public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    RootsUtil.randomlyRepair(random, stack);
  }

  @Override
  public boolean isRepairable(ItemStack stack) {
    return false;
  }

  @Override
  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
