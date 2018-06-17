package elucent.rootsclassic.block;

import elucent.rootsclassic.Roots;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFlareOrchid extends BlockBush {

  private static final AxisAlignedBB AXIS_ALIGNED_BB = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.5, 0.625);

  public BlockFlareOrchid() {
    super();
    setCreativeTab(Roots.tab);
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
    return (layer == BlockRenderLayer.CUTOUT);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    return AXIS_ALIGNED_BB;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }
}
