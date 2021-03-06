package elucent.rootsclassic.item;

import java.util.List;
import com.google.common.collect.Multimap;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEngravedSword extends ItemSword {

  private String[] numerals = { "0", "I", "II", "III", "IIII" };

  public ItemEngravedSword() {
    super(RegistryManager.engravedMaterial);
  }

  @Override
  public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
    Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
    if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
      multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
      multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3.0D, 0));
    }
    return multimap;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, World player, List<String> tooltip, net.minecraft.client.util.ITooltipFlag advanced) {
    if (stack.hasTagCompound()) {
      NBTTagCompound tag = stack.getTagCompound();
      if (tag.hasKey("spikes")) {
        tooltip.add(TextFormatting.WHITE + Roots.lang("roots.tooltip.spikes.name") + " " + numerals[tag.getInteger("spikes")]);
      }
      if (tag.hasKey("forceful")) {
        tooltip.add(TextFormatting.DARK_GRAY + Roots.lang("roots.mod.forceful.name") + numerals[tag.getInteger("forceful")]);
      }
      if (tag.hasKey("holy")) {
        tooltip.add(TextFormatting.GOLD + Roots.lang("roots.mod.holy.name") + numerals[tag.getInteger("holy")]);
      }
      if (tag.hasKey("aquatic")) {
        tooltip.add(TextFormatting.AQUA + Roots.lang("roots.mod.aquatic.name") + numerals[tag.getInteger("aquatic")]);
      }
      if (tag.hasKey("shadowstep")) {
        tooltip.add(TextFormatting.DARK_PURPLE + Roots.lang("roots.mod.shadowstep.name") + numerals[tag.getInteger("shadowstep")]);
      }
    }
  }
}
