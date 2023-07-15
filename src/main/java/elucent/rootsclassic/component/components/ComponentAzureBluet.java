package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;

public class ComponentAzureBluet extends ComponentBase {

  public ComponentAzureBluet() {
    super(Blocks.AZURE_BLUET, 6);
  }

  public void destroyBlockSafe(Level levelAccessor, BlockPos pos, int potency) {
    BlockState state = levelAccessor.getBlockState(pos);
    int tier = 2 + potency;
    Tier usedTier;
    if (tier == 3) {
      usedTier = Tiers.DIAMOND;
    }
    else if (tier > 3) {
      usedTier = Tiers.NETHERITE;
    }
    else {
      usedTier = Tiers.IRON;
    }
    if (TierSortingRegistry.isCorrectTierForDrops(usedTier, state) && state.getDestroySpeed(levelAccessor, pos) != -1) {
      levelAccessor.destroyBlock(pos, true);
    }
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster && !level.isClientSide) {
      BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int) size);
      destroyBlockSafe(level, pos, (int) potency);
      if (level.random.nextBoolean()) {
        destroyBlockSafe(level, pos.above(), (int) potency);
      }
      if (level.random.nextBoolean()) {
        destroyBlockSafe(level, pos.below(), (int) potency);
      }
      if (level.random.nextBoolean()) {
        destroyBlockSafe(level, pos.east(), (int) potency);
      }
      if (level.random.nextBoolean()) {
        destroyBlockSafe(level, pos.west(), (int) potency);
      }
      if (level.random.nextBoolean()) {
        destroyBlockSafe(level, pos.north(), (int) potency);
      }
      if (level.random.nextBoolean()) {
        destroyBlockSafe(level, pos.south(), (int) potency);
      }
    }
  }
}
