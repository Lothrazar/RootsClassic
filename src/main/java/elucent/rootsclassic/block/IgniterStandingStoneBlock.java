package elucent.rootsclassic.block;

import javax.annotation.Nullable;
import elucent.rootsclassic.blockentity.IgniterStandingStoneTile;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class IgniterStandingStoneBlock extends AttunedStandingStoneBlock implements EntityBlock {

  public IgniterStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
      return new IgniterStandingStoneTile(pos, state);
    }
    return null;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
    return createStandingStoneTicker(level, entityType, RootsRegistry.IGNITER_STANDING_STONE_TILE.get());
  }

  @Nullable
  protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends IgniterStandingStoneTile> standingStoneType) {
    return level.isClientSide ? createTickerHelper(entityType, standingStoneType, IgniterStandingStoneTile::clientTick) : createTickerHelper(entityType, standingStoneType, IgniterStandingStoneTile::serverTick);
  }
}
