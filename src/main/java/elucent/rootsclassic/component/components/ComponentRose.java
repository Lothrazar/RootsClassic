package elucent.rootsclassic.component.components;

import java.util.List;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsDamageTypes;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class ComponentRose extends ComponentBase {

  public ComponentRose() {
    super(Blocks.ROSE_BUSH, 14);
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
      List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
      // int damageDealt = 0;
      targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
      for (LivingEntity target : targets) {
        if (target instanceof Player && RootsConfig.disablePVP.get()) {
          //no pvp allowed
        }
        else {
          //            if (caster instanceof EntityPlayer) {
          //              if (!((EntityPlayer) caster).hasAchievement(RegistryManager.achieveSpellRose)) {
          //                PlayerManager.addAchievement(((EntityPlayer) caster), RegistryManager.achieveSpellRose);
          //              }
          //            }
          target.hurt(spellAttack(caster), (int) (9 + 2 * potency));
          RootsUtil.addTickTracking(target);
          target.getPersistentData().putFloat("RMOD_thornsDamage", 2.0f + (float) potency);
          if (caster instanceof Player player) {
            target.setLastHurtByPlayer(player);
          }
          else {
            target.setLastHurtByMob(caster);
          }
        }
      }
      //      if (damageDealt > 80) {
      //        if (caster instanceof EntityPlayer) {
      //          if (!((EntityPlayer) caster).hasAchievement(RegistryManager.achieveLotsDamage)) {
      //            PlayerManager.addAchievement(((EntityPlayer) caster), RegistryManager.achieveLotsDamage);
      //          }
      //        }
      //      }
    }
  }

  public static DamageSource spellAttack(LivingEntity attacker) {
    return attacker.damageSources().source(RootsDamageTypes.CACTUS, attacker);
  }
}
