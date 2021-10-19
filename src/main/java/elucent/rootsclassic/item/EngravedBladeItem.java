package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EngravedBladeItem extends SwordItem {

  private final String[] numerals = { "0", "I", "II", "III", "IIII" };

  public EngravedBladeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
    super(tier, attackDamageIn, attackSpeedIn, builderIn);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    if (stack.hasTag()) {
      CompoundNBT tag = stack.getTag();
      if (tag.contains("spikes")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.spikes").append(" " + numerals[tag.getInt("spikes")]).withStyle(TextFormatting.WHITE));
      }
      if (tag.contains("forceful")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.forceful")
            .append(" " + numerals[tag.getInt("forceful")]).withStyle(TextFormatting.DARK_GRAY));
      }
      if (tag.contains("holy")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.holy")
            .append(" " + numerals[tag.getInt("holy")]).withStyle(TextFormatting.GOLD));
      }
      if (tag.contains("aquatic")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.aquatic")
            .append(" " + numerals[tag.getInt("aquatic")]).withStyle(TextFormatting.AQUA));
      }
      if (tag.contains("shadowstep")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.shadowstep")
            .append(" " + numerals[tag.getInt("shadowstep")]).withStyle(TextFormatting.DARK_PURPLE));
      }
    }
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
