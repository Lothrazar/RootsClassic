package elucent.rootsclassic.blockentity;

import java.util.List;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class IgniterStandingStoneTile extends BEBase {

  private static final int RADIUS = 10;
  private static final int VRADIUS = 2;
  private int ticker = 0;

  public IgniterStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public IgniterStandingStoneTile(BlockPos pos, BlockState state) {
    this(RootsRegistry.IGNITER_STANDING_STONE_TILE.get(), pos, state);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, IgniterStandingStoneTile tile) {
    tile.ticker++;
    if (tile.ticker % 20 == 0) {
      List<LivingEntity> nearbyCreatures = level.getEntitiesOfClass(LivingEntity.class,
          new AABB(pos.getX() - RADIUS, pos.getY() - VRADIUS, pos.getZ() - RADIUS,
              pos.getX() + RADIUS, pos.getY() + VRADIUS, pos.getZ() + RADIUS));
      if (nearbyCreatures.size() > 0) {
        for (LivingEntity nearbyCreature : nearbyCreatures) {
          nearbyCreature.setSecondsOnFire(2);
        }
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, IgniterStandingStoneTile tile) {
    tile.ticker++;
    if (tile.ticker % 5 == 0 && level.isClientSide) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(255, 64, 32),
            pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
  }
}
