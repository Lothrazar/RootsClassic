package elucent.rootsclassic.component.components;

import java.util.List;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ComponentMidnightBloom extends ComponentBase {

  public ComponentMidnightBloom() {
    super(RootsRegistry.MIDNIGHT_BLOOM.get(), 36);
  }

  @Override
  public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class,
          new AABB(x - size * 6.0, y - size * 6.0, z - size * 6.0, x + size * 6.0, y + size * 6.0, z + size * 6.0));
      targets.removeIf(target -> target.getUUID() == caster.getUUID());
      for (LivingEntity target : targets) {
        CompoundTag persistentData = target.getPersistentData();
        persistentData.putBoolean(Const.NBT_TRACK_TICKS, false);
        persistentData.putInt(Const.NBT_SKIP_TICKS, 40 + 40 * (int) potency);
      }
    }
  }
}
