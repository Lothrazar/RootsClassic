package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualBanishRain extends RitualBase {

  public RitualBanishRain(String name, double r, double g, double b) {
    super(name, r, g, b);
  }

  @Override
  public boolean matches(World world, BlockPos pos) {
    if (super.matches(world, pos)) {
      if (world.getWorldInfo().isRaining() == true) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    inventory.clear();
    world.getWorldInfo().setRaining(false);
    world.getWorldInfo().setCleanWeatherTime(3600);
  }
}
