package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ComponentSunflower extends ComponentBase {

  private static final int POTION_DURATION = 15;

  public ComponentSunflower() {
    super(Blocks.SUNFLOWER, 16);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && caster instanceof LivingEntity) {
      int damageDealt = 0;
      List<LivingEntity> targets = world.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
      LivingEntity target;
      for (LivingEntity livingEntity : targets) {
        target = livingEntity;
        if (target.getUUID() == caster.getUUID()) {
          continue; //don't hurt self
        }
        if (target instanceof Player && RootsConfig.COMMON.disablePVP.get()) {
          continue; //no pvp allowed
        }
        if (target.isInvertedHealAndHarm()) {
          damageDealt += (int) (5 + 4 * potency);
          target.hurt(DamageSource.IN_FIRE, damageDealt);
          target.setSecondsOnFire(damageDealt);
          target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, POTION_DURATION, 2 + (int) potency));
          target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, POTION_DURATION, 2 + (int) potency));
        }
        else {
          damageDealt += (int) (3 + 2 * potency);
        }
        target.hurt(DamageSource.IN_FIRE, damageDealt);
        target.setLastHurtMob(caster);
        target.setLastHurtByMob((LivingEntity) caster);
      }
    }
  }
}
