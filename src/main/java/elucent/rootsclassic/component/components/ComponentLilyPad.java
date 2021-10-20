package elucent.rootsclassic.component.components;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentLilyPad extends ComponentBase {

  public ComponentLilyPad() {
    super(new ResourceLocation(Const.MODID, "lily_pad"), Blocks.LILY_PAD, 8);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof Player && !world.isClientSide) {
        BlockPos pos = RootsUtil.getRayTrace(world, (Player) caster, 4 + 2 * (int) size);
        if (world.getBlockState(pos.above()).canBeReplaced(Fluids.WATER)) {
          world.setBlock(pos.above(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
        }
        if (world.getBlockState(pos.above().west()).canBeReplaced(Fluids.WATER)) {
          world.setBlock(pos.above().west(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
        }
        if (world.getBlockState(pos.above().east()).canBeReplaced(Fluids.WATER)) {
          world.setBlock(pos.above().east(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
        }
        if (world.getBlockState(pos.above().north()).canBeReplaced(Fluids.WATER)) {
          world.setBlock(pos.above().north(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
        }
        if (world.getBlockState(pos.above().south()).canBeReplaced(Fluids.WATER)) {
          world.setBlock(pos.above().south(), Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 15), 3);
        }
      }
    }
  }
}
