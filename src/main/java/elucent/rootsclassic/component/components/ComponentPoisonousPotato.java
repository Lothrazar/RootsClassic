package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentPoisonousPotato extends ComponentBase {

  public ComponentPoisonousPotato() {
    super(new ResourceLocation(Const.MODID, "poisonous_potato"), Items.POISONOUS_POTATO, 24);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof PlayerEntity && !world.isClientSide) {
        BlockPos pos = RootsUtil.getRayTrace(world, (PlayerEntity) caster, 4 + 2 * (int) size);
        LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(world);
        if (lightningBolt != null) {
          lightningBolt.moveTo(pos.getX(), pos.getY(), pos.getZ());
          lightningBolt.setVisualOnly(false);
          world.addFreshEntity(lightningBolt);
        }
      }
    }
  }
}
