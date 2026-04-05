package elucent.rootsclassic.item;

import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.datacomponent.SpellDataList;
import elucent.rootsclassic.registry.RootsComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class CrystalStaffItem extends Item implements IManaRelatedItem {

  public CrystalStaffItem(Properties properties) {
    super(properties
	    .component(RootsComponents.SPELLS, SpellDataList.EMPTY)
	    .component(RootsComponents.SELECTED_SPELL, 1)
    );
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
  public boolean releaseUsing(ItemStack stack, Level levelAccessor, LivingEntity caster, int timeLeft) {
		SpellData selectedSpell = getSelectedSpell(stack);
    if (timeLeft < (72000 - 12) && selectedSpell != null) {
      //BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
      Player player = (Player) caster;
      Identifier compName = Identifier.tryParse(selectedSpell.effect());
      if (compName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
        if (comp == null || !caster.hasData(RootsAttachments.MANA)) {
          return false;
        }

        int potency = selectedSpell.potency() + 1;
        int efficiency = selectedSpell.efficiency();
        int size = selectedSpell.size();
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SylvanArmorItem
            && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SylvanArmorItem
            && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SylvanArmorItem
            && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SylvanArmorItem) {
          potency += 1;
        }
        //        double xpCost = (comp.getManaCost() + potency) * (1.0 - 0.25 * efficiency);
        ManaAttachment manaCap = player.getData(RootsAttachments.MANA);
        if (manaCap.getMana() >= comp.getManaCost() / (efficiency + 1)) {
          //pay mana cost
          manaCap.setMana(manaCap.getMana() - ((comp.getManaCost()) / (efficiency + 1)));
          comp.doEffect(levelAccessor, caster, EnumCastType.SPELL, caster.getX() + 3.0 * caster.getLookAngle().x, caster.getY() + 3.0 * caster.getLookAngle().y,
              caster.getZ() + 3.0 * caster.getLookAngle().z, potency, efficiency, 3.0 + 2.0 * size);
          if (levelAccessor.isClientSide()) {
            for (int i = 0; i < 90; i++) {
              double offX = levelAccessor.getRandom().nextFloat() * 0.5 - 0.25;
              double offY = levelAccessor.getRandom().nextFloat() * 0.5 - 0.25;
              double offZ = levelAccessor.getRandom().nextFloat() * 0.5 - 0.25;
              double coeff = (offX + offY + offZ) / 1.5 + 0.5;
              double dx = (caster.getLookAngle().x + offX) * coeff;
              double dy = (caster.getLookAngle().y + offY) * coeff;
              double dz = (caster.getLookAngle().z + offZ) * coeff;
              if (levelAccessor.getRandom().nextBoolean()) {
                levelAccessor.addParticle(MagicParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
                    caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
              }
              else {
                levelAccessor.addParticle(MagicParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
                    caster.getX() + dx, caster.getY() + 1.5 + dy, caster.getZ() + dz, dx, dy, dz);
              }
            }
          }
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (stack.has(RootsComponents.SELECTED_SPELL)) {
      if (!player.isShiftKeyDown()) {
        if (level.isClientSide() && Minecraft.getInstance().screen != null) {
          return InteractionResult.FAIL;
        }
        else {
          player.startUsingItem(hand);
          return InteractionResult.PASS;
        }
      }
      else {
				int selected = stack.get(RootsComponents.SELECTED_SPELL) + 1;
        if (selected > 4) {
	        selected = 1;
        }
	      stack.set(RootsComponents.SELECTED_SPELL, selected);
        return InteractionResult.FAIL;
      }
    }
    return InteractionResult.FAIL;
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
	  SpellData oldSpell = CrystalStaffItem.getSelectedSpell(oldS);
	  SpellData newSpell = CrystalStaffItem.getSelectedSpell(newS);
		if (oldSpell != null && newSpell != null) {
			if (!oldSpell.effect().equals(newSpell.effect()) ||
				oldS.get(RootsComponents.SELECTED_SPELL) != newS.get(RootsComponents.SELECTED_SPELL) || slotChanged) {
				return true;
			}
		}
    return slotChanged;
  }

  @Override
  public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
    if (stack.has(RootsComponents.SPELLS)) {
			SpellData spell = getSelectedSpell(stack);
      if (spell != null) {
        Identifier componentName = Identifier.tryParse(spell.effect());
        if (componentName != null) {
          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(componentName);
          if (comp != null) {
            int potency = spell.potency();
            int efficiency = spell.efficiency();
            int size = spell.size();
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
  }

  public static void addEffect(ItemStack stack, int slot, String effect, int potency, int efficiency, int size) {
		SpellData data = new SpellData(potency, efficiency, size, effect);
		SpellDataList spells = stack.getOrDefault(RootsComponents.SPELLS, SpellDataList.EMPTY);
		NonNullList<SpellData> newSpellList = NonNullList.createWithCapacity(spells.spellList().size());
		if (slot < 1 || slot > spells.spellList().size()) {
			throw new IndexOutOfBoundsException("Slot " + slot + " is out of bounds for spell list of size " + spells.spellList().size());
		}

    for (int i = 0; i < spells.spellList().size(); i++) {
			if (i == slot - 1) {
				newSpellList.add(data);
			} else {
      	newSpellList.add(spells.spellList().get(i));
			}
		}

		stack.set(RootsComponents.SPELLS, new SpellDataList(newSpellList));
  }

	public static SpellData getSelectedSpell(ItemStack stack) {
		if (stack.has(RootsComponents.SPELLS) && stack.has(RootsComponents.SELECTED_SPELL)) {
			SpellDataList spells = stack.get(RootsComponents.SPELLS);
			if (spells == null) {
				return null;
			}
			int selected = stack.getOrDefault(RootsComponents.SELECTED_SPELL, 1);
			return spells.spellList().get(selected - 1);
		}
		return null;
	}

	@Override
  public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		SpellData selectedSpell = getSelectedSpell(stack);
		if (selectedSpell != null) {
			String effect = selectedSpell.effect();
			Identifier compName = Identifier.tryParse(effect);
			if (compName != null) {
				ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
				if (comp != null) {
					builder.accept(Component.translatable("rootsclassic.tooltip.spelltypeheading")
						.append(": ").withStyle(ChatFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
				}
			}
			builder.accept(Component.translatable("  +" + selectedSpell.potency() + " ")
				.append(Component.translatable("rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
			builder.accept(Component.translatable("  +" + selectedSpell.efficiency() + " ")
				.append(Component.translatable("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
			builder.accept(Component.translatable("  +" + selectedSpell.size() + " ")
				.append(Component.translatable("rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
		} else {
			builder.accept(Component.translatable("rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
		}
  }
}
