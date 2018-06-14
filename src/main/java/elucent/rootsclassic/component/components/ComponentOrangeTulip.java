package elucent.rootsclassic.component.components;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ComponentOrangeTulip extends ComponentBase {

  Random random = new Random();

  public ComponentOrangeTulip() {
    super("orangetulip", Blocks.RED_FLOWER, 20);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    //
  }

  @Override
  public void castingAction(EntityPlayer player, int count, int potency, int efficiency, int size) {
    super.castingAction(player, count, potency, efficiency, size);
    if (count % 1 == 0) {
      List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX - (1.0 + 0.5 * size), player.posY - (1.0 + 0.5 * size), player.posZ - (1.0 + 0.5 * size), player.posX + (1.0 + 0.5 * size), player.posY + (1.0 + 0.5 * size), player.posZ + (1.0 + 0.5 * size)));
      for (int i = 0; i < entities.size(); i++) {
        if (entities.get(i) instanceof EntityLivingBase || entities.get(i) instanceof EntityArrow) {
          Vec3d v = new Vec3d(entities.get(i).posX - player.posX, entities.get(i).posY - (player.posY + 1.0), entities.get(i).posZ - player.posZ);
          v.normalize();
          entities.get(i).motionX = v.x * (0.05 + 0.05 * potency);
          entities.get(i).motionY = v.y * (0.05 + 0.05 * potency);
          entities.get(i).motionZ = v.z * (0.05 + 0.05 * potency);
        }
      }
    }
  }
}
