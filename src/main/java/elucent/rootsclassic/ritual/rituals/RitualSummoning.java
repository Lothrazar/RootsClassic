package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.ritual.RitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class RitualSummoning extends RitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
    if (!levelAccessor.isClientSide) {
	    ResourceLocation entityId = ResourceLocation.tryParse(config.getString("entity"));
			if (entityId == null) return;
			EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(entityId);
			if (entityType == null) return;
      Entity toSpawn = entityType.create(levelAccessor);
      if (toSpawn != null) {
        if (toSpawn instanceof Mob mob && levelAccessor instanceof ServerLevel sl) {
          EventHooks.finalizeMobSpawn(mob, sl, levelAccessor.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null);
        }
        toSpawn.setPos(pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5);
        inventory.clearContent();
        levelAccessor.addFreshEntity(toSpawn);
        BlockEntity tile = levelAccessor.getBlockEntity(pos);
        if (tile != null) {
          tile.setChanged();
        }
      }
    }
  }

  @Override
  public MutableComponent getInfoText(CompoundTag config) {
	  EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(config.getString("entity")));
    var egg = DeferredSpawnEggItem.deferredOnlyById(entityType);
    if (egg == null) return Component.empty();
    return Component.translatable(Const.MODID + ".jei.tooltip.summoning", entityType.getDescription());
  }

  @Override
  public ItemStack getResult(CompoundTag config, HolderLookup.Provider provider) {
	  EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(config.getString("entity")));
	  var egg = DeferredSpawnEggItem.deferredOnlyById(entityType);
    if (egg == null) return super.getResult(config, provider);
    var display = getInfoText(config);
		ItemStack stack = new ItemStack(egg);
	  stack.set(DataComponents.CUSTOM_NAME, display.withStyle(Style.EMPTY.withItalic(false)));
    return stack;
  }
}
