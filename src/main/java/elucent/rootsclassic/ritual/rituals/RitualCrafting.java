package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualCrafting extends RitualBase {

  public ItemStack result = ItemStack.EMPTY;

  public RitualCrafting(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  public RitualCrafting setResult(ItemStack stack) {
    this.result = stack;
    return this;
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    // if (Util.itemListsMatchWithSize(inventory, this.ingredients)) {
    ItemStack toSpawn = result.copy();
    if (!world.isClientSide) {
      ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      item.forcedLoading = true;
      world.addFreshEntity(item);
    }
    inventory.clearContent();
    world.getBlockEntity(pos).setChanged();
    //}
  }
}
