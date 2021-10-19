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

import java.util.List;

public class RepulsorStandingStoneTile extends TEBase implements ITickableTileEntity {

  private static final int RADIUS = 5;
  private static final int VRADIUS = 1;
  int ticker = 0;

  public RepulsorStandingStoneTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public RepulsorStandingStoneTile() {
    this(RootsRegistry.REPULSOR_STANDING_STONE_TILE.get());
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
        level.addParticle(MagicAuraParticleData.createData(255, 255, 32),
            worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    List<ItemEntity> nearbyItems = level.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(worldPosition.getX() - RADIUS, worldPosition.getY() - VRADIUS, worldPosition.getZ() - RADIUS, worldPosition.getX() + RADIUS, worldPosition.getY() + VRADIUS, worldPosition.getZ() + RADIUS));
    if (nearbyItems.size() > 0) {
      for (ItemEntity ei : nearbyItems) {
        if (Math.max(Math.abs(ei.getX() - (worldPosition.getX() + 0.5)), Math.abs(ei.getZ() - (worldPosition.getZ() + 0.5))) > 1.0) {
          Vector3d v = new Vector3d(ei.getX() - (worldPosition.getX() + 0.5), 0, ei.getZ() - (worldPosition.getZ() + 0.5));
          v.normalize();
          ei.setDeltaMovement(v.x * 0.05, ei.getDeltaMovement().y, v.z * 0.05);
        }
      }
    }
  }
}
