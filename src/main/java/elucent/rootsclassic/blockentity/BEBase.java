package elucent.rootsclassic.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BEBase extends BlockEntity {

  public static final String NBT_INVENTORY = "InventoryHandler";
  public static final String NBT_PROGRESS = "progress";
  public static final String NBT_BURNING = "burning";

  public BEBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
    super.onDataPacket(net, packet);
  }

  @Override
  public CompoundTag getUpdateTag() {
    return super.saveWithoutMetadata();
  }

  public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    this.setRemoved();
  }

  public InteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    return InteractionResult.PASS;
  }
}
