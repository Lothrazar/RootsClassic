package elucent.rootsclassic.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
public class DefaultManaCapability implements IManaCapability, INBTSerializable<CompoundTag> {

  public float maxMana = 40;
  public float mana = 40;

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

  public static final String NBT_MAX_MANA = "maxMana";
  public static final String NBT_MANA = "mana";

  @Override public CompoundTag serializeNBT() {
    CompoundTag tag = new CompoundTag();
    tag.putFloat(NBT_MAX_MANA, getMaxMana());
    tag.putFloat(NBT_MANA, getMana());
    return tag;
  }

  @Override public void deserializeNBT(CompoundTag nbt) {
    if (nbt.contains(NBT_MANA)) {
      setMana(nbt.getFloat(NBT_MANA));
    }
    if (nbt.contains(NBT_MAX_MANA)) {
      setMaxMana(nbt.getFloat(NBT_MAX_MANA));
    }
  }
}
