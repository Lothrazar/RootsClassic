package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.util.RootsUtil;

public class RitualEngravedSword extends RitualBase {

  public ItemStack result = null;

  public RitualBase setResult(ItemStack stack) {
    this.result = stack;
    return this;
  }

  public RitualEngravedSword(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    List<Item> items = new ArrayList<>();
    for (ItemStack i : incenses) {
      items.add(i.getItem());
    }
    if (RootsUtil.itemListMatchInventoryWithSize(inventory, this.getIngredients())) {
      ItemStack toSpawn = result.copy();
      if (!world.isClientSide) {
        int mods = 0;
        ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
        item.forcedLoading = true;
        ItemStack stack = item.getItem();
        CompoundNBT tag = stack.getTag() != null ? stack.getTag() : new CompoundNBT();
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
        world.addFreshEntity(item);
      }
      inventory.clearContent();
      TileEntity tile = world.getBlockEntity(pos);
      if (tile != null) {
        tile.setChanged();
      }
    }
  }

  public void addMod(CompoundNBT tag, String name) {
    if (tag.contains(name)) {
      tag.putInt(name, tag.getInt(name) + 1);
    }
    else {
      tag.putInt(name, 1);
    }
  }
}
