
package elucent.roots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelManager implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() == RegistryManager.infernalStem){
			return 2400;
		}
		return 0;
	}

}
