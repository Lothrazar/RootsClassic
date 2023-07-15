package elucent.rootsclassic.block.flowers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RadiantDaisyBlock extends BushBlock {

  private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);

  public RadiantDaisyBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }
}
