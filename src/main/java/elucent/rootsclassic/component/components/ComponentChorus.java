package elucent.rootsclassic.component.components;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;

public class ComponentChorus extends ComponentBase {

  public ComponentChorus() {
    super(new ResourceLocation(Const.MODID, "chorus"), Items.CHORUS_FRUIT, 12);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof PlayerEntity) {
        PlayerEntity player = (PlayerEntity) caster;
        player.setPos(player.getX() + player.getLookAngle().x * (8.0 + 8.0 * potency), player.getY() + player.getLookAngle().y * (8.0 + 8.0 * potency),
            player.getZ() + player.getLookAngle().z * (8.0 + 8.0 * potency));
      }
    }
  }
}
