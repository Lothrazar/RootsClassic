package elucent.rootsclassic.client.particles.factory;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class MagicParticleType extends ParticleType<MagicParticleTypeData> {

  public MagicParticleType() {
    super(false, MagicParticleTypeData.DESERIALIZER);
  }

  @Override
  public Codec<MagicParticleTypeData> func_230522_e_() {
    return MagicParticleTypeData.CODEC;
  }
}
