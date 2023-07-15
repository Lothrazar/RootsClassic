package elucent.rootsclassic.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ManaCapability implements IManaCapability, ICapabilitySerializable<CompoundTag> {

  private float maxMana = 40;
  private float mana = 40;

  public ManaCapability() {
    this.maxMana = 40;
    this.mana = 40;
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

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return RootsCapabilityManager.MANA_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this));
  }

  @Override
  public CompoundTag serializeNBT() {
    return writeNBT(this);
  }

  @Override
  public void deserializeNBT(CompoundTag nbt) {
    readNBT(this, nbt);
  }

  public CompoundTag writeNBT(ManaCapability instance) {
    CompoundTag tag = new CompoundTag();
    tag.putFloat("mana", instance.getMana());
    tag.putFloat("maxMana", instance.getMaxMana());
    return tag;
  }

  public void readNBT(ManaCapability instance, CompoundTag tag) {
    instance.setMana(tag.getFloat("mana"));
    instance.setMaxMana(tag.getFloat("maxMana"));
  }
}
