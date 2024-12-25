package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;

import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class RitualTimeShift extends SimpleRitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
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
    if (!levelAccessor.isClientSide && levelAccessor.getServer() != null) {
      for (ServerLevel serverLevel : levelAccessor.getServer().getAllLevels()) {
        serverLevel.setDayTime(serverLevel.getDayTime() + (long) shiftAmount);
      }
    }
  }
  
  @Override
  public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe<Void> recipe) {
    List<ItemStack> incensesWithoutClocks = new ArrayList<>(incensesFromNearby);
    incensesWithoutClocks.removeIf(stack -> stack.is(Items.CLOCK));
    
    if(incensesFromNearby.size() == incensesWithoutClocks.size()) {
      //No clocks.
      return false;
    } else {
      //The JSON recipe contains exactly one clock incense, so add back one clock.
      incensesWithoutClocks.add(new ItemStack(Items.CLOCK));
      return RootsUtil.matchesIngredients(incensesWithoutClocks, recipe.getIncenses());
    }
  }
}
