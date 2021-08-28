package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualImbuer extends RitualBase {

  public RitualImbuer(ResourceLocation name) {
    super(name, 1, 255, 255, 255);
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    ItemStack toSpawn = new ItemStack(RootsRegistry.CRYSTAL_STAFF.get(), 1);
    CrystalStaffItem.createData(toSpawn);
    for (int i = 0; i < incenses.size() && i < 4; i++) {
      if (incenses.get(i) != null) {
        if (incenses.get(i).getItem() == RootsRegistry.SPELL_POWDER.get() && incenses.get(i).hasTag()) {
          CompoundNBT tag = incenses.get(i).getTag();
          CrystalStaffItem.addEffect(toSpawn, i + 1, tag.getString(Const.NBT_EFFECT), tag.getInt(Const.NBT_POTENCY), tag.getInt(Const.NBT_EFFICIENCY), tag.getInt(Const.NBT_SIZE));
        }
      }
    }
    if (!world.isRemote) {
      ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      item.forceSpawn = true;
      world.addEntity(item);
    }
    inventory.clear();
    world.getTileEntity(pos).markDirty();
  }
}
