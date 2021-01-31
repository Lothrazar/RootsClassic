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
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStaff extends Item implements IManaRelatedItem {

  private static final String NBT_EFFICIENCY = "efficiency";
  private static final String NBT_SIZE = "size";
  private static final String NBT_POT = "potency";
  private static final String NBT_EFFECT = "effect";
  private static final String NBT_USES = "uses";
  private static final String NBT_MAX = "maxUses";
  public static int MAX_USES_PER_EFFICIENCY = 32;
  public static int MAX_USES_BASE = 65;
  private static double RANGE = 3.0;//dont change needs code support
  private static double SIZE_PER_LEVEL = 2.0;
  private static double SIZE_BASE = 3.0;
  Random random = new Random();

  public ItemStaff() {
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
  public double getDurabilityForDisplay(ItemStack stack) {
    if (stack.hasTagCompound()) {
      return 1.0 - (double) stack.getTagCompound().getInteger(NBT_USES) / (double) stack.getTagCompound().getInteger(NBT_MAX);
    }
    return 1.0;
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
    if (stack.hasTagCompound()) {
      if (stack.getTagCompound().getInteger(NBT_USES) < stack.getTagCompound().getInteger(NBT_MAX)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase caster, int timeLeft) {
    if (timeLeft < (72000 - 12) && stack.hasTagCompound() && caster.hasCapability(RootsCapabilityManager.manaCapability, null)) {
      NBTTagCompound stackTags = stack.getTagCompound();
      if (stackTags.getInteger(NBT_USES) >= 0) {
        stackTags.setInteger(NBT_USES, stackTags.getInteger(NBT_USES) - 1);
        ComponentBase comp = ComponentManager.getComponentFromName(stackTags.getString(NBT_EFFECT));
        int potency = stackTags.getInteger(NBT_POT);
        int efficiency = stackTags.getInteger(NBT_EFFICIENCY);
        int size = stackTags.getInteger(NBT_SIZE);
        EntityPlayer player = (EntityPlayer) caster;
        if (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemDruidRobes
            && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemDruidRobes
            && player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemDruidRobes
            && player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemDruidRobes) {
          potency += 1;
        }
        IManaCapability manaCap = player.getCapability(RootsCapabilityManager.manaCapability, null);
        if (manaCap.getMana() >= comp.getManaCost() / (efficiency + 1)) {
          manaCap.setMana(manaCap.getMana() - (comp.getManaCost() / (efficiency + 1)));
          Vec3d lookVec = caster.getLookVec();
          comp.doEffect(world, caster, EnumCastType.SPELL,
              caster.posX + RANGE * lookVec.x,
              caster.posY + RANGE * lookVec.y,
              caster.posZ + RANGE * lookVec.z, potency, efficiency,
              SIZE_BASE + SIZE_PER_LEVEL * size);
          for (int i = 0; i < 90; i++) {
            double offX = world.rand.nextFloat() * 0.5 - 0.25;
            double offY = world.rand.nextFloat() * 0.5 - 0.25;
            double offZ = world.rand.nextFloat() * 0.5 - 0.25;
            double coeff = (offX + offY + offZ) / 1.5 + 0.5;
            double dx = (lookVec.x + offX) * coeff;
            double dy = (lookVec.y + offY) * coeff;
            double dz = (lookVec.z + offZ) * coeff;
            if (world.rand.nextBoolean()) {
              Roots.proxy.spawnParticleMagicFX(world, caster.posX + dx, caster.posY + 1.5 + dy, caster.posZ + dz, dx, dy, dz, comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z);
            }
            else {
              Roots.proxy.spawnParticleMagicFX(world, caster.posX + dx, caster.posY + 1.5 + dy, caster.posZ + dz, dx, dy, dz, comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z);
            }
          }
        }
      }
    }
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (world.isRemote && Minecraft.getMinecraft().currentScreen != null) {
      return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    else {
      player.setActiveHand(hand);
      return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    if (stack.hasTagCompound()) {
      if (stack.getTagCompound().getInteger(NBT_USES) <= 0) {
        if (entity instanceof EntityPlayer) {
          ((EntityPlayer) entity).inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
        }
      }
    }
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    if (oldS.hasTagCompound() && newS.hasTagCompound()) {
      if (oldS.getTagCompound().getString(NBT_EFFECT) != newS.getTagCompound().getString(NBT_EFFECT)) {
        return true;
      }
    }
    return slotChanged;
  }

  @Override
  public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
    if (stack.hasTagCompound()) {
      ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString(NBT_EFFECT));
      int potency = stack.getTagCompound().getInteger(NBT_POT);
      int efficiency = stack.getTagCompound().getInteger(NBT_EFFICIENCY);
      int size = stack.getTagCompound().getInteger(NBT_SIZE);
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

  public static void createData(ItemStack stack, String effect, int potency, int efficiency, int size) {
    stack.setTagCompound(new NBTTagCompound());
    stack.getTagCompound().setString(NBT_EFFECT, effect);
    stack.getTagCompound().setInteger(NBT_POT, potency);
    stack.getTagCompound().setInteger(NBT_EFFICIENCY, efficiency);
    stack.getTagCompound().setInteger(NBT_SIZE, size);
    int uses = MAX_USES_BASE + MAX_USES_PER_EFFICIENCY * efficiency;
    stack.getTagCompound().setInteger(NBT_MAX, uses);
    stack.getTagCompound().setInteger(NBT_USES, uses);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, World player, List<String> tooltip, net.minecraft.client.util.ITooltipFlag advanced) {
    if (stack.hasTagCompound()) {
      NBTTagCompound tagCompound = stack.getTagCompound();
      ComponentBase comp = ComponentManager.getComponentFromName(tagCompound.getString(NBT_EFFECT));
      tooltip.add(TextFormatting.GOLD + I18n.translateToLocalFormatted("roots.tooltip.spelltypeheading.name") + ": " + comp.getTextColor() + comp.getEffectName());
      tooltip.add(TextFormatting.RED + "  +" + tagCompound.getInteger(NBT_POT) + " " + I18n.translateToLocalFormatted("roots.tooltip.spellpotency.name") + ".");
      tooltip.add(TextFormatting.RED + "  +" + tagCompound.getInteger(NBT_EFFICIENCY) + " " + I18n.translateToLocalFormatted("roots.tooltip.spellefficiency.name") + ".");
      tooltip.add(TextFormatting.RED + "  +" + tagCompound.getInteger(NBT_SIZE) + " " + I18n.translateToLocalFormatted("roots.tooltip.spellsize.name") + ".");
      tooltip.add("");
      tooltip.add(TextFormatting.GOLD + Integer.toString(tagCompound.getInteger(NBT_USES)) + " " + I18n.translateToLocalFormatted("roots.tooltip.usesremaining.name") + ".");
    }
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "_0", "inventory"));
    ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "_1", "inventory"));
  }

  public static class ColorHandler implements IItemColor {

    public ColorHandler() {
      //
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int layer) {
      if (stack.hasTagCompound() && stack.getItem() instanceof ItemStaff) {
        if (layer == 2) {
          ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString(NBT_EFFECT));
          return Util.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
        }
        if (layer == 1) {
          ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString(NBT_EFFECT));
          return Util.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
        }
      }
      return Util.intColor(255, 255, 255);
    }
  }
}
