package elucent.rootsclassic.blockentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.item.GrowthPowderItem;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GrowerStandingStoneTile extends BEBase {

  private static final double PCT_CHANCE_PER_BLOCK = 0.03;
  private static final int TICK_SPEED = 100;
  private static final int MAX_TICKER = 500 * 20;
  private static final int RADIUS = 4;
  private int ticker = 0;

  public GrowerStandingStoneTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public GrowerStandingStoneTile(BlockPos pos, BlockState state) {
    this(RootsRegistry.GROWER_STANDING_STONE_TILE.get(), pos, state);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, GrowerStandingStoneTile tile) {
    tile.updateTicker();
    if (tile.ticker % TICK_SPEED == 0) {
      tile.applyGrowthToArea();
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, GrowerStandingStoneTile tile) {
    tile.updateTicker();
    tile.spawnParticles();
  }

  private void updateTicker() {
    ticker++;
    if (ticker >= MAX_TICKER) {
      ticker = 0;
    }
  }

  private void applyGrowthToArea() {
    List<BlockPos> positions = new ArrayList<>();
    for (int xp = -1 * RADIUS; xp <= RADIUS; xp++) {
      for (int zp = -1 * RADIUS; zp <= RADIUS; zp++) {
        positions.add(this.getBlockPos().offset(xp, -1, zp));
        positions.add(this.getBlockPos().offset(xp, -2, zp));
      }
    }
    Collections.shuffle(positions);
    for (BlockPos pos : positions) {
      if (level.random.nextDouble() < PCT_CHANCE_PER_BLOCK) {
        //this percent chance to actually fire
        GrowthPowderItem.applyGrowthHere(level, pos);
      }
    }
  }

  private void spawnParticles() {
    if (ticker % 5 == 0 && level.isClientSide) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicAuraParticleData.createData(32, 255, 32),
            worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
  }
}
