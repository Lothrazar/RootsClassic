package elucent.rootsclassic.event;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ComponentSpellsEvent {

  public static int TICKS_PER_MANA_REGEN = 5;

  @SubscribeEvent
  public void onLivingTick(EntityTickEvent.Pre event) {
		if(event.getEntity() instanceof LivingEntity entity) {
			if (event.getEntity() instanceof Player player) {
				// armor regen if full set
				this.wildwoodArmorRegenFullset(player);
				tickManaRegen(entity);
			}
			tickSkipMovementCurse(event, entity);
		}
  }

  private void tickSkipMovementCurse(EntityTickEvent.Pre event, LivingEntity entity) {
    CompoundTag persistentData = entity.getPersistentData();
    if (persistentData.contains(Const.NBT_TRACK_TICKS) && persistentData.contains(Const.NBT_SKIP_TICKS)) {
      int skipTicks = persistentData.getInt(Const.NBT_SKIP_TICKS);
      if (skipTicks > 0) {
        persistentData.putInt(Const.NBT_SKIP_TICKS, skipTicks - 1);
        if (skipTicks <= 0) {
          persistentData.remove(Const.NBT_SKIP_TICKS);
          RootsUtil.decrementTickTracking(entity);
        }
        event.setCanceled(true);
      }
    }
  }

  private void tickManaRegen(LivingEntity entity) {
    if (entity.tickCount % TICKS_PER_MANA_REGEN == 0) {
      ManaAttachment attachment = entity.getData(RootsAttachments.MANA);
			attachment.setMana(attachment.getMana() + 1);
			entity.setData(RootsAttachments.MANA, attachment);
    }
  }

  private void wildwoodArmorRegenFullset(Player entity) {
    ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
    ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
    ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
    ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
    if (head.getItem() == RootsRegistry.WILDWOOD_MASK.get() &&
        chest.getItem() == RootsRegistry.WILDWOOD_PLATE.get() &&
        legs.getItem() == RootsRegistry.WILDWOOD_LEGGINGS.get() &&
        feet.getItem() == RootsRegistry.WILDWOOD_BOOTS.get()) {
      //
      if (entity.level().random.nextDouble() < 0.02 && entity.getHealth() < entity.getMaxHealth()) {
        entity.heal(1);//1 half heart
      }
    }
  }

  @SubscribeEvent
  public void onLivingDrops(LivingDropsEvent event) {
    CompoundTag persistentData = event.getEntity().getPersistentData();
    if (persistentData.contains(Const.NBT_DONT_DROP)) {
      if (!persistentData.getBoolean(Const.NBT_DONT_DROP)) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onLivingXP(LivingExperienceDropEvent event) {
    CompoundTag persistentData = event.getEntity().getPersistentData();
    if (persistentData.contains(Const.NBT_DONT_DROP)) {
      if (!persistentData.getBoolean(Const.NBT_DONT_DROP)) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onLivingDamage(LivingIncomingDamageEvent event) {
    LivingEntity entityLiving = event.getEntity();
    CompoundTag persistentData = entityLiving.getPersistentData();
    if (persistentData.contains(Const.NBT_VULN)) {
      event.setAmount((float) (event.getAmount() * (1.0 + persistentData.getDouble(Const.NBT_VULN))));
      persistentData.remove(Const.NBT_VULN);
    }
    DamageSource source = event.getSource();
    if (persistentData.contains(Const.NBT_THORNS) && source != null && source.getEntity() instanceof LivingEntity) {
      ((LivingEntity) event.getSource().getEntity()).hurt(entityLiving.damageSources().cactus(), persistentData.getFloat(Const.NBT_THORNS));
      persistentData.remove(Const.NBT_THORNS);
      RootsUtil.decrementTickTracking(entityLiving);
    }
    if (entityLiving instanceof Player player) {
      if (!player.getInventory().getSelected().isEmpty() && player.getInventory().getSelected().getItem() == RootsRegistry.ENGRAVED_BLADE.get()) {
        ItemStack sword = player.getInventory().getSelected();
        if (sword.has(RootsComponents.SHADOWSTEP)) {
          int stepLvl = sword.getOrDefault(RootsComponents.SHADOWSTEP, 0);
          double chance = stepLvl * 12.5;
          if (player.getCommandSenderWorld().random.nextInt(100) < chance) {
            event.setCanceled(true);
          }
        }
      }
    }
    if (source != null && source.getEntity() instanceof Player player) {
      if (!event.getEntity().getCommandSenderWorld().isClientSide) {
        if (!player.getInventory().getSelected().isEmpty() && player.getInventory().getSelected().getItem() == RootsRegistry.ENGRAVED_BLADE.get()) {
          ItemStack sword = player.getInventory().getSelected();
	        if (sword.has(RootsComponents.AQUATIC)) {
		        int aquaLvl = sword.getOrDefault(RootsComponents.AQUATIC, 0);
		        float amount = aquaLvl * 0.5f;
		        event.getEntity().hurt(entityLiving.damageSources().drown(), amount);
	        }
	        if (sword.has(RootsComponents.HOLY) && entityLiving.getType().is(EntityTypeTags.UNDEAD)) {
		        int holyLvl = sword.getOrDefault(RootsComponents.HOLY, 0);
		        float amount = holyLvl * 1.5f;
		        float currentAmount = event.getAmount();
		        event.setAmount(currentAmount + amount);
	        }
	        if (sword.has(RootsComponents.SPIKES)) {
		        int spikeLvl = sword.getOrDefault(RootsComponents.SPIKES, 0);
		        float amount = spikeLvl;
		        float currentAmount = event.getAmount();
		        event.setAmount(currentAmount + amount);
	        }
        }
      }
    }
  }
}
