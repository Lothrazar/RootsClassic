package elucent.rootsclassic.compat.jei.subtype;

import elucent.rootsclassic.registry.RootsComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class ComponentSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
  public static final ComponentSubtypeInterpreter INSTANCE = new ComponentSubtypeInterpreter();

  private ComponentSubtypeInterpreter() {

  }

  @Override
  public Object getSubtypeData(ItemStack ingredient, UidContext context) {
    return ingredient.get(RootsComponents.SPELL.get());
  }
}
