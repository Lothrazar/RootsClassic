package elucent.rootsclassic.block;

import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.tile.GrowerStandingStoneTile;
import elucent.rootsclassic.tile.TEBase;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GrowerStandingStoneBlock extends AttunedStandingStoneBlock {

  public GrowerStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
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
  public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
    if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
      return new GrowerStandingStoneTile();
    }
    return super.createTileEntity(state, world);
  }
}
