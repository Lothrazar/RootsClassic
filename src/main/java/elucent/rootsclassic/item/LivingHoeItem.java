package elucent.rootsclassic.item;

import com.lothrazar.library.util.ItemStackUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class LivingHoeItem extends HoeItem {

  public LivingHoeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
    super(tier, attackDamageIn, attackSpeedIn, builderIn);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level levelAccessor, Entity entity, int slot, boolean selected) {
    ItemStackUtil.randomlyRepair(levelAccessor.random, stack, 80);
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
