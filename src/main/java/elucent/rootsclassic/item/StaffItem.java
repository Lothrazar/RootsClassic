package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.IManaCapability;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;

import net.minecraft.world.item.Item.Properties;

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
  public UseAnim getUseAnimation(ItemStack stack) {
    return UseAnim.BOW;
  }

  @Override
  public int getUseDuration(ItemStack stack) {
    return 72000;
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      return 1.0 - (double) tag.getInt(NBT_USES) / (double) tag.getInt(NBT_MAX);
    }
    return 1.0;
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      return tag.getInt(NBT_USES) < tag.getInt(NBT_MAX);
    }
    return false;
  }

  @Override
  public void releaseUsing(ItemStack stack, Level world, LivingEntity caster, int timeLeft) {
    if (timeLeft < (72000 - 12) && stack.hasTag() && caster.getCapability(RootsCapabilityManager.MANA_CAPABILITY).isPresent()) {
      CompoundTag tag = stack.getTag();
      if (tag.getInt(NBT_USES) >= 0) {
        tag.putInt(NBT_USES, tag.getInt(NBT_USES) - 1);
        ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
        if (compName != null) {
          ComponentBase comp = ComponentManager.getComponentFromName(compName);
          if (comp != null) {
            int potency = tag.getInt(Const.NBT_POTENCY);
            int efficiency = tag.getInt(Const.NBT_EFFICIENCY);
            int size = tag.getInt(Const.NBT_SIZE);
            Player player = (Player) caster;
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SylvanArmorItem) {
              potency += 1;
            }
            IManaCapability manaCap = player.getCapability(RootsCapabilityManager.MANA_CAPABILITY).orElse(null);
            if (manaCap.getMana() >= comp.getManaCost() / (efficiency + 1)) {
              manaCap.setMana(manaCap.getMana() - (comp.getManaCost() / (efficiency + 1)));
              Vec3 lookVec = caster.getLookAngle();
              comp.doEffect(world, caster, EnumCastType.SPELL,
                  caster.getX() + RANGE * lookVec.x,
                  caster.getY() + RANGE * lookVec.y,
                  caster.getZ() + RANGE * lookVec.z, potency, efficiency,
                  SIZE_BASE + SIZE_PER_LEVEL * size);
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

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (world.isClientSide && Minecraft.getInstance().screen != null) {
      return new InteractionResultHolder<ItemStack>(InteractionResult.FAIL, stack);
    }
    else {
      player.startUsingItem(hand);
      return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, stack);
    }
  }

  @Override
  public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      if (tag.getInt(NBT_USES) <= 0 && entityIn instanceof Player) {
        ((Player) entityIn).inventory.setItem(itemSlot, ItemStack.EMPTY);
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
      CompoundTag tag = stack.getTag();
      ResourceLocation componentName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
      if (componentName != null) {
        ComponentBase comp = ComponentManager.getComponentFromName(componentName);
        if (comp != null) {
          int potency = tag.getInt(Const.NBT_POTENCY);
          int efficiency = tag.getInt(Const.NBT_EFFICIENCY);
          int size = tag.getInt(Const.NBT_SIZE);
          comp.castingAction((Player) player, count, potency, efficiency, size);
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

  public static void createData(ItemStack stack, String effect, int potency, int efficiency, int size) {
    CompoundTag tag = new CompoundTag();
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
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
      if (compName != null) {
        ComponentBase comp = ComponentManager.getComponentFromName(compName);
        if (comp != null) {
          tooltip.add(new TranslatableComponent("rootsclassic.tooltip.spelltypeheading")
              .append(": ").withStyle(ChatFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
        }
        else {
          //TODO: let people know it's an invalid effect
        }
      }
      tooltip.add(new TextComponent("  +" + tag.getInt(Const.NBT_POTENCY) + " ")
          .append(new TranslatableComponent("rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(new TextComponent("  +" + tag.getInt(Const.NBT_EFFICIENCY) + " ")
          .append(new TranslatableComponent("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(new TextComponent("  +" + tag.getInt(Const.NBT_SIZE) + " ")
          .append(new TranslatableComponent("rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(TextComponent.EMPTY);
      tooltip.add(new TextComponent(tag.getInt(NBT_USES) + " ")
          .append(new TranslatableComponent("rootsclassic.tooltip.usesremaining")).append(".").withStyle(ChatFormatting.GOLD));
    }
    else {
      tooltip.add(new TranslatableComponent("rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
    }
  }
  //	@SideOnly(Side.CLIENT)
  //	public void initModel() {
  //		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "_0", "inventory"));
  //		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "_1", "inventory"));
  //	}
}
