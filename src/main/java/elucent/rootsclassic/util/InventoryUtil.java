package elucent.rootsclassic.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtil {

  public static int getFirstEmptyStack(IItemHandler itemHandler) {
    if (itemHandler == null) return -1;
    for (int i = 0; i < itemHandler.getSlots(); i++) {
      if (itemHandler.getStackInSlot(i).isEmpty()) {
        return i;
      }
    }
    return -1;
  }

  public static boolean isFull(IItemHandler itemHandler) {
    if (itemHandler == null) return true;
    for (int i = 0; i < itemHandler.getSlots(); i++) {
      ItemStack stack = itemHandler.getStackInSlot(i);
      if (stack.isEmpty() || (stack.getCount() < stack.getMaxStackSize())) {
        return false;
      }
    }
    return true;
  }

  public static boolean isEmpty(IItemHandler itemHandler) {
    if (itemHandler == null) return true;
    for (int i = 0; i < itemHandler.getSlots(); i++) {
      ItemStack stack = itemHandler.getStackInSlot(i);
      if (!stack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public static void clearInventory(IItemHandler itemHandler) {
    if (itemHandler == null) return;
    for (int i = 0; i < itemHandler.getSlots(); i++) {
      ItemStack stack = itemHandler.getStackInSlot(i);
      if (!stack.isEmpty()) {
        stack.shrink(1);
      }
    }
  }

  public static ItemStack getLastStack(IItemHandler itemHandler) {
    if (itemHandler == null) return ItemStack.EMPTY;
    for (int i = itemHandler.getSlots() - 1; i >= 0; i--) {
      ItemStack stack = itemHandler.getStackInSlot(i);
      if (!stack.isEmpty()) {
        return stack;
      }
    }
    return ItemStack.EMPTY;
  }

  public static CustomInventory createIInventory(IItemHandler itemHandler) {
    if (itemHandler == null) return null;
    CustomInventory inventory = new CustomInventory(itemHandler.getSlots());
    for (int i = 0; i < itemHandler.getSlots(); i++) {
      inventory.setItem(i, itemHandler.getStackInSlot(i));
    }
    return inventory;
  }
}
