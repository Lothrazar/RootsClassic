package elucent.rootsclassic.block.mortar;

import com.mojang.datafixers.util.Pair;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import javax.annotation.Nonnull;

public class MortarBlockEntity extends BEBase {

  public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(8) {

    @Override
    protected int getCapacity(int slot, @Nonnull ItemResource stack) {
      return 1;
    }

    @Override
    protected void onContentsChanged(int index, ItemStack previousContents) {
      super.onContentsChanged(index, previousContents);
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
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    inventory.deserialize(input);
  }

  @Override
  protected void saveAdditional(ValueOutput output) {
    super.saveAdditional(output);
    inventory.serialize(output);
  }

  @Override
  public void preRemoveSideEffects(BlockPos pos, BlockState state) {
    super.preRemoveSideEffects(pos, state);
    try (Transaction tx = Transaction.openRoot()) {
      for (int i = 0; i < inventory.size(); ++i) {
        if (!inventory.getResource(i).isEmpty())
          Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getResource(i).toStack());
      }
      tx.commit();
    }
  }

  @Override
  public InteractionResult activate(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    if (hand == InteractionHand.MAIN_HAND) {
      if (heldItem.isEmpty()) {
        return tryDropSingleItem(level, pos, state);
      }
      else if (heldItem.getItem() == RootsRegistry.PESTLE.get()) {
        return tryActivateRecipe(player, state);
      }
      else {
        return tryInsertItem(level, pos, state, heldItem);
      }
    }
    return InteractionResult.PASS;
  }

  private InteractionResult tryInsertItem(Level levelAccessor, BlockPos pos, BlockState state, ItemStack heldItem) {
    if (!heldItem.isEmpty() && !InventoryUtil.isFull(inventory)) {
      ItemStack heldCopy = heldItem.copyWithCount(1);
      if (heldCopy.is(Items.GLOWSTONE_DUST) || heldCopy.is(Items.REDSTONE) || heldCopy.is(Items.GUNPOWDER)) {
        int maxCapacity = ComponentRecipe.getModifierCapacity(InventoryUtil.createWrappedInventory(inventory));
        int modifierCount = ComponentRecipe.getModifierCount(InventoryUtil.createWrappedInventory(inventory));
        if (modifierCount < maxCapacity) {
          try (Transaction tx = Transaction.openRoot()) {
            if (inventory.insert(ItemResource.of(heldCopy), 1, tx) != 1) {
              return InteractionResult.FAIL;
            }
            heldItem.shrink(1);
            setChanged();
            levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
            tx.commit();
            return InteractionResult.SUCCESS;
          }
        }
      } else {
        try (Transaction tx = Transaction.openRoot()) {
          if (inventory.insert(ItemResource.of(heldCopy), 1, tx) != 1) {
            return InteractionResult.FAIL;
          }
          heldItem.shrink(1);
          setChanged();
          levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
          tx.commit();
          return InteractionResult.SUCCESS;
        }
      }
    }
    return InteractionResult.PASS;
  }

  private InteractionResult tryDropSingleItem(Level levelAccessor, BlockPos pos, BlockState state) {
    if (!InventoryUtil.isEmpty(inventory)) {

      try (Transaction tx = Transaction.openRoot()) {
        Pair<Integer, ItemResource> lastPair = InventoryUtil.getLastResource(inventory);
        ItemResource lastResource = lastPair.getSecond();
        if (!lastResource.isEmpty()) {
          if (inventory.extract(lastResource, 1, tx) != 1) {
            return InteractionResult.FAIL;
          }
          dropItem(lastResource.toStack(), 0.5F);
          tx.commit();
        }
      }
      setChanged();
      levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
      return InteractionResult.SUCCESS;
    }
    return InteractionResult.PASS;
  }

  private InteractionResult tryActivateRecipe(Player player, BlockState state) {
    if (level.isClientSide()) return InteractionResult.PASS;

    RecipeHolder<ComponentRecipe> recipeHolder =((ServerLevel)level).recipeAccess().recipeMap().getRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get(),
	    InventoryUtil.createWrappedInventory(inventory), level).findFirst().orElse(null);
		if (recipeHolder == null) {
      player.sendOverlayMessage(Component.translatable("rootsclassic.mortar.invalid"));
      return InteractionResult.PASS;
    }
    else if (recipeHolder.value().needsMixin() && ComponentRecipe.getModifierCapacity(InventoryUtil.createWrappedInventory(inventory)) < 0) {
      player.sendOverlayMessage(Component.translatable("rootsclassic.mortar.mixin"));
      return InteractionResult.PASS;
    }
    if (!level.isClientSide()) {
      level.addFreshEntity(new ItemEntity(level,
	      getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5,
	      recipeHolder.value().assemble(InventoryUtil.createWrappedInventory(inventory))));
    }
    InventoryUtil.clearInventory(inventory);
    setChanged();
    level.sendBlockUpdated(getBlockPos(), state, level.getBlockState(worldPosition), 3);
    return InteractionResult.SUCCESS;
  }

  public void dropItem(ItemStack stack, float offsetY) {
    ItemStack copyStack = stack.copy();
    if (copyStack.isEmpty() || level.isClientSide()) {
      return;
    }
    else {
      BlockPos pos = getBlockPos();
      ItemEntity itementity = new ItemEntity(this.level, pos.getX(), pos.getY() + (double) offsetY, this.worldPosition.getZ(), copyStack);
      itementity.setDefaultPickUpDelay();
      this.level.addFreshEntity(itementity);
    }
  }

  private void calculateRotations() {
    for (int i = 0; i < inventory.size(); i++) {
      ItemResource stack = inventory.getResource(i);
      if (!stack.isEmpty()) {}
    }
  }
}
