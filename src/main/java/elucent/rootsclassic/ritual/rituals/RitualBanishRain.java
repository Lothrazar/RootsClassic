package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RitualBanishRain extends RitualBase {

  public RitualBanishRain(int level, double r, double g, double b) {
    super(level, r, g, b);
  }
  //	@Override
  //	public boolean incenseMatches(World world, BlockPos pos) {
  //		if (super.incenseMatches(world, pos)) {
  //			if (world.getWorldInfo().isRaining() == false) {
  //				return true;
  //			}
  //		}
  //		return false;
  //	}

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    world.getLevelData().setRaining(false);
  }
}
