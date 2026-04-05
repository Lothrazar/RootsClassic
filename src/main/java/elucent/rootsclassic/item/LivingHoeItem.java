package elucent.rootsclassic.item;

import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.Nullable;

public class LivingHoeItem extends HoeItem {

  public LivingHoeItem(ToolMaterial material, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
	  super(material, attackDamageIn, attackSpeedIn, properties.setNoCombineRepair());
  }

  @Override
  public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
    RootsUtil.randomlyRepair(level.getRandom(), itemStack);
  }
}
