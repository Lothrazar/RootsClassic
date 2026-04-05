package elucent.rootsclassic.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class InventoryUtil {

  public static int getFirstEmptyStack(ResourceHandler<ItemResource> itemHandler) {
    if (itemHandler == null) return -1;
    for (int i = 0; i < itemHandler.size(); i++) {
      if (itemHandler.getResource(i).isEmpty()) {
        return i;
      }
    }
    return -1;
  }

  public static boolean isFull(ResourceHandler<ItemResource> itemHandler) {
    if (itemHandler == null) return true;
    for (int i = 0; i < itemHandler.size(); i++) {
      ItemResource resource = itemHandler.getResource(i);
      if (resource.isEmpty() || (itemHandler.getAmountAsInt(i) < resource.getMaxStackSize())) {
        return false;
      }
    }
    return true;
  }

  public static boolean isEmpty(ResourceHandler<ItemResource> itemHandler) {
    if (itemHandler == null) return true;
    for (int i = 0; i < itemHandler.size(); i++) {
      ItemResource resource = itemHandler.getResource(i);
      if (!resource.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public static void clearInventory(ResourceHandler<ItemResource> itemHandler) {
    if (itemHandler == null) return;
    try (Transaction tx = Transaction.openRoot()) {
      for (int i = 0; i < itemHandler.size(); i++) {
        ItemResource resource = itemHandler.getResource(i);
        if (!resource.isEmpty()) {
          itemHandler.extract(resource, itemHandler.getAmountAsInt(i), tx);
        }
      }
      tx.commit();
    }
  }

  public static Pair<Integer, ItemResource> getLastResource(ResourceHandler<ItemResource> resourceHandler) {
    if (resourceHandler == null) return Pair.of(-1, ItemResource.EMPTY);
    for (int i = resourceHandler.size() - 1; i >= 0; i--) {
      ItemResource resource = resourceHandler.getResource(i);
      if (!resource.isEmpty()) {
        return Pair.of(i, resource);
      }
    }
    return Pair.of(-1, ItemResource.EMPTY);
  }

  public static CustomInventory createIInventory(ResourceHandler<ItemResource> itemHandler) {
    if (itemHandler == null) return null;
    CustomInventory inventory = new CustomInventory(itemHandler.size());
    for (int i = 0; i < itemHandler.size(); i++) {
      inventory.setItem(i, itemHandler.getResource(i).toStack(itemHandler.getAmountAsInt(i)));
    }
    return inventory;
  }

	public static RecipeWrapper createWrappedInventory(ResourceHandler<ItemResource> itemHandler) {
		if (itemHandler == null) return null;
		return new RecipeWrapper(itemHandler);
	}
}
