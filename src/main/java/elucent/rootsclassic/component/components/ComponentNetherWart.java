package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ComponentNetherWart extends ComponentBase {

  public ComponentNetherWart() {
    super(new ResourceLocation(Const.MODID, "nether_wart"), Items.NETHER_WART, 10);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      //   int damageDealt = 0;
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (LivingEntity target : targets) {
        if (target.getUUID() != caster.getUUID()) {
          if (target instanceof PlayerEntity && RootsConfig.COMMON.disablePVP.get()) {}
          else {
            //    damageDealt += (int) (5 + 3 * potency);
            target.hurt(DamageSource.IN_FIRE, (int) (5 + 3 * potency));
            target.setSecondsOnFire((int) (4 + 3 * potency));
            target.setLastHurtMob(caster);
            target.setLastHurtByMob((LivingEntity) caster);
          }
        }
      }
      //      if (damageDealt > 80) {
      //        if (caster instanceof EntityPlayer) {
      //          if (!((EntityPlayer) caster).hasAchievement(RegistryManager.achieveLotsDamage)) {
      //            PlayerManager.addAchievement(((EntityPlayer) caster), RegistryManager.achieveLotsDamage);
      //          }
      //        }
      //      }
    }
  }
}
