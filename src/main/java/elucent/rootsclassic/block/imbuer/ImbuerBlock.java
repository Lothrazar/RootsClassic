package elucent.rootsclassic.block.imbuer;

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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ImbuerBlock extends BaseBEBlock implements EntityBlock {

  private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D);

  public ImbuerBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new ImbuerBlockEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
    return createStandingStoneTicker(level, entityType, RootsRegistry.IMBUER_TILE.get());
  }

  @Nullable
  protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends ImbuerBlockEntity> standingStoneType) {
    return level.isClientSide ? createTickerHelper(entityType, standingStoneType, ImbuerBlockEntity::clientTick) : createTickerHelper(entityType, standingStoneType, ImbuerBlockEntity::serverTick);
  }
}
