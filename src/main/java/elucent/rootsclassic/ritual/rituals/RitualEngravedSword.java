package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;

import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RitualEngravedSword extends RitualCrafting {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, RitualCraftingConfig config) {
    List<Item> items = new ArrayList<>();
    for (ItemStack i : incenses) {
      items.add(i.getItem());
    }
    ItemStack toSpawn = config.result().copy();
    if (!levelAccessor.isClientSide) {
      int mods = 0;
      ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      ItemStack stack = item.getItem();
      CompoundTag tag = stack.getTag() != null ? stack.getTag() : new CompoundTag();
      for (Item i : items) {
        if (i == (RootsRegistry.ACACIA_BARK.get()) && mods < 4) {
          addMod(tag, "spikes");
          mods++;
        }
        if (i == RootsRegistry.SPRUCE_BARK.get() && mods < 4) {
          addMod(tag, "forceful");
          mods++;
        }
        if (i == RootsRegistry.BIRCH_BARK.get() && mods < 4) {
          addMod(tag, "holy");
          mods++;
        }
        if (i == RootsRegistry.JUNGLE_BARK.get() && mods < 4) {
          addMod(tag, "aquatic");
          mods++;
        }
        if (i == RootsRegistry.DARK_OAK_BARK.get() && mods < 4) {
          addMod(tag, "shadowstep");
          mods++;
        }
      }
      stack.setTag(tag);
      levelAccessor.addFreshEntity(item);
    }
    inventory.clearContent();
    BlockEntity tile = levelAccessor.getBlockEntity(pos);
    if (tile != null) {
      tile.setChanged();
    }
  }

  public void addMod(CompoundTag tag, String name) {
    if (tag.contains(name)) {
      tag.putInt(name, tag.getInt(name) + 1);
    }
    else {
      tag.putInt(name, 1);
    }
  }
  
  @Override
  public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe<RitualCraftingConfig> recipe) {
    List<ItemStack> incensesWithoutBarks = new ArrayList<>(incensesFromNearby);
    incensesWithoutBarks.removeIf(stack -> stack.is(RootsTags.BARKS));
    
    if(incensesFromNearby.size() - incensesWithoutBarks.size() > 4) {
      return false; //Too much bark.
    } else {
      return RootsUtil.matchesIngredients(incensesWithoutBarks, recipe.getIncenses());
    }
  }
}
