package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class ComponentOrangeTulip extends ComponentBase {

	public ComponentOrangeTulip() {
		super(new ResourceLocation(Const.MODID, "orange_tulip"), Blocks.ORANGE_TULIP, 20);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		//
	}

	@Override
	public void castingAction(PlayerEntity player, int count, int potency, int efficiency, int size) {
		super.castingAction(player, count, potency, efficiency, size);
		if (count % 1 == 0) {
			List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class,
					new AxisAlignedBB(player.getPosX() - (1.0 + 0.5 * size), player.getPosY() - (1.0 + 0.5 * size), player.getPosZ() - (1.0 + 0.5 * size),
							player.getPosX() + (1.0 + 0.5 * size), player.getPosY() + (1.0 + 0.5 * size), player.getPosZ() + (1.0 + 0.5 * size)));
			for (Entity entity : entities) {
				if (entity instanceof LivingEntity || entity instanceof AbstractArrowEntity) {
					Vector3d v = new Vector3d(entity.getPosX() - player.getPosX(), entity.getPosY() - (player.getPosY() + 1.0), entity.getPosZ() - player.getPosZ());
					v.normalize();
					entity.setMotion(v.x * (0.05 + 0.05 * potency), v.y * (0.05 + 0.05 * potency), v.z * (0.05 + 0.05 * potency));
				}
			}
		}
	}
}
