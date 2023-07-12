package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ComponentDandelion extends ComponentBase {

  public ComponentDandelion() {
    super(new ResourceLocation(Const.MODID, "dandelion"), Blocks.DANDELION, 8);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (LivingEntity target : targets) {
        if (target.getUUID() != caster.getUUID()) {
          target.setDeltaMovement(new Vector3d(caster.getLookAngle().x, (float) (potency == 0 ? 1.0 : 1.0 + 0.5 * potency), caster.getLookAngle().z));
          if (target instanceof PlayerEntity) {
            ((PlayerEntity) target).hurtMarked = true;
          }
        }
      }
    }
  }
}