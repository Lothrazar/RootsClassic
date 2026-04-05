package elucent.rootsclassic.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class RecipeWrapper implements RecipeInput {
  protected final ResourceHandler<ItemResource> resourceHandler;

  public RecipeWrapper(ResourceHandler<ItemResource> resourceHandler) {
    this.resourceHandler = resourceHandler;
  }

  @Override
  public ItemStack getItem(int index) {
    return resourceHandler.getResource(index).toStack(resourceHandler.getAmountAsInt(index));
  }

  @Override
  public int size() {
    return resourceHandler.size();
  }
}
