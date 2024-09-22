package elucent.rootsclassic.block;

import elucent.rootsclassic.blockentity.BEBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
  public BlockState playerWillDestroy(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    if (levelAccessor.getBlockEntity(pos) instanceof BEBase beBase) {
	    beBase.breakBlock(levelAccessor, pos, state, player);
    }
	  return super.playerWillDestroy(levelAccessor, pos, state, player);
  }

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof BEBase beBase) {
			return beBase.activate(level, pos, state, player, hand, player.getItemInHand(hand), hitResult);
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

  @SuppressWarnings("unchecked")
  @Nullable
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> typeA, BlockEntityType<E> typeE, BlockEntityTicker<? super E> typeE2) {
    return typeE == typeA ? (BlockEntityTicker<A>) typeE2 : null;
  }
}
