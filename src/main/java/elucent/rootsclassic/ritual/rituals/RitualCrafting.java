package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualCrafting extends RitualBase {

  public ItemStack result = ItemStack.EMPTY;

  public RitualCrafting(String name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  public RitualCrafting setResult(ItemStack stack) {
    this.result = stack;
    return this;
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    // if (Util.itemListsMatchWithSize(inventory, this.ingredients)) {
    ItemStack toSpawn = result.copy();
    System.out.println("Ritual tospawn " + toSpawn);
    if (!world.isRemote) {
      EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      item.forceSpawn = true;
      world.spawnEntity(item);
    }
    inventory.clear();
    world.getTileEntity(pos).markDirty();
    //}
  }
}
