package elucent.rootsclassic.item;

import java.util.ArrayList;
import java.util.List;
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
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.util.RootsUtil;

public class MutatingPowderItem extends Item {

  public MutatingPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if(world.isRemote) {
      for (int i = 0; i < 40; i++) {
        double velX = (player.getLookVec().x * 0.75) + 0.5 * (random.nextDouble() - 0.5);
        double velY = (player.getLookVec().y * 0.75) + 0.5 * (random.nextDouble() - 0.5);
        double velZ = (player.getLookVec().z * 0.75) + 0.5 * (random.nextDouble() - 0.5);
        world.addParticle(MagicParticleData.createData(142, 62, 56),
                player.getPosX() + 0.5 * player.getLookVec().x, player.getPosY() + 1.5 + 0.5 * player.getLookVec().y, player.getPosZ() + 0.5 * player.getLookVec().z, velX, velY, velZ);
      }
    }
    BlockPos pos = RootsUtil.getRayTrace(world, player, 4);
    List<ItemEntity> itemEntities = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
    ArrayList<ItemStack> items = new ArrayList<>();
    for (ItemEntity itemEntity : itemEntities) {
      items.add(itemEntity.getItem());
    }
    if (items.size() > 0) {
      MutagenRecipe recipe = MutagenManager.getRecipe(items, world, pos, player);
      if (recipe != null) {
        world.setBlockState(pos, recipe.result);
        if(world.isRemote) {
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
    if (!player.abilities.isCreativeMode) {
      stack.shrink(1);
    }
    return new ActionResult<>(ActionResultType.SUCCESS, stack);
  }
}
