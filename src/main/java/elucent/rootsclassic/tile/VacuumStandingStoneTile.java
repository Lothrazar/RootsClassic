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
  public void read(BlockState state, CompoundNBT tag) {
    super.read(state, tag);
  }

  @Override
  public CompoundNBT write(CompoundNBT tag) {
    super.write(tag);
    return tag;
  }

  @Override
  public void tick() {
    ticker++;
    if (ticker % 5 == 0 && world.isRemote) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        world.addParticle(MagicAuraParticleData.createData(255, 32, 160),
            pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    ArrayList<ItemEntity> nearbyItems = (ArrayList<ItemEntity>) this.getWorld().getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(this.getPos().getX() - 5, this.getPos().getY() - 5, this.getPos().getZ() - 5, this.getPos().getX() + 6, this.getPos().getY() + 6, this.getPos().getZ() + 6));
    if (nearbyItems.size() > 0) {
      for (ItemEntity nearbyItem : nearbyItems) {
        if (Math.max(Math.abs(nearbyItem.getPosX() - (this.getPos().getX() + 0.5)), Math.abs(nearbyItem.getPosZ() - (this.getPos().getZ() + 0.5))) > 1.0) {
          Vector3d v = new Vector3d(nearbyItem.getPosX() - (this.getPos().getX() + 0.5), 0, nearbyItem.getPosZ() - (this.getPos().getZ() + 0.5));
          v.normalize();
          nearbyItem.setMotion(-v.x * 0.05, nearbyItem.getMotion().y, -v.z * 0.05);
        }
      }
    }
  }
}
