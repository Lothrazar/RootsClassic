package elucent.rootsclassic.entity;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EntityTileAccelerator extends Entity {

  private BlockPos bePosition;
  private int lifetime = 0;
  private int potency = 1;

  public EntityTileAccelerator(EntityType<? extends EntityTileAccelerator> type, Level levelAccessor) {
    super(type, levelAccessor);
  }

  public EntityTileAccelerator(Level levelAccessor, BlockPos bePosition, int potency, int size) {
    this(RootsEntities.TILE_ACCELERATOR.get(), levelAccessor);
    this.setBEPosition(bePosition);
    this.potency = potency + 2;
    this.lifetime = 200 + 200 * size;
    this.setPosRaw(bePosition.getX(), bePosition.getY(), bePosition.getZ());
  }

  public void setBEPosition(BlockPos pos) {
    this.bePosition = pos;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void tick() {
    super.tick();
    if (!this.level().isClientSide()) {
      if (bePosition == null || this.level().getBlockState(bePosition).isAir()) {
        if (this.tickCount > 20 && !level().isClientSide()) {
          this.level().broadcastEntityEvent(this, (byte) 3);
          this.discard();
        }
        return;
      }
      BlockEntity blockEntity = level().getBlockEntity(bePosition);
      if (blockEntity != null) {
        BlockState state = level().getBlockState(this.bePosition);
        for (int i = 0; i < potency; i++) {
          BlockEntityTicker<BlockEntity> ticker = state.getTicker(level(), (BlockEntityType<BlockEntity>) blockEntity.getType());
          if (ticker != null) {
            ticker.tick(level(), bePosition, blockEntity.getBlockState(), blockEntity);
          }
        }
      }
    } else {
      if (level().isClientSide()) {
        for (int i = 0; i < 2; i++) {
          int side = random.nextInt(6);
          if (side == 0) {
            level().addParticle(MagicAuraParticleData.createData(255, 255, 255),
              getX(), getY() + random.nextDouble(), getZ() + random.nextDouble(), 0, 0, 0);
          }
          if (side == 1) {
            level().addParticle(MagicAuraParticleData.createData(255, 255, 255),
              getX() + 1.0, getY() + random.nextDouble(), getZ() + random.nextDouble(), 0, 0, 0);
          }
          if (side == 2) {
            level().addParticle(MagicAuraParticleData.createData(255, 255, 255),
              getX() + random.nextDouble(), getY(), getZ() + random.nextDouble(), 0, 0, 0);
          }
          if (side == 3) {
            level().addParticle(MagicAuraParticleData.createData(255, 255, 255),
              getX() + random.nextDouble(), getY() + 1.0, getZ() + random.nextDouble(), 0, 0, 0);
          }
          if (side == 4) {
            level().addParticle(MagicAuraParticleData.createData(255, 255, 255),
              getX() + random.nextDouble(), getY() + random.nextDouble(), getZ(), 0, 0, 0);
          }
          if (side == 5) {
            level().addParticle(MagicAuraParticleData.createData(255, 255, 255),
              getX() + random.nextDouble(), getY() + random.nextDouble(), getZ() + 1.0, 0, 0, 0);
          }
        }
      }
    }
    if (!level().isClientSide()) {
      lifetime--;
      if (lifetime <= 0) {
        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();
      }
    }
  }

  @Override
  public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
    return false;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {

  }

  @Override
  protected void readAdditionalSaveData(ValueInput input) {
    this.bePosition = input.read("bePos", BlockPos.CODEC).orElse(null);
    this.lifetime = input.getIntOr("lifetime", 0);
    this.potency = input.getIntOr("potency", 1);
  }

  @Override
  protected void addAdditionalSaveData(ValueOutput output) {
    output.store("bePos",  BlockPos.CODEC, bePosition);
    output.putInt("lifetime", lifetime);
    output.putInt("potency", potency);
  }
}
