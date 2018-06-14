package elucent.rootsclassic.component.components;

import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentFlareOrchid extends ComponentBase {

  Random random = new Random();

  public ComponentFlareOrchid() {
    super("flareorchid", RegistryManager.flareOrchid, 36);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      BlockPos pos = Util.getRayTrace(world, (EntityPlayer) caster, 16 + 8 * (int) size);
      world.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (3.0 + potency), true, true);
    }
  }
}
