package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.IManaCapability;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CrystalStaffItem extends Item implements IManaRelatedItem {

  public CrystalStaffItem(Properties properties) {
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
  public void releaseUsing(ItemStack stack, Level levelAccessor, LivingEntity caster, int timeLeft) {
    if (timeLeft < (72000 - 12) && stack.hasTag()) {
      //BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
      Player player = (Player) caster;
      ResourceLocation compName = ResourceLocation.tryParse(CrystalStaffItem.getEffect(stack));
      if (compName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get().getValue(compName);
        if (comp == null || !caster.getCapability(RootsCapabilityManager.MANA_CAPABILITY).isPresent()) {
          return;
        }
        int potency = getPotency(stack) + 1;
        int efficiency = CrystalStaffItem.getEfficiency(stack);
        int size = CrystalStaffItem.getSize(stack);
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SylvanArmorItem
            && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SylvanArmorItem
            && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SylvanArmorItem
            && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SylvanArmorItem) {
          potency += 1;
        }
        //        double xpCost = (comp.getManaCost() + potency) * (1.0 - 0.25 * efficiency);
        IManaCapability manaCap = player.getCapability(RootsCapabilityManager.MANA_CAPABILITY).orElse(null);
        if (manaCap.getMana() >= comp.getManaCost() / (efficiency + 1)) {
          //pay mana cost
          manaCap.setMana(manaCap.getMana() - ((comp.getManaCost()) / (efficiency + 1)));
          comp.doEffect(levelAccessor, caster, EnumCastType.SPELL, caster.getX() + 3.0 * caster.getLookAngle().x, caster.getY() + 3.0 * caster.getLookAngle().y,
              caster.getZ() + 3.0 * caster.getLookAngle().z, potency, efficiency, 3.0 + 2.0 * size);
          if (levelAccessor.isClientSide) {
            for (int i = 0; i < 90; i++) {
              double offX = levelAccessor.random.nextFloat() * 0.5 - 0.25;
              double offY = levelAccessor.random.nextFloat() * 0.5 - 0.25;
              double offZ = levelAccessor.random.nextFloat() * 0.5 - 0.25;
              double coeff = (offX + offY + offZ) / 1.5 + 0.5;
              double dx = (caster.getLookAngle().x + offX) * coeff;
              double dy = (caster.getLookAngle().y + offY) * coeff;
              double dz = (caster.getLookAngle().z + offZ) * coeff;
              if (levelAccessor.random.nextBoolean()) {
                levelAccessor.addParticle(MagicParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
                    caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
              }
              else {
                levelAccessor.addParticle(MagicParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
                    caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
              }
            }
          }
        }
      }
    }
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level levelAccessor, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      if (!player.isShiftKeyDown()) {
        if (levelAccessor.isClientSide && Minecraft.getInstance().screen != null) {
          return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
        }
        else {
          player.startUsingItem(hand);
          return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
      }
      else {
        tag.putInt(Const.NBT_SELECTED, tag.getInt(Const.NBT_SELECTED) + 1);
        if (tag.getInt(Const.NBT_SELECTED) > 4) {
          tag.putInt(Const.NBT_SELECTED, 1);
        }
        stack.setTag(tag);
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
      }
    }
    return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    if (oldS.hasTag() && newS.hasTag()) {
      if (!CrystalStaffItem.getEffect(oldS).equals(CrystalStaffItem.getEffect(newS)) ||
          oldS.getTag().getInt(Const.NBT_SELECTED) != newS.getTag().getInt(Const.NBT_SELECTED) || slotChanged) {
        return true;
      }
    }
    return slotChanged;
  }

  @Override
  public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      String effect = CrystalStaffItem.getEffect(stack);
      if (effect != null) {
        ResourceLocation componentName = ResourceLocation.tryParse(effect);
        if (componentName != null) {
          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get().getValue(componentName);
          if (comp != null) {
            int potency = tag.getInt(Const.NBT_POTENCY);
            int efficiency = tag.getInt(Const.NBT_EFFICIENCY);
            int size = tag.getInt(Const.NBT_SIZE);
            comp.castingAction((Player) player, count, potency, efficiency, size);
            if (player.getCommandSenderWorld().isClientSide) {
              if (player.getRandom().nextBoolean()) {
                player.getCommandSenderWorld().addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
                    player.getX() + 2.0 * (player.getRandom().nextFloat() - 0.5), player.getY() + 2.0 * (player.getRandom().nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * (player.getRandom().nextFloat() - 0.5),
                    player.getX(), player.getY() + 1.0, player.getZ());
              }
              else {
                player.getCommandSenderWorld().addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
                    player.getX() + 2.0 * (player.getRandom().nextFloat() - 0.5), player.getY() + 2.0 * (player.getRandom().nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * (player.getRandom().nextFloat() - 0.5),
                    player.getX(), player.getY() + 1.0, player.getZ());
              }
            }
          }
        }
      }
    }
  }

  public static void createData(ItemStack stack) {
    CompoundTag tag = new CompoundTag();
    tag.putInt(Const.NBT_SELECTED, 1);
    tag.putInt(Const.NBT_POTENCY + "1", 0);
    tag.putInt(Const.NBT_POTENCY + "2", 0);
    tag.putInt(Const.NBT_POTENCY + "3", 0);
    tag.putInt(Const.NBT_POTENCY + "4", 0);
    tag.putInt(Const.NBT_EFFICIENCY + "1", 0);
    tag.putInt(Const.NBT_EFFICIENCY + "2", 0);
    tag.putInt(Const.NBT_EFFICIENCY + "3", 0);
    tag.putInt(Const.NBT_EFFICIENCY + "4", 0);
    tag.putInt(Const.NBT_SIZE + "1", 0);
    tag.putInt(Const.NBT_SIZE + "2", 0);
    tag.putInt(Const.NBT_SIZE + "3", 0);
    tag.putInt(Const.NBT_SIZE + "4", 0);
    tag.putString(Const.NBT_EFFECT + "1", "");
    tag.putString(Const.NBT_EFFECT + "2", "");
    tag.putString(Const.NBT_EFFECT + "3", "");
    tag.putString(Const.NBT_EFFECT + "4", "");
    stack.setTag(tag);
  }

  public static void addEffect(ItemStack stack, int slot, String effect, int potency, int efficiency, int size) {
    CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
    tag.putString(Const.NBT_EFFECT + slot, effect);
    tag.putInt(Const.NBT_POTENCY + slot, potency);
    tag.putInt(Const.NBT_EFFICIENCY + slot, efficiency);
    tag.putInt(Const.NBT_SIZE + slot, size);
    stack.setTag(tag);
  }

  public static Integer getPotency(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      return tag.getInt(Const.NBT_POTENCY + tag.getInt(Const.NBT_SELECTED));
    }
    return 0;
  }

  public static Integer getEfficiency(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      return tag.getInt(Const.NBT_EFFICIENCY + tag.getInt(Const.NBT_SELECTED));
    }
    return 0;
  }

  public static Integer getSize(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      return tag.getInt(Const.NBT_SIZE + tag.getInt(Const.NBT_SELECTED));
    }
    return 0;
  }

  public static String getEffect(ItemStack stack) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      return tag.getString(Const.NBT_EFFECT + tag.getInt(Const.NBT_SELECTED));
    }
    return null;
  }

  //Unused?
  public static String getEffect(ItemStack stack, int slot) {
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      return tag.getString(Const.NBT_EFFECT + slot);
    }
    return null;
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level levelAccessor, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, levelAccessor, tooltip, flagIn);
    if (stack.hasTag()) {
      String effect = CrystalStaffItem.getEffect(stack);
      if (effect != null) {
        ResourceLocation compName = ResourceLocation.tryParse(effect);
        if (compName != null) {
          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get().getValue(compName);
          if (comp != null) {
            tooltip.add(Component.translatable("rootsclassic.tooltip.spelltypeheading")
                .append(": ").withStyle(ChatFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
          }
        }
      }
      else {
        //TODO: let people know it's an invalid effect
      }
      tooltip.add(Component.translatable("  +" + CrystalStaffItem.getPotency(stack) + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + CrystalStaffItem.getEfficiency(stack) + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + CrystalStaffItem.getSize(stack) + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
    }
    else {
      tooltip.add(Component.translatable("rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
    }
  }
}
