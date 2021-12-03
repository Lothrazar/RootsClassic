package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class RitualSummoning extends RitualBase {

  public EntityType<? extends Mob> entityType = null;

  public RitualSummoning setEntityType(EntityType<? extends Mob> entity) {
    this.entityType = entity;
    return this;
  }

  public RitualSummoning(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(Level world, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    if (RootsUtil.itemListMatchInventoryWithSize(inventory, this.getIngredients()) && !world.isClientSide) {
      Mob toSpawn = entityType.create(world);
      if (toSpawn != null) {
        toSpawn.finalizeSpawn((ServerLevel) world, world.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
        toSpawn.setPos(pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5);
        inventory.clearContent();
        world.addFreshEntity(toSpawn);
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile != null) {
          tile.setChanged();
        }
      }
    }
  }
}
