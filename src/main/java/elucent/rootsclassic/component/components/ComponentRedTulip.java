package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;

public class ComponentRedTulip extends ComponentBase {

  public ComponentRedTulip() {
    super(new ResourceLocation(Const.MODID, "red_tulip"), Blocks.RED_TULIP, 6);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && !world.isClientSide) {
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AABB(x - size * 2.4, y - size * 2.4, z - size * 2.4, x + size * 2.4, y + size * 2.4, z + size * 2.4));
      if (targets.size() > 0) {
        PhantomSkeletonEntity skeleton = new PhantomSkeletonEntity(world);
        skeleton.finalizeSpawn((ServerLevel) world, world.getCurrentDifficultyAt(new BlockPos(x, y, z)), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
        //				skeleton.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
        //				skeleton.getPersistentData().putBoolean(Const.NBT_DONT_DROP, false);
        //				skeleton.getPersistentData().putUniqueId("RMOD_dontTarget", caster.getUniqueID());
        skeleton.setPos(x, y + 2.0, z);
        //        skeleton.setAttackTarget(targets.get(random.nextInt(targets.size())));
        // if (skeleton.getAttackTarget().getUniqueID() != caster.getUniqueID()) {
        world.addFreshEntity(skeleton);
        // }
      }
    }
  }
}
