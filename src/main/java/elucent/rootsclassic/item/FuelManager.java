package elucent.rootsclassic.item;

import elucent.rootsclassic.RegistryManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

@SuppressWarnings("deprecation")
public class FuelManager implements IFuelHandler {

  @Override
  public int getBurnTime(ItemStack fuel) {
    if (fuel.getItem() == RegistryManager.infernalStem) {
      return 2400;
    }
    return 0;
  }
}
