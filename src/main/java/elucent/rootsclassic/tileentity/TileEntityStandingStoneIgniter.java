package elucent.rootsclassic.tileentity;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityStandingStoneIgniter extends TEBase implements ITickable {

  private static final int RADIUS = 10;
  private static final int VRADIUS = 2;
  int ticker = 0;
  Random random = new Random();

  public TileEntityStandingStoneIgniter() {
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
        Roots.proxy.spawnParticleMagicAuraFX(this.getWorld(), pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0, 255, 64, 32);
      }
    }
    if (ticker % 20 == 0) {
      List<EntityLivingBase> nearbyCreatures = world.getEntitiesWithinAABB(EntityLivingBase.class,
          new AxisAlignedBB(pos.getX() - RADIUS, pos.getY() - VRADIUS, pos.getZ() - RADIUS, pos.getX() + RADIUS, pos.getY() + VRADIUS, pos.getZ() + RADIUS));
      if (nearbyCreatures.size() > 0) {
        for (int i = 0; i < nearbyCreatures.size(); i++) {
          nearbyCreatures.get(i).setFire(2);
        }
      }
    }
  }
}
