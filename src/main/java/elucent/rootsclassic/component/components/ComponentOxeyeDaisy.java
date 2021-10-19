package elucent.rootsclassic.component.components;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      if (caster instanceof PlayerEntity) {
        Entity entity = RootsUtil.getRayTraceEntity(world, (PlayerEntity) caster, 4 + 2 * (int) size);
        if (entity != null) {
          if (!world.isClientSide) {
            if (world.getEntitiesOfClass(EntityAccelerator.class, new AxisAlignedBB(entity.getX() - 0.1, entity.getY() - 0.1, entity.getZ() - 0.1,
                entity.getX() + 0.1, entity.getY() + 0.1, entity.getZ() + 0.1)).size() == 0) {
              EntityAccelerator a = new EntityAccelerator(world, entity, (int) potency, (int) size);
              world.addFreshEntity(a);
            }
          }
        }
        else {
          BlockPos pos = RootsUtil.getRayTrace(world, (PlayerEntity) caster, 4 + 2 * (int) size);
          if (world.getBlockEntity(pos) != null && !world.isClientSide) {
            if (world.getEntitiesOfClass(EntityTileAccelerator.class, new AxisAlignedBB(pos.getX() - 0.1, pos.getY() - 0.1, pos.getZ() - 0.1, pos.getX() + 0.1, pos.getY() + 0.1, pos.getZ() + 0.1)).size() == 0) {
              EntityTileAccelerator a = new EntityTileAccelerator(world, pos, (int) potency, (int) size);
              world.addFreshEntity(a);
            }
          }
        }
      }
    }
  }
}
