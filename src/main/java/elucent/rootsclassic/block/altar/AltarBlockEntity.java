package elucent.rootsclassic.block.altar;

import com.google.common.collect.Lists;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualEffect;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.ritual.RitualRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AltarBlockEntity extends BEBase {
	private static final int RECIPE_PROGRESS_TIME = 200;
	private List<ItemStack> incenses = Collections.emptyList();
	private int ticker = 0;
	private int progress = 0;

	@Nullable
	private RitualRecipe<?> ritualCurrent = null;
	private int clientRitualLevel;
	private BlockPos clientRitualColor = BlockPos.ZERO;
	private BlockPos clientRitualSecondaryColor = BlockPos.ZERO;

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
				incenses.add(ItemStack.of(list.getCompound(i)));
			}
		}
		
		/*
		if (tag.contains("ritualName")) {
			setRitualNameFromString(tag.getString("ritualName"));
			setRitualCurrent(RitualRegistry.byName(getRitualName(), level.getRecipeManager()));
		}
		*/

		if (level != null && level.isClientSide() && tag.contains("ritual", 10)) {
			var ritualTag = tag.getCompound("ritual");
			clientRitualLevel = ritualTag.getInt("level");
			clientRitualColor = NbtUtils.readBlockPos(ritualTag.getCompound("color"));
			clientRitualSecondaryColor = NbtUtils.readBlockPos(ritualTag.getCompound("secondaryColor"));
		}

		if (tag.contains("progress")) {
			setProgress(tag.getInt("progress"));
		}
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("InventoryHandler", inventory.serializeNBT());
		if (incenses.size() > 0) {
			ListTag list = new ListTag();
			for (int i = 0; i < incenses.size(); i++) {
				//  ;
				//        if (incenses.get(i) != null) {
				list.add(incenses.get(i).save(new CompoundTag()));
				//        }
			}
			tag.put("incenses", list);
		}
		
		/*
		if (getRitualName() != null) {
			tag.putString("ritualName", getRitualName().toString());
		}
		*/

		if (level != null && !level.isClientSide() && ritualCurrent != null) {
			var ritualTag = new CompoundTag();
			ritualTag.putInt("level", ritualCurrent.level);
			ritualTag.put("color", NbtUtils.writeBlockPos(ritualCurrent.color));
			ritualTag.put("secondaryColor", NbtUtils.writeBlockPos(ritualCurrent.secondaryColor));
			tag.put("ritual", ritualTag);
		}

		tag.putInt("progress", getProgress());
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
	public InteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
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
					return InteractionResult.SUCCESS;
				}
			} else if (player.isShiftKeyDown() && heldItem.isEmpty() && this.getProgress() == 0) {
				// Try to start a new ritual

				setRitual(null);

				var optionalRitual = RitualRegistry.findMatchingByIngredients(this);
				if (optionalRitual.isEmpty()) {
					player.displayClientMessage(new TranslatableComponent("rootsclassic.error.noritual.ingredients"), true);
					return InteractionResult.FAIL;
				}

				var ritual = optionalRitual.get();

				if (!RitualPillars.verifyPositionBlocks(ritual, levelAccessor, pos)) {
					player.displayClientMessage(new TranslatableComponent("rootsclassic.error.noritual.stones"), true);
					return InteractionResult.FAIL;
				}
				//does it match everything else?
				if (ritual.incenseMatches(level, pos)) {
					setRitual(ritual);

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
					player.displayClientMessage(new TranslatableComponent("rootsclassic.ritual.started"), true);
				} else {
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
			} else {
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
		if (tile.getProgress() > 0 && tile.ritualCurrent != null) {
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
			if (tile.getProgress() == 0 && tile.ritualCurrent != null) {
				tile.ritualCurrent.doEffect(level, pos, InventoryUtil.createIInventory(tile.inventory), tile.incenses);

				tile.setRitual(null);
				tile.emptyAltar();
				tile.setChanged();
				level.sendBlockUpdated(pos, state, state, 3);
			}
		}
	}

	public static void clientTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
		if (tile.getProgress() > 0 && tile.ritualCurrent != null) {
			tile.setProgress(tile.getProgress() - 1);
			var pillars = RitualPillars.getRitualPillars(tile.clientRitualLevel);
			var pillarPositions = pillars.keySet().stream().toList();
			var color = tile.clientRitualColor;
			var secondaryColor = tile.clientRitualSecondaryColor;

			if (!pillars.isEmpty()) {
				BlockPos particlePos = pillarPositions.get(level.random.nextInt(pillars.size())).above().offset(pos);
				if (level.random.nextInt(6) == 0) {
					level.addParticle(MagicLineParticleData.createData(color.getX(), color.getY(), color.getZ()),
						particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
						particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
				} else {
					level.addParticle(MagicLineParticleData.createData(secondaryColor.getX(), secondaryColor.getY(), secondaryColor.getZ()),
						particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
						particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
				}
			}

			if (level.random.nextInt(4) == 0) {
				level.addParticle(MagicAltarParticleData.createData(color.getX(), color.getY(), color.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
			} else {
				level.addParticle(MagicAltarParticleData.createData(secondaryColor.getX(), secondaryColor.getY(), secondaryColor.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
			}
			if (level.random.nextInt(4) == 0) {
				level.addParticle(MagicAltarParticleData.createData(color.getX(), color.getY(), color.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
			} else {
				level.addParticle(MagicAltarParticleData.createData(secondaryColor.getX(), secondaryColor.getY(), secondaryColor.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
			}
			if (level.random.nextInt(4) == 0) {
				level.addParticle(MagicAltarParticleData.createData(color.getX(), color.getY(), color.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
			} else {
				level.addParticle(MagicAltarParticleData.createData(secondaryColor.getX(), secondaryColor.getY(), secondaryColor.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
			}
			if (level.random.nextInt(4) == 0) {
				level.addParticle(MagicAltarParticleData.createData(color.getX(), color.getY(), color.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
			} else {
				level.addParticle(MagicAltarParticleData.createData(secondaryColor.getX(), secondaryColor.getY(), secondaryColor.getZ()),
					pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
					.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
			}
		}
	}

	//  private void emptyNearbyBraziers() {
	//    for (TileEntityBrazier brazier : ritualCurrent.getRecipeBraziers(world, pos)) {
	//      brazier.setHeldItem(ItemStack.EMPTY);
	//      brazier.setBurning(false);
	//    }
	//  }

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

	public void setRitual(RitualRecipe<?> ritualCurrent) {
		this.ritualCurrent = ritualCurrent;
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

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		this.inventoryHolder.invalidate();
	}
}
