package elucent.rootsclassic.entity;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsEntities;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class EntityTileAccelerator extends Entity {

  BlockPos pos;
  Random random = new Random();
  int lifetime = 0;
  int potency = 1;

  public EntityTileAccelerator(EntityType<? extends EntityTileAccelerator> type, Level worldIn) {
    super(type, worldIn);
  }

  public EntityTileAccelerator(Level world, BlockPos pos, int potency, int size) {
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
        this.remove(RemovalReason.KILLED);
      }
      return;
    }
    BlockEntity tile = this.getCommandSenderWorld().getBlockEntity(this.pos);
    Block block = this.getCommandSenderWorld().getBlockState(this.pos).getBlock();

//    if (tile instanceof TickableBlockEntity) {
      for (int i = 0; i < potency; i++) {
//        ((TickableBlockEntity) this.getCommandSenderWorld().getBlockEntity(this.pos)).tick();
        //    block.tick(null, (ServerLevel) this.level,pos, new Random());
      }
//    }
//    else {
//      this.level.broadcastEntityEvent(this, (byte) 3);
//      this.remove(RemovalReason.KILLED);
//    }
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
  protected void defineSynchedData() {
  }

  @Override
  protected void readAdditionalSaveData(CompoundTag compound) {
    this.pos = new BlockPos(compound.getInt("posX"), compound.getInt("posY"), compound.getInt("posZ"));
    this.lifetime = compound.getInt("lifetime");
    this.potency = compound.getInt("potency");
  }

  @Override
  protected void addAdditionalSaveData(CompoundTag compound) {
    compound.putInt("posX", pos.getX());
    compound.putInt("posY", pos.getY());
    compound.putInt("posZ", pos.getZ());
    compound.putInt("lifetime", lifetime);
    compound.putInt("potency", potency);
  }

  @Override
  public Packet<?> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
