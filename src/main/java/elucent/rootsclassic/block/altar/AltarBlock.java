package elucent.rootsclassic.block.altar;

import elucent.rootsclassic.block.BaseTEBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class AltarBlock extends BaseTEBlock {
  private static final VoxelShape SHAPE = Stream.of(
          Block.box(0, 8, 0, 16, 12, 16),
          Block.box(4, 0, 4, 12, 8, 12),
          Block.box(2, 4, 2, 6, 8, 6),
          Block.box(10, 4, 2, 14, 8, 6),
          Block.box(10, 4, 10, 14, 8, 14),
          Block.box(2, 4, 10, 6, 8, 14)
  ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

  public AltarBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new AltarTile();
  }
}
