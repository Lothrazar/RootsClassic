package elucent.rootsclassic.component.components;

import java.util.List;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class ComponentSunflower extends ComponentBase {

  private static final int POTION_DURATION = 15;

  public ComponentSunflower() {
    super(Blocks.SUNFLOWER, 16);
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
      int damageDealt = 0;
      List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
      targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
      for (LivingEntity target : targets) {
        if (target instanceof Player && RootsConfig.disablePVP.get()) {
          //no pvp allowed
        }
        else {
          if (target.isInvertedHealAndHarm()) {
            damageDealt += (int) (5 + 4 * potency);
            target.hurt(spellAttack(caster), damageDealt);
            target.setSecondsOnFire(damageDealt);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, POTION_DURATION, 2 + (int) potency));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, POTION_DURATION, 2 + (int) potency));
          }
          else {
            damageDealt += (int) (3 + 2 * potency);
          }
          target.hurt(caster.damageSources().inFire(), damageDealt);
          target.setLastHurtMob(caster);
          target.setLastHurtByMob(caster);
        }
      }
    }
  }

  public static DamageSource spellAttack(LivingEntity attacker) {
    return attacker.damageSources().source(RootsDamageTypes.FIRE, attacker);
  }
}
