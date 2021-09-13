package elucent.rootsclassic.item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import elucent.rootsclassic.client.particles.MagicParticleData;

import net.minecraft.world.item.Item.Properties;

public class GrowthPowderItem extends Item {

  public GrowthPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level world = context.getLevel();
    Player player = context.getPlayer();
    if (player != null) {
      BlockPos pos = context.getClickedPos();
      InteractionHand hand = context.getHand();
      spawnGrowthParticle(world, player);
      boolean anySuccess = applyGrowthHere(world, pos);
      if (anySuccess && !player.getAbilities().instabuild) {
        player.getItemInHand(hand).shrink(1);
      }
    }
    return InteractionResult.PASS;
  }

  @SuppressWarnings("deprecation")
  public static boolean applyGrowthHere(Level world, BlockPos pos) {
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
      if (state.getBlock() instanceof BonemealableBlock) {
        BonemealableBlock igrowable = (BonemealableBlock) state.getBlock();
        if (!igrowable.isValidBonemealTarget(world, pos, state, world.isClientSide)) {
          return false;
        }
        Block block = state.getBlock();
        //no need to literally increase internal growth numbers, just force more  update ticks
        //TODO check if updateBlock is required here
        world.blockUpdated(pos, block);
        if (!world.isClientSide) {
          block.randomTick(state, (ServerLevel) world, pos, world.random);
        }
        return true;
      }
    }
    return false;
  }

  public static void spawnGrowthParticle(Level world, Player player) {
    Vec3 lookVec = player.getLookAngle();
    for (int i = 0; i < 40; i++) {
      double velX = (lookVec.x * 0.75) + 0.5 * (world.random.nextDouble() - 0.5);
      double velY = (lookVec.y * 0.75) + 0.5 * (world.random.nextDouble() - 0.5);
      double velZ = (lookVec.z * 0.75) + 0.5 * (world.random.nextDouble() - 0.5);
      world.addParticle(MagicParticleData.createData(39, 232, 55),
          player.getX() + 0.5 * lookVec.x, player.getY() + 1.5 + 0.5 * lookVec.y, player.getZ() + 0.5 * lookVec.z, velX, velY, velZ);
    }
  }
}
