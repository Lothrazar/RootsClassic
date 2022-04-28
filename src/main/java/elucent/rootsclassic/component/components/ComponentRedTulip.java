package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class ComponentRedTulip extends ComponentBase {

  public ComponentRedTulip() {
    super(Blocks.RED_TULIP, 6);
  }

  @Override
  public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && !level.isClientSide) {
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size * 2.4, y - size * 2.4, z - size * 2.4, x + size * 2.4, y + size * 2.4, z + size * 2.4));
      if (targets.size() > 0) {
        PhantomSkeletonEntity skeleton = new PhantomSkeletonEntity(level);
        skeleton.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(new BlockPos(x, y, z)), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
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
}
