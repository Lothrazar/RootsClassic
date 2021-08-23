package elucent.rootsclassic.client.particles;

import elucent.rootsclassic.client.particles.factory.ParticleRenderTypes;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

public class MagicLineParticle extends SpriteTexturedParticle {
	public double colorR = 0;
	public double colorG = 0;
	public double colorB = 0;

	public MagicLineParticle(ClientWorld worldIn, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, IAnimatedSprite sprite) {
		super(worldIn, x, y, z, 0, 0, 0);
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
		if (this.colorR > 1.0) {
			this.colorR = this.colorR / 255.0;
		}
		if (this.colorG > 1.0) {
			this.colorG = this.colorG / 255.0;
		}
		if (this.colorB > 1.0) {
			this.colorB = this.colorB / 255.0;
		}
		this.setColor(1, 1, 1);
		this.maxAge = 8;
		this.particleGravity = 0.0f;
		this.motionX = (vx - x) / this.maxAge;
		this.motionY = (vy - y) / this.maxAge;
		this.motionZ = (vz - z) / this.maxAge;
		this.selectSpriteRandomly(sprite);
	}

	@Override
	public void tick() {
		super.tick();
		float lifeCoeff = ((float) this.maxAge - (float) this.age) / this.maxAge;
		this.particleRed = Math.min(1.0f, (float) colorR * (1.5f - lifeCoeff) + lifeCoeff);
		this.particleGreen = Math.min(1.0f, (float) colorG * (1.5f - lifeCoeff) + lifeCoeff);
		this.particleBlue = Math.min(1.0f, (float) colorB * (1.5f - lifeCoeff) + lifeCoeff);
		this.particleAlpha = lifeCoeff;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return ParticleRenderTypes.MAGIC_RENDER;
	}

	@Override
	public boolean isAlive() {
		return this.age < this.maxAge;
	}
}
