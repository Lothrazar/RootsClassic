package elucent.rootsclassic.component.components;

import java.util.List;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsDamageTypes;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ComponentRadiantDaisy extends ComponentBase {

  public ComponentRadiantDaisy() {
    super(RootsRegistry.RADIANT_DAISY.get(), 24);
  }

  @Override
  public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (caster instanceof LivingEntity livingCaster) {
      double posX = livingCaster.getX() + livingCaster.getLookAngle().x * 0.5;
      double posY = livingCaster.getY() + 1.5 + livingCaster.getLookAngle().y * 0.5;
      double posZ = livingCaster.getZ() + livingCaster.getLookAngle().z * 0.5;
      double potencyMod = 0;
      double motionX = livingCaster.getLookAngle().x * 0.25;
      double motionY = livingCaster.getLookAngle().y * 0.25;
      double motionZ = livingCaster.getLookAngle().z * 0.25;
      for (int i = 0; i < 200 + 100 * size; i++) {
        boolean didHit = false;
        if (level.isClientSide) {
          level.addParticle(MagicAuraParticleData.createData(255, 255, 255),
              posX, posY, posZ, 0, 0, 0);
        }
        if (!level.isClientSide) {
          posX += motionX;
          posY += motionY;
          posZ += motionZ;
          List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(posX - 0.25, posY - 0.25, posZ - 0.25, posX + 0.25, posY + 0.25, posZ + 0.25));
          if (!targets.isEmpty()) {
            targets.removeIf(target -> target.getUUID() == livingCaster.getUUID());
            for (int j = 0; j < targets.size() && !didHit; j++) {
              if (targets.get(j) instanceof Player && RootsConfig.disablePVP.get()) {
                //no pvp allowed
              }
              else {
                didHit = true;
                targets.get(j).hurt(spellAttack(livingCaster), (float) (12 + 3.0 * (potency + potencyMod)));
                targets.get(j).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40 + (int) (20.0 * (potency + potencyMod)), 0));
                targets.get(j).setLastHurtMob(livingCaster);
                targets.get(j).setLastHurtByMob(livingCaster);
              }
            }
          }
          if (level.getBlockState(BlockPos.containing(posX, posY, posZ)).canOcclude()) {
            if (potency - potencyMod == -1.0) {
              didHit = true;
            }
            else {
              potencyMod -= 0.5;
              if (!level.getBlockState(BlockPos.containing(posX + 1.0, posY, posZ)).canOcclude() && motionX < 0) {
                motionX *= -1;
              }
              if (!level.getBlockState(BlockPos.containing(posX - 1.0, posY, posZ)).canOcclude() && motionX > 0) {
                motionX *= -1;
              }
              if (!level.getBlockState(BlockPos.containing(posX, posY + 1.0, posZ)).canOcclude() && motionY < 0) {
                motionY *= -1;
              }
              if (!level.getBlockState(BlockPos.containing(posX, posY - 1.0, posZ)).canOcclude() && motionY > 0) {
                motionY *= -1;
              }
              if (!level.getBlockState(BlockPos.containing(posX, posY, posZ + 1.0)).canOcclude() && motionZ < 0) {
                motionZ *= -1;
              }
              if (!level.getBlockState(BlockPos.containing(posX, posY, posZ - 1.0)).canOcclude() && motionZ > 0) {
                motionZ *= -1;
              }
            }
          }
          if (didHit) {
            return;
          }
        }
      }
    }
  }

  public static DamageSource spellAttack(LivingEntity attacker) {
    return attacker.damageSources().source(RootsDamageTypes.GENERIC, attacker);
  }
}
