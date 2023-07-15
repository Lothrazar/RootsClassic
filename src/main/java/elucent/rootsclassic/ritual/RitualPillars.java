package elucent.rootsclassic.ritual;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import io.netty.util.collection.IntObjectHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RitualPillars {

  private static final int INCENSE_RADIUS = 4;
  private static final IntObjectHashMap<Map<BlockPos, Block>> CACHE = new IntObjectHashMap<>();

  public static Map<BlockPos, Block> getRitualPillars(int level) {
    return CACHE.computeIfAbsent(level, $ -> {
      var builder = new ImmutableMap.Builder<BlockPos, Block>();
      if (level >= 1) {
        //the first circle of tier 1 stones
        builder.put(new BlockPos(-3, 0, -3), RootsRegistry.MUNDANE_STANDING_STONE.get());
        builder.put(new BlockPos(-3, 0, 3), RootsRegistry.MUNDANE_STANDING_STONE.get());
        builder.put(new BlockPos(3, 0, -3), RootsRegistry.MUNDANE_STANDING_STONE.get());
        builder.put(new BlockPos(3, 0, 3), RootsRegistry.MUNDANE_STANDING_STONE.get());
        builder.put(new BlockPos(3, 0, 0), RootsRegistry.MUNDANE_STANDING_STONE.get());
        builder.put(new BlockPos(-3, 0, 0), RootsRegistry.MUNDANE_STANDING_STONE.get());
        builder.put(new BlockPos(0, 0, 3), RootsRegistry.MUNDANE_STANDING_STONE.get());
        builder.put(new BlockPos(0, 0, -3), RootsRegistry.MUNDANE_STANDING_STONE.get());
      }
      if (level == 2) {
        //the outer tier 2 stones
        builder.put(new BlockPos(5, 1, 0), RootsRegistry.ATTUNED_STANDING_STONE.get());
        builder.put(new BlockPos(-5, 1, 0), RootsRegistry.ATTUNED_STANDING_STONE.get());
        builder.put(new BlockPos(0, 1, 5), RootsRegistry.ATTUNED_STANDING_STONE.get());
        builder.put(new BlockPos(0, 1, -5), RootsRegistry.ATTUNED_STANDING_STONE.get());
      }
      return builder.build();
    });
  }

  public static boolean verifyPositionBlocks(RitualRecipe<?> recipe, Level levelAccessor, BlockPos pos) {
    var pillars = getRitualPillars(recipe.level);
    return pillars.entrySet().stream().allMatch(entry -> {
      var loopPos = entry.getKey();
      var loopBlock = entry.getValue();
      BlockPos loopPosOffset = pos.offset(loopPos.getX(), loopPos.getY(), loopPos.getZ());
      if (levelAccessor.getBlockState(loopPosOffset).getBlock() != loopBlock) {
        Roots.LOGGER.info("{} level recipe has Missing block {} at position {}", recipe.level, loopBlock, loopPosOffset);
        return false;
      }
      else {
        return true;
      }
    });
  }

  public static List<BrazierBlockEntity> getRecipeBraziers(Level levelAccessor, BlockPos pos) {
    List<BrazierBlockEntity> links = new ArrayList<>();
    BlockEntity tileHere;
    for (int i = -1 * INCENSE_RADIUS; i <= INCENSE_RADIUS; i++) {
      for (int j = -1 * INCENSE_RADIUS; j <= INCENSE_RADIUS; j++) {
        if (levelAccessor.getBlockState(pos.offset(i, 0, j)).getBlock() == RootsRegistry.BRAZIER.get()) {
          tileHere = levelAccessor.getBlockEntity(pos.offset(i, 0, j));
          if (tileHere instanceof BrazierBlockEntity brazier) {
            links.add(brazier);
          }
        }
      }
    }
    return links;
  }
}