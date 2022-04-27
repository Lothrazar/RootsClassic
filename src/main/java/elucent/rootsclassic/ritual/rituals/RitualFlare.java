package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RitualFlare extends RitualBase {

  public RitualFlare(int level, double r, double g, double b) {
    super(level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    for (int i = 0; i < inventory.getContainerSize(); i++) {
      if (!inventory.getItem(i).isEmpty()) {
        ItemStack stack = inventory.getItem(i);
        if (stack.getItem() == Items.FLINT_AND_STEEL && stack.getDamageValue() < stack.getDamageValue() - 1) {
          if (!world.isClientSide) {
            ItemStack flintStack = new ItemStack(Items.FLINT_AND_STEEL, 1);
            flintStack.setDamageValue(stack.getDamageValue() + 1);
            ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, flintStack);
            item.forcedLoading = true;
            world.addFreshEntity(item);
          }
        }
      }
    }
    inventory.clearContent();
    List<LivingEntity> enemies = world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    if (enemies.size() > 0) {
      for (LivingEntity enemy : enemies) {
        enemy.setSecondsOnFire(world.random.nextInt(5) + 14);
      }
    }
  }
}
