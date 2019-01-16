package elucent.rootsclassic.event;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventComponentSpells {

  private static final String NBT_THORNS = "RMOD_thornsDamage";
  private static final String NBT_VULN = "RMOD_vuln";
  private static final String NBT_DONT_DROP = "RMOD_dropItems";
  private static final String NBT_SKIP_TICKS = "RMOD_skipTicks";
  public static int TICKS_PER_MANA_REGEN = 5;

  @SubscribeEvent
  public void onLivingTick(LivingUpdateEvent event) {
    EntityLivingBase entity = event.getEntityLiving();
    if (entity instanceof EntityPlayer) {
      // armor regen if full set 
      this.druidArmorRegenFullset((EntityPlayer) entity);
      tickManaRegen(entity);
    }
    tickSkipMovementCurse(event, entity);
  }

  private void tickSkipMovementCurse(LivingUpdateEvent event, EntityLivingBase entity) {
    if (entity.getEntityData().hasKey(Util.NBT_TRACK_TICKS)) {
      if (entity.getEntityData().hasKey(NBT_SKIP_TICKS)) {
        if (entity.getEntityData().getInteger(NBT_SKIP_TICKS) > 0) {
          entity.getEntityData().setInteger(NBT_SKIP_TICKS, entity.getEntityData().getInteger(NBT_SKIP_TICKS) - 1);
          if (entity.getEntityData().getInteger(NBT_SKIP_TICKS) <= 0) {
            entity.getEntityData().removeTag(NBT_SKIP_TICKS);
            Util.decrementTickTracking(entity);
          }
          event.setCanceled(true);
        }
      }
    }
  }

  private void tickManaRegen(EntityLivingBase entity) {
    if (entity.ticksExisted % TICKS_PER_MANA_REGEN == 0) {
      if (entity.hasCapability(RootsCapabilityManager.manaCapability, null)) {
        entity.getCapability(RootsCapabilityManager.manaCapability, null).setMana(entity.getCapability(RootsCapabilityManager.manaCapability, null).getMana() + 1.0f);
      }
    }
  }

  private void druidArmorRegenFullset(EntityPlayer entity) {
    ItemStack head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
    ItemStack chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
    ItemStack legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
    ItemStack feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
    if (head.getItem() == RegistryManager.druidArmorHead &&
        chest.getItem() == RegistryManager.druidArmorChest &&
        legs.getItem() == RegistryManager.druidArmorLegs &&
        feet.getItem() == RegistryManager.druidArmorBoots) {
      //
      if (entity.world.rand.nextDouble() < 0.02 && entity.getHealth() < entity.getMaxHealth()) {
        entity.heal(1);//1 half heart 
      }
    }
  }

  @SubscribeEvent
  public void onLivingDrops(LivingDropsEvent event) {
    if (event.getEntityLiving().getEntityData().hasKey(NBT_DONT_DROP)) {
      if (!event.getEntityLiving().getEntityData().getBoolean(NBT_DONT_DROP)) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onLivingXP(LivingExperienceDropEvent event) {
    if (event.getEntityLiving().getEntityData().hasKey(NBT_DONT_DROP)) {
      if (!event.getEntityLiving().getEntityData().getBoolean(NBT_DONT_DROP)) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onLivingDamage(LivingHurtEvent event) {
    EntityLivingBase entityLiving = event.getEntityLiving();
    if (entityLiving.getEntityData().hasKey(NBT_VULN)) {
      event.setAmount((float) (event.getAmount() * (1.0 + entityLiving.getEntityData().getDouble(NBT_VULN))));
      entityLiving.getEntityData().removeTag(NBT_VULN);
    }
    if (entityLiving.getEntityData().hasKey(NBT_THORNS) && event.getSource().getTrueSource() instanceof EntityLivingBase) {
      ((EntityLivingBase) event.getSource().getTrueSource()).attackEntityFrom(DamageSource.CACTUS, entityLiving.getEntityData().getFloat(NBT_THORNS));
      entityLiving.getEntityData().removeTag(NBT_THORNS);
      Util.decrementTickTracking(entityLiving);
    }
    if (entityLiving instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) entityLiving;
      if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == RegistryManager.engravedSword) {
        ItemStack sword = player.inventory.getCurrentItem();
        if (sword.hasTagCompound() && sword.getTagCompound().hasKey("shadowstep")) {
          int stepLvl = sword.getTagCompound().getInteger("shadowstep");
          double chance = stepLvl * 12.5;
          if (player.getEntityWorld().rand.nextInt(100) < chance) {
            event.setCanceled(true);
          }
        }
      }
    }
    if (event.getSource().getTrueSource() instanceof EntityPlayer) {
      if (!event.getEntity().getEntityWorld().isRemote) {
        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == RegistryManager.engravedSword) {
          ItemStack sword = player.inventory.getCurrentItem();
          if (sword.hasTagCompound() && sword.getTagCompound().hasKey("aquatic")) {
            int aquaLvl = sword.getTagCompound().getInteger("aquatic");
            float amount = aquaLvl * 0.5f;
            event.getEntity().attackEntityFrom(DamageSource.DROWN, amount);
          }
          if ((sword.hasTagCompound() && sword.getTagCompound().hasKey("holy")) && entityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            int holyLvl = sword.getTagCompound().getInteger("holy");
            float amount = holyLvl * 1.5f;
            float currentAmount = event.getAmount();
            event.setAmount(currentAmount + amount);
          }
          if (sword.hasTagCompound() && sword.getTagCompound().hasKey("spikes")) {
            int spikeLvl = sword.getTagCompound().getInteger("spikes");
            float amount = spikeLvl;
            float currentAmount = event.getAmount();
            event.setAmount(currentAmount + amount);
          }
        }
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onTextureStitch(TextureStitchEvent event) {
    event.getMap().registerSprite(Const.magicParticle);
  }
}
