package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class ComponentWhiteTulip extends ComponentBase {

  public ComponentWhiteTulip() {
    super(new ResourceLocation(Const.MODID, "white_tulip"), Blocks.WHITE_TULIP, 10);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      //   int damageDealt = 0;
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (LivingEntity target : targets) {
        if (target.getUUID() != caster.getUUID()) {
          if (target instanceof Player && RootsConfig.COMMON.disablePVP.get()) {}
          else {
            target.hurt(DamageSource.GENERIC, (int) (5 + 3 * potency));
            //     damageDealt += (int) (5 + 3 * potency);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200 + 100 * (int) potency, (int) potency));
            target.setLastHurtMob(caster);
            target.setLastHurtByMob((LivingEntity) caster);
          }
        }
      }
    }
  }
}
