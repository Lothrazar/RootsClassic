package elucent.rootsclassic.component.components;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;

public class ComponentChorus extends ComponentBase {

  public ComponentChorus() {
    super(new ResourceLocation(Const.MODID, "chorus"), Items.CHORUS_FRUIT, 12);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof Player player) {
        player.setPos(player.getX() + player.getLookAngle().x * (8.0 + 8.0 * potency), player.getY() + player.getLookAngle().y * (8.0 + 8.0 * potency),
            player.getZ() + player.getLookAngle().z * (8.0 + 8.0 * potency));
      }
    }
  }
}
