package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class RitualSacrifice extends SimpleRitualEffect {

  public final List<ItemStackTemplate> potentialDrops = new ArrayList<>();

  public RitualSacrifice() {
    potentialDrops.add(new ItemStackTemplate(Items.WHEAT_SEEDS, 1));
    potentialDrops.add(new ItemStackTemplate(Items.WHEAT_SEEDS, 1));
    potentialDrops.add(new ItemStackTemplate(Items.PUMPKIN_SEEDS, 1));
    potentialDrops.add(new ItemStackTemplate(Items.PUMPKIN_SEEDS, 1));
    potentialDrops.add(new ItemStackTemplate(Items.MELON_SEEDS, 1));
    potentialDrops.add(new ItemStackTemplate(Items.MELON_SEEDS, 1));
    potentialDrops.add(new ItemStackTemplate(Items.SUGAR_CANE, 1));
    potentialDrops.add(new ItemStackTemplate(Items.SUGAR_CANE, 1));
    potentialDrops.add(new ItemStackTemplate(Items.VINE, 1));
    potentialDrops.add(new ItemStackTemplate(Items.VINE, 1));
    potentialDrops.add(new ItemStackTemplate(Items.POPPY, 1));
    potentialDrops.add(new ItemStackTemplate(Items.BLUE_ORCHID, 1));
    potentialDrops.add(new ItemStackTemplate(Items.ALLIUM, 1));
    potentialDrops.add(new ItemStackTemplate(Items.AZURE_BLUET, 1));
    potentialDrops.add(new ItemStackTemplate(Items.RED_TULIP, 1));
    potentialDrops.add(new ItemStackTemplate(Items.ORANGE_TULIP, 1));
    potentialDrops.add(new ItemStackTemplate(Items.WHITE_TULIP, 1));
    potentialDrops.add(new ItemStackTemplate(Items.PINK_TULIP, 1));
    potentialDrops.add(new ItemStackTemplate(Items.LILY_PAD, 1));
  }

  @Override
  public void doEffect(Level level, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<LivingEntity> enemies = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    if (!enemies.isEmpty()) {
      for (LivingEntity enemy : enemies) {
        if (!(enemy instanceof Player)) {
          enemies.getFirst().setHealth(enemies.getFirst().getHealth() - 60.0f);
          if (!level.isClientSide()) {
            ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, potentialDrops.get(level.getRandom().nextInt(potentialDrops.size())).create());
            level.addFreshEntity(item);
          }
        }
      }
    }
  }
}
