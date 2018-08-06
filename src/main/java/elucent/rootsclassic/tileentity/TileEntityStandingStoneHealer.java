package elucent.rootsclassic.tileentity;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityStandingStoneHealer extends TEBase implements ITickable {

  private static final int RADIUS = 10;
  private static final int PTN_SECONDS = 25;
  int ticker = 0;
  Random random = new Random();

  public TileEntityStandingStoneHealer() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    return tag;
  }

  @Override
  public void update() {
    ticker++;
    if (ticker % 5 == 0) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        Roots.proxy.spawnParticleMagicAuraFX(this.getWorld(), pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0, 255, 32, 32);
      }
    }
    if (ticker % 20 == 0) {
      ArrayList<EntityLivingBase> nearbyCreatures = (ArrayList<EntityLivingBase>) this.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() - RADIUS, pos.getY() - RADIUS, pos.getZ() - RADIUS, pos.getX() + RADIUS, pos.getY() + RADIUS, pos.getZ() + RADIUS));
      for (int i = 0; i < nearbyCreatures.size(); i++) {
        nearbyCreatures.get(i).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, PTN_SECONDS * 20, 1));
      }
    }
    if (ticker >= 2000) {
      ticker = 0;
    }
  }
}
