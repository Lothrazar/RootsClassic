package elucent.rootsclassic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Util {

  public static final String NBT_TRACK_TICKS = "RMOD_trackTicks";
  public static Random random = new Random();
  public static ArrayList<Item> berries = new ArrayList<Item>();

  public static double randomDouble(double min, double max) {
    double range = max - min;
    double scale = random.nextDouble() * range;
    double shifted = scale + min;
    return shifted;
  }

  public static BlockPos getRayTrace(World world, EntityPlayer player, int reachDistance) {
    double x = player.posX;
    double y = player.posY + player.getEyeHeight();
    double z = player.posZ;
    for (int i = 0; i < reachDistance * 10.0; i++) {
      x += player.getLookVec().x * 0.1;
      y += player.getLookVec().y * 0.1;
      z += player.getLookVec().z * 0.1;
      if (world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR) {
        return new BlockPos(x, y, z);
      }
    }
    return new BlockPos(x, y, z);
  }

  public static void addTickTracking(Entity entity) {
    if (entity.getEntityData().hasKey(NBT_TRACK_TICKS)) {
      entity.getEntityData().setInteger(NBT_TRACK_TICKS, entity.getEntityData().getInteger(NBT_TRACK_TICKS) + 1);
    }
    else {
      entity.getEntityData().setInteger(NBT_TRACK_TICKS, 1);
    }
  }

  public static void decrementTickTracking(Entity entity) {
    if (entity.getEntityData().hasKey(NBT_TRACK_TICKS)) {
      entity.getEntityData().setInteger(NBT_TRACK_TICKS, entity.getEntityData().getInteger(NBT_TRACK_TICKS) - 1);
      if (entity.getEntityData().getInteger(NBT_TRACK_TICKS) == 0) {
        entity.removeTag(NBT_TRACK_TICKS);
      }
    }
  }

  public static Entity getRayTraceEntity(World world, EntityPlayer player, int reachDistance) {
    double x = player.posX;
    double y = player.posY + player.getEyeHeight();
    double z = player.posZ;
    for (int i = 0; i < reachDistance * 10.0; i++) {
      x += player.getLookVec().x * 0.1;
      y += player.getLookVec().y * 0.1;
      z += player.getLookVec().z * 0.1;
      List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - 0.1, y - 0.1, z - 0.1, x + 0.1, y + 0.1, z + 0.1));
      if (entities.size() > 0) {
        if (entities.get(0).getUniqueID() != player.getUniqueID()) {
          return entities.get(0);
        }
      }
    }
    return null;
  }

  public static void initBerries() {
    berries.add(RegistryManager.nightshade);
    berries.add(RegistryManager.blackCurrant);
    berries.add(RegistryManager.redCurrant);
    berries.add(RegistryManager.whiteCurrant);
    berries.add(RegistryManager.elderBerry);
  }

  public static boolean oreDictMatches(ItemStack stack1, ItemStack stack2) {

    if (OreDictionary.itemMatches(stack1, stack2, true)) {
      return true;
    }
    else {
      int[] oreIds = OreDictionary.getOreIDs(stack1);
      for (int i = 0; i < oreIds.length; i++) {
        if (OreDictionary.containsMatch(true, OreDictionary.getOres(OreDictionary.getOreName(oreIds[i])), stack2)) {
          return true;
        }
      }
    }
    return false;
  }

  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }

  public static boolean itemListsMatchWithSize(List<ItemStack> i1, List<ItemStack> i2) {
    ArrayList<ItemStack> recipe = new ArrayList<ItemStack>();
    ArrayList<ItemStack> available = new ArrayList<ItemStack>();
    recipe.addAll(i1);
    available.addAll(i2);
    for (int i = 0; i < recipe.size(); i++) {
      if (recipe.get(i) == null) {
        recipe.remove(i);
        i--;
      }
    }
    for (int i = 0; i < available.size(); i++) {
      if (available.get(i) == null) {
        available.remove(i);
        i--;
      }
    }
    if (available.size() == recipe.size()) {
      for (int j = 0; j < available.size(); j++) {
        boolean endIteration = false;
        for (int i = 0; i < recipe.size() && !endIteration; i++) {
          if (oreDictMatches(available.get(j), recipe.get(i))) {
            recipe.remove(i);
            endIteration = true;
          }
        }
      }
    }
    return recipe.size() == 0;
  }

  public static boolean itemListsMatch(List<ItemStack> i1, List<ItemStack> i2) {
    ArrayList<ItemStack> recipe = new ArrayList<ItemStack>();
    ArrayList<ItemStack> available = new ArrayList<ItemStack>();
    recipe.addAll(i1);
    available.addAll(i2);
    for (int i = 0; i < recipe.size(); i++) {
      if (recipe.get(i) == null) {
        recipe.remove(i);
        i--;
      }
    }
    for (int i = 0; i < available.size(); i++) {
      if (available.get(i) == null) {
        available.remove(i);
        i--;
      }
    }
    if (available.size() >= recipe.size()) {
      for (int j = 0; j < available.size(); j++) {
        boolean endIteration = false;
        for (int i = 0; i < recipe.size() && !endIteration; i++) {
          if (oreDictMatches(available.get(j), recipe.get(i))) {
            recipe.remove(i);
            endIteration = true;
          }
        }
      }
    }
    return recipe.size() == 0;
  }

  public static void randomlyRepair(Random rnd, ItemStack stack) {
    if (stack.isItemDamaged() && rnd.nextInt(80) == 0) {
      stack.setItemDamage(stack.getItemDamage() - 1);
    }
  }
}
