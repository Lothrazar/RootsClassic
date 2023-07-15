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

public class RepulsorStandingStoneTile extends BEBase {

  private static final int RADIUS = 5;
  private static final int VRADIUS = 1;
  private int ticker = 0;

  public RepulsorStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public RepulsorStandingStoneTile(BlockPos pos, BlockState state) {
    this(RootsRegistry.REPULSOR_STANDING_STONE_TILE.get(), pos, state);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, RepulsorStandingStoneTile tile) {
    tile.ticker++;
    List<ItemEntity> nearbyItems = level.getEntitiesOfClass(ItemEntity.class,
        new AABB(pos.getX() - RADIUS, pos.getY() - VRADIUS, pos.getZ() - RADIUS,
            pos.getX() + RADIUS, pos.getY() + VRADIUS, pos.getZ() + RADIUS));
    if (nearbyItems.size() > 0) {
      for (ItemEntity ei : nearbyItems) {
        if (Math.max(Math.abs(ei.getX() - (pos.getX() + 0.5)), Math.abs(ei.getZ() - (pos.getZ() + 0.5))) > 1.0) {
          Vec3 v = new Vec3(ei.getX() - (pos.getX() + 0.5), 0, ei.getZ() - (pos.getZ() + 0.5));
          v.normalize();
          ei.setDeltaMovement(v.x * 0.05, ei.getDeltaMovement().y, v.z * 0.05);
        }
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, RepulsorStandingStoneTile tile) {
    tile.ticker++;
    if (tile.ticker % 5 == 0 && level.isClientSide) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(255, 255, 32),
            pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
  }
}
