package elucent.rootsclassic.block;

import elucent.rootsclassic.Roots;
import elucent.rootsclassic.tileentity.TileEntityBrazier;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrazier extends TEBlockBase implements ITileEntityProvider {

  private static final AxisAlignedBB AXIS_ALIGNED_BB = new AxisAlignedBB(0.1875, 0, 0.1875, 0.8125, 0.75, 0.8125);

  public BlockBrazier() {
    super(Material.GROUND);
    setCreativeTab(Roots.tab);
    setHardness(1.0f);
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    return AXIS_ALIGNED_BB;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
    return (layer == BlockRenderLayer.CUTOUT);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityBrazier();
  }
}
