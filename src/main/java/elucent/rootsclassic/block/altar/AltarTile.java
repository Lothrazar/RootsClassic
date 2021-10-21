package elucent.rootsclassic.block.altar;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import elucent.rootsclassic.block.brazier.BrazierTile;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.RitualManager;
import elucent.rootsclassic.tile.TEBase;
import elucent.rootsclassic.util.InventoryUtil;

public class AltarTile extends TEBase implements ITickableTileEntity {

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

  public AltarTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public AltarTile() {
    super(RootsRegistry.ALTAR_TILE.get());
  }

  @Override
  public void load(BlockState state, CompoundNBT tag) {
    super.load(state, tag);
    inventory.deserializeNBT(tag.getCompound("InventoryHandler"));
    setIncenses(new ArrayList<>());
    if (tag.contains("incenses")) {
      ListNBT list = tag.getList("incenses", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < list.size(); i++) {
        getIncenses().add(ItemStack.of(list.getCompound(i)));
      }
    }
    if (tag.contains("ritualName")) {
      setRitualNameFromString(tag.getString("ritualName"));
      setRitualCurrent(RitualManager.getRitualFromName(getRitualName()));
    }
    if (tag.contains("progress")) {
      setProgress(tag.getInt("progress"));
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT tag) {
    tag = super.save(tag);
    tag.put("InventoryHandler", inventory.serializeNBT());
    if (getIncenses().size() > 0) {
      ListNBT list = new ListNBT();
      for (int i = 0; i < getIncenses().size(); i++) {
        //  ;
        //        if (getIncenses().get(i) != null) {
        list.add(getIncenses().get(i).save(new CompoundNBT()));
        //        }
      }
      tag.put("incenses", list);
    }
    if (getRitualName() != null) {
      tag.putString("ritualName", getRitualName().toString());
    }
    tag.putInt("progress", getProgress());
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    for (int i = 0; i < inventory.getSlots(); i++) {
      if (!world.isClientSide) {
        ItemStack slotStack = inventory.getStackInSlot(i);
        if(!slotStack.isEmpty()) {
          world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, slotStack));
        }
      }
    }
    this.setRemoved();
  }

  @Override
  public ActionResultType activate(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, ItemStack heldItem, BlockRayTraceResult hit) {
    if(hand == Hand.MAIN_HAND) {
      if (heldItem.isEmpty() && !player.isShiftKeyDown() && this.getProgress() == 0) {
        //try to withdraw an item
        if (inventory.getSlots() > 0) {
          ItemStack lastStack = InventoryUtil.getLastStack(inventory);
          if(!lastStack.isEmpty()) {
            if (!world.isClientSide) {
              world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, lastStack.copy()));
            }
            lastStack.shrink(1);
            setChanged();
            world.sendBlockUpdated(getBlockPos(), state, world.getBlockState(pos), 3);
          }
          return ActionResultType.SUCCESS;
        }
      }
      else if (player.isShiftKeyDown() && heldItem.isEmpty() && this.getProgress() == 0) {
        // Try to start a new ritual
        setRitualName(null);
        setRitualCurrent(null);
        RitualBase ritual = RitualManager.findMatchingByIngredients(this);
        if (ritual == null) {
          player.displayClientMessage(new TranslationTextComponent("rootsclassic.error.noritual.ingredients"), true);
          return ActionResultType.FAIL;
        }
        if (!ritual.verifyPositionBlocks(world, pos)) {
          player.displayClientMessage(new TranslationTextComponent("rootsclassic.error.noritual.stones"), true);
          return ActionResultType.FAIL;
        }
        //does it match everything else?
        if (ritual.incenseMatches(getLevel(), getBlockPos())) {
          setRitualCurrent(ritual);
          setRitualName(ritual.getName());
          setIncenses(RitualManager.getIncenses(world, getBlockPos()));
          setProgress(RECIPE_PROGRESS_TIME);
          for (BrazierTile brazier : ritual.getRecipeBraziers(world, pos)) {
            brazier.setBurning(true);
            brazier.setHeldItem(ItemStack.EMPTY);
          }
          //        this.emptyNearbyBraziers();
          //          System.out.println(" ritual STARTED " + ritual.name);
          setChanged();
          world.sendBlockUpdated(getBlockPos(), state, world.getBlockState(pos), 3);
          player.displayClientMessage(new TranslationTextComponent("rootsclassic.ritual.started"), true);
        }
        else {
          player.displayClientMessage(new TranslationTextComponent("rootsclassic.error.noritual.incense"), true);
        }
        return ActionResultType.SUCCESS;
        //      if (getRitualName() == null) {
        //          // but wait tho, maybe its valid but its not lit on fire yet
        //        Roots.statusMessage(player, "rootsclassic.error.noritual");
        //
        //        markDirty();
        //        world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
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
            world.sendBlockUpdated(getBlockPos(), state, world.getBlockState(pos), 3);
          }
          return ActionResultType.SUCCESS;
        }
      }
    }
    return ActionResultType.PASS;
  }

  @Override
  public void tick() {
    setTicker(getTicker() + 3);
    if (getTicker() > 360) {
      setTicker(0);
    }
    if (getProgress() > 0 && getRitualCurrent() != null) {
      setProgress(getProgress() - 1);
      if (level.isClientSide) {
        if (getRitualCurrent().getPositionsRelative().size() > 0) {
          BlockPos pos = getRitualCurrent().getPositionsRelative().get(level.random.nextInt(getRitualCurrent().getPositionsRelative().size())).above().offset(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
          if (level.random.nextInt(6) == 0) {
            level.addParticle(MagicLineParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
                pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5,
                getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5);
          }
          else {
            level.addParticle(MagicLineParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
                pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5,
                getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5);
          }
        }
        if (level.random.nextInt(4) == 0) {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              0.125 * Math.sin(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)));
        }
        else {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              0.125 * Math.sin(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)));
        }
        if (level.random.nextInt(4) == 0) {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)));
        }
        else {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)));
        }
        if (level.random.nextInt(4) == 0) {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)));
        }
        else {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)));
        }
        if (level.random.nextInt(4) == 0) {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              .125 * Math.sin(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)));
        }
        else {
          level.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
              getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.875, getBlockPos().getZ() + 0.5,
              .125 * Math.sin(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)));
        }
      }
      //      if (getProgress() % 40 == 0) {
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
      if (getProgress() == 0 && getRitualCurrent() != null) {
        getRitualCurrent().doEffect(getLevel(), getBlockPos(), InventoryUtil.createIInventory(inventory), getIncenses());
        setRitualName(null);
        setRitualCurrent(null);
        emptyAltar();
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getLevel().getBlockState(getBlockPos()), getLevel().getBlockState(getBlockPos()), 3);
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
  protected void invalidateCaps() {
    super.invalidateCaps();
    this.inventoryHolder.invalidate();
  }
}
