package elucent.rootsclassic.block;

import javax.annotation.Nullable;
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

public class BaseBEBlock extends Block {

  public BaseBEBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    if (levelAccessor.getBlockEntity(pos) instanceof BEBase) {
      ((BEBase) levelAccessor.getBlockEntity(pos)).breakBlock(levelAccessor, pos, state, player);
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public InteractionResult use(BlockState state, Level levelAccessor, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
    if (levelAccessor.getBlockEntity(pos) instanceof BEBase) {
      return ((BEBase) levelAccessor.getBlockEntity(pos)).activate(levelAccessor, pos, state, player, handIn, player.getItemInHand(handIn), hit);
    }
    return super.use(state, levelAccessor, pos, player, handIn, hit);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> typeA, BlockEntityType<E> typeE, BlockEntityTicker<? super E> typeE2) {
    return typeE == typeA ? (BlockEntityTicker<A>) typeE2 : null;
  }
}
