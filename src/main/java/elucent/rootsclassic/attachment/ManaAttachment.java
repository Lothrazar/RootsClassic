package elucent.rootsclassic.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class ManaAttachment implements IMana, INBTSerializable<CompoundTag> {

  private float maxMana = 40;
  private float mana = 40;

  public ManaAttachment() {
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

  @Override
  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    return writeNBT(this);
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
    readNBT(this, tag);
  }

  public CompoundTag writeNBT(ManaAttachment instance) {
    CompoundTag tag = new CompoundTag();
    tag.putFloat("mana", instance.getMana());
    tag.putFloat("maxMana", instance.getMaxMana());
    return tag;
  }

  public void readNBT(ManaAttachment instance, CompoundTag tag) {
    instance.setMana(tag.getFloat("mana"));
    instance.setMaxMana(tag.getFloat("maxMana"));
  }
}
