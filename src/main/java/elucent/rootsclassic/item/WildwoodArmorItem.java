package elucent.rootsclassic.item;

import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class WildwoodArmorItem extends Item {

  public WildwoodArmorItem(ArmorMaterial materialHolder, ArmorType type, Item.Properties properties) {
    super(properties.humanoidArmor(materialHolder, type).setNoCombineRepair());
  }

	@Override
  public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
		if (slot != null && slot.getIndex() < 4)
			RootsUtil.randomlyRepair(level.getRandom(), stack);
	}

  @Override
  public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
    builder.accept(Component.empty());
    builder.accept(Component.translatable("rootsclassic.attribute.equipped").withStyle(ChatFormatting.GRAY));
    builder.accept(Component.literal(" ").append(Component.translatable("rootsclassic.attribute.increasedmanaregen")).withStyle(ChatFormatting.BLUE));
  }
}
