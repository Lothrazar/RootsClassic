package elucent.rootsclassic.component.components;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentFlareOrchid extends ComponentBase {

  public ComponentFlareOrchid() {
    super(new ResourceLocation(Const.MODID, "flare_orchid"), RootsRegistry.FLARE_ORCHID.get(), 36);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      BlockPos pos = RootsUtil.getRayTrace(world, (PlayerEntity) caster, 16 + 8 * (int) size);
      world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) (3.0 + potency), Mode.DESTROY);
    }
  }
}
