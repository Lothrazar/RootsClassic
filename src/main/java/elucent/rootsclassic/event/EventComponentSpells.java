package elucent.rootsclassic.event;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventComponentSpells {

  public static int TICKS_PER_MANA_REGEN = 5;

  @SubscribeEvent
  public void onLivingTarget(LivingSetAttackTargetEvent event) {
    EntityLivingBase attackTarget = event.getTarget();
    if (attackTarget instanceof EntityPlayer) {
      Entity entityAttacker = event.getEntity();
      if (entityAttacker.getEntityData().hasKey("RMOD_dontTarget")) {
        if (attackTarget.getUniqueID().toString().equals(
            entityAttacker.getEntityData().getString("RMOD_dontTarget"))) {
          if (event.isCancelable()) {
            event.setCanceled(true);
          }
        }
      }
    }
  }

  @SubscribeEvent
  public void onLivingTick(LivingUpdateEvent event) {
    EntityLivingBase entity = event.getEntityLiving();
    if (entity instanceof EntityPlayer) {
      if (entity.ticksExisted % TICKS_PER_MANA_REGEN == 0) {
        if (entity.hasCapability(RootsCapabilityManager.manaCapability, null)) {
          entity.getCapability(RootsCapabilityManager.manaCapability, null).setMana(entity.getCapability(RootsCapabilityManager.manaCapability, null).getMana() + 1.0f);
        }
      }
    }
    if (entity.getEntityData().hasKey("RMOD_trackTicks")) {
      if (entity.getEntityData().hasKey("RMOD_skipTicks")) {
        if (entity.getEntityData().getInteger("RMOD_skipTicks") > 0) {
          //          if (entity.getHealth() <= 0) {
          //            if (entity.getLastAttackedEntity() instanceof EntityPlayer) {
          //              //              if (!((EntityPlayer) event.getEntityLiving().getLastAttacker()).hasAchievement(RegistryManager.achieveTimeStop)) {
          //              //                PlayerManager.addAchievement((EntityPlayer) event.getEntityLiving().getLastAttacker(), RegistryManager.achieveTimeStop);
          //              //              }
          //            }
          //          }
          entity.getEntityData().setInteger("RMOD_skipTicks", entity.getEntityData().getInteger("RMOD_skipTicks") - 1);
          if (entity.getEntityData().getInteger("RMOD_skipTicks") <= 0) {
            entity.getEntityData().removeTag("RMOD_skipTicks");
            Util.decrementTickTracking(entity);
          }
          event.setCanceled(true);
        }
      }
    }
  }

  @SubscribeEvent
  public void onLivingDrops(LivingDropsEvent event) {
    if (event.getEntityLiving().getEntityData().hasKey("RMOD_dropItems")) {
      if (!event.getEntityLiving().getEntityData().getBoolean("RMOD_dropItems")) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onLivingXP(LivingExperienceDropEvent event) {
    if (event.getEntityLiving().getEntityData().hasKey("RMOD_dropItems")) {
      if (!event.getEntityLiving().getEntityData().getBoolean("RMOD_dropItems")) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public void onLivingDamage(LivingHurtEvent event) {
    EntityLivingBase entityLiving = event.getEntityLiving();
    if (entityLiving.getEntityData().hasKey("RMOD_vuln")) {
      event.setAmount((float) (event.getAmount() * (1.0 + entityLiving.getEntityData().getDouble("RMOD_vuln"))));
      entityLiving.getEntityData().removeTag("RMOD_vuln");
    }
    if (entityLiving.getEntityData().hasKey("RMOD_thornsDamage") && event.getSource().getTrueSource() instanceof EntityLivingBase) {
      ((EntityLivingBase) event.getSource().getTrueSource()).attackEntityFrom(DamageSource.CACTUS, entityLiving.getEntityData().getFloat("RMOD_thornsDamage"));
      entityLiving.getEntityData().removeTag("RMOD_thornsDamage");
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
