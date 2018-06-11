package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualTimeShift extends RitualBase {

  public RitualTimeShift(String parName, double r, double g, double b, double r2, double g2, double b2) {
    super(parName, r, g, b, r2, g2, b2);
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    long shiftAmount = 0;
    List<Item> items = new ArrayList<Item>();
    for (ItemStack i : incenses) {
      items.add(i.getItem());
    }
    for (Item i : items) {
      if (i == Items.CLOCK) {
        shiftAmount += 1000;
      }
    }
    inventory.clear();
    world.setWorldTime(world.getWorldTime() + shiftAmount);
  }
}
