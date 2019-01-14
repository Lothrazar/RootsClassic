package elucent.rootsclassic.ritual.rituals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualSummoning extends RitualBase {

  public Class result = null;

  public RitualSummoning setResult(Class entity) {
    this.result = entity;
    return this;
  }

  public RitualSummoning(String name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    if (Util.itemListsMatchWithSize(inventory, this.getIngredients())) {
      EntityCreature toSpawn;
      try {
        toSpawn = (EntityCreature) result.getConstructor(World.class).newInstance(world);
        toSpawn.setWorld(world);
        toSpawn.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        toSpawn.setPosition(pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5);
        inventory.clear();
        if (!world.isRemote) {
          world.spawnEntity(toSpawn);
        }
        world.getTileEntity(pos).markDirty();
      }
      catch (InstantiationException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      catch (SecurityException e) {
        e.printStackTrace();
      }
    }
  }
}
