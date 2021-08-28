package elucent.rootsclassic.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CustomInventory implements IInventory {

  private final NonNullList<ItemStack> itemStacks;

  public CustomInventory(int size) {
    this.itemStacks = NonNullList.withSize(size, ItemStack.EMPTY);
  }

  @Override
  public int getSizeInventory() {
    return itemStacks.size();
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack itemstack : this.itemStacks) {
      if (!itemstack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ItemStack getStackInSlot(int index) {
    return this.itemStacks.get(index);
  }

  @Override
  public ItemStack decrStackSize(int index, int count) {
    return ItemStackHelper.getAndRemove(this.itemStacks, index);
  }

  @Override
  public ItemStack removeStackFromSlot(int index) {
    return ItemStackHelper.getAndRemove(this.itemStacks, index);
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {
    this.itemStacks.set(index, stack);
  }

  @Override
  public void markDirty() {}

  @Override
  public boolean isUsableByPlayer(PlayerEntity player) {
    return true;
  }

  @Override
  public void clear() {
    this.itemStacks.clear();
  }
}
