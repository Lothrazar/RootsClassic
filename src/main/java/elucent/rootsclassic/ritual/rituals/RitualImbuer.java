package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class RitualImbuer extends SimpleRitualEffect {

  @Override
  public void doEffect(Level level, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    ItemStack toSpawn = new ItemStack(RootsRegistry.CRYSTAL_STAFF.get(), 1);
    for (int i = 0; i < incenses.size() && i < 4; i++) {
			ItemStack incense = incenses.get(i);
      if (incense != null && !incense.isEmpty()) {
        if (incense.getItem() == RootsRegistry.SPELL_POWDER.get() && incense.has(RootsComponents.SPELL)) {
	        SpellData spellData = incense.get(RootsComponents.SPELL);
	        Identifier effect = Identifier.tryParse(spellData.effect());
          CrystalStaffItem.addEffect(toSpawn, i + 1, effect.toString(), spellData.potency(), spellData.efficiency(), spellData.size());
        }
      }
    }
    if (!level.isClientSide()) {
      ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      level.addFreshEntity(item);
    }
    inventory.clearContent();
    level.getBlockEntity(pos).setChanged();
  }
  
  @Override
  public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe recipe) {
    List<ItemStack> incensesWithoutPowders = new ArrayList<>(incensesFromNearby);
    incensesWithoutPowders.removeIf(stack -> stack.is(RootsRegistry.SPELL_POWDER.get()));
    
    if(incensesWithoutPowders.size() == incensesFromNearby.size()) {
      return false; //Need to add at least one spell powder.
    } else {
      return RootsUtil.matchesIngredients(incensesWithoutPowders, recipe.getIncenses());
    }
  }
}
