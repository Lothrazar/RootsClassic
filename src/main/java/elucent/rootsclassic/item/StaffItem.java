package elucent.rootsclassic.item;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.IManaCapability;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class StaffItem extends Item implements IManaRelatedItem {

  private static final String NBT_USES = "uses";
  private static final String NBT_MAX = "maxUses";
  private static final double RANGE = 3.0; //dont change needs code support
  private static final double SIZE_PER_LEVEL = 2.0;
  private static final double SIZE_BASE = 3.0;

  public StaffItem(Properties properties) {
    super(properties);
  }

  @Override
  public UseAction getUseAnimation(ItemStack stack) {
    return UseAction.BOW;
  }

  @Override
  public int getUseDuration(ItemStack stack) {
    return 72000;
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundNBT tag = stack.getTag();
      return 1.0 - (double) tag.getInt(NBT_USES) / (double) tag.getInt(NBT_MAX);
    }
    return 1.0;
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundNBT tag = stack.getTag();
      return tag.getInt(NBT_USES) < tag.getInt(NBT_MAX);
    }
    return false;
  }

  @Override
  public void releaseUsing(ItemStack stack, World world, LivingEntity caster, int timeLeft) {
    if (timeLeft < (72000 - 12) && stack.hasTag() && caster.getCapability(RootsCapabilityManager.MANA_CAPABILITY).isPresent()) {
      CompoundNBT tag = stack.getTag();
      if (tag.getInt(NBT_USES) >= 0) {
        tag.putInt(NBT_USES, tag.getInt(NBT_USES) - 1);
        ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
        if (compName != null) {
          ComponentBase comp = ComponentManager.getComponentFromName(compName);
          if (comp != null) {
            int potency = tag.getInt(Const.NBT_POTENCY);
            int efficiency = tag.getInt(Const.NBT_EFFICIENCY);
            int size = tag.getInt(Const.NBT_SIZE);
            PlayerEntity player = (PlayerEntity) caster;
            if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlotType.FEET).getItem() instanceof SylvanArmorItem) {
              potency += 1;
            }
            IManaCapability manaCap = player.getCapability(RootsCapabilityManager.MANA_CAPABILITY).orElse(null);
            if (manaCap.getMana() >= comp.getManaCost() / (efficiency + 1)) {
              manaCap.setMana(manaCap.getMana() - (comp.getManaCost() / (efficiency + 1)));
              Vector3d lookVec = caster.getLookAngle();
              comp.doEffect(world, caster, EnumCastType.SPELL,
                  caster.getX() + RANGE * lookVec.x,
                  caster.getY() + RANGE * lookVec.y,
                  caster.getZ() + RANGE * lookVec.z, potency, efficiency,
                  SIZE_BASE + SIZE_PER_LEVEL * size);
              if (world.isClientSide) {
                for (int i = 0; i < 90; i++) {
                  double offX = world.random.nextFloat() * 0.5 - 0.25;
                  double offY = world.random.nextFloat() * 0.5 - 0.25;
                  double offZ = world.random.nextFloat() * 0.5 - 0.25;
                  double coeff = (offX + offY + offZ) / 1.5 + 0.5;
                  double dx = (lookVec.x + offX) * coeff;
                  double dy = (lookVec.y + offY) * coeff;
                  double dz = (lookVec.z + offZ) * coeff;
                  if (world.random.nextBoolean()) {
                    world.addParticle(MagicParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
                            caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
                  }
                  else {
                    world.addParticle(MagicParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
                            caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  @Override
  public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (world.isClientSide && Minecraft.getInstance().screen != null) {
      return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
    }
    else {
      player.startUsingItem(hand);
      return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
    }
  }

  @Override
  public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    if (stack.hasTag()) {
      CompoundNBT tag = stack.getTag();
      if (tag.getInt(NBT_USES) <= 0 && entityIn instanceof PlayerEntity) {
        ((PlayerEntity) entityIn).inventory.setItem(itemSlot, ItemStack.EMPTY);
      }
    }
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    if (oldS.hasTag() && newS.hasTag()) {
      if (oldS.getTag().getString(Const.NBT_EFFECT) != newS.getTag().getString(Const.NBT_EFFECT)) {
        return true;
      }
    }
    return slotChanged;
  }

  @Override
  public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    if (stack.hasTag()) {
      CompoundNBT tag = stack.getTag();
      ResourceLocation componentName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
      if (componentName != null) {
        ComponentBase comp = ComponentManager.getComponentFromName(componentName);
        if (comp != null) {
          int potency = tag.getInt(Const.NBT_POTENCY);
          int efficiency = tag.getInt(Const.NBT_EFFICIENCY);
          int size = tag.getInt(Const.NBT_SIZE);
          comp.castingAction((PlayerEntity) player, count, potency, efficiency, size);

          if (player.getCommandSenderWorld().isClientSide) {
            if (random.nextBoolean()) {
              player.getCommandSenderWorld().addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
                      player.getX() + 2.0 * (random.nextFloat() - 0.5), player.getY() + 2.0 * (random.nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * (random.nextFloat() - 0.5),
                      player.getX(), player.getY() + 1.0, player.getZ());
            }
            else {
              player.getCommandSenderWorld().addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
                      player.getX() + 2.0 * (random.nextFloat() - 0.5), player.getY() + 2.0 * (random.nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * (random.nextFloat() - 0.5),
                      player.getX(), player.getY() + 1.0, player.getZ());
            }
          }
        }
      }
    }
  }

  public static void createData(ItemStack stack, String effect, int potency, int efficiency, int size) {
    CompoundNBT tag = new CompoundNBT();
    tag.putString(Const.NBT_EFFECT, effect);
    tag.putInt(Const.NBT_POTENCY, potency);
    tag.putInt(Const.NBT_EFFICIENCY, efficiency);
    tag.putInt(Const.NBT_SIZE, size);
    int uses = getMaxUsesBase() + getMaxUsesPerEfficiency() * efficiency;
    tag.putInt(NBT_MAX, uses);
    tag.putInt(NBT_USES, uses);
    stack.setTag(tag);
  }

  public static int getMaxUsesBase() {
    return RootsConfig.COMMON.staffUsesBasic.get();
  }

  public static int getMaxUsesPerEfficiency() {
    return RootsConfig.COMMON.staffUsesEfficiency.get();
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    if (stack.hasTag()) {
      CompoundNBT tag = stack.getTag();
      ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
      if (compName != null) {
        ComponentBase comp = ComponentManager.getComponentFromName(compName);
        if (comp != null) {
          tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.spelltypeheading")
              .append(": ").withStyle(TextFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
        }
        else {
          //TODO: let people know it's an invalid effect
        }
      }
      tooltip.add(new StringTextComponent("  +" + tag.getInt(Const.NBT_POTENCY) + " ")
          .append(new TranslationTextComponent("rootsclassic.tooltip.spellpotency")).append(".").withStyle(TextFormatting.RED));
      tooltip.add(new StringTextComponent("  +" + tag.getInt(Const.NBT_EFFICIENCY) + " ")
          .append(new TranslationTextComponent("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(TextFormatting.RED));
      tooltip.add(new StringTextComponent("  +" + tag.getInt(Const.NBT_SIZE) + " ")
          .append(new TranslationTextComponent("rootsclassic.tooltip.spellsize")).append(".").withStyle(TextFormatting.RED));
      tooltip.add(StringTextComponent.EMPTY);
      tooltip.add(new StringTextComponent(tag.getInt(NBT_USES) + " ")
          .append(new TranslationTextComponent("rootsclassic.tooltip.usesremaining")).append(".").withStyle(TextFormatting.GOLD));
    }
    else {
      tooltip.add(new TranslationTextComponent("rootsclassic.error.unset").withStyle(TextFormatting.GRAY));
    }
  }
  //	@SideOnly(Side.CLIENT)
  //	public void initModel() {
  //		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "_0", "inventory"));
  //		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "_1", "inventory"));
  //	}
}
