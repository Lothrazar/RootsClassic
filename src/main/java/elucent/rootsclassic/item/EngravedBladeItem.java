package elucent.rootsclassic.item;

import elucent.rootsclassic.registry.RootsComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class EngravedBladeItem extends Item {

  private final String[] numerals = { "0", "I", "II", "III", "IIII" }; // TODO: get this from proper LANG key

  public EngravedBladeItem(ToolMaterial material, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
    super(properties.sword(material, attackDamageIn, attackSpeedIn).setNoCombineRepair());
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
    if (stack.has(RootsComponents.SPIKES)) {
      builder.accept(Component.translatable("rootsclassic.tooltip.spikes").append(" " + numerals[stack.getOrDefault(RootsComponents.SPIKES, 0)]).withStyle(ChatFormatting.WHITE));
    }
    if (stack.has(RootsComponents.FORCEFUL)) {
      builder.accept(Component.translatable("rootsclassic.tooltip.forceful")
        .append(" " + numerals[stack.getOrDefault(RootsComponents.FORCEFUL, 0)]).withStyle(ChatFormatting.DARK_GRAY));
    }
    if (stack.has(RootsComponents.HOLY)) {
      builder.accept(Component.translatable("rootsclassic.tooltip.holy")
        .append(" " + numerals[stack.getOrDefault(RootsComponents.HOLY, 0)]).withStyle(ChatFormatting.GOLD));
    }
    if (stack.has(RootsComponents.AQUATIC)) {
      builder.accept(Component.translatable("rootsclassic.tooltip.aquatic")
        .append(" " + numerals[stack.getOrDefault(RootsComponents.AQUATIC, 0)]).withStyle(ChatFormatting.AQUA));
    }
    if (stack.has(RootsComponents.SHADOWSTEP)) {
      builder.accept(Component.translatable("rootsclassic.tooltip.shadowstep")
        .append(" " + numerals[stack.getOrDefault(RootsComponents.SHADOWSTEP, 0)]).withStyle(ChatFormatting.DARK_PURPLE));
    }
  }
}
