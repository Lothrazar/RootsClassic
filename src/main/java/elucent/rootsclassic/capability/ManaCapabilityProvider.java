package elucent.rootsclassic.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaCapabilityProvider implements ICapabilitySerializable<INBT> {

	private LazyOptional<IManaCapability> instance;
	private IManaCapability manaCapability;

	ManaCapabilityProvider() {
		this.manaCapability = RootsCapabilityManager.MANA_CAPABILITY.getDefaultInstance();
		this.instance = LazyOptional.of(() -> manaCapability);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return RootsCapabilityManager.MANA_CAPABILITY.orEmpty(cap, instance);
	}

	@Override
	public INBT serializeNBT() {
		return RootsCapabilityManager.MANA_CAPABILITY.writeNBT(manaCapability, null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		RootsCapabilityManager.MANA_CAPABILITY.readNBT(manaCapability, null, nbt);
	}
}
