package elucent.rootsclassic.item;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDustPetal extends Item {

  Random random = new Random();

  public ItemDustPetal() {
    super();
    this.setMaxStackSize(1);
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    return slotChanged;
  }

  public static void createData(ItemStack stack, String effect, List<ItemStack> items) {
    stack.setTagCompound(new NBTTagCompound());
    int potency = 0;
    int efficiency = 0;
    int size = 0;
    stack.getTagCompound().setString("effect", effect);
    for (ItemStack itemStack : items) {
      if (itemStack.isEmpty() == false) {
        if (itemStack.getItem() == Items.GLOWSTONE_DUST) {
          potency++;
        }
        if (itemStack.getItem() == Items.REDSTONE) {
          efficiency++;
        }
        if (itemStack.getItem() == Items.GUNPOWDER) {
          size++;
        }
      }
    }
    stack.getTagCompound().setInteger("potency", potency);
    stack.getTagCompound().setInteger("efficiency", efficiency);
    stack.getTagCompound().setInteger("size", size);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, World player, List<String> tooltip, net.minecraft.client.util.ITooltipFlag advanced) {
    if (stack.hasTagCompound()) {
      NBTTagCompound tags = stack.getTagCompound();
      ComponentBase comp = ComponentManager.getComponentFromName(tags.getString("effect"));
      tooltip.add(TextFormatting.GOLD + I18n.translateToLocalFormatted("roots.tooltip.spelltypeheading.name") + ": " + comp.getTextColor() + comp.getEffectName());
      tooltip.add(TextFormatting.RED + "  +" + tags.getInteger("potency") + " " + I18n.translateToLocalFormatted("roots.tooltip.spellpotency.name") + ".");
      tooltip.add(TextFormatting.RED + "  +" + tags.getInteger("efficiency") + " " + I18n.translateToLocalFormatted("roots.tooltip.spellefficiency.name") + ".");
      tooltip.add(TextFormatting.RED + "  +" + tags.getInteger("size") + " " + I18n.translateToLocalFormatted("roots.tooltip.spellsize.name") + ".");
    }
  }
}
