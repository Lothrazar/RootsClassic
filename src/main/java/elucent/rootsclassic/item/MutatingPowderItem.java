package elucent.rootsclassic.item;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.library.item.ItemFlib;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class MutatingPowderItem extends ItemFlib {

  public MutatingPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level levelAccessor, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (levelAccessor.isClientSide) {
      for (int i = 0; i < 40; i++) {
        double velX = (player.getLookAngle().x * 0.75) + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
        double velY = (player.getLookAngle().y * 0.75) + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
        double velZ = (player.getLookAngle().z * 0.75) + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
        levelAccessor.addParticle(MagicParticleData.createData(142, 62, 56),
            player.getX() + 0.5 * player.getLookAngle().x, player.getY() + 1.5 + 0.5 * player.getLookAngle().y, player.getZ() + 0.5 * player.getLookAngle().z, velX, velY, velZ);
      }
    }
    BlockPos pos = RootsUtil.getRayTrace(levelAccessor, player, 4);
    List<ItemEntity> itemEntities = levelAccessor.getEntitiesOfClass(ItemEntity.class, new AABB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    List<ItemStack> items = new ArrayList<>();
    for (ItemEntity itemEntity : itemEntities) {
      items.add(itemEntity.getItem());
    }
    if (items.size() > 0) {
      MutagenRecipe recipe = MutagenManager.getRecipe(items, levelAccessor, pos, player);
      if (recipe != null) {
        levelAccessor.setBlockAndUpdate(pos, recipe.result);
        if (levelAccessor.isClientSide) {
          for (int i = 0; i < 100; i++) {
            double velX = 1.5 * (levelAccessor.random.nextDouble() - 0.5);
            double velY = 1.5 * (levelAccessor.random.nextDouble() - 0.5);
            double velZ = 1.5 * (levelAccessor.random.nextDouble() - 0.5);
            levelAccessor.addParticle(MagicParticleData.createData(142, 62, 56),
                pos.getX() + levelAccessor.random.nextDouble(), pos.getY() + levelAccessor.random.nextDouble(), pos.getZ() + levelAccessor.random.nextDouble(), velX, velY, velZ);
          }
        }
        for (ItemEntity itemEntity : itemEntities) {
          itemEntity.discard();
        }
        recipe.onCrafted(levelAccessor, pos, player);
      }
    }
    if (!player.getAbilities().instabuild) {
      stack.shrink(1);
    }
    return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
  }
}
