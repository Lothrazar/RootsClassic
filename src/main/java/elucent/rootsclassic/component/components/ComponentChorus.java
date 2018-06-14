package elucent.rootsclassic.component.components;

import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class ComponentChorus extends ComponentBase {

  Random random = new Random();

  public ComponentChorus() {
    super("chorus", Items.CHORUS_FRUIT, 12);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof EntityPlayer) {
        EntityPlayer player = (EntityPlayer) caster;
        player.setPosition(player.posX + player.getLookVec().x * (8.0 + 8.0 * potency), player.posY + player.getLookVec().y * (8.0 + 8.0 * potency), player.posZ + player.getLookVec().z * (8.0 + 8.0 * potency));
      }
    }
  }
}
