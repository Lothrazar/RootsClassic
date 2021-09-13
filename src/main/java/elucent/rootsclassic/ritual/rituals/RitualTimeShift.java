package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualTimeShift extends RitualBase {

  public RitualTimeShift(ResourceLocation parName, int level, double r, double g, double b) {
    super(parName, level, r, g, b);
  }

  @Override
  public void doEffect(Level world, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    long shiftAmount = 0;
    List<Item> items = new ArrayList<>();
    for (ItemStack i : incenses) {
      items.add(i.getItem());
    }
    for (Item i : items) {
      if (i == Items.CLOCK) {
        shiftAmount += 1000;
      }
    }
    inventory.clearContent();
    if (!world.isClientSide && world.getServer() != null) {
      for (ServerLevel serverworld : world.getServer().getAllLevels()) {
        serverworld.setDayTime(serverworld.getDayTime() + shiftAmount);
      }
    }
  }
}
