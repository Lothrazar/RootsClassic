package elucent.rootsclassic.item;

import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class MutatingPowderItem extends Item {

  public MutatingPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (level.isClientSide()) {
      for (int i = 0; i < 40; i++) {
        double velX = (player.getLookAngle().x * 0.75) + 0.5 * (level.getRandom().nextDouble() - 0.5);
        double velY = (player.getLookAngle().y * 0.75) + 0.5 * (level.getRandom().nextDouble() - 0.5);
        double velZ = (player.getLookAngle().z * 0.75) + 0.5 * (level.getRandom().nextDouble() - 0.5);
        level.addParticle(MagicParticleData.createData(142, 62, 56),
            player.getX() + 0.5 * player.getLookAngle().x, player.getY() + 1.5 + 0.5 * player.getLookAngle().y, player.getZ() + 0.5 * player.getLookAngle().z, velX, velY, velZ);
      }
    }
    BlockPos pos = RootsUtil.getRayTrace(level, player, 4);
    List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    List<ItemStack> items = new ArrayList<>();
    for (ItemEntity itemEntity : itemEntities) {
      items.add(itemEntity.getItem());
    }
    if (!items.isEmpty()) {
      MutagenRecipe recipe = MutagenManager.getRecipe(items, level, pos, player);
      if (recipe != null) {
        level.setBlockAndUpdate(pos, recipe.result);
        if (level.isClientSide()) {
          for (int i = 0; i < 100; i++) {
            double velX = 1.5 * (level.getRandom().nextDouble() - 0.5);
            double velY = 1.5 * (level.getRandom().nextDouble() - 0.5);
            double velZ = 1.5 * (level.getRandom().nextDouble() - 0.5);
            level.addParticle(MagicParticleData.createData(142, 62, 56),
                pos.getX() + level.getRandom().nextDouble(),
              pos.getY() + level.getRandom().nextDouble(),
              pos.getZ() + level.getRandom().nextDouble(), velX, velY, velZ);
          }
        }
        for (ItemEntity itemEntity : itemEntities) {
          itemEntity.discard();
        }
        recipe.onCrafted(level, pos, player);
      }
    }
    stack.consume(1, player);
    return InteractionResult.SUCCESS;
  }
}
