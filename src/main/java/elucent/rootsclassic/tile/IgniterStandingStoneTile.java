package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class IgniterStandingStoneTile extends TEBase implements ITickableTileEntity {

  private static final int RADIUS = 10;
  private static final int VRADIUS = 2;
  int ticker = 0;

  public IgniterStandingStoneTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public IgniterStandingStoneTile() {
    this(RootsRegistry.IGNITER_STANDING_STONE_TILE.get());
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
        level.addParticle(MagicAuraParticleData.createData(255, 64, 32),
                worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    if (ticker % 20 == 0) {
      List<LivingEntity> nearbyCreatures = level.getEntitiesOfClass(LivingEntity.class,
          new AxisAlignedBB(worldPosition.getX() - RADIUS, worldPosition.getY() - VRADIUS, worldPosition.getZ() - RADIUS, worldPosition.getX() + RADIUS, worldPosition.getY() + VRADIUS, worldPosition.getZ() + RADIUS));
      if (nearbyCreatures.size() > 0) {
        for (LivingEntity nearbyCreature : nearbyCreatures) {
          nearbyCreature.setSecondsOnFire(2);
        }
      }
    }
  }
}
