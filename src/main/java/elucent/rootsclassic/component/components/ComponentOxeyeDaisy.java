package elucent.rootsclassic.component.components;

import java.util.Random;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentOxeyeDaisy extends ComponentBase {

  Random random = new Random();

  public ComponentOxeyeDaisy() {
    super("oxeyedaisy", Blocks.RED_FLOWER, 8, 14);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof EntityPlayer) {
        Entity entity = Util.getRayTraceEntity(world, (EntityPlayer) caster, 4 + 2 * (int) size);
        if (entity != null) {
          if (!world.isRemote) {
            if (world.getEntitiesWithinAABB(EntityAccelerator.class, new AxisAlignedBB(entity.posX - 0.1, entity.posY - 0.1, entity.posZ - 0.1, entity.posX + 0.1, entity.posY + 0.1, entity.posZ + 0.1)).size() == 0) {
              EntityAccelerator a = new EntityAccelerator(world, entity, (int) potency, (int) size);
              world.spawnEntity(a);
            }
          }
        }
        else {
          BlockPos pos = Util.getRayTrace(world, (EntityPlayer) caster, 4 + 2 * (int) size);
          if (world.getTileEntity(pos) != null && !world.isRemote) {
            if (world.getEntitiesWithinAABB(EntityTileAccelerator.class, new AxisAlignedBB(pos.getX() - 0.1, pos.getY() - 0.1, pos.getZ() - 0.1, pos.getX() + 0.1, pos.getY() + 0.1, pos.getZ() + 0.1)).size() == 0) {
              EntityTileAccelerator a = new EntityTileAccelerator(world, pos, (int) potency, (int) size);
              world.spawnEntity(a);
            }
          }
        }
      }
    }
  }
}
