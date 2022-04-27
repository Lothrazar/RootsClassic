package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class ComponentSunflower extends ComponentBase {

  private static final int POTION_DURATION = 15;

  public ComponentSunflower() {
    super(new ResourceLocation(Const.MODID, "sunflower"), Blocks.SUNFLOWER, 16);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && caster instanceof LivingEntity) {
      int damageDealt = 0;
      List<LivingEntity> targets = world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      LivingEntity target;
      for (LivingEntity livingEntity : targets) {
        target = livingEntity;
        if (target.getUUID() == caster.getUUID()) {
          continue; //don't hurt self
        }
        if (target instanceof PlayerEntity && RootsConfig.COMMON.disablePVP.get()) {
          continue; //no pvp allowed
        }
        if (target.isInvertedHealAndHarm()) {
          damageDealt += (int) (5 + 4 * potency);
          target.hurt(DamageSource.IN_FIRE, damageDealt);
          target.setSecondsOnFire(damageDealt);
          target.addEffect(new EffectInstance(Effects.WEAKNESS, POTION_DURATION, 2 + (int) potency));
          target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, POTION_DURATION, 2 + (int) potency));
        }
        else {
          damageDealt += (int) (3 + 2 * potency);
        }
        target.hurt(DamageSource.IN_FIRE, damageDealt);
        target.setLastHurtMob(caster);
        target.setLastHurtByMob((LivingEntity) caster);
      }
    }
  }
}
