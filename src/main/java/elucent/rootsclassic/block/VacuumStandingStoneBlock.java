package elucent.rootsclassic.block;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import elucent.rootsclassic.tile.TEBase;
import elucent.rootsclassic.tile.VacuumStandingStoneTile;

public class VacuumStandingStoneBlock extends AttunedStandingStoneBlock {

  public VacuumStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    super.onBlockHarvested(world, pos, state, player);
    if (world.getTileEntity(pos) instanceof TEBase) {
      ((TEBase) world.getTileEntity(pos)).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return state.get(HALF) == DoubleBlockHalf.UPPER;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    if (state.get(HALF) == DoubleBlockHalf.UPPER) {
      return new VacuumStandingStoneTile();
    }
    return super.createTileEntity(state, world);
  }
}
