package elucent.rootsclassic.block.altar;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import elucent.rootsclassic.block.BaseBEBlock;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AltarBlock extends BaseBEBlock implements EntityBlock {

  private static final VoxelShape SHAPE = Stream.of(
      Block.box(0, 8, 0, 16, 12, 16),
      Block.box(4, 0, 4, 12, 8, 12),
      Block.box(2, 4, 2, 6, 8, 6),
      Block.box(10, 4, 2, 14, 8, 6),
      Block.box(10, 4, 10, 14, 8, 14),
      Block.box(2, 4, 10, 6, 8, 14)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

  public AltarBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new AltarBlockEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
    return createStandingStoneTicker(level, entityType, RootsRegistry.ALTAR_TILE.get());
  }

  @Nullable
  protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends AltarBlockEntity> standingStoneType) {
    return level.isClientSide ? createTickerHelper(entityType, standingStoneType, AltarBlockEntity::clientTick) : createTickerHelper(entityType, standingStoneType, AltarBlockEntity::serverTick);
  }
}
