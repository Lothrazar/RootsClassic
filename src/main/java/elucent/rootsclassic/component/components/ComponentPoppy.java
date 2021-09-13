package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;

public class ComponentPoppy extends ComponentBase {

  public ComponentPoppy() {
    super(new ResourceLocation(Const.MODID, "poppy"), Blocks.POPPY, 8);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ArrayList<Monster> targets = (ArrayList<Monster>) world.getEntitiesOfClass(Monster.class, new AABB(x - size * 2.4, y - size * 2.4, z - size * 2.4, x + size * 2.4, y + size * 2.4, z + size * 2.4));
      for (int i = 0; i < targets.size(); i++) {
        targets.get(i).setTarget(null);
        int j = world.random.nextInt(targets.size());
        if (j != i && world.random.nextDouble() >= 1.0 / (potency + 1.0)) {
          //          if (caster instanceof EntityPlayer) {
          //            if (!((EntityPlayer) caster).hasAchievement(RegistryManager.achieveSpellInsanity)) {
          //              PlayerManager.addAchievement((EntityPlayer) caster, RegistryManager.achieveSpellInsanity);
          //            }
          //          }
          targets.get(i).setTarget(targets.get(j));
        }
      }
    }
  }
}
