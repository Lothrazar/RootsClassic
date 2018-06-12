package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentPoppy extends ComponentBase {

  Random random = new Random();

  public ComponentPoppy() {
    super("poppy", "Insanity", Blocks.RED_FLOWER, 0, 8);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ArrayList<EntityMob> targets = (ArrayList<EntityMob>) world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(x - size * 2.4, y - size * 2.4, z - size * 2.4, x + size * 2.4, y + size * 2.4, z + size * 2.4));
      for (int i = 0; i < targets.size(); i++) {
        targets.get(i).setAttackTarget(null);
        int j = random.nextInt(targets.size());
        if (j != i && random.nextDouble() >= 1.0 / (potency + 1.0)) {
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
