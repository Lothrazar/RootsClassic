package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.util.RootsUtil;

public class RitualSummoning extends RitualBase {

  public EntityType<? extends MobEntity> entityType = null;

  public RitualSummoning setEntityType(EntityType<? extends MobEntity> entity) {
    this.entityType = entity;
    return this;
  }

  public RitualSummoning(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    if (RootsUtil.itemListMatchInventoryWithSize(inventory, this.getIngredients()) && !world.isRemote) {
      MobEntity toSpawn = entityType.create(world);
      if (toSpawn != null) {
        toSpawn.onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(pos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
        toSpawn.setPosition(pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5);
        inventory.clear();
        world.addEntity(toSpawn);
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null) {
          tile.markDirty();
        }
      }
    }
  }
}
