package elucent.rootsclassic.item;

import com.lothrazar.library.item.ItemFlib;
import elucent.rootsclassic.client.particles.MagicParticleData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GrowthPowderItem extends ItemFlib {

  public GrowthPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level levelAccessor = context.getLevel();
    Player player = context.getPlayer();
    if (player != null) {
      BlockPos pos = context.getClickedPos();
      InteractionHand hand = context.getHand();
      if (levelAccessor.isClientSide) {
        spawnGrowthParticle(levelAccessor, player);
      }
      boolean anySuccess = applyGrowthHere(levelAccessor, pos);
      if (anySuccess && !player.getAbilities().instabuild) {
        player.getItemInHand(hand).shrink(1);
      }
    }
    return InteractionResult.PASS;
  }

  @SuppressWarnings("deprecation")
  public static boolean applyGrowthHere(Level levelAccessor, BlockPos pos) {
    BlockState state = levelAccessor.getBlockState(pos);
    if (state.getBlock() == Blocks.DIRT) {
      levelAccessor.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
      return true;
    }
    else if (state.getBlock() == Blocks.WATER && //TODO: Check if this still fires at water
        levelAccessor.isEmptyBlock(pos.above())) {
          levelAccessor.setBlockAndUpdate(pos.above(), Blocks.LILY_PAD.defaultBlockState());
        }
    else {
      if (state.getBlock() instanceof BonemealableBlock igrowable) {
        if (!igrowable.isValidBonemealTarget(levelAccessor, pos, state, levelAccessor.isClientSide)) {
          return false;
        }
        Block block = state.getBlock();
        //no need to literally increase internal growth numbers, just force more  update ticks
        //TODO check if updateBlock is required here
        levelAccessor.blockUpdated(pos, block);
        if (!levelAccessor.isClientSide) {
          block.randomTick(state, (ServerLevel) levelAccessor, pos, levelAccessor.random);
        }
        return true;
      }
    }
    return false;
  }

  public static void spawnGrowthParticle(Level levelAccessor, Player player) {
    Vec3 lookVec = player.getLookAngle();
    for (int i = 0; i < 40; i++) {
      double velX = (lookVec.x * 0.75) + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
      double velY = (lookVec.y * 0.75) + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
      double velZ = (lookVec.z * 0.75) + 0.5 * (levelAccessor.random.nextDouble() - 0.5);
      levelAccessor.addParticle(MagicParticleData.createData(39, 232, 55),
          player.getX() + 0.5 * lookVec.x, player.getY() + 1.5 + 0.5 * lookVec.y, player.getZ() + 0.5 * lookVec.z, velX, velY, velZ);
    }
  }
}
