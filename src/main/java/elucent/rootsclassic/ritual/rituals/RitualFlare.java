package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RitualFlare extends SimpleRitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    for (int i = 0; i < inventory.getContainerSize(); i++) {
      if (!inventory.getItem(i).isEmpty()) {
        ItemStack stack = inventory.getItem(i);
        if (stack.getItem() == Items.FLINT_AND_STEEL && stack.getDamageValue() < stack.getDamageValue() - 1) {
          if (!levelAccessor.isClientSide) {
            ItemStack flintStack = new ItemStack(Items.FLINT_AND_STEEL, 1);
            flintStack.setDamageValue(stack.getDamageValue() + 1);
            ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, flintStack);
            levelAccessor.addFreshEntity(item);
          }
        }
      }
    }
    inventory.clearContent();
    List<LivingEntity> enemies = levelAccessor.getEntitiesOfClass(LivingEntity.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    if (enemies.size() > 0) {
      for (LivingEntity enemy : enemies) {
        enemy.setSecondsOnFire(levelAccessor.random.nextInt(5) + 14);
      }
    }
  }
}
