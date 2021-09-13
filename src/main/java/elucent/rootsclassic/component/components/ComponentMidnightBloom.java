package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.registry.RootsRegistry;

public class ComponentMidnightBloom extends ComponentBase {

  public ComponentMidnightBloom() {
    super(new ResourceLocation(Const.MODID, "midnight_bloom"), RootsRegistry.MIDNIGHT_BLOOM.get(), 36);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesOfClass(LivingEntity.class, new AABB(x - size * 6.0, y - size * 6.0, z - size * 6.0, x + size * 6.0, y + size * 6.0, z + size * 6.0));
      for (LivingEntity target : targets) {
        if (target.getUUID() != caster.getUUID()) {
          CompoundTag persistentData = target.getPersistentData();
          persistentData.putBoolean("RMOD_trackTicks", false);
          persistentData.putInt("RMOD_skipTicks", 40 + 40 * (int) potency);
        }
      }
    }
  }
}
