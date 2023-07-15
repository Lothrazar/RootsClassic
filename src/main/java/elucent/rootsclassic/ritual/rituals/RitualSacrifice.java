package elucent.rootsclassic.ritual.rituals;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class RitualSacrifice extends SimpleRitualEffect {

  public List<ItemStack> potentialDrops = new ArrayList<>();

  public RitualSacrifice() {
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
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<LivingEntity> enemies = levelAccessor.getEntitiesOfClass(LivingEntity.class, new AABB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    if (enemies.size() > 0) {
      for (LivingEntity enemy : enemies) {
        if (!(enemy instanceof Player)) {
          enemies.get(0).setHealth(enemies.get(0).getHealth() - 60.0f);
          if (!levelAccessor.isClientSide) {
            ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, potentialDrops.get(levelAccessor.random.nextInt(potentialDrops.size())));
            levelAccessor.addFreshEntity(item);
          }
        }
      }
    }
  }
}
