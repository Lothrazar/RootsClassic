package elucent.rootsclassic.tile;

import java.util.ArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.registry.RootsRegistry;

public class HealerStandingStone extends TEBase implements ITickableTileEntity {

  private static final int RADIUS = 10;
  private static final int PTN_SECONDS = 25;
  int ticker = 0;

  public HealerStandingStone(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public HealerStandingStone() {
    this(RootsRegistry.HEALER_STANDING_STONE_TILE.get());
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
    if (ticker % 5 == 0) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        world.addParticle(MagicAuraParticleData.createData(255, 32, 32),
            pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
    if (ticker % 20 == 0) {
      ArrayList<LivingEntity> nearbyCreatures = (ArrayList<LivingEntity>) this.getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX() - RADIUS, pos.getY() - RADIUS, pos.getZ() - RADIUS, pos.getX() + RADIUS, pos.getY() + RADIUS, pos.getZ() + RADIUS));
      for (LivingEntity nearbyCreature : nearbyCreatures) {
        nearbyCreature.addPotionEffect(new EffectInstance(Effects.REGENERATION, PTN_SECONDS * 20, 1));
      }
    }
    if (ticker >= 2000) {
      ticker = 0;
    }
  }
}
