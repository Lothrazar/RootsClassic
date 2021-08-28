package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;

public class ComponentDandelion extends ComponentBase {

	public ComponentDandelion() {
		super(new ResourceLocation(Const.MODID, "dandelion"), Blocks.DANDELION, 8);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
			for (LivingEntity target : targets) {
				if (target.getUniqueID() != caster.getUniqueID()) {
					target.setMotion(new Vector3d(caster.getLookVec().x, (float) (potency == 0 ? 1.0 : 1.0 + 0.5 * potency), caster.getLookVec().z));
					if (target instanceof PlayerEntity) {
						((PlayerEntity) target).velocityChanged = true;
					}
				}
			}
		}
	}
}