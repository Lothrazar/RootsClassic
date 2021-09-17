package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class AcceleratorStandingStoneTile extends TEBase implements ITickableTileEntity {

  private static final int POTION_SECONDS = 25;
  private static final int RADIUS = 10;
  int ticker = 0;

  public AcceleratorStandingStoneTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public AcceleratorStandingStoneTile() {
    this(RootsRegistry.ACCELERATOR_STANDING_STONE_TILE.get());
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
        world.addParticle(MagicAuraParticleData.createData(32, 255, 255),
                pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    if (ticker % 20 == 0) {
      List<LivingEntity> nearbyCreatures = this.getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX() - RADIUS, pos.getY() - RADIUS, pos.getZ() - RADIUS, pos.getX() + RADIUS, pos.getY() + RADIUS, pos.getZ() + RADIUS));
      if (nearbyCreatures.size() > 0) {
        for (LivingEntity nearbyCreature : nearbyCreatures) {
          nearbyCreature.addPotionEffect(new EffectInstance(Effects.SPEED, POTION_SECONDS * 20, 1));
        }
      }
      ticker = 0;
    }
  }
}
