package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ComponentApple extends ComponentBase {

  Random random = new Random();

  public ComponentApple() {
    super("apple", Items.APPLE, 16);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof EntityPlayer) {
        ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>(((EntityPlayer) caster).getActivePotionEffects());
        ((EntityPlayer) caster).clearActivePotions();
        for (int i = 0; i < effects.size(); i++) {
          PotionEffect effect = effects.get(i);
          if (effect.getPotion().getName() == "Soul Fray") {
            ((EntityPlayer) caster).addPotionEffect(effect);
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("slowness")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("speed"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("mining_fatigue")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("haste"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("poison")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("wither")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("blindness")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("nausea")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("hunger")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("saturation"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getPotion() == Potion.getPotionFromResourceLocation("weakness")) {
            ((EntityPlayer) caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("strength"), effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
        }
      }
    }
  }
}
