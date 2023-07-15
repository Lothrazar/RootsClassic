package elucent.rootsclassic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import elucent.rootsclassic.Const;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class RootsUtil {

  private static final Random random = new Random();

  public static double randomDouble(double min, double max) {
    double range = max - min;
    double scale = random.nextDouble() * range;
    return scale + min;
  }

  public static BlockPos getRayTrace(Level levelAccessor, LivingEntity livingEntity, int reachDistance) {
    double x = livingEntity.getX();
    double y = livingEntity.getY() + livingEntity.getEyeHeight();
    double z = livingEntity.getZ();
    for (int i = 0; i < reachDistance * 10.0; i++) {
      x += livingEntity.getLookAngle().x * 0.1;
      y += livingEntity.getLookAngle().y * 0.1;
      z += livingEntity.getLookAngle().z * 0.1;
      if (levelAccessor.getBlockState(BlockPos.containing(x, y, z)).getBlock() != Blocks.AIR) {
        return BlockPos.containing(x, y, z);
      }
    }
    return BlockPos.containing(x, y, z);
  }

  public static void addTickTracking(Entity entity) {
    if (entity.getPersistentData().contains(Const.NBT_TRACK_TICKS)) {
      entity.getPersistentData().putInt(Const.NBT_TRACK_TICKS, entity.getPersistentData().getInt(Const.NBT_TRACK_TICKS) + 1);
    }
    else {
      entity.getPersistentData().putInt(Const.NBT_TRACK_TICKS, 1);
    }
  }

  public static void decrementTickTracking(Entity entity) {
    if (entity.getPersistentData().contains(Const.NBT_TRACK_TICKS)) {
      entity.getPersistentData().putInt(Const.NBT_TRACK_TICKS, entity.getPersistentData().getInt(Const.NBT_TRACK_TICKS) - 1);
      if (entity.getPersistentData().getInt(Const.NBT_TRACK_TICKS) == 0) {
        entity.removeTag(Const.NBT_TRACK_TICKS);
      }
    }
  }

  public static Entity getRayTraceEntity(Level levelAccessor, LivingEntity livingEntity, int reachDistance) {
    double x = livingEntity.getX();
    double y = livingEntity.getY() + livingEntity.getEyeHeight();
    double z = livingEntity.getZ();
    for (int i = 0; i < reachDistance * 10.0; i++) {
      x += livingEntity.getLookAngle().x * 0.1;
      y += livingEntity.getLookAngle().y * 0.1;
      z += livingEntity.getLookAngle().z * 0.1;
      List<Entity> entities = levelAccessor.getEntitiesOfClass(Entity.class, new AABB(x - 0.1, y - 0.1, z - 0.1, x + 0.1, y + 0.1, z + 0.1));
      if (entities.size() > 0) {
        if (entities.get(0).getUUID() != livingEntity.getUUID()) {
          return entities.get(0);
        }
      }
    }
    return null;
  }

  public static boolean matchesIngredients(List<ItemStack> inv, List<Ingredient> ingredients) {
    if (inv.size() != ingredients.size()) return false;
    var available = new ArrayList<>(inv);
    return ingredients.stream().allMatch(ingredient -> {
      var match = available.stream().filter(ingredient).findFirst();
      match.ifPresent(available::remove);
      return match.isPresent();
    });
  }
}
