package elucent.rootsclassic.item;

import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MutatingPowderItem extends Item {

  public MutatingPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if(world.isClientSide) {
      for (int i = 0; i < 40; i++) {
        double velX = (player.getLookAngle().x * 0.75) + 0.5 * (random.nextDouble() - 0.5);
        double velY = (player.getLookAngle().y * 0.75) + 0.5 * (random.nextDouble() - 0.5);
        double velZ = (player.getLookAngle().z * 0.75) + 0.5 * (random.nextDouble() - 0.5);
        world.addParticle(MagicParticleData.createData(142, 62, 56),
                player.getX() + 0.5 * player.getLookAngle().x, player.getY() + 1.5 + 0.5 * player.getLookAngle().y, player.getZ() + 0.5 * player.getLookAngle().z, velX, velY, velZ);
      }
    }
    BlockPos pos = RootsUtil.getRayTrace(world, player, 4);
    List<ItemEntity> itemEntities = world.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    ArrayList<ItemStack> items = new ArrayList<>();
    for (ItemEntity itemEntity : itemEntities) {
      items.add(itemEntity.getItem());
    }
    if (items.size() > 0) {
      MutagenRecipe recipe = MutagenManager.getRecipe(items, world, pos, player);
      if (recipe != null) {
        world.setBlockAndUpdate(pos, recipe.result);
        if(world.isClientSide) {
          for (int i = 0; i < 100; i++) {
            double velX = 1.5 * (random.nextDouble() - 0.5);
            double velY = 1.5 * (random.nextDouble() - 0.5);
            double velZ = 1.5 * (random.nextDouble() - 0.5);
            world.addParticle(MagicParticleData.createData(142, 62, 56),
                    pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), velX, velY, velZ);
          }
        }
        for (ItemEntity itemEntity : itemEntities) {
          itemEntity.remove();
        }
        recipe.onCrafted(world, pos, player);
      }
    }
    if (!player.abilities.instabuild) {
      stack.shrink(1);
    }
    return new ActionResult<>(ActionResultType.SUCCESS, stack);
  }
}
