package elucent.rootsclassic.blockentity;

import java.util.List;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VacuumStandingStoneTile extends BEBase {

  private int ticker = 0;

  public VacuumStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public VacuumStandingStoneTile(BlockPos pos, BlockState state) {
    this(RootsRegistry.VACUUM_STANDING_STONE_TILE.get(), pos, state);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, VacuumStandingStoneTile tile) {
    tile.ticker++;
    double range = 6.0d;
    List<ItemEntity> nearbyItems = level.getEntitiesOfClass(ItemEntity.class, new AABB(
        pos.getX() - range, pos.getY() - range, pos.getZ() - range,
        pos.getX() + range, pos.getY() + range, pos.getZ() + range));
    if (nearbyItems.size() > 0) {
      for (ItemEntity nearbyItem : nearbyItems) {
        if (nearbyItem.getItem().isEmpty() || !nearbyItem.isAlive()) {
          continue;
        }
        // constant force!
        float strength = 0.05F;
        Vec3 entityVector = new Vec3(nearbyItem.getX(), nearbyItem.getY() - nearbyItem.getMyRidingOffset() + nearbyItem.getBbHeight() / 2, nearbyItem.getZ());
        Vec3 finalVector = new Vec3(pos.getX(), pos.getY() + 0.5, pos.getZ()).subtract(entityVector);
        if (Math.sqrt(finalVector.x * finalVector.x + finalVector.y * finalVector.y + finalVector.z * finalVector.z) > 1) {
          finalVector = finalVector.normalize();
        }
        nearbyItem.setDeltaMovement(finalVector.multiply(strength, nearbyItem.getDeltaMovement().y, strength));
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, VacuumStandingStoneTile tile) {
    tile.ticker++;
    if (tile.ticker % 5 == 0 && level.isClientSide) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(255, 32, 160),
            pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
  }
}
