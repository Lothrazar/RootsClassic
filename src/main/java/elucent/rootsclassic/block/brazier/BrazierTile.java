package elucent.rootsclassic.block.brazier;

import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.tile.TEBase;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BrazierTile extends TEBase {

  private static final int TOTAL_BURN_TIME = 2400;
  private boolean burning = false;
  private int progress = 0;
  public final ItemStackHandler inventory = new ItemStackHandler(1) {

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
      return 1;
    }
  };
  private LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> inventory);

  public BrazierTile(BlockPos pos, BlockState state) {
    super(RootsRegistry.BRAZIER_TILE.get(), pos, state);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    inventory.deserializeNBT(tag.getCompound("InventoryHandler"));
    if (tag.contains("burning")) {
      setBurning(tag.getBoolean("burning"));
    }
    if (tag.contains("progress")) {
      progress = tag.getInt("progress");
    }
  }

  @Override
  public CompoundTag save(CompoundTag tag) {
    tag = super.save(tag);
    tag.put("InventoryHandler", inventory.serializeNBT());
    tag.putBoolean("burning", isBurning());
    tag.putInt("progress", progress);
    return tag;
  }

  @Override
  public void breakBlock(Level world, BlockPos pos, BlockState state, Player player) {
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
  public InteractionResult activate(Level world, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack playerItem, BlockHitResult hit) {
    if (playerItem.isEmpty()) {
      if (!getHeldItem().isEmpty() && !isBurning()) {
        if (player.isShiftKeyDown()) {
          player.displayClientMessage(getHeldItem().getHoverName(), true);
        }
        else {
          dropContaining();
          notifyUpdate(state);
          player.displayClientMessage(new TranslatableComponent("rootsclassic.brazier.burning.empty"), true);
        }
        return InteractionResult.SUCCESS;
      }
      else if (isBurning()) {
        if (player.isShiftKeyDown()) {
          player.displayClientMessage(new TranslatableComponent("rootsclassic.brazier.burning.off"), true);
          stopBurning();
          notifyUpdate(state);
          return InteractionResult.SUCCESS;
        }
      }
    }
    else if (playerItem.getItem() == Items.FLINT_AND_STEEL) {
      if (!getHeldItem().isEmpty()) {
        startBurning();
        player.displayClientMessage(new TranslatableComponent("rootsclassic.brazier.burning.on"), true);
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
        player.displayClientMessage(new TranslatableComponent("rootsclassic.brazier.burning.added"), true);
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

  private void tick() {
    setTicker(getTicker() + (isBurning() ? 12 : 3));
    if (progress > 0) {
      Level world = getLevel();
      progress--;
      if (progress % 2 == 0) {
        world.addParticle(ParticleTypes.SMOKE, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, 0, world.random.nextDouble() * 0.0625 + 0.0625, 0);
      }
      if (progress % 20 == 0) {
        world.addParticle(ParticleTypes.FLAME, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, 0, 0, 0);
      }
      if (progress <= 0) {
        setBurning(false);
        //        heldItem = ItemStack.EMPTY;
        setChanged();
        world.sendBlockUpdated(getBlockPos(), world.getBlockState(getBlockPos()), world.getBlockState(getBlockPos()), 3);
      }
    }
    if (getTicker() > 360) {
      setTicker(0);
    }
  }

  public boolean isBurning() {
    return burning;
  }

  public void setBurning(boolean burning) {
    this.burning = burning;
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
