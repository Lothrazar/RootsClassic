package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualBanishRain extends RitualBase {

  public RitualBanishRain(ResourceLocation name, int level, double r, double g, double b) {
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
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    world.getLevelData().setRaining(false);
  }
}
