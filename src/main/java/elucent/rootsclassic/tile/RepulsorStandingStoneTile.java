package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RepulsorStandingStoneTile extends TEBase {

  private static final int RADIUS = 5;
  private static final int VRADIUS = 1;

  public RepulsorStandingStoneTile(BlockPos pos, BlockState state) {
    this(RootsRegistry.REPULSOR_STANDING_STONE_TILE.get(), pos, state);
  }

  private void tick() {
    ticker++;
    if (ticker % 5 == 0) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(255, 255, 32),
            worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    List<ItemEntity> nearbyItems = level.getEntitiesOfClass(ItemEntity.class, new AABB(worldPosition.getX() - RADIUS, worldPosition.getY() - VRADIUS, worldPosition.getZ() - RADIUS, worldPosition.getX() + RADIUS, worldPosition.getY() + VRADIUS, worldPosition.getZ() + RADIUS));
    if (nearbyItems.size() > 0) {
      for (ItemEntity ei : nearbyItems) {
        if (Math.max(Math.abs(ei.getX() - (worldPosition.getX() + 0.5)), Math.abs(ei.getZ() - (worldPosition.getZ() + 0.5))) > 1.0) {
          Vec3 v = new Vec3(ei.getX() - (worldPosition.getX() + 0.5), 0, ei.getZ() - (worldPosition.getZ() + 0.5));
          v.normalize();
          ei.setDeltaMovement(v.x * 0.05, ei.getDeltaMovement().y, v.z * 0.05);
        }
      }
    }
  }
}
