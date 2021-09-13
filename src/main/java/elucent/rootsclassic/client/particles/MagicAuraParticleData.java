package elucent.rootsclassic.client.particles;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;
import elucent.rootsclassic.registry.ParticleRegistry;

public class MagicAuraParticleData implements ParticleProvider<MagicParticleTypeData> {

  private final SpriteSet spriteSet;

  public MagicAuraParticleData(SpriteSet sprite) {
    this.spriteSet = sprite;
  }

  @Override
  public Particle createParticle(MagicParticleTypeData data, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
    return new MagicAuraParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, data.color.getRed(), data.color.getGreen(), data.color.getBlue(), this.spriteSet);
  }

  public static ParticleOptions createData(ParticleColor color) {
    return new MagicParticleTypeData(ParticleRegistry.MAGIC_AURA_TYPE.get(), color);
  }

  public static ParticleOptions createData(double r, double g, double b) {
    return new MagicParticleTypeData(ParticleRegistry.MAGIC_AURA_TYPE.get(), new ParticleColor(r, g, b));
  }
}
