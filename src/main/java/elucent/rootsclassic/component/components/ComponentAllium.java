package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;

public class ComponentAllium extends ComponentBase {

  public ComponentAllium() {
    super(new ResourceLocation(Const.MODID, "allium"), Blocks.ALLIUM, 8);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      //      int damageDealt = 0;
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (LivingEntity target : targets) {
        if (target.getUUID() != caster.getUUID()) {
          if (target instanceof Player && RootsConfig.COMMON.disablePVP.get()) {}
          else {
            //            damageDealt += (int) (4 + 2 * potency);
            target.hurt(DamageSource.GENERIC, (int) (5 + 2 * potency));
            target.setLastHurtMob(caster);
            target.setLastHurtByMob((LivingEntity) caster);
            target.getPersistentData().putDouble("RMOD_vuln", 1.0 + 0.5 * potency);
          }
        }
      }
    }
  }
}
