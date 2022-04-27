package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class RitualSummoning extends RitualBase {

  public EntityType<? extends MobEntity> entityType = null;

  public RitualSummoning setEntityType(EntityType<? extends MobEntity> entity) {
    this.entityType = entity;
    return this;
  }

  public RitualSummoning(int level, double r, double g, double b) {
    super(level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    if (RootsUtil.itemListMatchInventoryWithSize(inventory, this.getIngredients()) && !world.isClientSide) {
      MobEntity toSpawn = entityType.create(world);
      if (toSpawn != null) {
        toSpawn.finalizeSpawn((ServerWorld) world, world.getCurrentDifficultyAt(pos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
        toSpawn.setPos(pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5);
        inventory.clearContent();
        world.addFreshEntity(toSpawn);
        TileEntity tile = world.getBlockEntity(pos);
        if (tile != null) {
          tile.setChanged();
        }
      }
    }
  }
}
