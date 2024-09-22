package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RitualCrafting extends RitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
    // if (Util.itemListsMatchWithSize(inventory, this.ingredients)) {
    ItemStack toSpawn = ItemStack.parseOptional(levelAccessor.registryAccess(), config.getCompound("result"));
    if (!levelAccessor.isClientSide) {
      ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      levelAccessor.addFreshEntity(item);
    }
    inventory.clearContent();
		var blockEntity = levelAccessor.getBlockEntity(pos);
		if (blockEntity != null)
			blockEntity.setChanged();
    //}
  }

  @Override
  public ItemStack getResult(CompoundTag config, HolderLookup.Provider provider) {
    return ItemStack.parseOptional(provider, config.getCompound("result"));
  }
}
