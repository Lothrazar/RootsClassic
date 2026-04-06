package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RitualMassBreed extends SimpleRitualEffect {

  @Override
  public void doEffect(Level level, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<Animal> animals = level.getEntitiesOfClass(Animal.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22, pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    if (!animals.isEmpty()) {
      for (Animal animal : animals) {
        animal.setInLove(level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false));
        animal.getPersistentData().putInt("InLove", 400);
      }
    }
  }
}
