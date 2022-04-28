package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RitualMassBreed extends RitualBase {

  public RitualMassBreed(int level, double r, double g, double b) {
    super(level, r, g, b);
  }

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<Animal> animals = levelAccessor.getEntitiesOfClass(Animal.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22, pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    if (animals.size() > 0) {
      for (Animal animal : animals) {
        animal.setInLove(levelAccessor.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false));
        animal.getPersistentData().putInt("InLove", 400);
      }
    }
  }
}
