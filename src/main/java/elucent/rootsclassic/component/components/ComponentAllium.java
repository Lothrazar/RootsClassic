package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ComponentAllium extends ComponentBase {

	public ComponentAllium() {
		super(Blocks.ALLIUM, 8);
	}

	@Override
	public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			//      int damageDealt = 0;
			List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
			for (LivingEntity target : targets) {
				if (target.getUUID() != caster.getUUID()) {
					if (target instanceof Player && RootsConfig.COMMON.disablePVP.get()) {
					} else {
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
