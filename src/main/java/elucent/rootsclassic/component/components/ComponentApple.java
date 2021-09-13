package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;

public class ComponentApple extends ComponentBase {

  public ComponentApple() {
    super(new ResourceLocation(Const.MODID, "apple"), Items.APPLE, 16);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof Player) {
        Player player = (Player) caster;
        ArrayList<MobEffectInstance> effects = new ArrayList<>(player.getActiveEffects());
        player.removeAllEffects();
        for (MobEffectInstance effect : effects) {
          if (effect.getEffect().getDescriptionId().equals("Soul Fray")) { //TODO: Check if Blood magic uses this name still
            player.addEffect(effect);
          }
          if (effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == MobEffects.DIG_SLOWDOWN) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == MobEffects.POISON) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == MobEffects.WITHER) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == MobEffects.BLINDNESS) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == MobEffects.CONFUSION) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == MobEffects.HUNGER) {
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == MobEffects.WEAKNESS) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
        }
      }
    }
  }
}
