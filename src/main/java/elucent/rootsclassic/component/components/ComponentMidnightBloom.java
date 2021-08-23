package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ComponentMidnightBloom extends ComponentBase {

	public ComponentMidnightBloom() {
		super(new ResourceLocation(Const.MODID, "midnight_bloom"), RootsRegistry.MIDNIGHT_BLOOM.get(), 36);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(x - size * 6.0, y - size * 6.0, z - size * 6.0, x + size * 6.0, y + size * 6.0, z + size * 6.0));
			for (LivingEntity target : targets) {
				if (target.getUniqueID() != caster.getUniqueID()) {
					CompoundNBT persistentData = target.getPersistentData();
					persistentData.putBoolean("RMOD_trackTicks", false);
					persistentData.putInt("RMOD_skipTicks", 40 + 40 * (int) potency);
				}
			}
		}
	}
}
