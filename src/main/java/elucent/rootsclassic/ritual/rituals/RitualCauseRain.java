package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RitualCauseRain extends RitualBase {

  public RitualCauseRain(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
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
  public void doEffect(Level world, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    world.getLevelData().setRaining(true);
  }
}
