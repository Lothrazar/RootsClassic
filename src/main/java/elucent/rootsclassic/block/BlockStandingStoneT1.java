package elucent.rootsclassic.block;

import elucent.rootsclassic.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockStandingStoneT1 extends Block {

  public BlockStandingStoneT1() {
    super(Material.ROCK);
    setCreativeTab(Roots.tab);
    setHardness(1.0f);
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    return new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1.0, 0.75);
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }
}
