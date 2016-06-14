package elucent.roots.entity.fx;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleMagicAura extends Particle {

	Random random = new Random();
	public double colorR = 0;
	public double colorG = 0;
	public double colorB = 0;
	public int lifetime = 4;
	public ResourceLocation texture = new ResourceLocation("roots:entity/magicParticle");
	public ParticleMagicAura(World worldIn, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
		super(worldIn, x,y,z,0,0,0);
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
		if (this.colorR > 1.0){
			this.colorR = this.colorR/255.0;
		}
		if (this.colorG > 1.0){
			this.colorG = this.colorG/255.0;
		}
		if (this.colorB > 1.0){
			this.colorB = this.colorB/255.0;
		}
		this.setRBGColorF(1, 1, 1);
		this.particleMaxAge = 8;
		this.motionX = vx;
		this.motionY = vy;
		this.motionZ = vz;
		this.particleScale = 0.5f;
	    TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
		this.setParticleTexture(sprite);
	}
	
	@Override
	public boolean isTransparent(){
		return true;
	}
	
	@Override
	public int getFXLayer(){
		return 1;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		this.motionX *= 0.9;
		this.motionY += 0.003;
		this.motionZ *= 0.9;
		if (random.nextInt(4) >= 2 && this.particleAge > 0){
			this.particleAge --;
		}
		float lifeCoeff = ((float)this.particleMaxAge-(float)this.particleAge)/(float)this.particleMaxAge;
		this.particleRed = Math.min(1.0f, (float)colorR*(1.5f-lifeCoeff)+lifeCoeff);
		this.particleGreen = Math.min(1.0f, (float)colorG*(1.5f-lifeCoeff)+lifeCoeff);
		this.particleBlue = Math.min(1.0f, (float)colorB*(1.5f-lifeCoeff)+lifeCoeff);
		this.particleAlpha = lifeCoeff;
		this.particleScale = lifeCoeff;
		if (lifeCoeff > 0.5){
			this.particleScale = 0.5f+4.0f*(1.0f-lifeCoeff);
		}
		if (lifeCoeff <= 0.5){
			this.particleScale = 5.0f*(lifeCoeff);
		}
	}
}
