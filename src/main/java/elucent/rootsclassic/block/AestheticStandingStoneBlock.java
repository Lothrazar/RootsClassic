package elucent.rootsclassic.block;

import javax.annotation.Nullable;
import elucent.rootsclassic.blockentity.AestheticStandingStoneTile;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class AestheticStandingStoneBlock extends AttunedStandingStoneBlock implements EntityBlock {

  public AestheticStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
      return new AestheticStandingStoneTile(pos, state);
    }
    return null;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
    return createStandingStoneTicker(level, entityType, RootsRegistry.AESTHETIC_STANDING_STONE_TILE.get());
  }

  @Nullable
  protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends AestheticStandingStoneTile> standingStoneType) {
    return level.isClientSide ? createTickerHelper(entityType, standingStoneType, AestheticStandingStoneTile::clientTick) : null;
  }
}
