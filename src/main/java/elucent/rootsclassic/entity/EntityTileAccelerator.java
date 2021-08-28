package elucent.rootsclassic.entity;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsEntities;

public class EntityTileAccelerator extends Entity {

  BlockPos pos;
  Random random = new Random();
  int lifetime = 0;
  int potency = 1;

  public EntityTileAccelerator(EntityType<? extends EntityTileAccelerator> type, World worldIn) {
    super(type, worldIn);
  }

  public EntityTileAccelerator(World world, BlockPos pos, int potency, int size) {
    this(RootsEntities.TILE_ACCELERATOR.get(), world);
    this.pos = pos;
    this.potency = potency + 2;
    this.lifetime = 200 + 200 * size;
    this.setRawPosition(pos.getX(), pos.getY(), pos.getZ());
  }

  @Override
  public void tick() {
    super.tick();
    if (pos == null) {
      if (!world.isRemote) {
        this.world.setEntityState(this, (byte) 3);
        this.remove();
      }
      return;
    }
    if (this.getEntityWorld().getTileEntity(this.pos) instanceof ITickableTileEntity) {
      for (int i = 0; i < potency; i++) {
        ((ITickableTileEntity) this.getEntityWorld().getTileEntity(this.pos)).tick();
      }
    }
    else {
      this.world.setEntityState(this, (byte) 3);
      this.remove();
    }
    for (int i = 0; i < 2; i++) {
      int side = random.nextInt(6);
      if (side == 0) {
        world.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getPosX(), getPosY() + random.nextDouble(), getPosZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 1) {
        world.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getPosX() + 1.0, getPosY() + random.nextDouble(), getPosZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 2) {
        world.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getPosX() + random.nextDouble(), getPosY(), getPosZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 3) {
        world.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getPosX() + random.nextDouble(), getPosY() + 1.0, getPosZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 4) {
        world.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getPosX() + random.nextDouble(), getPosY() + random.nextDouble(), getPosZ(), 0, 0, 0);
      }
      if (side == 5) {
        world.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getPosX() + random.nextDouble(), getPosY() + random.nextDouble(), getPosZ() + 1.0, 0, 0, 0);
      }
    }
    lifetime--;
    if (lifetime <= 0) {
      this.world.setEntityState(this, (byte) 3);
      this.remove();
    }
  }

  @Override
  protected void registerData() {}

  @Override
  protected void readAdditional(CompoundNBT compound) {
    this.pos = new BlockPos(compound.getInt("posX"), compound.getInt("posY"), compound.getInt("posZ"));
    this.lifetime = compound.getInt("lifetime");
    this.potency = compound.getInt("potency");
  }

  @Override
  protected void writeAdditional(CompoundNBT compound) {
    compound.putInt("posX", pos.getX());
    compound.putInt("posY", pos.getY());
    compound.putInt("posZ", pos.getZ());
    compound.putInt("lifetime", lifetime);
    compound.putInt("potency", potency);
  }

  @Override
  public IPacket<?> createSpawnPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
