package elucent.rootsclassic.block.altar;

import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.ritual.RitualRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class AltarBlockEntity extends BEBase {
  private static final int RECIPE_PROGRESS_TIME = 200;
  private ArrayList<ItemStack> incenses = new ArrayList<>();
  private int ticker = 0;
  private int progress = 0;
  private ResourceLocation ritualName = null;
  private RitualBase ritualCurrent = null;
  //	private ItemStack resultItem = ItemStack.EMPTY; TODO: Unused
  public final ItemStackHandler inventory = new ItemStackHandler(3) {

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
      return 1;
    }
  };
  private LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> inventory);

  public AltarBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public AltarBlockEntity(BlockPos pos, BlockState state) {
    super(RootsRegistry.ALTAR_TILE.get(), pos, state);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    inventory.deserializeNBT(tag.getCompound("InventoryHandler"));
    setIncenses(new ArrayList<>());
    if (tag.contains("incenses")) {
      ListTag list = tag.getList("incenses", CompoundTag.TAG_COMPOUND);
      for (int i = 0; i < list.size(); i++) {
        getIncenses().add(ItemStack.of(list.getCompound(i)));
      }
    }
    if (tag.contains("ritualName")) {
      setRitualNameFromString(tag.getString("ritualName"));
      setRitualCurrent(RitualBaseRegistry.RITUALS.get().getValue(getRitualName()));
    }
    if (tag.contains("progress")) {
      setProgress(tag.getInt("progress"));
    }
  }

  @Override
  public void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    tag.put("InventoryHandler", inventory.serializeNBT());
    if (getIncenses().size() > 0) {
      ListTag list = new ListTag();
      for (int i = 0; i < getIncenses().size(); i++) {
        //  ;
        //        if (getIncenses().get(i) != null) {
        list.add(getIncenses().get(i).save(new CompoundTag()));
        //        }
      }
      tag.put("incenses", list);
    }
    if (getRitualName() != null) {
      tag.putString("ritualName", getRitualName().toString());
    }
    tag.putInt("progress", getProgress());
  }

  @Override
  public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    for (int i = 0; i < inventory.getSlots(); i++) {
      if (!levelAccessor.isClientSide) {
        ItemStack slotStack = inventory.getStackInSlot(i);
        if(!slotStack.isEmpty()) {
          levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, slotStack));
        }
      }
    }
    this.setRemoved();
  }

  @Override
  public InteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    if(hand == InteractionHand.MAIN_HAND) {
      if (heldItem.isEmpty() && !player.isShiftKeyDown() && this.getProgress() == 0) {
        //try to withdraw an item
        if (inventory.getSlots() > 0) {
          ItemStack lastStack = InventoryUtil.getLastStack(inventory);
          if(!lastStack.isEmpty()) {
            if (!levelAccessor.isClientSide) {
              levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, lastStack.copy()));
            }
            lastStack.shrink(1);
            setChanged();
            levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
          }
          return InteractionResult.SUCCESS;
        }
      }
      else if (player.isShiftKeyDown() && heldItem.isEmpty() && this.getProgress() == 0) {
        // Try to start a new ritual
        setRitualName(null);
        setRitualCurrent(null);
        RitualBase ritual = RitualRegistry.findMatchingByIngredients(this);
        if (ritual == null) {
          player.displayClientMessage(new TranslatableComponent("rootsclassic.error.noritual.ingredients"), true);
          return InteractionResult.FAIL;
        }
        if (!ritual.verifyPositionBlocks(levelAccessor, pos)) {
          player.displayClientMessage(new TranslatableComponent("rootsclassic.error.noritual.stones"), true);
          return InteractionResult.FAIL;
        }
        //does it match everything else?
        if (ritual.incenseMatches(level, pos)) {
          setRitualCurrent(ritual);
          setRitualName(ritual.getRegistryName());
          setIncenses(RitualRegistry.getIncenses(levelAccessor, pos));
          setProgress(RECIPE_PROGRESS_TIME);
          for (BrazierBlockEntity brazier : ritual.getRecipeBraziers(levelAccessor, pos)) {
            brazier.setBurning(true);
            brazier.setHeldItem(ItemStack.EMPTY);
          }
          //        this.emptyNearbyBraziers();
          //          System.out.println(" ritual STARTED " + ritual.name);
          setChanged();
          levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
          player.displayClientMessage(new TranslatableComponent("rootsclassic.ritual.started"), true);
        }
        else {
          player.displayClientMessage(new TranslatableComponent("rootsclassic.error.noritual.incense"), true);
        }
        return InteractionResult.SUCCESS;
        //      if (getRitualName() == null) {
        //          // but wait tho, maybe its valid but its not lit on fire yet
        //        Roots.statusMessage(player, "rootsclassic.error.noritual");
        //
        //        markDirty();
        //        levelAccessor.notifyBlockUpdate(getPos(), state, levelAccessor.getBlockState(pos), 3);
        //        return true;
        //      }
      }
      else {
        //try to insert an item into the altar
        if (!InventoryUtil.isFull(inventory) && getProgress() == 0) {
          ItemStack copyStack = heldItem.copy();
          copyStack.setCount(1);
          ItemStack remaining = ItemHandlerHelper.insertItem(inventory, copyStack, false);
          if (remaining.isEmpty()) {
            heldItem.shrink(1);
            setChanged();
            levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
          }
          return InteractionResult.SUCCESS;
        }
      }
    }
    return InteractionResult.PASS;
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
    tile.setTicker(tile.getTicker() + 3);
    if (tile.getTicker() > 360) {
      tile.setTicker(0);
    }
    if (tile.getProgress() > 0 && tile.getRitualCurrent() != null) {
      tile.setProgress(tile.getProgress() - 1);
      //      if (tile.getProgress() % 40 == 0) {
      //        setIncenses(RitualManager.getIncenses(getWorld(), getPos()));
      //        boolean doesMatch = false;
      //        for (int i = 0; i < RitualManager.rituals.size(); i++) {
      //          if (RitualManager.rituals.get(i).incenseMatches(getWorld(), getPos())) {
      //            doesMatch = true;
      //          }
      //        }
      //        if (!doesMatch) {
      //          setRitualCurrent(null);
      //          setRitualName(null);
      //        }
      //      }
      if (tile.getProgress() == 0 && tile.getRitualCurrent() != null) {
        tile.getRitualCurrent().doEffect(level, pos, InventoryUtil.createIInventory(tile.inventory), tile.getIncenses());
        tile.setRitualName(null);
        tile.setRitualCurrent(null);
        tile.emptyAltar();
        tile.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
    if (tile.getProgress() > 0 && tile.getRitualCurrent() != null) {
      tile.setProgress(tile.getProgress() - 1);
      if (tile.getRitualCurrent().getPositionsRelative().size() > 0) {
        BlockPos particlePos = tile.getRitualCurrent().getPositionsRelative().get(level.random.nextInt(tile.getRitualCurrent().getPositionsRelative().size())).above()
                .offset(pos.getX(), pos.getY(), pos.getZ());
        if (level.random.nextInt(6) == 0) {
          level.addParticle(MagicLineParticleData.createData(tile.getRitualCurrent().getColor().x, tile.getRitualCurrent().getColor().y, tile.getRitualCurrent().getColor().z),
                  particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
                  particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
        }
        else {
          level.addParticle(MagicLineParticleData.createData(tile.getRitualCurrent().getSecondaryColor().x, tile.getRitualCurrent().getSecondaryColor().y, tile.getRitualCurrent().getSecondaryColor().z),
                  particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
                  particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
        }
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getColor().x, tile.getRitualCurrent().getColor().y, tile.getRitualCurrent().getColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getSecondaryColor().x, tile.getRitualCurrent().getSecondaryColor().y, tile.getRitualCurrent().getSecondaryColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getColor().x, tile.getRitualCurrent().getColor().y, tile.getRitualCurrent().getColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getSecondaryColor().x, tile.getRitualCurrent().getSecondaryColor().y, tile.getRitualCurrent().getSecondaryColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getColor().x, tile.getRitualCurrent().getColor().y, tile.getRitualCurrent().getColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getSecondaryColor().x, tile.getRitualCurrent().getSecondaryColor().y, tile.getRitualCurrent().getSecondaryColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getColor().x, tile.getRitualCurrent().getColor().y, tile.getRitualCurrent().getColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                .125 * Math.sin(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(tile.getRitualCurrent().getSecondaryColor().x, tile.getRitualCurrent().getSecondaryColor().y, tile.getRitualCurrent().getSecondaryColor().z),
                pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
                .125 * Math.sin(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
    }
  }

  //  private void emptyNearbyBraziers() {
  //    for (TileEntityBrazier brazier : getRitualCurrent().getRecipeBraziers(world, pos)) {
  //      brazier.setHeldItem(ItemStack.EMPTY);
  //      brazier.setBurning(false);
  //    }
  //  }

  public ArrayList<ItemStack> getIncenses() {
    return incenses;
  }

  public void setIncenses(ArrayList<ItemStack> incenses) {
    this.incenses = incenses;
  }

  public int getTicker() {
    return ticker;
  }

  public void setTicker(int ticker) {
    this.ticker = ticker;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public ResourceLocation getRitualName() {
    return ritualName;
  }

  public void setRitualNameFromString(String ritualName) {
    this.ritualName = ResourceLocation.tryParse(ritualName);
  }

  public void setRitualName(ResourceLocation ritualName) {
    this.ritualName = ritualName;
  }

  public RitualBase getRitualCurrent() {
    return ritualCurrent;
  }

  public void setRitualCurrent(RitualBase ritualCurrent) {
    this.ritualCurrent = ritualCurrent;
  }

  public void emptyAltar() {
    for(int i = 0; i < inventory.getSlots(); i++) {
      inventory.setStackInSlot(i, ItemStack.EMPTY);
    }
  }
  
  //	public ItemStack getResultItem() { TODO: Unused?
  //		return resultItem;
  //	}
  //
  //	public void setResultItem(ItemStack resultItem) {
  //		if (resultItem == null) {
  //			resultItem = ItemStack.EMPTY;
  //		}
  //		this.resultItem = resultItem;
  //	}

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    this.inventoryHolder.invalidate();
  }
}
