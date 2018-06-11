package elucent.rootsclassic.entity;

import java.util.Random;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityTileAccelerator extends Entity {

  BlockPos pos;
  Random random = new Random();
  int lifetime = 0;
  int potency = 1;

  public EntityTileAccelerator(World world, BlockPos pos, int potency, int size) {
    super(world);
    this.pos = pos;
    this.potency = potency + 2;
    this.lifetime = 200 + 200 * size;
    this.posX = pos.getX();
    this.posY = pos.getY();
    this.posZ = pos.getZ();
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    if (this.getEntityWorld().getTileEntity(this.pos) instanceof ITickable) {
      for (int i = 0; i < potency; i++) {
        ((ITickable) this.getEntityWorld().getTileEntity(this.pos)).update();
      }
    }
    else {
      this.kill();
      this.getEntityWorld().removeEntity(this);
    }
    for (int i = 0; i < 2; i++) {
      int side = random.nextInt(6);
      if (side == 0) {
        Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), pos.getX(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0, 255, 255, 255);
      }
      if (side == 1) {
        Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), pos.getX() + 1.0, pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0, 255, 255, 255);
      }
      if (side == 2) {
        Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble(), 0, 0, 0, 255, 255, 255);
      }
      if (side == 3) {
        Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), pos.getX() + random.nextDouble(), pos.getY() + 1.0, pos.getZ() + random.nextDouble(), 0, 0, 0, 255, 255, 255);
      }
      if (side == 4) {
        Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ(), 0, 0, 0, 255, 255, 255);
      }
      if (side == 5) {
        Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + 1.0, 0, 0, 0, 255, 255, 255);
      }
    }
    lifetime--;
    if (lifetime <= 0) {
      this.kill();
      this.getEntityWorld().removeEntity(this);
    }
  }

  @Override
  protected void entityInit() {}

  @Override
  protected void readEntityFromNBT(NBTTagCompound compound) {
    this.pos = new BlockPos(compound.getInteger("posX"), compound.getInteger("posY"), compound.getInteger("posZ"));
    this.lifetime = compound.getInteger("lifetime");
    this.potency = compound.getInteger("potency");
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound compound) {
    compound.setInteger("posX", pos.getX());
    compound.setInteger("posY", pos.getY());
    compound.setInteger("posZ", pos.getZ());
    compound.setInteger("lifetime", lifetime);
    compound.setInteger("potency", potency);
  }
}
