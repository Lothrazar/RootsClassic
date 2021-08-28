package elucent.rootsclassic.component.components;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;

public class ComponentRadiantDaisy extends ComponentBase {

  public ComponentRadiantDaisy() {
    super(new ResourceLocation(Const.MODID, "radiant_daisy"), RootsRegistry.RADIANT_DAISY.get(), 24);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!world.isRemote) {
      PlayerEntity player = (PlayerEntity) caster;
      double posX = player.getPosX() + player.getLookVec().x * 0.5;
      double posY = player.getPosY() + 1.5 + player.getLookVec().y * 0.5;
      double posZ = player.getPosZ() + player.getLookVec().z * 0.5;
      double potencyMod = 0;
      double motionX = player.getLookVec().x * 0.25;
      double motionY = player.getLookVec().y * 0.25;
      double motionZ = player.getLookVec().z * 0.25;
      for (int i = 0; i < 200 + 100 * size; i++) {
        boolean didHit = false;
        player.getEntityWorld().addParticle(MagicAuraParticleData.createData(255, 255, 255),
            posX, posY, posZ, 0, 0, 0);
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        List<LivingEntity> targets = player.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(posX - 0.25, posY - 0.25, posZ - 0.25, posX + 0.25, posY + 0.25, posZ + 0.25));
        if (targets.size() > 0) {
          for (int j = 0; j < targets.size() && !didHit; j++) {
            if (targets.get(j).getUniqueID() != player.getUniqueID()) {
              if (targets.get(j) instanceof PlayerEntity && RootsConfig.COMMON.disablePVP.get()) {}
              else {
                didHit = true;
                targets.get(j).attackEntityFrom(DamageSource.GENERIC, (float) (12 + 3.0 * (potency + potencyMod)));
                targets.get(j).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 40 + (int) (20.0 * (potency + potencyMod)), 0));
                targets.get(j).setLastAttackedEntity(player);
                targets.get(j).setRevengeTarget(player);
              }
            }
          }
        }
        if (player.getEntityWorld().getBlockState(new BlockPos(posX, posY, posZ)).isSolid()) {
          if (potency - potencyMod == -1.0) {
            didHit = true;
          }
          else {
            potencyMod -= 0.5;
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX + 1.0, posY, posZ)).isSolid() && motionX < 0) {
              motionX *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX - 1.0, posY, posZ)).isSolid() && motionX > 0) {
              motionX *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY + 1.0, posZ)).isSolid() && motionY < 0) {
              motionY *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY - 1.0, posZ)).isSolid() && motionY > 0) {
              motionY *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY, posZ + 1.0)).isSolid() && motionZ < 0) {
              motionZ *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY, posZ - 1.0)).isSolid() && motionZ > 0) {
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
