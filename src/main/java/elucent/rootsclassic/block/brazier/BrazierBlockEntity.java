package elucent.rootsclassic.block.brazier;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import javax.annotation.Nonnull;

public class BrazierBlockEntity extends BEBase {

  private static final int TOTAL_BURN_TIME = 2400;
  private int ticker = 0;
  private boolean burning = false;
  private int progress = 0;
  public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(1) {

    @Override
    protected int getCapacity(int slot, @Nonnull ItemResource stack) {
      return 1;
    }
  };

  public BrazierBlockEntity(BlockPos pos, BlockState state) {
    super(RootsRegistry.BRAZIER_TILE.get(), pos, state);
  }

  @Override
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    inventory.deserialize(input);
    setBurning(input.getBooleanOr(NBT_BURNING, false));
    progress = input.getIntOr(NBT_PROGRESS, 0);
  }

  @Override
  protected void saveAdditional(ValueOutput output) {
    super.saveAdditional(output);
    inventory.serialize(output);
    output.putBoolean(NBT_BURNING, isBurning());
    output.putInt(NBT_PROGRESS, progress);
  }

  @Override
  public void preRemoveSideEffects(BlockPos pos, BlockState state) {
    super.preRemoveSideEffects(pos, state);
    dropContaining(pos);
  }

  private void dropContaining(BlockPos pos) {
    if (!level.isClientSide()) {
      try (Transaction tx = Transaction.openRoot()) {
        for (int i = 0; i < inventory.size(); ++i) {
          if (!inventory.getResource(i).isEmpty())
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getResource(i).toStack());
        }
        tx.commit();
      }
    }
    setHeldItem(ItemStack.EMPTY);
  }

  private void notifyUpdate(BlockState state) {
    this.setChanged();
    this.getLevel().sendBlockUpdated(getBlockPos(), state, level.getBlockState(worldPosition), 3);
  }

  @Override
  public InteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack playerItem, BlockHitResult hit) {
    if (playerItem.isEmpty()) {
      if (!getHeldItem().isEmpty() && !isBurning()) {
        if (player.isShiftKeyDown()) {
          player.sendOverlayMessage(getHeldItem().getHoverName());
        }
        else {
          dropContaining(worldPosition);
          notifyUpdate(state);
          player.sendOverlayMessage(Component.translatable("rootsclassic.brazier.burning.empty"));
        }
        return InteractionResult.SUCCESS;
      }
      else if (isBurning()) {
        if (player.isShiftKeyDown()) {
          player.sendOverlayMessage(Component.translatable("rootsclassic.brazier.burning.off"));
          stopBurning();
          notifyUpdate(state);
          return InteractionResult.SUCCESS;
        }
      }
    }
    else if (playerItem.getItem() == Items.FLINT_AND_STEEL) {
      if (!getHeldItem().isEmpty()) {
        startBurning();
        player.sendOverlayMessage(Component.translatable("rootsclassic.brazier.burning.on"));
        notifyUpdate(state);
        return InteractionResult.SUCCESS;
      }
    }
    else {
      if (getHeldItem().isEmpty()) {
        setHeldItem(playerItem.copyWithCount(1));
        playerItem.consume(1, player);
        player.sendOverlayMessage(Component.translatable("rootsclassic.brazier.burning.added"));
        notifyUpdate(state);
        return InteractionResult.SUCCESS;
      }
    }
    return InteractionResult.PASS;
  }

  private void startBurning() {
    setBurning(true);
    progress = TOTAL_BURN_TIME;
  }

  private void stopBurning() {
    setBurning(false);
    progress = 0;
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity tile) {
    if (tile.progress > 0) {
      tile.progress--;
      if (tile.progress <= 0) {
        tile.setBurning(false);
        //        heldItem = ItemStack.EMPTY;
        tile.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity tile) {
    tile.setTicker(tile.getTicker() + (tile.isBurning() ? 12 : 3));
    if (tile.progress > 0) {
      tile.progress--;
      if (level.isClientSide()) {
        if (tile.progress % 2 == 0) {
          level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, level.getRandom().nextDouble() * 0.0625 + 0.0625, 0);
        }
        if (tile.progress % 20 == 0) {
          level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
        }
      }
      if (tile.progress <= 0) {
        tile.setBurning(false);
        //        heldItem = ItemStack.EMPTY;
        tile.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
      }
    }
    if (tile.getTicker() > 360) {
      tile.setTicker(0);
    }
  }

  public boolean isBurning() {
    return burning;
  }

  public void setBurning(boolean burning) {
    this.burning = burning;
  }

  public int getTicker() {
    return ticker;
  }

  public void setTicker(int ticker) {
    this.ticker = ticker;
  }

  /**
   * Gets the item currently in the brazier.
   * MODIFICATIONS TO THE ITEMSTACK RETURNED WILL NOT AFFECT THE ITEM IN THE BRAZIER. Use {@link #setHeldItem} to change the item in the brazier.
   * @return The item currently in the brazier, or an empty stack if there is none.
   */
  public ItemStack getHeldItem() {
    return inventory.getResource(0).toStack();
  }

  public void setHeldItem(ItemStack heldItem) {
    int amount = heldItem.isEmpty() ? 0 : 1;
    ItemResource resource = heldItem.isEmpty() ? ItemResource.EMPTY : ItemResource.of(heldItem);
    try (Transaction tx = Transaction.openRoot()) {
      inventory.set(0, resource, amount);
      tx.commit();
    }
  }
}
