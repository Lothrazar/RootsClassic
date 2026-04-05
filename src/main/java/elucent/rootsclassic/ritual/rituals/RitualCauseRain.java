package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RitualCauseRain extends SimpleRitualEffect {

  @Override
  public void doEffect(Level level, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    if (level instanceof ServerLevel serverLevel)
      serverLevel.getWeatherData().setRaining(true);
  }
}
