package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ComponentChorus extends ComponentBase {

  public ComponentChorus() {
    super(Items.CHORUS_FRUIT, 12);
  }

  @Override
  public void doEffect(Level level, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && caster instanceof Player player) {
      player.setPos(player.getX() + player.getLookAngle().x * (8.0 + 8.0 * potency), player.getY() + player.getLookAngle().y * (8.0 + 8.0 * potency),
          player.getZ() + player.getLookAngle().z * (8.0 + 8.0 * potency));
    }
  }
}
