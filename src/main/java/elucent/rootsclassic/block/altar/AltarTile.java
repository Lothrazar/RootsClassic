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
	public void read(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
		inventory.deserializeNBT(tag.getCompound("InventoryHandler"));
		setIncenses(new ArrayList<>());
		if (tag.contains("incenses")) {
			ListNBT list = tag.getList("incenses", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < list.size(); i++) {
				getIncenses().add(ItemStack.read(list.getCompound(i)));
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
	public CompoundNBT write(CompoundNBT tag) {
		tag = super.write(tag);

		tag.put("InventoryHandler", inventory.serializeNBT());

		if (getIncenses().size() > 0) {
			ListNBT list = new ListNBT();
			for (int i = 0; i < getIncenses().size(); i++) {
				//  ;
				//        if (getIncenses().get(i) != null) {
				list.add(getIncenses().get(i).write(new CompoundNBT()));
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
			if (!world.isRemote) {
				world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.getStackInSlot(i)));
			}
		}
		this.remove();
	}

	@Override
	public ActionResultType activate(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, ItemStack heldItem, BlockRayTraceResult hit) {
		if (heldItem.isEmpty() && !player.isSneaking() && this.getProgress() == 0) {
			//try to withdraw an item
			if (inventory.getSlots() > 0) {
				ItemStack lastStack = InventoryUtil.getLastStack(inventory);
				if (!world.isRemote) {
					world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, lastStack.copy()));
				} else {
					lastStack.shrink(1);
				}
				markDirty();
				world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
				return ActionResultType.SUCCESS;
			}
		} else if (player.isSneaking() && heldItem.isEmpty() && this.getProgress() == 0) {
			// Try to start a new ritual
			setRitualName(null);
			setRitualCurrent(null);
			RitualBase ritual = RitualManager.findMatchingByIngredients(this);
			if (ritual == null) {
				player.sendStatusMessage(new TranslationTextComponent("rootsclassic.error.noritual.ingredients"), true);
				return ActionResultType.FAIL;
			}
			if (!ritual.verifyPositionBlocks(world, pos)) {
				player.sendStatusMessage(new TranslationTextComponent("rootsclassic.error.noritual.stones"), true);
				return ActionResultType.FAIL;
			}
			//does it match everything else?
			if (ritual.incenseMatches(getWorld(), getPos())) {
				setRitualCurrent(ritual);
				setRitualName(ritual.getName());
				setIncenses(RitualManager.getIncenses(world, getPos()));
				setProgress(RECIPE_PROGRESS_TIME);
				for (BrazierTile brazier : ritual.getRecipeBraziers(world, pos)) {
					brazier.setBurning(true);
					brazier.setHeldItem(ItemStack.EMPTY);
				}
				//        this.emptyNearbyBraziers();
				//          System.out.println(" ritual STARTED " + ritual.name);
				markDirty();
				world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
				player.sendStatusMessage(new TranslationTextComponent("rootsclassic.ritual.started"), true);
			} else {
				player.sendStatusMessage(new TranslationTextComponent("rootsclassic.error.noritual.incense"), true);
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
		} else {
			//try to insert an item into the altar

			if (!InventoryUtil.isFull(inventory) && getProgress() == 0) {
				ItemStack copyStack = heldItem.copy();
				copyStack.setCount(1);

				ItemStack remaining = ItemHandlerHelper.insertItem(inventory, copyStack, false);
				if(remaining.isEmpty()) {
					heldItem.shrink(1);
					markDirty();
					world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
				}
				return ActionResultType.SUCCESS;
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
			if (world.isRemote) {
				if (getRitualCurrent().getPositionsRelative().size() > 0) {
					BlockPos pos = getRitualCurrent().getPositionsRelative().get(world.rand.nextInt(getRitualCurrent().getPositionsRelative().size())).up().add(getPos().getX(), getPos().getY(), getPos().getZ());
					if (world.rand.nextInt(6) == 0) {
						world.addParticle(MagicLineParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
								pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5,
								getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5);
					} else {
						world.addParticle(MagicLineParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
								pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5,
								getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5);
					}
				}
				if (world.rand.nextInt(4) == 0) {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							0.125 * Math.sin(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)));
				} else {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							0.125 * Math.sin(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)));
				}
				if (world.rand.nextInt(4) == 0) {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)));
				} else {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)));
				}
				if (world.rand.nextInt(4) == 0) {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)));
				} else {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)));
				}

				if (world.rand.nextInt(4) == 0) {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)));
				} else {
					world.addParticle(MagicAltarParticleData.createData(getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z),
							getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5,
							.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)));
				}
			}
			//      if (getProgress() % 40 == 0) {
			//        setIncenses(RitualManager.getIncenses(getWorld(), getPos()));
			//        boolean doesMatch = false;
			//        for (int i = 0; i < RitualManager.rituals.size(); i++) {
			//          if (RitualManager.rituals.get(i).incesceMatches(getWorld(), getPos())) {
			//            doesMatch = true;
			//          }
			//        }
			//        if (!doesMatch) {
			//          setRitualCurrent(null);
			//          setRitualName(null);
			//        }
			//      }
			if (getProgress() == 0 && getRitualCurrent() != null) {
				getRitualCurrent().doEffect(getWorld(), getPos(), InventoryUtil.createIInventory(inventory), getIncenses());
				setRitualName(null);
				setRitualCurrent(null);
				markDirty();
				world.notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
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
		this.ritualName = ResourceLocation.tryCreate(ritualName);
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
