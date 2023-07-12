package elucent.rootsclassic.block;

import elucent.rootsclassic.tile.AcceleratorStandingStoneTile;
import elucent.rootsclassic.tile.TEBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AcceleratorStandingStoneBlock extends AttunedStandingStoneBlock {

  public AcceleratorStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    super.playerWillDestroy(world, pos, state, player);
    if (world.getBlockEntity(pos) instanceof TEBase) {
      ((TEBase) world.getBlockEntity(pos)).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return state.getValue(HALF) == DoubleBlockHalf.UPPER;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
      return new AcceleratorStandingStoneTile();
    }
    return super.createTileEntity(state, world);
  }
}
