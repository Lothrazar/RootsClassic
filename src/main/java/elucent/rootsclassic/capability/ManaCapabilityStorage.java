package elucent.rootsclassic.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaCapabilityStorage implements IStorage<IManaCapability> {

  @Override
  public NBTBase writeNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side) {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setFloat("maxMana", instance.getMaxMana());
    tag.setFloat("mana", instance.getMana());
    return tag;
  }

  @Override
  public void readNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side,
      NBTBase nbt) {
    if (nbt instanceof NBTTagCompound) {
      NBTTagCompound tag = (NBTTagCompound) nbt;
      if (tag.hasKey("mana")) {
        instance.setMana(tag.getFloat("mana"));
      }
      if (tag.hasKey("maxMana")) {
        instance.setMaxMana(tag.getFloat("maxMana"));
      }
    }
  }
}
