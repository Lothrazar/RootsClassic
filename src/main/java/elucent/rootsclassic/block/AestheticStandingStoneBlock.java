package elucent.rootsclassic.block;

import elucent.rootsclassic.blockentity.AestheticStandingStoneTile;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import javax.annotation.Nullable;

public class AestheticStandingStoneBlock extends AttunedStandingStoneBlock implements EntityBlock {

  public AestheticStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    super.playerWillDestroy(levelAccessor, pos, state, player);
    if (levelAccessor.getBlockEntity(pos) instanceof BEBase) {
      ((BEBase) levelAccessor.getBlockEntity(pos)).breakBlock(levelAccessor, pos, state, player);
    }
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
