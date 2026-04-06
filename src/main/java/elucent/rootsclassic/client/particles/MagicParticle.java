package elucent.rootsclassic.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;

public class MagicParticle extends SingleQuadParticle {

  public float colorR = 0;
  public float colorG = 0;
  public float colorB = 0;

  public MagicParticle(ClientLevel levelAccessor, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, SpriteSet sprite) {
    super(levelAccessor, x, y, z, 0, 0, 0, sprite.get(0, 1));
    this.colorR = r;
    this.colorG = g;
    this.colorB = b;
    if (this.colorR > 1.0) {
      this.colorR = this.colorR / 255;
    }
    if (this.colorG > 1.0) {
      this.colorG = this.colorG / 255;
    }
    if (this.colorB > 1.0) {
      this.colorB = this.colorB / 255;
    }
    this.setColor(1, 1, 1);
    this.lifetime = 20;
    this.xd = vx;
    this.yd = vy;
    this.zd = vz;
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
    float brightness = 0.5f + 0.5f * lifeCoeff;
    this.rCol = Math.min(1.0f, colorR * brightness);
    this.gCol = Math.min(1.0f, colorG * brightness);
    this.bCol = Math.min(1.0f, colorB * brightness);

    this.alpha = lifeCoeff;
    this.quadSize = 0.1F * lifeCoeff;
  }

  @Override
  protected SingleQuadParticle.Layer getLayer() {
    return SingleQuadParticle.Layer.TRANSLUCENT;
  }

//  @Override
//  public ParticleRenderType getRenderType() {
//    return ParticleRenderTypes.MAGIC_RENDER;
//  }

  @Override
  public boolean isAlive() {
    return this.age < this.lifetime;
  }
}
