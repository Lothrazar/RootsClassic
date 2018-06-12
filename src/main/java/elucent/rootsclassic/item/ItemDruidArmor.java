package elucent.rootsclassic.item;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.model.ModelDruidArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDruidArmor extends ItemArmor {

  Random rnd = new Random();

  public ItemDruidArmor(int reduction, EntityEquipmentSlot slot) {
    super(RegistryManager.druidArmorMaterial, reduction, slot);

    setCreativeTab(Roots.tab);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, World player, List<String> tooltip, net.minecraft.client.util.ITooltipFlag advanced) {
    tooltip.add("");
    tooltip.add(TextFormatting.GRAY + I18n.format("roots.attribute.equipped.name"));
    tooltip.add(TextFormatting.BLUE + " " + I18n.format("roots.attribute.increasedregen.name"));
  }

  @SideOnly(Side.CLIENT)
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    return Const.MODID + ":textures/models/armor/druidArmor.png";
  }

  @SideOnly(Side.CLIENT)
  @Override
  public ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default) {
    return new ModelDruidArmor(slot);
  }

  @Override
  public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
    if (stack.isItemDamaged() && rnd.nextInt(80) == 0) {
      stack.setItemDamage(stack.getItemDamage() - 1);
    }

  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }
}
