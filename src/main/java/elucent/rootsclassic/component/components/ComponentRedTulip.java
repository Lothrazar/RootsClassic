package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class ComponentRedTulip extends ComponentBase {

  public ComponentRedTulip() {
    super(Blocks.RED_TULIP, 6);
  }

  @Override
  public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && !level.isClientSide) {
      PhantomSkeletonEntity skeleton = new PhantomSkeletonEntity(level);
      skeleton.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(BlockPos.containing(x, y, z)), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
      //				skeleton.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
      //				skeleton.getPersistentData().putBoolean(Const.NBT_DONT_DROP, false);
      //				skeleton.getPersistentData().putUniqueId("RMOD_dontTarget", caster.getUniqueID());
      skeleton.setPos(x, y + 2.0, z);
      //        skeleton.setAttackTarget(targets.get(random.nextInt(targets.size())));
      // if (skeleton.getAttackTarget().getUniqueID() != caster.getUniqueID()) {
      level.addFreshEntity(skeleton);
      // }
    }
  }
}
