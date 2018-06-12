package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentPinkTulip extends ComponentBase {

  public ComponentPinkTulip() {
    super("pinktulip", "Vampiric Burst", Blocks.RED_FLOWER, 7, 10);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (int i = 0; i < targets.size(); i++) {
        if (targets.get(i).getUniqueID() != caster.getUniqueID()) {
          targets.get(i).attackEntityFrom(DamageSource.WITHER, (int) (3 + 2 * potency));
          ((EntityLivingBase) caster).heal(targets.size() * (float) (1.0 + 0.5 * potency));
          targets.get(i).setLastAttackedEntity(caster);
          targets.get(i).setRevengeTarget((EntityLivingBase) caster);
        }
      }
    }
  }
}
