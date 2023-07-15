package elucent.rootsclassic.client.particles.factory;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class MagicParticleType extends ParticleType<MagicParticleTypeData> {

  public MagicParticleType() {
    super(false, MagicParticleTypeData.DESERIALIZER);
  }

  @Override
  public Codec<MagicParticleTypeData> codec() {
    return MagicParticleTypeData.CODEC;
  }
}
