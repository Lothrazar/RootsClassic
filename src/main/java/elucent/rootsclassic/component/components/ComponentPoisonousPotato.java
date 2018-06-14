package elucent.rootsclassic.component.components;

import java.util.Random;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentPoisonousPotato extends ComponentBase {

  Random random = new Random();

  public ComponentPoisonousPotato() {
    super("poisonouspotato", "Electric Spark", Items.POISONOUS_POTATO, 24);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof EntityPlayer && !world.isRemote) {
        BlockPos pos = Util.getRayTrace(world, (EntityPlayer) caster, 4 + 2 * (int) size);
        world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
      }
    }
  }
}
