package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class ComponentPinkTulip extends ComponentBase {

  public ComponentPinkTulip() {
    super(new ResourceLocation(Const.MODID, "pink_tulip"), Blocks.PINK_TULIP, 10);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (int i = 0; i < targets.size(); i++) {
        if (targets.get(i).getUUID() != caster.getUUID()) {
          targets.get(i).hurt(DamageSource.WITHER, (int) (3 + 2 * potency));
          ((LivingEntity) caster).heal(targets.size() * (float) (1.0 + 0.5 * potency));
          targets.get(i).setLastHurtMob(caster);
          targets.get(i).setLastHurtByMob((LivingEntity) caster);
        }
      }
    }
  }
}
