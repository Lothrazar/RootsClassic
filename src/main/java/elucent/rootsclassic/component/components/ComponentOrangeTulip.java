package elucent.rootsclassic.component.components;

import java.util.List;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;

public class ComponentOrangeTulip extends ComponentBase {

  public ComponentOrangeTulip() {
    super(new ResourceLocation(Const.MODID, "orange_tulip"), Blocks.ORANGE_TULIP, 20);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    //
  }

  @Override
  public void castingAction(Player player, int count, int potency, int efficiency, int size) {
    super.castingAction(player, count, potency, efficiency, size);
    if (count % 1 == 0) {
      List<Entity> entities = player.level.getEntitiesOfClass(Entity.class,
          new AABB(player.getX() - (1.0 + 0.5 * size), player.getY() - (1.0 + 0.5 * size), player.getZ() - (1.0 + 0.5 * size),
              player.getX() + (1.0 + 0.5 * size), player.getY() + (1.0 + 0.5 * size), player.getZ() + (1.0 + 0.5 * size)));
      for (Entity entity : entities) {
        if (entity instanceof LivingEntity || entity instanceof AbstractArrow) {
          Vec3 v = new Vec3(entity.getX() - player.getX(), entity.getY() - (player.getY() + 1.0), entity.getZ() - player.getZ());
          v.normalize();
          entity.setDeltaMovement(v.x * (0.05 + 0.05 * potency), v.y * (0.05 + 0.05 * potency), v.z * (0.05 + 0.05 * potency));
        }
      }
    }
  }
}
