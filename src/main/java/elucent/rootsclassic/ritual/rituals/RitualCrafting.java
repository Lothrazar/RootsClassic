package elucent.rootsclassic.ritual.rituals;

import com.mojang.serialization.DynamicOps;
import elucent.rootsclassic.ritual.RitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RitualCrafting extends RitualEffect {

  @Override
  public void doEffect(Level level, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
    // if (Util.itemListsMatchWithSize(inventory, this.ingredients)) {
    DynamicOps<Tag> ops = level.registryAccess().createSerializationContext(NbtOps.INSTANCE);
    ItemStack toSpawn = config.read("result", ItemStack.CODEC, ops).orElse(ItemStack.EMPTY);
    if (!level.isClientSide()) {
      ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      level.addFreshEntity(item);
    }
    inventory.clearContent();
		var blockEntity = level.getBlockEntity(pos);
		if (blockEntity != null)
			blockEntity.setChanged();
    //}
  }

  @Override
  public ItemStack getResult(CompoundTag config) {
    return config.read("result", ItemStack.CODEC).orElse(ItemStack.EMPTY);
  }
}
