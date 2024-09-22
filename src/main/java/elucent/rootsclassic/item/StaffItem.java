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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StaffItem extends Item implements IManaRelatedItem {

  private static final double RANGE = 3.0; //don't change needs code support
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
  public void releaseUsing(ItemStack stack, Level levelAccessor, LivingEntity caster, int timeLeft) {
    if (timeLeft < (72000 - 12) && stack.has(RootsComponents.STAFF_USES) && stack.has(RootsComponents.SPELL)
	    && caster.hasData(RootsAttachments.MANA)) {
			StaffUses staffUses = stack.get(RootsComponents.STAFF_USES);
      if (staffUses.uses() >= 0) {
				staffUses = new StaffUses(staffUses.uses() - 1, staffUses.maxUses());
				stack.set(RootsComponents.STAFF_USES, staffUses);

	      SpellData spellData = stack.get(RootsComponents.SPELL);
        ResourceLocation compName = ResourceLocation.tryParse(spellData.effect());
        if (compName != null) {
          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get(compName);
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
              comp.doEffect(levelAccessor, caster, EnumCastType.SPELL,
                  caster.getX() + RANGE * lookVec.x,
                  caster.getY() + RANGE * lookVec.y,
                  caster.getZ() + RANGE * lookVec.z, potency, efficiency,
                  SIZE_BASE + SIZE_PER_LEVEL * size);
              if (levelAccessor.isClientSide) {
                for (int i = 0; i < 90; i++) {
                  double offX = levelAccessor.random.nextFloat() * 0.5 - 0.25;
                  double offY = levelAccessor.random.nextFloat() * 0.5 - 0.25;
                  double offZ = levelAccessor.random.nextFloat() * 0.5 - 0.25;
                  double coeff = (offX + offY + offZ) / 1.5 + 0.5;
                  double dx = (lookVec.x + offX) * coeff;
                  double dy = (lookVec.y + offY) * coeff;
                  double dz = (lookVec.z + offZ) * coeff;
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
    }
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level levelAccessor, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (levelAccessor.isClientSide && Minecraft.getInstance().screen != null) {
      return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }
    else {
      player.startUsingItem(hand);
      return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }
  }

  @Override
  public void inventoryTick(ItemStack stack, Level levelAccessor, Entity entityIn, int itemSlot, boolean isSelected) {
	  if (stack.has(RootsComponents.STAFF_USES)) {
		  StaffUses staffUses = stack.get(RootsComponents.STAFF_USES);
      if (staffUses.uses() <= 0 && entityIn instanceof Player) {
        stack.shrink(1);
        if (entityIn instanceof Player player) {
	        player.awardStat(Stats.ITEM_BROKEN.get(stack.getItem()));
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
      ResourceLocation componentName = ResourceLocation.tryParse(spellData.effect());
      if (componentName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get(componentName);
        if (comp != null) {
          int potency = spellData.potency();
          int efficiency = spellData.efficiency();
          int size = spellData.size();
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
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltip, tooltipFlag);
    if (stack.has(RootsComponents.STAFF_USES) && stack.has(RootsComponents.SPELL)) {
	    SpellData spellData = stack.get(RootsComponents.SPELL);
	    StaffUses staffUses = stack.get(RootsComponents.STAFF_USES);

      ResourceLocation compName = ResourceLocation.tryParse(spellData.effect());
      if (compName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get(compName);
        if (comp != null) {
          tooltip.add(Component.translatable("rootsclassic.tooltip.spelltypeheading")
              .append(": ").withStyle(ChatFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
        }
        else {
          //TODO: let people know it's an invalid effect
        }
      }
      tooltip.add(Component.translatable("  +" + spellData.potency() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + spellData.efficiency() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + spellData.size() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.empty());
      tooltip.add(Component.translatable(staffUses.uses() + " ")
          .append(Component.translatable("rootsclassic.tooltip.usesremaining")).append(".").withStyle(ChatFormatting.GOLD));
    }
    else {
      tooltip.add(Component.translatable("rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
    }
  }
  //	@SideOnly(Side.CLIENT)
  //	public void initModel() {
  //		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "_0", "inventory"));
  //		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "_1", "inventory"));
  //	}
}
