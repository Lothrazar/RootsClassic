package elucent.rootsclassic.block;

import com.mojang.serialization.MapCodec;
import elucent.rootsclassic.blockentity.BEBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class BaseBEBlock extends BaseEntityBlock {
  public static final MapCodec<BaseBEBlock> CODEC = simpleCodec(BaseBEBlock::new);

  public BaseBEBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  public BlockState playerWillDestroy(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    if (levelAccessor.getBlockEntity(pos) instanceof BEBase beBase) {
      beBase.breakBlock(levelAccessor, pos, state, player);
    }
    return super.playerWillDestroy(levelAccessor, pos, state, player);
  }

  @Override
  protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
    if (level.getBlockEntity(pos) instanceof BEBase beBase) {
      return beBase.activate(level, pos, state, player, hand, player.getItemInHand(hand), hitResult);
    }
    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
    return null;
  }
}
