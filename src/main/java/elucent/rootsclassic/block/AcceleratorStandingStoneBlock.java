package elucent.rootsclassic.block;

import elucent.rootsclassic.tile.AcceleratorStandingStoneTile;
import elucent.rootsclassic.tile.TEBase;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class AcceleratorStandingStoneBlock extends AttunedStandingStoneBlock {

  public AcceleratorStandingStoneBlock(Properties properties) {
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
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
      return new AcceleratorStandingStoneTile(pos, state);
    }
    return super.newBlockEntity(pos, state);
  }
}
