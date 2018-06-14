package elucent.rootsclassic.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaCapabilityStorage implements IStorage<IManaCapability> {

  public static final String NBT_MAX_MANA = "maxMana";
  public static final String NBT_MANA = "mana";

  @Override
  public NBTBase writeNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side) {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setFloat(NBT_MAX_MANA, instance.getMaxMana());
    tag.setFloat(NBT_MANA, instance.getMana());
    return tag;
  }

  @Override
  public void readNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side,
      NBTBase nbt) {
    if (nbt instanceof NBTTagCompound) {
      NBTTagCompound tag = (NBTTagCompound) nbt;
      if (tag.hasKey(NBT_MANA)) {
        instance.setMana(tag.getFloat(NBT_MANA));
      }
      if (tag.hasKey(NBT_MAX_MANA)) {
        instance.setMaxMana(tag.getFloat(NBT_MAX_MANA));
      }
    }
  }
}
