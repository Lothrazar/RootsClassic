package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class ComponentNetherWart extends ComponentBase {

	public ComponentNetherWart() {
		super(Items.NETHER_WART, 10);
	}

	@Override
	public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			//   int damageDealt = 0;
			ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
			for (LivingEntity target : targets) {
				if (target.getUUID() != caster.getUUID()) {
					if (target instanceof Player && RootsConfig.COMMON.disablePVP.get()) {
					} else {
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
