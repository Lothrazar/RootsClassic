package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ComponentPoppy extends ComponentBase {

	public ComponentPoppy() {
		super(new ResourceLocation(Const.MODID, "poppy"), Blocks.POPPY, 8);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			ArrayList<MonsterEntity> targets = (ArrayList<MonsterEntity>) world.getEntitiesWithinAABB(MonsterEntity.class, new AxisAlignedBB(x - size * 2.4, y - size * 2.4, z - size * 2.4, x + size * 2.4, y + size * 2.4, z + size * 2.4));
			for (int i = 0; i < targets.size(); i++) {
				targets.get(i).setAttackTarget(null);
				int j = world.rand.nextInt(targets.size());
				if (j != i && world.rand.nextDouble() >= 1.0 / (potency + 1.0)) {
					//          if (caster instanceof EntityPlayer) {
					//            if (!((EntityPlayer) caster).hasAchievement(RegistryManager.achieveSpellInsanity)) {
					//              PlayerManager.addAchievement((EntityPlayer) caster, RegistryManager.achieveSpellInsanity);
					//            }
					//          }
					targets.get(i).setAttackTarget(targets.get(j));
				}
			}
		}
	}
}
