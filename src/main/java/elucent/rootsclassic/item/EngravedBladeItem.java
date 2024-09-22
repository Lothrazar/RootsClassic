package elucent.rootsclassic.item;

import elucent.rootsclassic.registry.RootsComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EngravedBladeItem extends SwordItem {

  private final String[] numerals = { "0", "I", "II", "III", "IIII" }; // TODO: get this from proper LANG key

  public EngravedBladeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
    super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamageIn, attackSpeedIn)));
  }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltip, tooltipFlag);
		if (stack.has(RootsComponents.SPIKES)) {
			tooltip.add(Component.translatable("rootsclassic.tooltip.spikes").append(" " + numerals[stack.getOrDefault(RootsComponents.SPIKES, 0)]).withStyle(ChatFormatting.WHITE));
		}
		if (stack.has(RootsComponents.FORCEFUL)) {
			tooltip.add(Component.translatable("rootsclassic.tooltip.forceful")
				.append(" " + numerals[stack.getOrDefault(RootsComponents.FORCEFUL, 0)]).withStyle(ChatFormatting.DARK_GRAY));
		}
		if (stack.has(RootsComponents.HOLY)) {
			tooltip.add(Component.translatable("rootsclassic.tooltip.holy")
				.append(" " + numerals[stack.getOrDefault(RootsComponents.HOLY, 0)]).withStyle(ChatFormatting.GOLD));
		}
		if (stack.has(RootsComponents.AQUATIC)) {
			tooltip.add(Component.translatable("rootsclassic.tooltip.aquatic")
				.append(" " + numerals[stack.getOrDefault(RootsComponents.AQUATIC, 0)]).withStyle(ChatFormatting.AQUA));
		}
		if (stack.has(RootsComponents.SHADOWSTEP)) {
			tooltip.add(Component.translatable("rootsclassic.tooltip.shadowstep")
				.append(" " + numerals[stack.getOrDefault(RootsComponents.SHADOWSTEP, 0)]).withStyle(ChatFormatting.DARK_PURPLE));
		}
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
