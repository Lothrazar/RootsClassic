package elucent.rootsclassic.client.particles;

import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;
import elucent.rootsclassic.registry.ParticleRegistry;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;

public class MagicAuraParticleData implements IParticleFactory<MagicParticleTypeData> {
	private final IAnimatedSprite spriteSet;

	public MagicAuraParticleData(IAnimatedSprite sprite) {
		this.spriteSet = sprite;
	}

	@Override
	public Particle makeParticle(MagicParticleTypeData data, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		return new MagicAuraParticle(worldIn, x,y,z, xSpeed, ySpeed, zSpeed, data.color.getRed(), data.color.getGreen(), data.color.getBlue(), this.spriteSet);
	}

	public static IParticleData createData(ParticleColor color) {
		return new MagicParticleTypeData(ParticleRegistry.MAGIC_AURA_TYPE.get(), color);
	}

	public static IParticleData createData(double r, double g, double b) {
		return new MagicParticleTypeData(ParticleRegistry.MAGIC_AURA_TYPE.get(), new ParticleColor(r,g,b));
	}
}
