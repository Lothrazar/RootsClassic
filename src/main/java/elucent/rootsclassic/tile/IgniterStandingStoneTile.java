package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class IgniterStandingStoneTile extends TEBase {

  private static final int RADIUS = 10;
  private static final int VRADIUS = 2;
  int ticker = 0;

  public IgniterStandingStoneTile(BlockPos pos, BlockState state) {
    super(RootsRegistry.IGNITER_STANDING_STONE_TILE.get(), pos, state);
  }

  private void tick() {
    ticker++;
    if (ticker % 5 == 0) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(255, 64, 32),
            worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    if (ticker % 20 == 0) {
      List<LivingEntity> nearbyCreatures = level.getEntitiesOfClass(LivingEntity.class,
          new AABB(worldPosition.getX() - RADIUS, worldPosition.getY() - VRADIUS, worldPosition.getZ() - RADIUS, worldPosition.getX() + RADIUS, worldPosition.getY() + VRADIUS, worldPosition.getZ() + RADIUS));
      if (nearbyCreatures.size() > 0) {
        for (LivingEntity nearbyCreature : nearbyCreatures) {
          nearbyCreature.setSecondsOnFire(2);
        }
      }
    }
  }
}
