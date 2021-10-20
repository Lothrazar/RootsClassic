package elucent.rootsclassic.blockentity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

public class BEBase extends BlockEntity {

  public BEBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
    load(packet.getTag());
  }

  @Override
  public CompoundTag getUpdateTag() {
    return save(new CompoundTag());
  }

  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return new ClientboundBlockEntityDataPacket(getBlockPos(), 0, getUpdateTag());
  }

  public void breakBlock(Level world, BlockPos pos, BlockState state, Player player) {
    this.setRemoved();
  }

  public InteractionResult activate(Level world, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    return InteractionResult.PASS;
  }
}
