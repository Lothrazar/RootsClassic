package elucent.rootsclassic.component.components;

import java.util.List;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ComponentBlueOrchid extends ComponentBase {

  public ComponentBlueOrchid() {
    super(Blocks.BLUE_ORCHID, 14);
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster && !level.isClientSide) {
      BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int) size);
      BlockState state = level.getBlockState(pos);
      if (state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(BlockTags.DIRT) || state.is(Blocks.GRASS_BLOCK) || state.is(BlockTags.SAND) || state.is(Blocks.GRAVEL)) {
        if (state.is(Blocks.GRASS_BLOCK)) {
          state = Blocks.DIRT.defaultBlockState();
          level.setBlockAndUpdate(pos, state);
        }
        level.setBlockAndUpdate(pos.above(), state);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.getX() - size, pos.getY() - size, pos.getZ() - size, pos.getX() + size, pos.getY() + size, pos.getZ() + size));
        targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
        for (LivingEntity target : targets) {
          target.push(0, 3, 0);
          Vec3 motion = target.getDeltaMovement();
          target.setDeltaMovement(motion.x, 0.65 + level.random.nextDouble() + 0.25 * potency, motion.z);
          if (target instanceof Player targetPlayer) {
            targetPlayer.hurtMarked = true;
          }
        }
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().west().north(), state);
        }
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().east().south(), state);
        }
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().north().east(), state);
        }
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().south().west(), state);
        }
        if (level.random.nextInt(1) == 0) {
          level.setBlockAndUpdate(pos.above().west(), state);
        }
        if (level.random.nextInt(1) == 0) {
          level.setBlockAndUpdate(pos.above().east(), state);
        }
        if (level.random.nextInt(1) == 0) {
          level.setBlockAndUpdate(pos.above().north(), state);
        }
        if (level.random.nextInt(1) == 0) {
          level.setBlockAndUpdate(pos.above().south(), state);
        }
        level.setBlockAndUpdate(pos.above().above(), state);
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().above().west(), state);
        }
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().above().east(), state);
        }
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().above().north(), state);
        }
        if (level.random.nextInt(3) == 0) {
          level.setBlockAndUpdate(pos.above().above().south(), state);
        }
        level.setBlockAndUpdate(pos.above().above().above(), state);
      }
    }
  }
}
