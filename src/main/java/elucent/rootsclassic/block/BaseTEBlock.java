package elucent.rootsclassic.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import elucent.rootsclassic.tile.TEBase;

public class BaseTEBlock extends Block {

  public BaseTEBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    if (world.getTileEntity(pos) instanceof TEBase) {
      ((TEBase) world.getTileEntity(pos)).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
    if (worldIn.getTileEntity(pos) instanceof TEBase) {
      return ((TEBase) worldIn.getTileEntity(pos)).activate(worldIn, pos, state, player, handIn, player.getHeldItem(handIn), hit);
    }
    return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
  }
}
