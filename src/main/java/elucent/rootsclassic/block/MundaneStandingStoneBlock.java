package elucent.rootsclassic.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class MundaneStandingStoneBlock extends Block {

  private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

  public MundaneStandingStoneBlock(Properties properties) {
    super(properties);
  }
  //	@Override
  //	public boolean isOpaqueCube(BlockState state) {
  //		return false;
  //	}

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }
}
