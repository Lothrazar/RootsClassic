package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ComponentApple extends ComponentBase {

  public ComponentApple() {
    super(Items.APPLE, 16);
  }

  @Override
  public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && caster instanceof Player player) {
      List<MobEffectInstance> effects = new ArrayList<>(player.getActiveEffects());
      player.removeAllEffects();
      for (MobEffectInstance effect : effects) {
//        if (effect.getEffect().getDescriptionId().equals("Soul Fray")) { //TODO: Check if Blood magic uses this name still
//          player.addEffect(effect);
//        }
        if (effect.getEffect() == MobEffects.SLOWNESS) {
          player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, effect.getDuration(), effect.getAmplifier() + (int) potency));
        }
        if (effect.getEffect() == MobEffects.MINING_FATIGUE) {
          player.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, effect.getDuration(), effect.getAmplifier() + (int) potency));
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
        if (effect.getEffect() == MobEffects.NAUSEA) {
          player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, effect.getDuration(), effect.getAmplifier() + (int) potency));
        }
        if (effect.getEffect() == MobEffects.HUNGER) {
          player.addEffect(new MobEffectInstance(MobEffects.SATURATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
        }
        if (effect.getEffect() == MobEffects.WEAKNESS) {
          player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, effect.getDuration(), effect.getAmplifier() + (int) potency));
        }
      }
    }
  }
}
