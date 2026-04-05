package elucent.rootsclassic.item;

import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.datacomponent.StaffUses;
import elucent.rootsclassic.registry.RootsComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class StaffItem extends Item implements IManaRelatedItem {

  private static final double RANGE = 3.0; //don't change needs code support
  private static final double SIZE_PER_LEVEL = 2.0;
  private static final double SIZE_BASE = 3.0;

  public StaffItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemUseAnimation getUseAnimation(ItemStack stack) {
    return ItemUseAnimation.BOW;
  }

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

  @Override
  public int getBarWidth(ItemStack stack) {
    if (stack.has(RootsComponents.STAFF_USES)) {
	    StaffUses staffUses = stack.get(RootsComponents.STAFF_USES);
      return Math.round((float) staffUses.uses() * 13.0F / (float) staffUses.maxUses());
    }
    return 1;
  }

  @Override
  public boolean isBarVisible(ItemStack stack) {
	  if (stack.has(RootsComponents.STAFF_USES)) {
		  StaffUses staffUses = stack.get(RootsComponents.STAFF_USES);
      return staffUses.uses() < staffUses.maxUses();
    }
    return false;
  }

  @Override
  public boolean releaseUsing(ItemStack stack, Level level, LivingEntity caster, int timeLeft) {
    if (timeLeft < (72000 - 12) && stack.has(RootsComponents.STAFF_USES) && stack.has(RootsComponents.SPELL)
	    && caster.hasData(RootsAttachments.MANA)) {
			StaffUses staffUses = stack.get(RootsComponents.STAFF_USES);
      if (staffUses.uses() >= 0) {
				staffUses = new StaffUses(staffUses.uses() - 1, staffUses.maxUses());
				stack.set(RootsComponents.STAFF_USES, staffUses);

	      SpellData spellData = stack.get(RootsComponents.SPELL);
        Identifier compName = Identifier.tryParse(spellData.effect());
        if (compName != null) {
          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
          if (comp != null) {
	          int potency = spellData.potency();
	          int efficiency = spellData.efficiency();
	          int size = spellData.size();
            Player player = (Player) caster;
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SylvanArmorItem
                && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SylvanArmorItem) {
              potency += 1;
            }
	          ManaAttachment mana = caster.getData(RootsAttachments.MANA);
            if (mana.getMana() >= comp.getManaCost() / (efficiency + 1)) {
	            mana.setMana(mana.getMana() - (comp.getManaCost() / (efficiency + 1)));
							caster.setData(RootsAttachments.MANA, mana);

              Vec3 lookVec = caster.getLookAngle();
              comp.doEffect(level, caster, EnumCastType.SPELL,
                  caster.getX() + RANGE * lookVec.x,
                  caster.getY() + RANGE * lookVec.y,
                  caster.getZ() + RANGE * lookVec.z, potency, efficiency,
                  SIZE_BASE + SIZE_PER_LEVEL * size);
              if (level.isClientSide()) {
                for (int i = 0; i < 90; i++) {
                  double offX = level.getRandom().nextFloat() * 0.5 - 0.25;
                  double offY = level.getRandom().nextFloat() * 0.5 - 0.25;
                  double offZ = level.getRandom().nextFloat() * 0.5 - 0.25;
                  double coeff = (offX + offY + offZ) / 1.5 + 0.5;
                  double dx = (lookVec.x + offX) * coeff;
                  double dy = (lookVec.y + offY) * coeff;
                  double dz = (lookVec.z + offZ) * coeff;
                  if (level.getRandom().nextBoolean()) {
                    level.addParticle(MagicParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
                        caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
                  }
                  else {
                    level.addParticle(MagicParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
                        caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
                  }
                }
              }
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand hand) {
    if (level.isClientSide() && Minecraft.getInstance().screen != null) {
      return InteractionResult.FAIL;
    }
    else {
      player.startUsingItem(hand);
      return InteractionResult.PASS;
    }
  }

  @Override
  public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
	  if (itemStack.has(RootsComponents.STAFF_USES)) {
		  StaffUses staffUses = itemStack.get(RootsComponents.STAFF_USES);
      if (staffUses.uses() <= 0 && owner instanceof Player) {
        itemStack.shrink(1);
        if (owner instanceof Player player) {
	        player.awardStat(Stats.ITEM_BROKEN.get(itemStack.getItem()));
        }
      }
    }
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    if (oldS.has(RootsComponents.SPELL) && newS.has(RootsComponents.SPELL)) {
      if (!oldS.get(RootsComponents.SPELL).equals(newS.get(RootsComponents.SPELL))) {
        return true;
      }
    }
    return slotChanged;
  }

  @Override
  public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
    if (stack.has(RootsComponents.SPELL)) {
			SpellData spellData = stack.get(RootsComponents.SPELL);
      Identifier componentName = Identifier.tryParse(spellData.effect());
      if (componentName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(componentName);
        if (comp != null) {
          int potency = spellData.potency();
          int efficiency = spellData.efficiency();
          int size = spellData.size();
          comp.castingAction((Player) player, count, potency, efficiency, size);
          if (level.isClientSide()) {
            if (player.getRandom().nextBoolean()) {
              level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
                  player.getX() + 2.0 * (player.getRandom().nextFloat() - 0.5), player.getY() + 2.0 * (player.getRandom().nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * (player.getRandom().nextFloat() - 0.5),
                  player.getX(), player.getY() + 1.0, player.getZ());
            }
            else {
              level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
                  player.getX() + 2.0 * (player.getRandom().nextFloat() - 0.5), player.getY() + 2.0 * (player.getRandom().nextFloat() - 0.5) + 1.0, player.getZ() + 2.0 * (player.getRandom().nextFloat() - 0.5),
                  player.getX(), player.getY() + 1.0, player.getZ());
            }
          }
        }
      }
    }
  }

  public static void createData(ItemStack stack, String effect, int potency, int efficiency, int size) {
		stack.set(RootsComponents.SPELL, new SpellData(potency, efficiency, size, effect));
    int uses = getMaxUsesBase() + getMaxUsesPerEfficiency() * efficiency;
		stack.set(RootsComponents.STAFF_USES, new StaffUses(uses, uses));
  }

  public static int getMaxUsesBase() {
    return RootsConfig.COMMON.staffUsesBasic.get();
  }

  public static int getMaxUsesPerEfficiency() {
    return RootsConfig.COMMON.staffUsesEfficiency.get();
  }

	@Override
  public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
    if (stack.has(RootsComponents.STAFF_USES) && stack.has(RootsComponents.SPELL)) {
	    SpellData spellData = stack.get(RootsComponents.SPELL);
	    StaffUses staffUses = stack.get(RootsComponents.STAFF_USES);

      Identifier compName = Identifier.tryParse(spellData.effect());
      if (compName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
        if (comp != null) {
          builder.accept(Component.translatable("rootsclassic.tooltip.spelltypeheading")
              .append(": ").withStyle(ChatFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
        }
        else {
          //TODO: let people know it's an invalid effect
        }
      }
      builder.accept(Component.translatable("  +" + spellData.potency() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
      builder.accept(Component.translatable("  +" + spellData.efficiency() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
      builder.accept(Component.translatable("  +" + spellData.size() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
      builder.accept(Component.empty());
      builder.accept(Component.translatable(staffUses.uses() + " ")
          .append(Component.translatable("rootsclassic.tooltip.usesremaining")).append(".").withStyle(ChatFormatting.GOLD));
    }
    else {
      builder.accept(Component.translatable("rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
    }
  }
  //	@SideOnly(Side.CLIENT)
  //	public void initModel() {
  //		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "_0", "inventory"));
  //		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "_1", "inventory"));
  //	}
}
