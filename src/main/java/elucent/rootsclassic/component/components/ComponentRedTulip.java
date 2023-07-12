package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

public class ComponentRedTulip extends ComponentBase {

  public ComponentRedTulip() {
    super(new ResourceLocation(Const.MODID, "red_tulip"), Blocks.RED_TULIP, 6);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && !world.isClientSide) {
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(x - size * 2.4, y - size * 2.4, z - size * 2.4, x + size * 2.4, y + size * 2.4, z + size * 2.4));
      if (targets.size() > 0) {
        PhantomSkeletonEntity skeleton = new PhantomSkeletonEntity(world);
        skeleton.finalizeSpawn((ServerWorld) world, world.getCurrentDifficultyAt(new BlockPos(x, y, z)), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
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
