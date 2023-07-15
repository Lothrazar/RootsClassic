package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ComponentPoisonousPotato extends ComponentBase {

  public ComponentPoisonousPotato() {
    super(Items.POISONOUS_POTATO, 24);
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!level.isClientSide && type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
      BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int) size);
      LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
      if (lightningBolt != null) {
        lightningBolt.moveTo(pos.getX(), pos.getY(), pos.getZ());
        lightningBolt.setVisualOnly(false);
        level.addFreshEntity(lightningBolt);
      }
    }
  }
}
