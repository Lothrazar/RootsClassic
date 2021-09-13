package elucent.rootsclassic.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class ManaCapabilityProvider implements ICapabilityProvider {

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return RootsCapabilityManager.MANA_CAPABILITY.orEmpty(cap,  LazyOptional.of(() -> new DefaultManaCapability()));
  }



}
