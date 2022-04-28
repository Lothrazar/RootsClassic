package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RitualBanishRain extends RitualBase {

  public RitualBanishRain(int level, double r, double g, double b) {
    super(level, r, g, b);
  }
  //	@Override
  //	public boolean incenseMatches(Level level, BlockPos pos) {
  //		if (super.incenseMatches(world, pos)) {
  //			if (level.getWorldInfo().isRaining() == false) {
  //				return true;
  //			}
  //		}
  //		return false;
  //	}

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    levelAccessor.getLevelData().setRaining(false);
  }
}
