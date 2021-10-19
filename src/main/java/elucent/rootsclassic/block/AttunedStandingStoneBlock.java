package elucent.rootsclassic.block;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

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
  public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
    DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
    if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
      return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? stateIn : Blocks.AIR.defaultBlockState();
    }
    else {
      return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
  }

  @Override
  public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    if (!world.isClientSide && player.isCreative()) {
      AttunedStandingStoneBlock.removeBottomHalf(world, pos, state, player);
    }
    super.playerWillDestroy(world, pos, state, player);
  }

  public static void removeBottomHalf(World world, BlockPos pos, BlockState state, PlayerEntity player) {
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
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    BlockPos blockpos = context.getClickedPos();
    if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
      return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
    }
    else {
      return null;
    }
  }

  @Override
  public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    worldIn.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
  }

  @Override
  public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
    BlockPos blockpos = pos.below();
    BlockState blockstate = worldIn.getBlockState(blockpos);
    return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP) : blockstate.is(this);
  }

  @Override
  public PushReaction getPistonPushReaction(BlockState state) {
    return PushReaction.DESTROY;
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
      return BOTTOM_SHAPE;
    }
    else {
      return TOP_SHAPE;
    }
  }
}
