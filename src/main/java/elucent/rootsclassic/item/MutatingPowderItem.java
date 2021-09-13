package elucent.rootsclassic.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.util.RootsUtil;

import net.minecraft.world.item.Item.Properties;

public class MutatingPowderItem extends Item {

  public MutatingPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    for (int i = 0; i < 40; i++) {
      double velX = (player.getLookAngle().x * 0.75) + 0.5 * (world.random.nextDouble() - 0.5);
      double velY = (player.getLookAngle().y * 0.75) + 0.5 * (world.random.nextDouble() - 0.5);
      double velZ = (player.getLookAngle().z * 0.75) + 0.5 * (world.random.nextDouble() - 0.5);
      world.addParticle(MagicParticleData.createData(142, 62, 56),
          player.getX() + 0.5 * player.getLookAngle().x, player.getY() + 1.5 + 0.5 * player.getLookAngle().y, player.getZ() + 0.5 * player.getLookAngle().z, velX, velY, velZ);
    }
    BlockPos pos = RootsUtil.getRayTrace(world, player, 4);
    List<ItemEntity> itemEntities = world.getEntitiesOfClass(ItemEntity.class, new AABB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    ArrayList<ItemStack> items = new ArrayList<>();
    for (ItemEntity itemEntity : itemEntities) {
      items.add(itemEntity.getItem());
    }
    if (items.size() > 0) {
      MutagenRecipe recipe = MutagenManager.getRecipe(items, world, pos, player);
      if (recipe != null) {
        world.setBlockAndUpdate(pos, recipe.result);
        for (int i = 0; i < 100; i++) {
          double velX = 1.5 * (world.random.nextDouble() - 0.5);
          double velY = 1.5 * (world.random.nextDouble() - 0.5);
          double velZ = 1.5 * (world.random.nextDouble() - 0.5);
          world.addParticle(MagicParticleData.createData(142, 62, 56),
              pos.getX() + world.random.nextDouble(), pos.getY() + world.random.nextDouble(), pos.getZ() + world.random.nextDouble(), velX, velY, velZ);
        }
        for (ItemEntity itemEntity : itemEntities) {
          itemEntity.remove(Entity.RemovalReason.DISCARDED);
        }
        recipe.onCrafted(world, pos, player);
      }
    }
    if (!player.getAbilities().instabuild) {
      stack.shrink(1);
    }
    return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
  }
}
