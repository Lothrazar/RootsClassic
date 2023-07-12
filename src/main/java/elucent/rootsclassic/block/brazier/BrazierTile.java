package elucent.rootsclassic.block.brazier;

import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.tile.TEBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BrazierTile extends TEBase implements ITickableTileEntity {

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

  public BrazierTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public BrazierTile() {
    super(RootsRegistry.BRAZIER_TILE.get());
  }

  @Override
  public void load(BlockState state, CompoundNBT tag) {
    super.load(state, tag);
    inventory.deserializeNBT(tag.getCompound("InventoryHandler"));
    if (tag.contains("burning")) {
      setBurning(tag.getBoolean("burning"));
    }
    if (tag.contains("progress")) {
      progress = tag.getInt("progress");
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT tag) {
    tag = super.save(tag);
    tag.put("InventoryHandler", inventory.serializeNBT());
    tag.putBoolean("burning", isBurning());
    tag.putInt("progress", progress);
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, BlockState state, PlayerEntity player) {
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
  public ActionResultType activate(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, ItemStack playerItem, BlockRayTraceResult hit) {
    if (playerItem.isEmpty()) {
      if (!getHeldItem().isEmpty() && !isBurning()) {
        if (player.isShiftKeyDown()) {
          player.displayClientMessage(getHeldItem().getHoverName(), true);
        }
        else {
          dropContaining();
          notifyUpdate(state);
          player.displayClientMessage(new TranslationTextComponent("rootsclassic.brazier.burning.empty"), true);
        }
        return ActionResultType.SUCCESS;
      }
      else if (isBurning()) {
        if (player.isShiftKeyDown()) {
          player.displayClientMessage(new TranslationTextComponent("rootsclassic.brazier.burning.off"), true);
          stopBurning();
          notifyUpdate(state);
          return ActionResultType.SUCCESS;
        }
      }
    }
    else if (playerItem.getItem() == Items.FLINT_AND_STEEL) {
      if (!getHeldItem().isEmpty()) {
        startBurning();
        player.displayClientMessage(new TranslationTextComponent("rootsclassic.brazier.burning.on"), true);
        notifyUpdate(state);
        return ActionResultType.SUCCESS;
      }
    }
    else {
      if (getHeldItem().isEmpty()) {
        setHeldItem(new ItemStack(playerItem.getItem(), 1));
        if (playerItem.hasTag()) {
          getHeldItem().setTag(playerItem.getTag());
        }
        playerItem.shrink(1);
        player.displayClientMessage(new TranslationTextComponent("rootsclassic.brazier.burning.added"), true);
        notifyUpdate(state);
        return ActionResultType.SUCCESS;
      }
    }
    return ActionResultType.PASS;
  }

  private void startBurning() {
    setBurning(true);
    progress = TOTAL_BURN_TIME;
  }

  private void stopBurning() {
    setBurning(false);
    progress = 0;
  }

  @Override
  public void tick() {
    setTicker(getTicker() + (isBurning() ? 12 : 3));
    if (progress > 0) {
      World world = getLevel();
      progress--;

      if(world.isClientSide) {
        if (progress % 2 == 0) {
          world.addParticle(ParticleTypes.SMOKE, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, 0, world.random.nextDouble() * 0.0625 + 0.0625, 0);
        }
        if (progress % 20 == 0) {
          world.addParticle(ParticleTypes.FLAME, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, 0, 0, 0);
        }
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
  protected void invalidateCaps() {
    super.invalidateCaps();
    inventoryHolder.invalidate();
  }
}
