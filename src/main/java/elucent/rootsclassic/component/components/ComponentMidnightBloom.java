package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentMidnightBloom extends ComponentBase {

  Random random = new Random();

  public ComponentMidnightBloom() {
    super("midnightbloom", "Time Stop", RegistryManager.midnightBloom, 36);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - size * 6.0, y - size * 6.0, z - size * 6.0, x + size * 6.0, y + size * 6.0, z + size * 6.0));
      for (int i = 0; i < targets.size(); i++) {
        if (targets.get(i).getUniqueID() != caster.getUniqueID()) {
          targets.get(i).getEntityData().setBoolean("RMOD_trackTicks", false);
          targets.get(i).getEntityData().setInteger("RMOD_skipTicks", 40 + 40 * (int) potency);
        }
      }
    }
  }
}
