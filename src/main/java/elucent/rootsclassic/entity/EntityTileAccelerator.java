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
  private BlockPos pos;
  private final Random random = new Random();
  private int lifetime = 0;
  private int potency = 1;

  public EntityTileAccelerator(EntityType<? extends EntityTileAccelerator> type, World worldIn) {
    super(type, worldIn);
  }

  public EntityTileAccelerator(World world, BlockPos pos, int potency, int size) {
    this(RootsEntities.TILE_ACCELERATOR.get(), world);
    this.pos = pos;
    this.potency = potency + 2;
    this.lifetime = 200 + 200 * size;
    this.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
  }

  @Override
  public void tick() {
    super.tick();
    if (pos == null) {
      if (!level.isClientSide) {
        this.level.broadcastEntityEvent(this, (byte) 3);
        this.remove();
      }
      return;
    }
    if (this.getCommandSenderWorld().getBlockEntity(this.pos) instanceof ITickableTileEntity) {
      for (int i = 0; i < potency; i++) {
        ((ITickableTileEntity) this.getCommandSenderWorld().getBlockEntity(this.pos)).tick();
      }
    }
    else {
      this.level.broadcastEntityEvent(this, (byte) 3);
      this.remove();
    }
    for (int i = 0; i < 2; i++) {
      int side = random.nextInt(6);
      if (side == 0) {
        level.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getX(), getY() + random.nextDouble(), getZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 1) {
        level.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getX() + 1.0, getY() + random.nextDouble(), getZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 2) {
        level.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getX() + random.nextDouble(), getY(), getZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 3) {
        level.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getX() + random.nextDouble(), getY() + 1.0, getZ() + random.nextDouble(), 0, 0, 0);
      }
      if (side == 4) {
        level.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getX() + random.nextDouble(), getY() + random.nextDouble(), getZ(), 0, 0, 0);
      }
      if (side == 5) {
        level.addParticle(MagicAuraParticleData.createData(255, 255, 255),
            getX() + random.nextDouble(), getY() + random.nextDouble(), getZ() + 1.0, 0, 0, 0);
      }
    }
    lifetime--;
    if (lifetime <= 0) {
      this.level.broadcastEntityEvent(this, (byte) 3);
      this.remove();
    }
  }

  @Override
  protected void defineSynchedData() {}

  @Override
  protected void readAdditionalSaveData(CompoundNBT compound) {
    this.pos = new BlockPos(compound.getInt("posX"), compound.getInt("posY"), compound.getInt("posZ"));
    this.lifetime = compound.getInt("lifetime");
    this.potency = compound.getInt("potency");
  }

  @Override
  protected void addAdditionalSaveData(CompoundNBT compound) {
    compound.putInt("posX", pos.getX());
    compound.putInt("posY", pos.getY());
    compound.putInt("posZ", pos.getZ());
    compound.putInt("lifetime", lifetime);
    compound.putInt("potency", potency);
  }

  @Override
  public IPacket<?> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
