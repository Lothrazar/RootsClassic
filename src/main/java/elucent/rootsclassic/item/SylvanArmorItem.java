package elucent.rootsclassic.item;

import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class SylvanArmorItem extends Item {

  public SylvanArmorItem(ArmorMaterial materialHolder, ArmorType type, Item.Properties properties) {
    super(properties.humanoidArmor(materialHolder, type).setNoCombineRepair());
  }

  @Override
  public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
    if (slot != null && slot.getIndex() < 4) {
		  RootsUtil.randomlyRepair(level.getRandom(), stack);
		  if (level.getRandom().nextInt(40) == 0 && owner instanceof Player player) {
			  ManaAttachment mana = player.getData(RootsAttachments.MANA);
			  mana.setMana(mana.getMana() + 1.0f);
			  player.setData(RootsAttachments.MANA, mana);
		  }
	  }
  }

  @Override
  public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
    super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    builder.accept(Component.empty());
    builder.accept(Component.translatable("rootsclassic.attribute.equipped").withStyle(ChatFormatting.GRAY));
    builder.accept(Component.literal(" ").append(Component.translatable("rootsclassic.attribute.increasedmanaregen")).withStyle(ChatFormatting.BLUE));
    builder.accept(Component.empty());
    builder.accept(Component.translatable("rootsclassic.attribute.fullset").withStyle(ChatFormatting.GRAY));
    builder.accept(Component.literal(" +1 ").append(Component.translatable("rootsclassic.attribute.potency")).withStyle(ChatFormatting.BLUE));
  }
}
