package elucent.rootsclassic.tileentity;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class TileEntityStandingStoneRepulsor extends TEBase implements ITickable {

  private static final int RADIUS = 5;
  private static final int VRADIUS = 1;
  int ticker = 0;
  Random random = new Random();

  public TileEntityStandingStoneRepulsor() {
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
        Roots.proxy.spawnParticleMagicAuraFX(this.getWorld(), pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0, 255, 255, 32);
      }
    }
    List<EntityItem> nearbyItems = this.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - RADIUS, pos.getY() - VRADIUS, pos.getZ() - RADIUS, pos.getX() + RADIUS, pos.getY() + VRADIUS, pos.getZ() + RADIUS));
    if (nearbyItems.size() > 0) {
      for (EntityItem ei : nearbyItems) {
        if (Math.max(Math.abs(ei.posX - (pos.getX() + 0.5)), Math.abs(ei.posZ - (pos.getZ() + 0.5))) > 1.0) {
          Vec3d v = new Vec3d(ei.posX - (pos.getX() + 0.5), 0, ei.posZ - (pos.getZ() + 0.5));
          v.normalize();
          ei.motionX = v.x * 0.05;
          ei.motionZ = v.z * 0.05;
        }
      }
    }
  }
}
