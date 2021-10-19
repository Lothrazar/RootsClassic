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

import net.minecraft.block.AbstractBlock.Properties;

public class BaseTEBlock extends Block {

  public BaseTEBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    if (world.getBlockEntity(pos) instanceof TEBase) {
      ((TEBase) world.getBlockEntity(pos)).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
    if (worldIn.getBlockEntity(pos) instanceof TEBase) {
      return ((TEBase) worldIn.getBlockEntity(pos)).activate(worldIn, pos, state, player, handIn, player.getItemInHand(handIn), hit);
    }
    return super.use(state, worldIn, pos, player, handIn, hit);
  }
}
