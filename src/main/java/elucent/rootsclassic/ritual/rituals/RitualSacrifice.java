package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualSacrifice extends RitualBase {

  public ArrayList<ItemStack> potentialDrops = new ArrayList<ItemStack>();
  Random random = new Random();

  public RitualSacrifice(String name, double r, double g, double b) {
    super(name, r, g, b);
    potentialDrops.add(new ItemStack(Items.WHEAT_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.WHEAT_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.PUMPKIN_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.PUMPKIN_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.MELON_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.MELON_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.REEDS, 1));
    potentialDrops.add(new ItemStack(Items.REEDS, 1));
    potentialDrops.add(new ItemStack(Blocks.VINE, 1));
    potentialDrops.add(new ItemStack(Blocks.VINE, 1));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 0));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 1));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 2));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 3));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 4));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 5));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 6));
    potentialDrops.add(new ItemStack(Blocks.RED_FLOWER, 1, 7));
    potentialDrops.add(new ItemStack(Blocks.WATERLILY, 1));
  }

  @Override
  public boolean incesceMatches(World world, BlockPos pos) {
    if (super.incesceMatches(world, pos)) {
      if (world.getWorldInfo().isRaining() == false) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    inventory.clear();
    List<EntityLivingBase> enemies = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    if (enemies.size() > 0) {
      for (int i = 0; i < enemies.size(); i++) {
        if (!(enemies.get(i) instanceof EntityPlayer)) {
          enemies.get(0).setHealth(enemies.get(0).getHealth() - 60.0f);
          if (!world.isRemote) {
            EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, potentialDrops.get(random.nextInt(potentialDrops.size())));
            item.forceSpawn = true;
            world.spawnEntity(item);
          }
        }
      }
    }
  }
}
