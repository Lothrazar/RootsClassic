package elucent.rootsclassic.block.imbuer;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.item.powder.SpellPowderItem;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ImbuerBlockEntity extends BEBase {

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
      if (slot == 0) {
        return stack.is(Tags.Items.RODS_WOODEN);
      }
      else {
        return stack.getItem() instanceof SpellPowderItem;
      }
    }
  };

  public ImbuerBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public ImbuerBlockEntity(BlockPos pos, BlockState state) {
    super(RootsRegistry.IMBUER_TILE.get(), pos, state);
  }

  public ItemStack getStick() {
    return inventory.getStackInSlot(STICK);
  }

  public ItemStack getSpellPowder() {
    return inventory.getStackInSlot(DUST);
  }

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
    inventory.deserializeNBT(registries, tag.getCompound(NBT_INVENTORY));
  }

  @Override
  public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.saveAdditional(tag, registries);
    tag.put(NBT_INVENTORY, inventory.serializeNBT(registries));
  }

  @Override
  public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    for (int i = 0; i < inventory.getSlots(); i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      if (!stack.isEmpty() && !levelAccessor.isClientSide) {
        levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
      }
    }
    this.setRemoved();
  }

  @Override
  public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    if (progress == 0 && hand == InteractionHand.MAIN_HAND) {
      if (heldItem.isEmpty()) {
        if (!getStick().isEmpty()) {
          if (!levelAccessor.isClientSide) {
            levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getStick()));
          }
          inventory.setStackInSlot(STICK, ItemStack.EMPTY);
          setChanged();
          levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
          return ItemInteractionResult.SUCCESS;
        }
        else if (!getSpellPowder().isEmpty()) {
          if (!levelAccessor.isClientSide) {
            levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getSpellPowder()));
          }
          inventory.setStackInSlot(DUST, ItemStack.EMPTY);
          setChanged();
          levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
          return ItemInteractionResult.SUCCESS;
        }
      }
      else if (heldItem.is(Tags.Items.RODS_WOODEN)) {
        if (getStick().isEmpty()) {
          ItemStack copyStack = heldItem.copy();
          copyStack.setCount(1);
          inventory.setStackInSlot(STICK, copyStack);
          heldItem.shrink(1);
          setChanged();
          levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
          return ItemInteractionResult.SUCCESS;
        }
      }
      else if (heldItem.getItem() == RootsRegistry.SPELL_POWDER.get()) {
        if (getSpellPowder().isEmpty()) {
          ItemStack copyStack = heldItem.copy();
          copyStack.setCount(1);
          inventory.setStackInSlot(DUST, copyStack);
          heldItem.shrink(1);
          setChanged();
          levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
          return ItemInteractionResult.SUCCESS;
        }
      }
    }
    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, ImbuerBlockEntity tile) {
    if (tile.progress == 0) {
      tile.spin += 4;
    }
    else {
      tile.spin += 12;
    }
    ItemStack dustStack = tile.getSpellPowder();
    ItemStack stickStack = tile.getStick();
    if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
      tile.progress++;
    }
    if (tile.progress != 0 && tile.progress % 1 == 0) {
      if (tile.progress > 40) {
        tile.progress = 0;
        if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
	        if (dustStack.has(RootsComponents.SPELL)) {
		        SpellData data = dustStack.get(RootsComponents.SPELL);
            ItemStack staff = new ItemStack(RootsRegistry.STAFF.get(), 1);
            String effectName = data.effect();
            int potency = data.potency();
            int efficiency = data.efficiency();
            int size = data.size();
            StaffItem.createData(staff, effectName, potency, efficiency, size);
            if (!level.isClientSide) {
              level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, staff));
            }
            tile.inventory.setStackInSlot(STICK, ItemStack.EMPTY);
            tile.inventory.setStackInSlot(DUST, ItemStack.EMPTY);
            tile.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
          }
        }
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, ImbuerBlockEntity tile) {
    if (tile.progress == 0) {
      tile.spin += 4;
    }
    else {
      tile.spin += 12;
    }
    ItemStack dustStack = tile.getSpellPowder();
    ItemStack stickStack = tile.getStick();
    if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
      tile.progress++;
    }
    if (tile.progress != 0 && tile.progress % 1 == 0) {
      int chance = level.random.nextInt(4);
      if (dustStack.has(RootsComponents.SPELL)) {
	      SpellData data = dustStack.get(RootsComponents.SPELL);
	      ResourceLocation compName = ResourceLocation.tryParse(data.effect());
	      if (compName != null) {
		      ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get(compName);
		      if (comp != null && level.isClientSide) {
			      if (chance == 0) {
				      if (level.random.nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
			      if (chance == 1) {
				      if (level.random.nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
			      if (chance == 2) {
				      if (level.random.nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
			      if (chance == 3) {
				      if (level.random.nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
		      }
	      }
      }
      if (tile.progress > 40) {
        tile.progress = 0;
      }
    }
  }
}
