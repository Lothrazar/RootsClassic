package elucent.rootsclassic.item;

import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;

public class PestleItem extends Item {

  public PestleItem(Properties properties) {
    super(properties.craftRemainder(new ItemStackTemplate(RootsRegistry.PESTLE, 1)));
  }
}
