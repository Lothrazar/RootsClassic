package elucent.rootsclassic.block;

import elucent.rootsclassic.blockentity.BEBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BaseBEBlock extends Block {

  public BaseBEBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
    if (world.getBlockEntity(pos) instanceof BEBase) {
      ((BEBase) world.getBlockEntity(pos)).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
    if (worldIn.getBlockEntity(pos) instanceof BEBase) {
      return ((BEBase) worldIn.getBlockEntity(pos)).activate(worldIn, pos, state, player, handIn, player.getItemInHand(handIn), hit);
    }
    return super.use(state, worldIn, pos, player, handIn, hit);
  }

  @Nullable
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> typeA, BlockEntityType<E> typeE, BlockEntityTicker<? super E> typeE2) {
    return typeE == typeA ? (BlockEntityTicker<A>)typeE2 : null;
  }
}
