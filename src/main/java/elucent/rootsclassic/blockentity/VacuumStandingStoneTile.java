package elucent.rootsclassic.blockentity;

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

import java.util.ArrayList;

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
  public CompoundTag save(CompoundTag tag) {
    saveAdditional(tag);
    return super.save(tag);
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
  }
  
  public static void serverTick(Level level, BlockPos pos, BlockState state, VacuumStandingStoneTile tile) {
    tile.ticker++;
    ArrayList<ItemEntity> nearbyItems = (ArrayList<ItemEntity>) level.getEntitiesOfClass(ItemEntity.class,
            new AABB(pos.getX() - 5, pos.getY() - 5, pos.getZ() - 5,
                    pos.getX() + 6, pos.getY() + 6, pos.getZ() + 6));
    if (nearbyItems.size() > 0) {
      for (ItemEntity nearbyItem : nearbyItems) {
        if (Math.max(Math.abs(nearbyItem.getX() - (pos.getX() + 0.5)), Math.abs(nearbyItem.getZ() - (pos.getZ() + 0.5))) > 1.0) {
          Vec3 v = new Vec3(nearbyItem.getX() - (pos.getX() + 0.5), 0, nearbyItem.getZ() - (pos.getZ() + 0.5));
          v.normalize();
          nearbyItem.setDeltaMovement(-v.x * 0.05, nearbyItem.getDeltaMovement().y, -v.z * 0.05);
        }
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
