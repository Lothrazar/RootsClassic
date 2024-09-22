package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RitualEngravedSword extends RitualCrafting {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
    List<Item> items = new ArrayList<>();
    for (ItemStack i : incenses) {
      items.add(i.getItem());
    }
	  ItemStack toSpawn = ItemStack.parseOptional(levelAccessor.registryAccess(), config.getCompound("result"));
    if (!levelAccessor.isClientSide) {
      int mods = 0;
      ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      ItemStack stack = item.getItem();
      for (Item i : items) {
        if (i == (RootsRegistry.ACACIA_BARK.get()) && mods < 4) {
          addMod(stack, RootsComponents.SPIKES);
          mods++;
        }
        if (i == RootsRegistry.SPRUCE_BARK.get() && mods < 4) {
          addMod(stack, RootsComponents.FORCEFUL);
          mods++;
        }
        if (i == RootsRegistry.BIRCH_BARK.get() && mods < 4) {
          addMod(stack, RootsComponents.HOLY);
          mods++;
        }
        if (i == RootsRegistry.JUNGLE_BARK.get() && mods < 4) {
          addMod(stack, RootsComponents.AQUATIC);
          mods++;
        }
        if (i == RootsRegistry.DARK_OAK_BARK.get() && mods < 4) {
          addMod(stack, RootsComponents.SHADOWSTEP);
          mods++;
        }
      }
      levelAccessor.addFreshEntity(item);
    }
    inventory.clearContent();
    BlockEntity tile = levelAccessor.getBlockEntity(pos);
    if (tile != null) {
      tile.setChanged();
    }
  }

  public void addMod(ItemStack stack, Supplier<DataComponentType<Integer>> componentTypeSupplier) {
    if (stack.has(componentTypeSupplier)) {
      stack.set(componentTypeSupplier, stack.getOrDefault(componentTypeSupplier, 0) + 1);
    }
    else {
	    stack.set(componentTypeSupplier, 1);
    }
  }
}
