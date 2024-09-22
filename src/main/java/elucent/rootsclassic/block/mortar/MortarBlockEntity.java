package elucent.rootsclassic.block.mortar;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class MortarBlockEntity extends BEBase {

  public final ItemStackHandler inventory = new ItemStackHandler(8) {

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
      return 1;
    }

    @Override
    protected void onContentsChanged(int slot) {
      super.onContentsChanged(slot);
      calculateRotations();
    }
  };

  public MortarBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public MortarBlockEntity(BlockPos pos, BlockState state) {
    this(RootsRegistry.MORTAR_TILE.get(), pos, state);
  }

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		inventory.deserializeNBT(registries, tag.getCompound("InventoryHandler"));
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("InventoryHandler", inventory.serializeNBT(registries));
	}

  @Override
  public void breakBlock(Level levelAccessor, BlockPos pos, BlockState state, Player player) {
    dropAllItems(levelAccessor, pos);
    this.setRemoved();
  }

  @Override
  public ItemInteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    if (hand == InteractionHand.MAIN_HAND) {
      if (heldItem.isEmpty()) {
        return tryDropSingleItem(levelAccessor, pos, state);
      }
      else if (heldItem.getItem() == RootsRegistry.PESTLE.get()) {
        return tryActivateRecipe(player, state);
      }
      else {
        return tryInsertItem(levelAccessor, pos, state, heldItem);
      }
    }
    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
  }

  private ItemInteractionResult tryInsertItem(Level levelAccessor, BlockPos pos, BlockState state, ItemStack heldItem) {
    if (!heldItem.isEmpty() && !InventoryUtil.isFull(inventory)) {
      ItemStack heldCopy = heldItem.copy();
      heldCopy.setCount(1);
      if (heldItem.getItem() == Items.GLOWSTONE_DUST || heldItem.getItem() == Items.REDSTONE || heldItem.getItem() == Items.GUNPOWDER) {
        int maxCapacity = ComponentRecipe.getModifierCapacity(InventoryUtil.createWrappedInventory(inventory));
        int modifierCount = ComponentRecipe.getModifierCount(InventoryUtil.createWrappedInventory(inventory));
        if (modifierCount < maxCapacity) {
          ItemStack restStack = ItemHandlerHelper.insertItem(inventory, heldCopy, false);
          if (restStack.isEmpty()) {
            heldItem.shrink(1);
            setChanged();
            levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
            return ItemInteractionResult.SUCCESS;
          }
          else {
            return ItemInteractionResult.FAIL;
          }
        }
      }
      else {
        ItemStack restStack = ItemHandlerHelper.insertItem(inventory, heldCopy, false);
        if (restStack.isEmpty()) {
          heldItem.shrink(1);
          setChanged();
          levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
          return ItemInteractionResult.SUCCESS;
        }
        else {
          return ItemInteractionResult.FAIL;
        }
      }
    }
    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
  }

  private ItemInteractionResult tryDropSingleItem(Level levelAccessor, BlockPos pos, BlockState state) {
    if (!InventoryUtil.isEmpty(inventory)) {
      ItemStack lastStack = InventoryUtil.getLastStack(inventory);
      if (!lastStack.isEmpty()) {
        dropItem(lastStack, 0.5F);
        lastStack.shrink(1);
      }
      setChanged();
      levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
      return ItemInteractionResult.SUCCESS;
    }
    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
  }

  private ItemInteractionResult tryActivateRecipe(Player player, BlockState state) {
    RecipeHolder<ComponentRecipe> recipeHolder = level.getRecipeManager().getRecipeFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get(),
	    InventoryUtil.createWrappedInventory(inventory), level).orElse(null);
		if (recipeHolder == null) {
      player.displayClientMessage(Component.translatable("rootsclassic.mortar.invalid"), true);
      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    else if (recipeHolder.value().needsMixin() && ComponentRecipe.getModifierCapacity(InventoryUtil.createWrappedInventory(inventory)) < 0) {
      player.displayClientMessage(Component.translatable("rootsclassic.mortar.mixin"), true);
      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    if (!level.isClientSide) {
      level.addFreshEntity(new ItemEntity(level,
	      getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5,
	      recipeHolder.value().assemble(InventoryUtil.createWrappedInventory(inventory), level.registryAccess())));
    }
    InventoryUtil.clearInventory(inventory);
    setChanged();
    level.sendBlockUpdated(getBlockPos(), state, level.getBlockState(worldPosition), 3);
    return ItemInteractionResult.SUCCESS;
  }

  public void dropItem(ItemStack stack, float offsetY) {
    ItemStack copyStack = stack.copy();
    if (copyStack.isEmpty() || level.isClientSide) {
      return;
    }
    else {
      BlockPos pos = getBlockPos();
      ItemEntity itementity = new ItemEntity(this.level, pos.getX(), pos.getY() + (double) offsetY, this.worldPosition.getZ(), copyStack);
      itementity.setDefaultPickUpDelay();
      this.level.addFreshEntity(itementity);
    }
  }

  private void dropAllItems(Level levelAccessor, BlockPos pos) {
    for (int i = 0; i < inventory.getSlots(); i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      dropItem(stack, 0F);
    }
  }

  private void calculateRotations() {
    for (int i = 0; i < inventory.getSlots(); i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      if (!stack.isEmpty()) {}
    }
  }
}
