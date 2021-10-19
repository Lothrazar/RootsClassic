package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;

public class ComponentApple extends ComponentBase {

  public ComponentApple() {
    super(new ResourceLocation(Const.MODID, "apple"), Items.APPLE, 16);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof PlayerEntity) {
        PlayerEntity player = (PlayerEntity) caster;
        ArrayList<EffectInstance> effects = new ArrayList<>(player.getActiveEffects());
        player.removeAllEffects();
        for (EffectInstance effect : effects) {
          if (effect.getEffect().getDescriptionId().equals("Soul Fray")) { //TODO: Check if Blood magic uses this name still
            player.addEffect(effect);
          }
          if (effect.getEffect() == Effects.MOVEMENT_SLOWDOWN) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == Effects.DIG_SLOWDOWN) {
            player.addEffect(new EffectInstance(Effects.DIG_SPEED, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == Effects.POISON) {
            player.addEffect(new EffectInstance(Effects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == Effects.WITHER) {
            player.addEffect(new EffectInstance(Effects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == Effects.BLINDNESS) {
            player.addEffect(new EffectInstance(Effects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == Effects.CONFUSION) {
            player.addEffect(new EffectInstance(Effects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == Effects.HUNGER) {
            player.addEffect(new EffectInstance(Effects.SATURATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
          if (effect.getEffect() == Effects.WEAKNESS) {
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, effect.getDuration(), effect.getAmplifier() + (int) potency));
          }
        }
      }
    }
  }
}
