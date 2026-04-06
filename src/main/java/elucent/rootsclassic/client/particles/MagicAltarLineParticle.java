package elucent.rootsclassic.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;

public class MagicAltarLineParticle extends SingleQuadParticle {

  public float colorR = 0;
  public float colorG = 0;
  public float colorB = 0;

  public MagicAltarLineParticle(ClientLevel levelAccessor, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, SpriteSet sprite) {
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
    this.lifetime = 16;
    this.gravity = 0.0f;
    this.quadSize = 0.05f;
    this.xd = (vx - x) / this.lifetime;
    this.yd = (vy - y) / this.lifetime;
    this.zd = (vz - z) / this.lifetime;
    this.alpha = 0.0f;
  }

  @Override
  public void tick() {
    super.tick();
    float lifeCoeff = ((float) this.lifetime - (float) this.age) / this.lifetime;
    float brightness = 0.5f + 0.5f * lifeCoeff;
    this.rCol = Math.min(1.0f, colorR * brightness);
    this.gCol = Math.min(1.0f, colorG * brightness);
    this.bCol = Math.min(1.0f, colorB * brightness);
    this.alpha = 1.0f - lifeCoeff;
    this.quadSize = 0.1F * (0.5f + 2.0f * (1.0f - lifeCoeff));
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
