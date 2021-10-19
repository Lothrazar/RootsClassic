package elucent.rootsclassic.client.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import elucent.rootsclassic.client.particles.factory.ParticleRenderTypes;

public class MagicParticle extends SpriteTexturedParticle {

  public double colorR = 0;
  public double colorG = 0;
  public double colorB = 0;

  public MagicParticle(ClientWorld worldIn, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, IAnimatedSprite sprite) {
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
    this.lifetime = 20;
    this.xd = vx;
    this.yd = vy;
    this.zd = vz;
    this.pickSprite(sprite);
  }

  @Override
  public void tick() {
    super.tick();
    this.xd *= 0.65;
    this.yd *= 0.65;
    this.zd *= 0.65;
    if (random.nextInt(4) == 0) {
      this.age--;
    }
    float lifeCoeff = ((float) this.lifetime - (float) this.age) / this.lifetime;
    this.rCol = Math.min(1.0f, (float) colorR * (1.5f - lifeCoeff) + lifeCoeff);
    this.gCol = Math.min(1.0f, (float) colorG * (1.5f - lifeCoeff) + lifeCoeff);
    this.bCol = Math.min(1.0f, (float) colorB * (1.5f - lifeCoeff) + lifeCoeff);
    this.alpha = lifeCoeff;
    this.quadSize = 0.1F * lifeCoeff;
  }

  @Override
  public IParticleRenderType getRenderType() {
    return ParticleRenderTypes.MAGIC_RENDER;
  }

  @Override
  public boolean isAlive() {
    return this.age < this.lifetime;
  }
}
