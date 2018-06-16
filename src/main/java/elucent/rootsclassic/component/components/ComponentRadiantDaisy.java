package elucent.rootsclassic.component.components;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.ConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentRadiantDaisy extends ComponentBase {

  Random random = new Random();

  public ComponentRadiantDaisy() {
    super("radiantdaisy", RegistryManager.radiantDaisy, 24);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!world.isRemote) {
      EntityPlayer player = (EntityPlayer) caster;
      double posX = player.posX + player.getLookVec().x * 0.5;
      double posY = player.posY + 1.5 + player.getLookVec().y * 0.5;
      double posZ = player.posZ + player.getLookVec().z * 0.5;
      double potencyMod = 0;
      double motionX = player.getLookVec().x * 0.25;
      double motionY = player.getLookVec().y * 0.25;
      double motionZ = player.getLookVec().z * 0.25;
      for (int i = 0; i < 200 + 100 * size; i++) {
        boolean didHit = false;
        Roots.proxy.spawnParticleMagicAuraFX(player.getEntityWorld(), posX, posY, posZ, 0, 0, 0, 255, 255, 255);
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        List<EntityLivingBase> targets = player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - 0.25, posY - 0.25, posZ - 0.25, posX + 0.25, posY + 0.25, posZ + 0.25));
        if (targets.size() > 0) {
          for (int j = 0; j < targets.size() && !didHit; j++) {
            if (targets.get(j).getUniqueID() != player.getUniqueID()) {
              if (targets.get(j) instanceof EntityPlayer && ConfigManager.disablePVP) {}
              else {
                didHit = true;
                targets.get(j).attackEntityFrom(DamageSource.GENERIC, (float) (12 + 3.0 * (potency + potencyMod)));
                targets.get(j).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("blindness"), 40 + (int) (20.0 * (potency + potencyMod)), 0));
                targets.get(j).setLastAttackedEntity(player);
                targets.get(j).setRevengeTarget(player);
              }
            }
          }
        }
        if (player.getEntityWorld().getBlockState(new BlockPos(posX, posY, posZ)).isFullCube()) {
          if (potency - potencyMod == -1.0) {
            didHit = true;
          }
          else {
            potencyMod -= 0.5;
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX + 1.0, posY, posZ)).isFullCube() && motionX < 0) {
              motionX *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX - 1.0, posY, posZ)).isFullCube() && motionX > 0) {
              motionX *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY + 1.0, posZ)).isFullCube() && motionY < 0) {
              motionY *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY - 1.0, posZ)).isFullCube() && motionY > 0) {
              motionY *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY, posZ + 1.0)).isFullCube() && motionZ < 0) {
              motionZ *= -1;
            }
            if (!player.getEntityWorld().getBlockState(new BlockPos(posX, posY, posZ - 1.0)).isFullCube() && motionZ > 0) {
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
