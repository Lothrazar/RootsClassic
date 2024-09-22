package elucent.rootsclassic.client.particles.factory;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class MagicParticleType extends ParticleType<MagicParticleTypeData> {

  public MagicParticleType() {
    super(false);
  }

	@Override
	public MapCodec<MagicParticleTypeData> codec() {
		return MagicParticleTypeData.CODEC;
	}

	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, MagicParticleTypeData> streamCodec() {
		return MagicParticleTypeData.STREAM_CODEC;
	}
}
