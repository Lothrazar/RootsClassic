package elucent.rootsclassic.entity;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsEntities;

public class EntityAccelerator extends Entity {

  Entity entity;
  Random random = new Random();
  int lifetime = 0;
  int potency = 1;

  public EntityAccelerator(EntityType<? extends EntityAccelerator> type, World worldIn) {
    super(type, worldIn);
  }

  public EntityAccelerator(World world, Entity entity, int potency, int size) {
    this(RootsEntities.ENTITY_ACCELERATOR.get(), world);
    this.entity = entity;
    this.potency = potency + 2;
    this.lifetime = 200 + 200 * size;
    this.setRawPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
  }

  @Override
  public void tick() {
    super.tick();
    this.setRawPosition((entity.getBoundingBox().maxX + entity.getBoundingBox().minX) / 2.0 - 0.5,
        (entity.getBoundingBox().maxY + entity.getBoundingBox().minY) / 2.0 - 0.5,
        (entity.getBoundingBox().maxZ + entity.getBoundingBox().minZ) / 2.0 - 0.5);
    if (entity == null) {
      this.world.setEntityState(this, (byte) 3);
      this.remove();
    }
    else {
      for (int i = 0; i < potency; i++) {
        entity.tick();
        entity.baseTick();
      }
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
    this.lifetime = compound.getInt("lifetime");
    this.potency = compound.getInt("potency");
  }

  @Override
  protected void writeAdditional(CompoundNBT compound) {
    compound.putInt("lifetime", lifetime);
    compound.putInt("potency", potency);
  }

  @Override
  public IPacket<?> createSpawnPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
