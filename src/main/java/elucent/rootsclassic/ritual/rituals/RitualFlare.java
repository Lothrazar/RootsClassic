package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualFlare extends RitualBase {

  Random random = new Random();

  public RitualFlare(String name, double r, double g, double b) {
    super(name, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) != null) {
        if (inventory.get(i).getItem() == Items.FLINT_AND_STEEL && inventory.get(i).getItemDamage() < inventory.get(i).getMaxDamage() - 1) {
          if (!world.isRemote) {
            EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, new ItemStack(Items.FLINT_AND_STEEL, 1, inventory.get(i).getItemDamage() + 1));
            item.forceSpawn = true;
            world.spawnEntity(item);
          }
        }
      }
    }
    inventory.clear();
    List<EntityLivingBase> enemies = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22, pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    if (enemies.size() > 0) {
      for (int i = 0; i < enemies.size(); i++) {
        enemies.get(i).setFire(random.nextInt(5) + 14);
      }
    }
  }
}
