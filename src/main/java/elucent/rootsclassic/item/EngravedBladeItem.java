package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class EngravedBladeItem extends SwordItem {

  private final String[] numerals = { "0", "I", "II", "III", "IIII" }; // TODO: get this from proper LANG key

  public EngravedBladeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
    super(tier, attackDamageIn, attackSpeedIn, builderIn);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level levelAccessor, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, levelAccessor, tooltip, flagIn);
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      if (tag.contains("spikes")) {
        tooltip.add(Component.translatable("rootsclassic.tooltip.spikes").append(" " + numerals[tag.getInt("spikes")]).withStyle(ChatFormatting.WHITE));
      }
      if (tag.contains("forceful")) {
        tooltip.add(Component.translatable("rootsclassic.tooltip.forceful")
            .append(" " + numerals[tag.getInt("forceful")]).withStyle(ChatFormatting.DARK_GRAY));
      }
      if (tag.contains("holy")) {
        tooltip.add(Component.translatable("rootsclassic.tooltip.holy")
            .append(" " + numerals[tag.getInt("holy")]).withStyle(ChatFormatting.GOLD));
      }
      if (tag.contains("aquatic")) {
        tooltip.add(Component.translatable("rootsclassic.tooltip.aquatic")
            .append(" " + numerals[tag.getInt("aquatic")]).withStyle(ChatFormatting.AQUA));
      }
      if (tag.contains("shadowstep")) {
        tooltip.add(Component.translatable("rootsclassic.tooltip.shadowstep")
            .append(" " + numerals[tag.getInt("shadowstep")]).withStyle(ChatFormatting.DARK_PURPLE));
      }
    }
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
