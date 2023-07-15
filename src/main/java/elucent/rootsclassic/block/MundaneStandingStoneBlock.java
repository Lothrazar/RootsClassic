package elucent.rootsclassic.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MundaneStandingStoneBlock extends Block {

  private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

  public MundaneStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }
}
