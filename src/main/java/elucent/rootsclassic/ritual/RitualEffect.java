package elucent.rootsclassic.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class RitualEffect {

  public abstract void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config);

  public ItemStack getResult(CompoundTag config, HolderLookup.Provider provider) {
    return ItemStack.EMPTY;
  }

  public MutableComponent getInfoText(CompoundTag config) {
    var id = RitualBaseRegistry.RITUALS.getKey(this);
    if (id == null) return Component.empty();
    return Component.translatable(id.getNamespace() + ".jei.tooltip." + id.getPath());
  }
}