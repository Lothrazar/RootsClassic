package elucent.rootsclassic.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AttunedStandingStoneBlock extends Block {
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
  public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
    DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
    if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
      return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? stateIn : Blocks.AIR.defaultBlockState();
    }
    else {
      return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
  }

  @Override
  public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
    if (!world.isClientSide && player.isCreative()) {
      AttunedStandingStoneBlock.removeBottomHalf(world, pos, state, player);
    }
    super.playerWillDestroy(world, pos, state, player);
  }

  public static void removeBottomHalf(Level world, BlockPos pos, BlockState state, Player player) {
    DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
    if (doubleblockhalf == DoubleBlockHalf.UPPER) {
      BlockPos blockpos = pos.below();
      BlockState blockstate = world.getBlockState(blockpos);
      if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
        world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
        world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
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
  public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    worldIn.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
  }

  @Override
  public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
    BlockPos blockpos = pos.below();
    BlockState blockstate = worldIn.getBlockState(blockpos);
    return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP) : blockstate.is(this);
  }

  @Override
  public PushReaction getPistonPushReaction(BlockState state) {
    return PushReaction.DESTROY;
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
    if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
      return BOTTOM_SHAPE;
    }
    else {
      return TOP_SHAPE;
    }
  }

  @Nullable
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> typeA, BlockEntityType<E> typeE, BlockEntityTicker<? super E> typeE2) {
    return typeE == typeA ? (BlockEntityTicker<A>)typeE2 : null;
  }
}
