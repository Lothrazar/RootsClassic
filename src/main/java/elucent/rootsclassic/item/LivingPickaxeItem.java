package elucent.rootsclassic.item;

import com.lothrazar.library.util.ItemStackUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class LivingPickaxeItem extends PickaxeItem {

  public LivingPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
    super(tier, attackDamageIn, attackSpeedIn, builder);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level levelAccessor, Entity entityIn, int itemSlot, boolean isSelected) {
    ItemStackUtil.randomlyRepair(levelAccessor.random, stack, 80);
  }

  @Override
  public boolean isRepairable(ItemStack stack) {
    return false;
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
