package elucent.rootsclassic.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaCapabilityStorage implements IStorage<IManaCapability> {

  public static final String NBT_MAX_MANA = "maxMana";
  public static final String NBT_MANA = "mana";

  @Override
  public INBT writeNBT(Capability<IManaCapability> capability, IManaCapability instance, Direction side) {
    CompoundNBT tag = new CompoundNBT();
    tag.putFloat(NBT_MAX_MANA, instance.getMaxMana());
    tag.putFloat(NBT_MANA, instance.getMana());
    return tag;
  }

  @Override
  public void readNBT(Capability<IManaCapability> capability, IManaCapability instance, Direction side, INBT nbt) {
    if (nbt instanceof CompoundNBT) {
      CompoundNBT tag = (CompoundNBT) nbt;
      if (tag.contains(NBT_MANA)) {
        instance.setMana(tag.getFloat(NBT_MANA));
      }
      if (tag.contains(NBT_MAX_MANA)) {
        instance.setMaxMana(tag.getFloat(NBT_MAX_MANA));
      }
    }
  }
}
