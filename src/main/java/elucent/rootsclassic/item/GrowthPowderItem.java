package elucent.rootsclassic.item;

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
import elucent.rootsclassic.client.particles.MagicParticleData;

public class GrowthPowderItem extends Item {

  public GrowthPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    World world = context.getWorld();
    PlayerEntity player = context.getPlayer();
    if (player != null) {
      BlockPos pos = context.getPos();
      Hand hand = context.getHand();
      if(world.isRemote) {
        spawnGrowthParticle(world, player);
      }
      boolean anySuccess = applyGrowthHere(world, pos);
      if (anySuccess && !player.abilities.isCreativeMode) {
        player.getHeldItem(hand).shrink(1);
      }
    }
    return ActionResultType.PASS;
  }

  @SuppressWarnings("deprecation")
  public static boolean applyGrowthHere(World world, BlockPos pos) {
    BlockState state = world.getBlockState(pos);
    if (state.getBlock() == Blocks.DIRT) {
      world.setBlockState(pos, Blocks.GRASS.getDefaultState());
      return true;
    }
    else if (state.getBlock() == Blocks.WATER && //TODO: Check if this still fires at water
        world.isAirBlock(pos.up())) {
          world.setBlockState(pos.up(), Blocks.LILY_PAD.getDefaultState());
        }
    else {
      if (state.getBlock() instanceof IGrowable) {
        IGrowable igrowable = (IGrowable) state.getBlock();
        if (!igrowable.canGrow(world, pos, state, world.isRemote)) {
          return false;
        }
        Block block = state.getBlock();
        //no need to literally increase internal growth numbers, just force more  update ticks
        //TODO check if updateBlock is required here
        world.updateBlock(pos, block);
        if (!world.isRemote) {
          block.randomTick(state, (ServerWorld) world, pos, world.rand);
        }
        return true;
      }
    }
    return false;
  }

  public static void spawnGrowthParticle(World world, PlayerEntity player) {
    Vector3d lookVec = player.getLookVec();
    for (int i = 0; i < 40; i++) {
      double velX = (lookVec.x * 0.75) + 0.5 * (random.nextDouble() - 0.5);
      double velY = (lookVec.y * 0.75) + 0.5 * (random.nextDouble() - 0.5);
      double velZ = (lookVec.z * 0.75) + 0.5 * (random.nextDouble() - 0.5);
      world.addParticle(MagicParticleData.createData(39, 232, 55),
          player.getPosX() + 0.5 * lookVec.x, player.getPosY() + 1.5 + 0.5 * lookVec.y, player.getPosZ() + 0.5 * lookVec.z, velX, velY, velZ);
    }
  }
}
