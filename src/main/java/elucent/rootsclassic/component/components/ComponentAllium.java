package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class ComponentAllium extends ComponentBase {

	public ComponentAllium() {
		super(Blocks.ALLIUM, 8);
	}

	@Override
	public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
			//      int damageDealt = 0;
			ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
			targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
			for (LivingEntity target : targets) {
				if (target instanceof Player && RootsConfig.COMMON.disablePVP.get()) {
					//no pvp allowed
				} else {
					//            damageDealt += (int) (4 + 2 * potency);
					target.hurt(spellAttack(caster), (int) (5 + 2 * potency));
					target.setLastHurtMob(caster);
					target.setLastHurtByMob((LivingEntity) caster);
					target.getPersistentData().putDouble("RMOD_vuln", 1.0 + 0.5 * potency);
				}
			}
		}
	}

	public static DamageSource spellAttack(LivingEntity attacker) {
		return new EntityDamageSource(Const.MODID + ".generic", attacker).bypassArmor();
	}
}
