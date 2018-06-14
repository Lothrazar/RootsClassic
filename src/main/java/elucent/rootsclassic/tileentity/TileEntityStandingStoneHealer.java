package elucent.rootsclassic.tileentity;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityStandingStoneHealer extends TEBase implements ITickable {

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
        Roots.proxy.spawnParticleMagicAuraFX(this.getWorld(), this.getPos().getX() + 0.5 + xShift, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5 + zShift, 0, 0, 0, 255, 32, 32);
      }
    }
    if (ticker % 20 == 0) {
      ArrayList<EntityLivingBase> nearbyCreatures = (ArrayList<EntityLivingBase>) this.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPos().getX() - 9, this.getPos().getY() - 9, this.getPos().getZ() - 9, this.getPos().getX() + 10, this.getPos().getY() + 10, this.getPos().getZ() + 10));
      if (nearbyCreatures.size() > 0) {
        for (int i = 0; i < nearbyCreatures.size(); i++) {
          nearbyCreatures.get(i).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), 25, 1));
        }
      }
    }
  }
}
