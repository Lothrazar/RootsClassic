package elucent.rootsclassic.ritual.rituals;

import com.google.gson.JsonObject;
import elucent.rootsclassic.ritual.RitualEffect;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class RitualSummoning extends RitualEffect<RitualSummoning.RitualSummoningConfig> {

	@Override
	public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, RitualSummoningConfig config) {
		if (!levelAccessor.isClientSide) {
			Entity toSpawn = config.entityType.create(levelAccessor);

			if (toSpawn instanceof Mob) {
				Mob mob = (Mob) toSpawn;
				mob.finalizeSpawn((ServerLevel) levelAccessor, levelAccessor.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
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

	@Override
	public RitualSummoningConfig fromJSON(JsonObject object) {
		var id = GsonHelper.getAsString(object, "entity");
		var type = Registry.ENTITY_TYPE.get(new ResourceLocation(id));
		return new RitualSummoningConfig(type);
	}

	@Override
	public void toNetwork(RitualSummoningConfig config, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(config.entityType.getRegistryName());
	}

	@Override
	public RitualSummoningConfig fromNetwork(FriendlyByteBuf buffer) {
		var id = buffer.readResourceLocation();
		var type = Registry.ENTITY_TYPE.get(id);
		return new RitualSummoningConfig(type);
	}

	public record RitualSummoningConfig(EntityType<?> entityType) {
	}

}
