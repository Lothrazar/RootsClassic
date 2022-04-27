package elucent.rootsclassic.item;

import elucent.rootsclassic.client.particles.MagicParticleData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GrowthPowderItem extends Item {

  public GrowthPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType useOn(ItemUseContext context) {
    World world = context.getLevel();
    PlayerEntity player = context.getPlayer();
    if (player != null) {
      BlockPos pos = context.getClickedPos();
      Hand hand = context.getHand();
      if(world.isClientSide) {
        spawnGrowthParticle(world, player);
      }
      boolean anySuccess = applyGrowthHere(world, pos);
      if (anySuccess && !player.abilities.instabuild) {
        player.getItemInHand(hand).shrink(1);
      }
    }
    return ActionResultType.PASS;
  }

  @SuppressWarnings("deprecation")
  public static boolean applyGrowthHere(World world, BlockPos pos) {
    BlockState state = world.getBlockState(pos);
    if (state.getBlock() == Blocks.DIRT) {
      world.setBlockAndUpdate(pos, Blocks.GRASS.defaultBlockState());
      return true;
    }
    else if (state.getBlock() == Blocks.WATER && //TODO: Check if this still fires at water
        world.isEmptyBlock(pos.above())) {
          world.setBlockAndUpdate(pos.above(), Blocks.LILY_PAD.defaultBlockState());
        }
    else {
      if (state.getBlock() instanceof IGrowable) {
        IGrowable igrowable = (IGrowable) state.getBlock();
        if (!igrowable.isValidBonemealTarget(world, pos, state, world.isClientSide)) {
          return false;
        }
        Block block = state.getBlock();
        //no need to literally increase internal growth numbers, just force more  update ticks
        //TODO check if updateBlock is required here
        world.blockUpdated(pos, block);
        if (!world.isClientSide) {
          block.randomTick(state, (ServerWorld) world, pos, world.random);
        }
        return true;
      }
    }
    return false;
  }

  public static void spawnGrowthParticle(World world, PlayerEntity player) {
    Vector3d lookVec = player.getLookAngle();
    for (int i = 0; i < 40; i++) {
      double velX = (lookVec.x * 0.75) + 0.5 * (random.nextDouble() - 0.5);
      double velY = (lookVec.y * 0.75) + 0.5 * (random.nextDouble() - 0.5);
      double velZ = (lookVec.z * 0.75) + 0.5 * (random.nextDouble() - 0.5);
      world.addParticle(MagicParticleData.createData(39, 232, 55),
          player.getX() + 0.5 * lookVec.x, player.getY() + 1.5 + 0.5 * lookVec.y, player.getZ() + 0.5 * lookVec.z, velX, velY, velZ);
    }
  }
}
