package elucent.rootsclassic.item;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.capability.IManaCapability;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrystalStaff extends Item implements IManaRelatedItem {

  Random random = new Random();

  public ItemCrystalStaff() {
    super();
    this.setMaxStackSize(1);
  }

  @Override
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }

  @Override
  public int getMaxItemUseDuration(ItemStack stack) {
    return 72000;
  }

  @Override
  public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
    if (timeLeft < (72000 - 12) && stack.hasTagCompound()) {
      //BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
      EntityPlayer pl = (EntityPlayer) player;
      ComponentBase comp = ComponentManager.getComponentFromName(ItemCrystalStaff.getEffect(stack));
      if (comp == null || !pl.hasCapability(RootsCapabilityManager.manaCapability, null)) {
        return;
      }
      int potency = getPotency(stack) + 1;
      int efficiency = ItemCrystalStaff.getEfficiency(stack);
      int size = ItemCrystalStaff.getSize(stack);
      if (pl.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemDruidRobes
          && pl.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemDruidRobes
          && pl.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemDruidRobes
          && pl.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemDruidRobes) {
        potency += 1;
      }
      //        double xpCost = (comp.getManaCost() + potency) * (1.0 - 0.25 * efficiency);
      Random random = new Random();
      IManaCapability manaCap = pl.getCapability(RootsCapabilityManager.manaCapability, null);
      if (manaCap.getMana() >= ((float) comp.getManaCost()) / (efficiency + 1)) {
        //pay mana cost
        manaCap.setMana(manaCap.getMana() - (((float) comp.getManaCost()) / (efficiency + 1)));
        comp.doEffect(world, player, EnumCastType.SPELL, player.posX + 3.0 * player.getLookVec().x, player.posY + 3.0 * player.getLookVec().y, player.posZ + 3.0 * player.getLookVec().z, potency, efficiency, 3.0 + 2.0 * size);
        for (int i = 0; i < 90; i++) {
          double offX = random.nextFloat() * 0.5 - 0.25;
          double offY = random.nextFloat() * 0.5 - 0.25;
          double offZ = random.nextFloat() * 0.5 - 0.25;
          double coeff = (offX + offY + offZ) / 1.5 + 0.5;
          double dx = (player.getLookVec().x + offX) * coeff;
          double dy = (player.getLookVec().y + offY) * coeff;
          double dz = (player.getLookVec().z + offZ) * coeff;
          if (random.nextBoolean()) {
            Roots.proxy.spawnParticleMagicFX(world, player.posX + dx, player.posY + 1.5 + dy, player.posZ + dz, dx, dy, dz, comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z);
          }
          else {
            Roots.proxy.spawnParticleMagicFX(world, player.posX + dx, player.posY + 1.5 + dy, player.posZ + dz, dx, dy, dz, comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z);
          }
        }
      }
    }
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.hasTagCompound() && !player.isSneaking()) {
      if (world.isRemote && Minecraft.getMinecraft().currentScreen != null) {
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
      }
      else {
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
      }
    }
    else if (stack.hasTagCompound()) {
      stack.getTagCompound().setInteger("selected", stack.getTagCompound().getInteger("selected") + 1);
      if (stack.getTagCompound().getInteger("selected") > 4) {
        stack.getTagCompound().setInteger("selected", 1);
      }
      return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    if (oldS.hasTagCompound() && newS.hasTagCompound()) {
      if (ItemCrystalStaff.getEffect(oldS) != ItemCrystalStaff.getEffect(newS) || oldS.getTagCompound().getInteger("selected") != newS.getTagCompound().getInteger("selected") || slotChanged) {
        return true;
      }
    }
    return slotChanged;
  }

  @Override
  public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
    if (stack.hasTagCompound()) {
      ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString("effect"));
      int potency = stack.getTagCompound().getInteger("potency");
      int efficiency = stack.getTagCompound().getInteger("efficiency");
      int size = stack.getTagCompound().getInteger("size");
      if (comp != null) {
        comp.castingAction((EntityPlayer) player, count, potency, efficiency, size);
        if (random.nextBoolean()) {
          Roots.proxy.spawnParticleMagicLineFX(player.getEntityWorld(), player.posX + 2.0 * (random.nextFloat() - 0.5), player.posY + 2.0 * (random.nextFloat() - 0.5) + 1.0, player.posZ + 2.0 * (random.nextFloat() - 0.5), player.posX, player.posY + 1.0, player.posZ, comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z);
        }
        else {
          Roots.proxy.spawnParticleMagicLineFX(player.getEntityWorld(), player.posX + 2.0 * (random.nextFloat() - 0.5), player.posY + 2.0 * (random.nextFloat() - 0.5) + 1.0, player.posZ + 2.0 * (random.nextFloat() - 0.5), player.posX, player.posY + 1.0, player.posZ, comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z);
        }
      }
    }
  }

  public static void createData(ItemStack stack) {
    stack.setTagCompound(new NBTTagCompound());
    stack.getTagCompound().setInteger("selected", 1);
    stack.getTagCompound().setInteger("potency1", 0);
    stack.getTagCompound().setInteger("potency2", 0);
    stack.getTagCompound().setInteger("potency3", 0);
    stack.getTagCompound().setInteger("potency4", 0);
    stack.getTagCompound().setInteger("efficiency1", 0);
    stack.getTagCompound().setInteger("efficiency2", 0);
    stack.getTagCompound().setInteger("efficiency3", 0);
    stack.getTagCompound().setInteger("efficiency4", 0);
    stack.getTagCompound().setInteger("size1", 0);
    stack.getTagCompound().setInteger("size2", 0);
    stack.getTagCompound().setInteger("size3", 0);
    stack.getTagCompound().setInteger("size4", 0);
    stack.getTagCompound().setString("effect1", "");
    stack.getTagCompound().setString("effect2", "");
    stack.getTagCompound().setString("effect3", "");
    stack.getTagCompound().setString("effect4", "");
  }

  public static void addEffect(ItemStack stack, int slot, String effect, int potency, int efficiency, int size) {
    stack.getTagCompound().setString("effect" + slot, effect);
    stack.getTagCompound().setInteger("potency" + slot, potency);
    stack.getTagCompound().setInteger("efficiency" + slot, efficiency);
    stack.getTagCompound().setInteger("size" + slot, size);
  }

  public static Integer getPotency(ItemStack stack) {
    if (stack.hasTagCompound()) {
      return stack.getTagCompound().getInteger("potency" + stack.getTagCompound().getInteger("selected"));
    }
    return 0;
  }

  public static Integer getEfficiency(ItemStack stack) {
    if (stack.hasTagCompound()) {
      return stack.getTagCompound().getInteger("efficiency" + stack.getTagCompound().getInteger("selected"));
    }
    return 0;
  }

  public static Integer getSize(ItemStack stack) {
    if (stack.hasTagCompound()) {
      return stack.getTagCompound().getInteger("size" + stack.getTagCompound().getInteger("selected"));
    }
    return 0;
  }

  public static String getEffect(ItemStack stack) {
    if (stack.hasTagCompound()) {
      return stack.getTagCompound().getString("effect" + stack.getTagCompound().getInteger("selected"));
    }
    return null;
  }

  public static String getEffect(ItemStack stack, int slot) {
    if (stack.hasTagCompound()) {
      return stack.getTagCompound().getString("effect" + slot);
    }
    return null;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, World player, List<String> tooltip, net.minecraft.client.util.ITooltipFlag advanced) {
    if (stack.hasTagCompound()) {
      ComponentBase comp = ComponentManager.getComponentFromName(ItemCrystalStaff.getEffect(stack));
      if (comp != null) {
        tooltip.add(TextFormatting.GOLD + I18n.translateToLocalFormatted("roots.tooltip.spelltypeheading.name") + ": " + comp.getTextColor() + comp.getEffectName());
        tooltip.add(TextFormatting.RED + "  +" + ItemCrystalStaff.getPotency(stack) + " " + I18n.translateToLocalFormatted("roots.tooltip.spellpotency.name") + ".");
        tooltip.add(TextFormatting.RED + "  +" + ItemCrystalStaff.getEfficiency(stack) + " " + I18n.translateToLocalFormatted("roots.tooltip.spellefficiency.name") + ".");
        tooltip.add(TextFormatting.RED + "  +" + ItemCrystalStaff.getSize(stack) + " " + I18n.translateToLocalFormatted("roots.tooltip.spellsize.name") + ".");
      }
    }
  }

  public static class ColorHandler implements IItemColor {

    public ColorHandler() {
      //
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int layer) {
      if (stack.hasTagCompound()) {
        if (layer == 2) {
          ComponentBase comp = ComponentManager.getComponentFromName(ItemCrystalStaff.getEffect(stack));
          if (comp != null) {
            return Util.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
          }
        }
        if (layer == 1) {
          ComponentBase comp = ComponentManager.getComponentFromName(ItemCrystalStaff.getEffect(stack));
          if (comp != null) {
            return Util.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
          }
        }
      }
      return Util.intColor(255, 255, 255);
    }
  }
}
