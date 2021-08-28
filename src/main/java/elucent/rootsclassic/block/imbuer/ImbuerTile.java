package elucent.rootsclassic.block.imbuer;

import javax.annotation.Nonnull;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.item.SpellPowderItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.tile.TEBase;

public class ImbuerTile extends TEBase implements ITickableTileEntity {
	private static final int STICK = 0;
	private static final int DUST = 1;
	public int progress = 0;
	public int spin = 0;

	public final ItemStackHandler inventory = new ItemStackHandler(2) {
		@Override
		protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
			return 1;
		}

		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			if(slot == 0) {
				return stack.getItem().isIn(Tags.Items.RODS_WOODEN);
			} else {
				return stack.getItem() instanceof SpellPowderItem;
			}
		}
	};
	private LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> inventory);

	public ImbuerTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public ImbuerTile() {
		super(RootsRegistry.IMBUER_TILE.get());
	}

	public ItemStack getStick() {
		return inventory.getStackInSlot(STICK);
	}

	public ItemStack getSpellPowder() {
		return inventory.getStackInSlot(DUST);
	}

	@Override
	public void read(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
		inventory.deserializeNBT(tag.getCompound("InventoryHandler"));
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag = super.write(tag);

		tag.put("InventoryHandler", inventory.serializeNBT());

		return tag;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		for(int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (!stack.isEmpty() && !world.isRemote) {
				world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
			}
		}
		this.remove();
	}

	@Override
	public ActionResultType activate(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, ItemStack heldItem, BlockRayTraceResult hit) {
		if (progress == 0 && hand == Hand.MAIN_HAND) {
			if (heldItem.isEmpty()) {
				if (!getStick().isEmpty()) {
					if (!world.isRemote) {
						world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getStick()));
					}
					inventory.setStackInSlot(STICK, ItemStack.EMPTY);
					markDirty();
					world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
					return ActionResultType.SUCCESS;
				} else if (!getSpellPowder().isEmpty()) {
					if (!world.isRemote) {
						world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getSpellPowder()));
					}
					inventory.setStackInSlot(DUST, ItemStack.EMPTY);
					markDirty();
					world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
					return ActionResultType.SUCCESS;
				}
			} else if (heldItem.getItem().isIn(Tags.Items.RODS_WOODEN)) {
				if (getStick().isEmpty()) {
					ItemStack copyStack = heldItem.copy();
					copyStack.setCount(1);
					inventory.setStackInSlot(STICK, copyStack);
					heldItem.shrink(1);
					markDirty();
					world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
					return ActionResultType.SUCCESS;
				}
			} else if (heldItem.getItem() == RootsRegistry.SPELL_POWDER.get()) {
				if (getSpellPowder().isEmpty()) {
					ItemStack copyStack = heldItem.copy();
					copyStack.setCount(1);
					inventory.setStackInSlot(DUST, copyStack);
					heldItem.shrink(1);
					markDirty();
					world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public void tick() {
		if (progress == 0) {
			spin += 4;
		} else {
			spin += 12;
		}
		ItemStack dustStack = getSpellPowder();
		ItemStack stickStack = getStick();

		if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
			progress++;
		}
		if (progress != 0 && progress % 1 == 0) {
			int chance = world.rand.nextInt(4);
			if (dustStack.hasTag()) {
				CompoundNBT tag = dustStack.getTag();
				if (tag.contains(Const.NBT_EFFECT)) {
					ResourceLocation compName = ResourceLocation.tryCreate(tag.getString(Const.NBT_EFFECT));
					if (compName != null) {
						ComponentBase comp = ComponentManager.getComponentFromName(compName);
						if (comp != null) {
							if (chance == 0) {
								if (world.rand.nextBoolean()) {
									world.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
											getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.125,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								} else {
									world.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
											getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.125,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								}
							}
							if (chance == 1) {
								if (world.rand.nextBoolean()) {
									world.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
											getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.125,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								} else {
									world.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
											getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.125,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								}
							}
							if (chance == 2) {
								if (world.rand.nextBoolean()) {
									world.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
											getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.875,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								} else {
									world.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
											getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.875,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								}
							}
							if (chance == 3) {
								if (world.rand.nextBoolean()) {
									world.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
											getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.875,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								} else {
									world.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
											getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.875,
											getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5);
								}
							}
						}
					}
				}
			}
			if (progress > 40) {
				progress = 0;
				if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
					if (dustStack.hasTag()) {
						ItemStack staff = new ItemStack(RootsRegistry.STAFF.get(), 1);
						CompoundNBT tag = dustStack.getTag();
						String effectName = tag.getString(Const.NBT_EFFECT);
						int potency = tag.getInt(Const.NBT_POTENCY);
						int duration = tag.getInt(Const.NBT_EFFICIENCY);
						int size = tag.getInt(Const.NBT_SIZE);
						StaffItem.createData(staff, effectName, potency, duration, size);
						if (!getWorld().isRemote) {
							getWorld().addEntity(new ItemEntity(getWorld(), getPos().getX() + 0.5, getPos().getY() + 1.0, getPos().getZ() + 0.5, staff));
						}
						inventory.setStackInSlot(STICK, ItemStack.EMPTY);
						inventory.setStackInSlot(DUST, ItemStack.EMPTY);
						markDirty();
						world.notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
					}
				}
			}
		}
	}

	@Override
	protected void invalidateCaps() {
		super.invalidateCaps();
		inventoryHolder.invalidate();
	}
}
