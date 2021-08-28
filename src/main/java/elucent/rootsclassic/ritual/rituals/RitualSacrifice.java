package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualSacrifice extends RitualBase {

  public ArrayList<ItemStack> potentialDrops = new ArrayList<>();

  public RitualSacrifice(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
    potentialDrops.add(new ItemStack(Items.WHEAT_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.WHEAT_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.PUMPKIN_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.PUMPKIN_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.MELON_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.MELON_SEEDS, 1));
    potentialDrops.add(new ItemStack(Items.SUGAR_CANE, 1));
    potentialDrops.add(new ItemStack(Items.SUGAR_CANE, 1));
    potentialDrops.add(new ItemStack(Blocks.VINE, 1));
    potentialDrops.add(new ItemStack(Blocks.VINE, 1));
    potentialDrops.add(new ItemStack(Blocks.POPPY, 1));
    potentialDrops.add(new ItemStack(Blocks.BLUE_ORCHID, 1));
    potentialDrops.add(new ItemStack(Blocks.ALLIUM, 1));
    potentialDrops.add(new ItemStack(Blocks.AZURE_BLUET, 1));
    potentialDrops.add(new ItemStack(Blocks.RED_TULIP, 1));
    potentialDrops.add(new ItemStack(Blocks.ORANGE_TULIP, 1));
    potentialDrops.add(new ItemStack(Blocks.WHITE_TULIP, 1));
    potentialDrops.add(new ItemStack(Blocks.PINK_TULIP, 1));
    potentialDrops.add(new ItemStack(Blocks.LILY_PAD, 1));
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    inventory.clear();
    List<LivingEntity> enemies = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    if (enemies.size() > 0) {
      for (LivingEntity enemy : enemies) {
        if (!(enemy instanceof PlayerEntity)) {
          enemies.get(0).setHealth(enemies.get(0).getHealth() - 60.0f);
          if (!world.isRemote) {
            ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, potentialDrops.get(world.rand.nextInt(potentialDrops.size())));
            item.forceSpawn = true;
            world.addEntity(item);
          }
        }
      }
    }
  }
}
