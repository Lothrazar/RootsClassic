package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VacuumStandingStoneTile extends TEBase {

  public VacuumStandingStoneTile(BlockPos pos, BlockState state) {
    this(RootsRegistry.VACUUM_STANDING_STONE_TILE.get(), pos, state);
  }

  private void tick() {
    ticker++;
    if (ticker % 5 == 0) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(255, 32, 160),
            worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    ArrayList<ItemEntity> nearbyItems = (ArrayList<ItemEntity>) this.getLevel().getEntitiesOfClass(ItemEntity.class, new AABB(this.getBlockPos().getX() - 5, this.getBlockPos().getY() - 5, this.getBlockPos().getZ() - 5, this.getBlockPos().getX() + 6, this.getBlockPos().getY() + 6, this.getBlockPos().getZ() + 6));
    if (nearbyItems.size() > 0) {
      for (ItemEntity nearbyItem : nearbyItems) {
        if (Math.max(Math.abs(nearbyItem.getX() - (this.getBlockPos().getX() + 0.5)), Math.abs(nearbyItem.getZ() - (this.getBlockPos().getZ() + 0.5))) > 1.0) {
          Vec3 v = new Vec3(nearbyItem.getX() - (this.getBlockPos().getX() + 0.5), 0, nearbyItem.getZ() - (this.getBlockPos().getZ() + 0.5));
          v.normalize();
          nearbyItem.setDeltaMovement(-v.x * 0.05, nearbyItem.getDeltaMovement().y, -v.z * 0.05);
        }
      }
    }
  }
}
