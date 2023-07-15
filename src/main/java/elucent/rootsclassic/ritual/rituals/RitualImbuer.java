package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RitualImbuer extends SimpleRitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    ItemStack toSpawn = new ItemStack(RootsRegistry.CRYSTAL_STAFF.get(), 1);
    CrystalStaffItem.createData(toSpawn);
    for (int i = 0; i < incenses.size() && i < 4; i++) {
      if (incenses.get(i) != null) {
        if (incenses.get(i).getItem() == RootsRegistry.SPELL_POWDER.get() && incenses.get(i).hasTag()) {
          CompoundTag tag = incenses.get(i).getTag();
          CrystalStaffItem.addEffect(toSpawn, i + 1, tag.getString(Const.NBT_EFFECT), tag.getInt(Const.NBT_POTENCY), tag.getInt(Const.NBT_EFFICIENCY), tag.getInt(Const.NBT_SIZE));
        }
      }
    }
    if (!levelAccessor.isClientSide) {
      ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      levelAccessor.addFreshEntity(item);
    }
    inventory.clearContent();
    levelAccessor.getBlockEntity(pos).setChanged();
  }
}
