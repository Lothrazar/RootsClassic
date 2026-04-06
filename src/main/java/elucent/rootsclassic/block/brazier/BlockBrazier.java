package elucent.rootsclassic.block.brazier;

import elucent.rootsclassic.block.BaseBEBlock;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class BlockBrazier extends BaseBEBlock {

  private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);

  public BlockBrazier(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new BrazierBlockEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
    return createBrazierTicker(level, entityType, RootsRegistry.BRAZIER_TILE.get());
  }

  protected static <T extends BlockEntity> @Nullable BlockEntityTicker<T> createBrazierTicker(
    Level level, BlockEntityType<T> actualType, BlockEntityType<? extends BrazierBlockEntity> expectedType
  ) {
    return level instanceof ServerLevel
      ? createTickerHelper(actualType, expectedType, BrazierBlockEntity::serverTick)
      : createTickerHelper(actualType, expectedType, BrazierBlockEntity::clientTick);
  }
}
