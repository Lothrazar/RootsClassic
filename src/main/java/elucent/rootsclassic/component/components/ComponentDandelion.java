package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class ComponentDandelion extends ComponentBase {

	public ComponentDandelion() {
		super(Blocks.DANDELION, 8);
	}

	@Override
	public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
			for (LivingEntity target : targets) {
				if (target.getUUID() != caster.getUUID()) {
					target.setDeltaMovement(new Vec3(caster.getLookAngle().x, (float) (potency == 0 ? 1.0 : 1.0 + 0.5 * potency), caster.getLookAngle().z));
					if (target instanceof Player) {
						((Player) target).hurtMarked = true;
					}
				}
			}
		}
	}
}