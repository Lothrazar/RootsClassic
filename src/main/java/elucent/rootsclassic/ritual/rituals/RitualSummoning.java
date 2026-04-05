package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.ritual.RitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class RitualSummoning extends RitualEffect {

  @Override
  public void doEffect(Level level, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
    if (!level.isClientSide()) {
	    Identifier entityId = Identifier.tryParse(config.getStringOr("entity", ""));
			if (entityId == null) return;
			EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getValue(entityId);
			if (entityType == null) return;
      Entity toSpawn = entityType.create(level, EntitySpawnReason.MOB_SUMMONED);
      if (toSpawn != null) {
        if (toSpawn instanceof Mob mob && level instanceof ServerLevel serverLevel) {
          EventHooks.finalizeMobSpawn(mob, serverLevel, serverLevel.getCurrentDifficultyAt(pos), EntitySpawnReason.MOB_SUMMONED, (SpawnGroupData) null);
        }
        toSpawn.setPos(pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5);
        inventory.clearContent();
        level.addFreshEntity(toSpawn);
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile != null) {
          tile.setChanged();
        }
      }
    }
  }

  @Override
  public MutableComponent getInfoText(CompoundTag config) {
	  EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.tryParse(config.getStringOr("entity", "")));
    var egg = SpawnEggItem.byId(entityType);
    if (egg == null) return Component.empty();
    return Component.translatable(Const.MODID + ".jei.tooltip.summoning", entityType.getDescription());
  }

  @Override
  public ItemStack getResult(CompoundTag config) {
	  EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.tryParse(config.getStringOr("entity", "")));
	  var egg = SpawnEggItem.byId(entityType);
    if (egg.isEmpty()) return super.getResult(config);
    var display = getInfoText(config);
		ItemStack stack = new ItemStack(egg.get());
	  stack.set(DataComponents.CUSTOM_NAME, display.withStyle(Style.EMPTY.withItalic(false)));
    return stack;
  }
}
