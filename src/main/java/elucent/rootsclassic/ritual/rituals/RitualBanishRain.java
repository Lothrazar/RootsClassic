package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RitualBanishRain extends SimpleRitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    levelAccessor.getLevelData().setRaining(false);
  }
}
