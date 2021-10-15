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
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    if (stack.hasTag()) {
      CompoundNBT tag = stack.getTag();
      if (tag.contains("spikes")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.spikes").appendString(" " + numerals[tag.getInt("spikes")]).mergeStyle(TextFormatting.WHITE));
      }
      if (tag.contains("forceful")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.forceful")
            .appendString(" " + numerals[tag.getInt("forceful")]).mergeStyle(TextFormatting.DARK_GRAY));
      }
      if (tag.contains("holy")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.holy")
            .appendString(" " + numerals[tag.getInt("holy")]).mergeStyle(TextFormatting.GOLD));
      }
      if (tag.contains("aquatic")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.aquatic")
            .appendString(" " + numerals[tag.getInt("aquatic")]).mergeStyle(TextFormatting.AQUA));
      }
      if (tag.contains("shadowstep")) {
        tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.shadowstep")
            .appendString(" " + numerals[tag.getInt("shadowstep")]).mergeStyle(TextFormatting.DARK_PURPLE));
      }
    }
  }

  @Override
  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    return false;
  }
}
