package elucent.rootsclassic.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class SimpleRitualEffect extends RitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, CompoundTag config) {
    doEffect(levelAccessor, pos, inventory, incenses);
  }

  public abstract void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses);
}