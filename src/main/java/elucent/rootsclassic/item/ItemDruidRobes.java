package elucent.rootsclassic.item;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.model.ModelDruidRobes;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDruidRobes extends ItemArmor {

  Random rnd = new Random();

  public ItemDruidRobes(int reduction, EntityEquipmentSlot slot) {
    super(RegistryManager.druidRobesMaterial, reduction, slot);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    return Const.MODID + ":textures/models/armor/druidrobes.png";
  }

  @SideOnly(Side.CLIENT)
  @Override
  public ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default) {
    return new ModelDruidRobes(slot);
  }

  @Override
  public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
    Util.randomlyRepair(world.rand, stack);
    if (rnd.nextInt(40) == 0) {
      if (player.hasCapability(RootsCapabilityManager.manaCapability, null)) {
        player.getCapability(RootsCapabilityManager.manaCapability, null).setMana(player.getCapability(RootsCapabilityManager.manaCapability, null).getMana() + 1.0f);
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, World player, List<String> tooltip, net.minecraft.client.util.ITooltipFlag advanced) {
    tooltip.add("");
    tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("roots.attribute.equipped.name"));
    tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocalFormatted("roots.attribute.increasedmanaregen.name"));
    tooltip.add("");
    tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("roots.attribute.fullset.name"));
    tooltip.add(TextFormatting.BLUE + " +1 " + I18n.translateToLocalFormatted("roots.attribute.potency.name"));
  }
}
