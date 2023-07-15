package elucent.rootsclassic.block.mortar;

import javax.annotation.Nullable;
import elucent.rootsclassic.block.BaseBEBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MortarBlock extends BaseBEBlock implements EntityBlock {

  private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D);

  public MortarBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter levelAccessor, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new MortarBlockEntity(pos, state);
  }
}
