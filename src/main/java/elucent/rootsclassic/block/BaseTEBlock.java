package elucent.rootsclassic.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.tile.TEBase;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public abstract class BaseTEBlock extends BaseEntityBlock {

  public BaseTEBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
    if (world.getBlockEntity(pos) instanceof TEBase) {
      ((TEBase) world.getBlockEntity(pos)).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
    if (worldIn.getBlockEntity(pos) instanceof TEBase) {
      return ((TEBase) worldIn.getBlockEntity(pos)).activate(worldIn, pos, state, player, handIn, player.getItemInHand(handIn), hit);
    }
    return super.use(state, worldIn, pos, player, handIn, hit);
  }
}
