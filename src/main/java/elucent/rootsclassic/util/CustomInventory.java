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
  public int getContainerSize() {
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
  public ItemStack getItem(int index) {
    return this.itemStacks.get(index);
  }

  @Override
  public ItemStack removeItem(int index, int count) {
    return ItemStackHelper.takeItem(this.itemStacks, index);
  }

  @Override
  public ItemStack removeItemNoUpdate(int index) {
    return ItemStackHelper.takeItem(this.itemStacks, index);
  }

  @Override
  public void setItem(int index, ItemStack stack) {
    this.itemStacks.set(index, stack);
  }

  @Override
  public void setChanged() {}

  @Override
  public boolean stillValid(PlayerEntity player) {
    return true;
  }

  @Override
  public void clearContent() {
    this.itemStacks.clear();
  }
}
