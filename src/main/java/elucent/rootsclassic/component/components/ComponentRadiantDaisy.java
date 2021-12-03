package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ComponentRadiantDaisy extends ComponentBase {

  public ComponentRadiantDaisy() {
    super(new ResourceLocation(Const.MODID, "radiant_daisy"), RootsRegistry.RADIANT_DAISY.get(), 24);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    Player player = (Player) caster;
    double posX = player.getX() + player.getLookAngle().x * 0.5;
    double posY = player.getY() + 1.5 + player.getLookAngle().y * 0.5;
    double posZ = player.getZ() + player.getLookAngle().z * 0.5;
    double potencyMod = 0;
    double motionX = player.getLookAngle().x * 0.25;
    double motionY = player.getLookAngle().y * 0.25;
    double motionZ = player.getLookAngle().z * 0.25;
    for (int i = 0; i < 200 + 100 * size; i++) {
      boolean didHit = false;
      if(world.isClientSide) {
        world.addParticle(MagicAuraParticleData.createData(255, 255, 255),
                posX, posY, posZ, 0, 0, 0);
      }
      if(!world.isClientSide) {
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        List<LivingEntity> targets = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, new AABB(posX - 0.25, posY - 0.25, posZ - 0.25, posX + 0.25, posY + 0.25, posZ + 0.25));
        if (targets.size() > 0) {
          for (int j = 0; j < targets.size() && !didHit; j++) {
            if (targets.get(j).getUUID() != player.getUUID()) {
              if (targets.get(j) instanceof Player && RootsConfig.COMMON.disablePVP.get()) {}
              else {
                didHit = true;
                targets.get(j).hurt(DamageSource.GENERIC, (float) (12 + 3.0 * (potency + potencyMod)));
                targets.get(j).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40 + (int) (20.0 * (potency + potencyMod)), 0));
                targets.get(j).setLastHurtMob(player);
                targets.get(j).setLastHurtByMob(player);
              }
            }
          }
        }
        if (player.getCommandSenderWorld().getBlockState(new BlockPos(posX, posY, posZ)).canOcclude()) {
          if (potency - potencyMod == -1.0) {
            didHit = true;
          }
          else {
            potencyMod -= 0.5;
            if (!player.getCommandSenderWorld().getBlockState(new BlockPos(posX + 1.0, posY, posZ)).canOcclude() && motionX < 0) {
              motionX *= -1;
            }
            if (!player.getCommandSenderWorld().getBlockState(new BlockPos(posX - 1.0, posY, posZ)).canOcclude() && motionX > 0) {
              motionX *= -1;
            }
            if (!player.getCommandSenderWorld().getBlockState(new BlockPos(posX, posY + 1.0, posZ)).canOcclude() && motionY < 0) {
              motionY *= -1;
            }
            if (!player.getCommandSenderWorld().getBlockState(new BlockPos(posX, posY - 1.0, posZ)).canOcclude() && motionY > 0) {
              motionY *= -1;
            }
            if (!player.getCommandSenderWorld().getBlockState(new BlockPos(posX, posY, posZ + 1.0)).canOcclude() && motionZ < 0) {
              motionZ *= -1;
            }
            if (!player.getCommandSenderWorld().getBlockState(new BlockPos(posX, posY, posZ - 1.0)).canOcclude() && motionZ > 0) {
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
