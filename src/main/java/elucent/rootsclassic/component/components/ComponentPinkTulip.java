package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ComponentPinkTulip extends ComponentBase {

	public ComponentPinkTulip() {
		super(new ResourceLocation(Const.MODID, "pink_tulip"), Blocks.PINK_TULIP, 10);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
			for (int i = 0; i < targets.size(); i++) {
				if (targets.get(i).getUniqueID() != caster.getUniqueID()) {
					targets.get(i).attackEntityFrom(DamageSource.WITHER, (int) (3 + 2 * potency));
					((LivingEntity) caster).heal(targets.size() * (float) (1.0 + 0.5 * potency));
					targets.get(i).setLastAttackedEntity(caster);
					targets.get(i).setRevengeTarget((LivingEntity) caster);
				}
			}
		}
	}
}
