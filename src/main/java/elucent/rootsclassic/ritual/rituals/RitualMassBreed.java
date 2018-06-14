package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualMassBreed extends RitualBase {

  public RitualMassBreed(String name, double r, double g, double b) {
    super(name, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    inventory.clear();
    List<EntityAnimal> animals = (List<EntityAnimal>) world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22, pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    if (animals.size() > 0) {
      for (int i = 0; i < animals.size(); i++) {
        animals.get(i).setInLove(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false));
        animals.get(i).getEntityData().setInteger("InLove", 400);
      }
    }
  }
}
