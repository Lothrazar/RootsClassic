package elucent.rootsclassic.block.altar;

import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.ritual.RitualRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AltarBlockEntity extends BEBase {

  private static final int RECIPE_PROGRESS_TIME = 200;
  private List<ItemStack> incenses = new ArrayList<>();
  private int ticker = 0;
  private int progress = 0;
  @Nullable
  private RecipeHolder<RitualRecipe> currentRitual = null;
  private int clientRitualLevel;
  private Color clientRitualColor;
  private Color clientRitualSecondaryColor;
  //	private ItemStack resultItem = ItemStack.EMPTY; TODO: Unused
  public final ItemStackHandler inventory = new ItemStackHandler(3) {

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
      return 1;
    }
  };

  public AltarBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public AltarBlockEntity(BlockPos pos, BlockState state) {
    super(RootsRegistry.ALTAR_TILE.get(), pos, state);
  }

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		inventory.deserializeNBT(registries, tag.getCompound("InventoryHandler"));
		setIncenses(new ArrayList<>());
		if (tag.contains("incenses")) {
			ListTag list = tag.getList("incenses", CompoundTag.TAG_COMPOUND);
			for (int i = 0; i < list.size(); i++) {
				getIncenses().add(ItemStack.parseOptional(registries, list.getCompound(i)));
			}
		}
		if (tag.contains("progress")) {
			setProgress(tag.getInt("progress"));
		}
		if (level != null && level.isClientSide() && tag.contains("ritual", 10)) {
			var ritualTag = tag.getCompound("ritual");
			clientRitualLevel = ritualTag.getInt("level");
			clientRitualColor = new Color(ritualTag.getInt("color"));
			clientRitualSecondaryColor = new Color(ritualTag.getInt("secondaryColor"));
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("InventoryHandler", inventory.serializeNBT(registries));
		if (!getIncenses().isEmpty()) {
			ListTag list = new ListTag();
			for (int i = 0; i < getIncenses().size(); i++) {
				if (!getIncenses().get(i).isEmpty()) {
					list.add(getIncenses().get(i).saveOptional(registries));
				}
			}
			tag.put("incenses", list);
		}
		tag.putInt("progress", getProgress());
		if (level != null && !level.isClientSide() && currentRitual != null) {
			var ritualTag = new CompoundTag();
			RitualRecipe ritual = currentRitual.value();
			ritualTag.putInt("level", ritual.level);
			ritualTag.putInt("color", ritual.getColorInt());
			ritualTag.putInt("secondaryColor", ritual.getSecondaryColorInt());
			tag.put("ritual", ritualTag);
		}
	}

  @Override
  public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    for (int i = 0; i < inventory.getSlots(); i++) {
      if (!levelAccessor.isClientSide) {
        ItemStack slotStack = inventory.getStackInSlot(i);
        if (!slotStack.isEmpty()) {
          levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, slotStack));
        }
      }
    }
    this.setRemoved();
  }

  @Override
  public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    if (hand == InteractionHand.MAIN_HAND) {
      if (heldItem.isEmpty() && !player.isShiftKeyDown() && this.getProgress() == 0) {
        //try to withdraw an item
        if (inventory.getSlots() > 0) {
          ItemStack lastStack = InventoryUtil.getLastStack(inventory);
          if (!lastStack.isEmpty()) {
            if (!levelAccessor.isClientSide) {
              levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, lastStack.copy()));
            }
            lastStack.shrink(1);
            setChanged();
            levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
          }
          return ItemInteractionResult.SUCCESS;
        }
      }
      else if (player.isShiftKeyDown() && heldItem.isEmpty() && this.getProgress() == 0) {
        // Try to start a new ritual
        setCurrentRitual(null);
        var optionalRitual = RitualRegistry.findMatchingByIngredients(this);
        if (optionalRitual.isEmpty()) {
          player.displayClientMessage(Component.translatable("rootsclassic.error.noritual.ingredients"), true);
          return ItemInteractionResult.FAIL;
        }
        var recipeHolder = optionalRitual.get();
				var recipe = recipeHolder.value();
        if (!RitualPillars.verifyPositionBlocks(recipe, levelAccessor, pos)) {
          player.displayClientMessage(Component.translatable("rootsclassic.error.noritual.stones"), true);
          return ItemInteractionResult.FAIL;
        }
        //does it match everything else?
        if (recipe.incenseMatches(level, pos) || recipe.getIncenses().isEmpty()) {
          setCurrentRitual(recipeHolder);
          setIncenses(RitualRegistry.getIncenses(levelAccessor, pos));
          setProgress(RECIPE_PROGRESS_TIME);
          for (BrazierBlockEntity brazier : RitualPillars.getRecipeBraziers(levelAccessor, pos)) {
            brazier.setBurning(true);
            brazier.setHeldItem(ItemStack.EMPTY);
          }
          //        this.emptyNearbyBraziers();
          //          System.out.println(" ritual STARTED " + ritual.name);
          setChanged();
          levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
          player.displayClientMessage(Component.translatable("rootsclassic.ritual.started"), true);
        }
        else {
          player.displayClientMessage(Component.translatable("rootsclassic.error.noritual.incense"), true);
        }
        return ItemInteractionResult.SUCCESS;
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
          return ItemInteractionResult.SUCCESS;
        }
      }
    }
    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
    tile.setTicker(tile.getTicker() + 3);
    if (tile.getTicker() > 360) {
      tile.setTicker(0);
    }
    if (tile.getProgress() > 0 && tile.getCurrentRitual() != null) {
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
      if (tile.getProgress() == 0 && tile.getCurrentRitual() != null) {
        tile.getCurrentRitual().value().doEffect(level, pos, InventoryUtil.createIInventory(tile.inventory), tile.getIncenses());
        tile.setCurrentRitual(null);
        tile.emptyAltar();
        tile.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
    if (tile.getProgress() > 0 && tile.getCurrentRitual() != null) {
      tile.setProgress(tile.getProgress() - 1);
      var pillars = RitualPillars.getRitualPillars(tile.clientRitualLevel);
      var pillarPositions = pillars.keySet().stream().toList();
      var color = tile.clientRitualColor;
      var secondaryColor = tile.clientRitualSecondaryColor;
      if (color == null || secondaryColor == null) return;
      if (pillarPositions.size() > 0) {
        BlockPos particlePos = pillarPositions.get(level.random.nextInt(pillarPositions.size())).above()
            .offset(pos.getX(), pos.getY(), pos.getZ());
        if (level.random.nextInt(6) == 0) {
          level.addParticle(MagicLineParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()),
              particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
              particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
        }
        else {
          level.addParticle(MagicLineParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()),
              particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
              particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
        }
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.random.nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(color.getRed(), color.getGreen(), color.getBlue()),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue()),
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

  public List<ItemStack> getIncenses() {
    return incenses;
  }

  public void setIncenses(List<ItemStack> incenses) {
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

  @Nullable
  public RecipeHolder<RitualRecipe> getCurrentRitual() {
    return this.currentRitual;
  }

  public void setCurrentRitual(RecipeHolder<RitualRecipe> currentRitual) {
    this.currentRitual = currentRitual;
    setChanged();
  }

  public void emptyAltar() {
    for (int i = 0; i < inventory.getSlots(); i++) {
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
}
