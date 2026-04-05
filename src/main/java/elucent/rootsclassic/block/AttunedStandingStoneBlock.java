package elucent.rootsclassic.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class AttunedStandingStoneBlock extends BaseBEBlock {

  private static final VoxelShape BOTTOM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
  private static final VoxelShape TOP_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
  public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

  public AttunedStandingStoneBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(HALF);
  }

  @SuppressWarnings("deprecation")
  @Override
  protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos,
                                   Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
    DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
    if (directionToNeighbour.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (directionToNeighbour == Direction.UP)) {
      return neighbourState.is(this) && neighbourState.getValue(HALF) != doubleblockhalf ? state : Blocks.AIR.defaultBlockState();
    }
    else {
      return doubleblockhalf == DoubleBlockHalf.LOWER && directionToNeighbour == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }
  }

  @Override
  public BlockState playerWillDestroy(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    if (!levelAccessor.isClientSide() && player.isCreative()) {
      AttunedStandingStoneBlock.removeBottomHalf(levelAccessor, pos, state, player);
    }
    return super.playerWillDestroy(levelAccessor, pos, state, player);
  }

  public static void removeBottomHalf(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
    if (doubleblockhalf == DoubleBlockHalf.UPPER) {
      BlockPos blockpos = pos.below();
      BlockState blockstate = levelAccessor.getBlockState(blockpos);
      if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
        levelAccessor.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
        levelAccessor.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
      }
    }
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockPos blockpos = context.getClickedPos();
    if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
      return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
    }
    else {
      return null;
    }
  }

  @Override
  public void setPlacedBy(Level levelAccessor, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    levelAccessor.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
  }

  @Override
  public boolean canSurvive(BlockState state, LevelReader levelAccessor, BlockPos pos) {
    BlockPos blockpos = pos.below();
    BlockState blockstate = levelAccessor.getBlockState(blockpos);
    return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(levelAccessor, blockpos, Direction.UP) : blockstate.is(this);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
    if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
      return BOTTOM_SHAPE;
    }
    else {
      return TOP_SHAPE;
    }
  }
}
