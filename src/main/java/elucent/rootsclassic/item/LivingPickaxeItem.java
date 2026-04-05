package elucent.rootsclassic.item;

import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.Nullable;

public class LivingPickaxeItem extends Item {

  public LivingPickaxeItem(ToolMaterial material, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
	  super(properties.pickaxe(material, attackDamageIn, attackSpeedIn).setNoCombineRepair());
  }

  @Override
  public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
    RootsUtil.randomlyRepair(level.getRandom(), itemStack);
  }
}
