package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;

public class VacuumStandingStoneTile extends TEBase implements ITickableTileEntity {

  int ticker = 0;

  public VacuumStandingStoneTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public VacuumStandingStoneTile() {
    this(RootsRegistry.VACUUM_STANDING_STONE_TILE.get());
  }

  @Override
  public void load(BlockState state, CompoundNBT tag) {
    super.load(state, tag);
  }

  @Override
  public CompoundNBT save(CompoundNBT tag) {
    super.save(tag);
    return tag;
  }

  @Override
  public void tick() {
    ticker++;
    if (ticker % 5 == 0 && level.isClientSide) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(255, 32, 160),
            worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    ArrayList<ItemEntity> nearbyItems = (ArrayList<ItemEntity>) this.getLevel().getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(this.getBlockPos().getX() - 5, this.getBlockPos().getY() - 5, this.getBlockPos().getZ() - 5, this.getBlockPos().getX() + 6, this.getBlockPos().getY() + 6, this.getBlockPos().getZ() + 6));
    if (nearbyItems.size() > 0) {
      for (ItemEntity nearbyItem : nearbyItems) {
        if (Math.max(Math.abs(nearbyItem.getX() - (this.getBlockPos().getX() + 0.5)), Math.abs(nearbyItem.getZ() - (this.getBlockPos().getZ() + 0.5))) > 1.0) {
          Vector3d v = new Vector3d(nearbyItem.getX() - (this.getBlockPos().getX() + 0.5), 0, nearbyItem.getZ() - (this.getBlockPos().getZ() + 0.5));
          v.normalize();
          nearbyItem.setDeltaMovement(-v.x * 0.05, nearbyItem.getDeltaMovement().y, -v.z * 0.05);
        }
      }
    }
  }
}
