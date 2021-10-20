package elucent.rootsclassic.component.components;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentOxeyeDaisy extends ComponentBase {

  public ComponentOxeyeDaisy() {
    super(new ResourceLocation(Const.MODID, "oxeye_daisy"), Blocks.OXEYE_DAISY, 14);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof Player) {
        Entity entity = RootsUtil.getRayTraceEntity(world, (Player) caster, 4 + 2 * (int) size);
        if (entity != null) {
          if (!world.isClientSide) {
            if (world.getEntitiesOfClass(EntityAccelerator.class, new AABB(entity.getX() - 0.1, entity.getY() - 0.1, entity.getZ() - 0.1,
                entity.getX() + 0.1, entity.getY() + 0.1, entity.getZ() + 0.1)).size() == 0) {
              EntityAccelerator a = new EntityAccelerator(world, entity, (int) potency, (int) size);
              world.addFreshEntity(a);
            }
          }
        }
        else {
          BlockPos pos = RootsUtil.getRayTrace(world, (Player) caster, 4 + 2 * (int) size);
          if (world.getBlockEntity(pos) != null && !world.isClientSide) {
            if (world.getEntitiesOfClass(EntityTileAccelerator.class, new AABB(pos.getX() - 0.1, pos.getY() - 0.1, pos.getZ() - 0.1, pos.getX() + 0.1, pos.getY() + 0.1, pos.getZ() + 0.1)).size() == 0) {
              EntityTileAccelerator a = new EntityTileAccelerator(world, pos, (int) potency, (int) size);
              world.addFreshEntity(a);
            }
          }
        }
      }
    }
  }
}
