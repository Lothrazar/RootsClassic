package elucent.rootsclassic.block.brazier;

import javax.annotation.Nonnull;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BrazierBlockEntity extends BEBase {

  private static final int TOTAL_BURN_TIME = 2400;
  //	private ItemStack heldItem = ItemStack.EMPTY;
  private int ticker = 0;
  private boolean burning = false;
  private int progress = 0;
  public final ItemStackHandler inventory = new ItemStackHandler(1) {

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
      return 1;
    }
  };
  private LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> inventory);

  public BrazierBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public BrazierBlockEntity(BlockPos pos, BlockState state) {
    super(RootsRegistry.BRAZIER_TILE.get(), pos, state);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    inventory.deserializeNBT(tag.getCompound(NBT_INVENTORY));
    if (tag.contains(NBT_BURNING)) {
      setBurning(tag.getBoolean(NBT_BURNING));
    }
    if (tag.contains(NBT_PROGRESS)) {
      progress = tag.getInt(NBT_PROGRESS);
    }
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    tag.put(NBT_INVENTORY, inventory.serializeNBT());
    tag.putBoolean(NBT_BURNING, isBurning());
    tag.putInt(NBT_PROGRESS, progress);
  }

  @Override
  public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    if (getHeldItem() != null && !isBurning()) {
      dropContaining();
    }
    this.setRemoved();
  }

  private void dropContaining() {
    if (!level.isClientSide) {
      level.addFreshEntity(new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 1.0, worldPosition.getZ() + 0.5, getHeldItem()));
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
          player.displayClientMessage(getHeldItem().getHoverName(), true);
        }
        else {
          dropContaining();
          notifyUpdate(state);
          player.displayClientMessage(Component.translatable("rootsclassic.brazier.burning.empty"), true);
        }
        return InteractionResult.SUCCESS;
      }
      else if (isBurning()) {
        if (player.isShiftKeyDown()) {
          player.displayClientMessage(Component.translatable("rootsclassic.brazier.burning.off"), true);
          stopBurning();
          notifyUpdate(state);
          return InteractionResult.SUCCESS;
        }
      }
    }
    else if (playerItem.getItem() == Items.FLINT_AND_STEEL) {
      if (!getHeldItem().isEmpty()) {
        startBurning();
        player.displayClientMessage(Component.translatable("rootsclassic.brazier.burning.on"), true);
        notifyUpdate(state);
        return InteractionResult.SUCCESS;
      }
    }
    else {
      if (getHeldItem().isEmpty()) {
        setHeldItem(new ItemStack(playerItem.getItem(), 1));
        if (playerItem.hasTag()) {
          getHeldItem().setTag(playerItem.getTag());
        }
        playerItem.shrink(1);
        player.displayClientMessage(Component.translatable("rootsclassic.brazier.burning.added"), true);
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
    tile.setTicker(tile.getTicker() + (tile.isBurning() ? 12 : 3));
    if (tile.progress > 0) {
      tile.progress--;
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

  public static void clientTick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity tile) {
    tile.setTicker(tile.getTicker() + (tile.isBurning() ? 12 : 3));
    if (tile.progress > 0) {
      tile.progress--;
      if (level.isClientSide) {
        if (tile.progress % 2 == 0) {
          level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, level.random.nextDouble() * 0.0625 + 0.0625, 0);
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

  public ItemStack getHeldItem() {
    return inventory.getStackInSlot(0);
  }

  public void setHeldItem(ItemStack heldItem) {
    inventory.setStackInSlot(0, heldItem);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    inventoryHolder.invalidate();
  }
}
