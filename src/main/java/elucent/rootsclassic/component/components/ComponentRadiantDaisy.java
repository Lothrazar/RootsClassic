package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.common.collect.Lists;
import elucent.rootsclassic.ConfigManager;
import elucent.rootsclassic.PlayerManager;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentEffect;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ComponentRadiantDaisy extends ComponentBase {

  Random random = new Random();

  public ComponentRadiantDaisy() {
    super("radiantdaisy", "Radiance", RegistryManager.radiantDaisy, 24);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!world.isRemote) {
      EntityPlayer player = (EntityPlayer) caster;
      double posX = player.posX + player.getLookVec().xCoord * 0.5;
      double posY = player.posY + 1.5 + player.getLookVec().yCoord * 0.5;
      double posZ = player.posZ + player.getLookVec().zCoord * 0.5;
      double potencyMod = 0;
      double motionX = player.getLookVec().xCoord * 0.25;
      double motionY = player.getLookVec().yCoord * 0.25;
      double motionZ = player.getLookVec().zCoord * 0.25;
      for (int i = 0; i < 200 + 100 * size; i++) {
        boolean didHit = false;
        Roots.proxy.spawnParticleMagicAuraFX(player.getEntityWorld(), posX, posY, posZ, 0, 0, 0, 255, 255, 255);
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        List<EntityLivingBase> targets = (List<EntityLivingBase>) player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - 0.25, posY - 0.25, posZ - 0.25, posX + 0.25, posY + 0.25, posZ + 0.25));
        if (targets.size() > 0) {
          for (int j = 0; j < targets.size() && !didHit; j++) {
            if (targets.get(j).getUniqueID() != player.getUniqueID()) {
              if (targets.get(j) instanceof EntityPlayer && ConfigManager.disablePVP) {}
              else {
                didHit = true;
                targets.get(j).attackEntityFrom(DamageSource.generic, (float) (12 + 3.0 * (potency + potencyMod)));
                targets.get(j).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("blindness"), 40 + (int) (20.0 * (potency + potencyMod)), 0));
                targets.get(j).setLastAttacker(player);
                targets.get(j).setRevengeTarget((EntityLivingBase) player);
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
