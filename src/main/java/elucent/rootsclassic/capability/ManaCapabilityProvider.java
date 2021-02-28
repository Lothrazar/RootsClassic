package elucent.rootsclassic.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

@SuppressWarnings("rawtypes")
public class ManaCapabilityProvider implements ICapabilityProvider, INBTSerializable, IManaCapability {

  float mana;
  float maxMana;

  ManaCapabilityProvider(boolean isNew) {
    if (isNew) {
      mana = 40;
      maxMana = 40;
    }
  }

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    return RootsCapabilityManager.manaCapability != null && capability == RootsCapabilityManager.manaCapability;
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    if (capability == RootsCapabilityManager.manaCapability) {
      return RootsCapabilityManager.manaCapability.cast(this);
    }
    return null;
  }

  @Override
  public float getMana() {
    return mana;
  }

  @Override
  public float getMaxMana() {
    return maxMana;
  }

  @Override
  public void setMana(float mana) {
    this.mana = mana;
    if (mana < 0) {
      this.mana = 0;
    }
    if (mana > getMaxMana()) {
      this.mana = getMaxMana();
    }
  }

  @Override
  public void setMaxMana(float maxMana) {
    this.maxMana = maxMana;
  }

  @Override
  public NBTBase serializeNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setFloat(ManaCapabilityStorage.NBT_MAX_MANA, getMaxMana());
    tag.setFloat(ManaCapabilityStorage.NBT_MANA, getMana());
    return tag;
  }

  @Override
  public void deserializeNBT(NBTBase nbt) {
    if (nbt instanceof NBTTagCompound) {
      NBTTagCompound tag = (NBTTagCompound) nbt;
      //System.out.println("Loading NBT! Mana=" + tag.getFloat("mana") + "/" + tag.getFloat("maxMana"));
      if (tag.hasKey(ManaCapabilityStorage.NBT_MAX_MANA)) {
        setMaxMana(tag.getFloat(ManaCapabilityStorage.NBT_MAX_MANA));
      }
      if (tag.hasKey(ManaCapabilityStorage.NBT_MANA)) {
        setMana(tag.getFloat(ManaCapabilityStorage.NBT_MANA));
      }
    }
  }
}
