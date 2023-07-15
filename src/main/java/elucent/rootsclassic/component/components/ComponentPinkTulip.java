package elucent.rootsclassic.component.components;

import java.util.List;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class ComponentPinkTulip extends ComponentBase {

  public ComponentPinkTulip() {
    super(Blocks.PINK_TULIP, 10);
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
      List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
      targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
      for (LivingEntity target : targets) {
        if (target instanceof Player && RootsConfig.disablePVP.get()) {
          //no pvp allowed
        }
        else {
          target.hurt(spellAttack(caster), (int) (3 + 2 * potency));
          ((LivingEntity) caster).heal(targets.size() * (float) (1.0 + 0.5 * potency));
          target.setLastHurtMob(caster);
          target.setLastHurtByMob((LivingEntity) caster);
        }
      }
    }
  }

  public static DamageSource spellAttack(LivingEntity attacker) {
    return attacker.damageSources().source(RootsDamageTypes.WITHER, attacker);
  }
}
