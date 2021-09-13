package elucent.rootsclassic.component.components;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentAzureBluet extends ComponentBase {

  public ComponentAzureBluet() {
    super(new ResourceLocation(Const.MODID, "azure_bluet"), Blocks.AZURE_BLUET, 6);
  }

  public void destroyBlockSafe(Level world, BlockPos pos, int potency) {
    BlockState state = world.getBlockState(pos);
    if (state.getBlock().getHarvestLevel(world.getBlockState(pos)) <= 2 + potency && state.getDestroySpeed(world, pos) != -1) {
      world.destroyBlock(pos, true);
    }
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof Player && !world.isClientSide) {
        BlockPos pos = RootsUtil.getRayTrace(world, (Player) caster, 4 + 2 * (int) size);
        destroyBlockSafe(world, pos, (int) potency);
        if (world.random.nextBoolean()) {
          destroyBlockSafe(world, pos.above(), (int) potency);
        }
        if (world.random.nextBoolean()) {
          destroyBlockSafe(world, pos.below(), (int) potency);
        }
        if (world.random.nextBoolean()) {
          destroyBlockSafe(world, pos.east(), (int) potency);
        }
        if (world.random.nextBoolean()) {
          destroyBlockSafe(world, pos.west(), (int) potency);
        }
        if (world.random.nextBoolean()) {
          destroyBlockSafe(world, pos.north(), (int) potency);
        }
        if (world.random.nextBoolean()) {
          destroyBlockSafe(world, pos.south(), (int) potency);
        }
      }
    }
  }
}
