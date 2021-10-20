package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualMassBreed extends RitualBase {

  public RitualMassBreed(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(Level world, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<Animal> animals = world.getEntitiesOfClass(Animal.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22, pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    if (animals.size() > 0) {
      for (Animal animal : animals) {
        animal.setInLove(world.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false));
        animal.getPersistentData().putInt("InLove", 400);
      }
    }
  }
}
