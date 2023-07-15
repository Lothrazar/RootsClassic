package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluids;

public class ComponentLilyPad extends ComponentBase {

  public ComponentLilyPad() {
    super(Blocks.LILY_PAD, 8);
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!level.isClientSide && type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
      BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int) size);
      if (level.getBlockState(pos.above()).canBeReplaced(Fluids.WATER)) {
        level.setBlock(pos.above(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
      }
      if (level.getBlockState(pos.above().west()).canBeReplaced(Fluids.WATER)) {
        level.setBlock(pos.above().west(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
      }
      if (level.getBlockState(pos.above().east()).canBeReplaced(Fluids.WATER)) {
        level.setBlock(pos.above().east(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
      }
      if (level.getBlockState(pos.above().north()).canBeReplaced(Fluids.WATER)) {
        level.setBlock(pos.above().north(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
      }
      if (level.getBlockState(pos.above().south()).canBeReplaced(Fluids.WATER)) {
        level.setBlock(pos.above().south(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
      }
    }
  }
}
