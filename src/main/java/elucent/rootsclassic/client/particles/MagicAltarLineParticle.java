package elucent.rootsclassic.client.particles;

import com.lothrazar.library.render.type.ParticleRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class MagicAltarLineParticle extends TextureSheetParticle {

  public double colorR = 0;
  public double colorG = 0;
  public double colorB = 0;

  public MagicAltarLineParticle(ClientLevel levelAccessor, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, SpriteSet sprite) {
    super(levelAccessor, x, y, z, 0, 0, 0);
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
    this.lifetime = 16;
    this.gravity = 0.0f;
    this.quadSize = 0.05f;
    this.xd = (vx - x) / this.lifetime;
    this.yd = (vy - y) / this.lifetime;
    this.zd = (vz - z) / this.lifetime;
    this.alpha = 0.0f;
    this.pickSprite(sprite);
  }

  @Override
  public void tick() {
    super.tick();
    float lifeCoeff = ((float) this.lifetime - (float) this.age) / this.lifetime;
    this.rCol = Math.min(1.0f, (float) colorR * (1.5f - lifeCoeff) + lifeCoeff);
    this.gCol = Math.min(1.0f, (float) colorG * (1.5f - lifeCoeff) + lifeCoeff);
    this.bCol = Math.min(1.0f, (float) colorB * (1.5f - lifeCoeff) + lifeCoeff);
    this.alpha = 1.0f - lifeCoeff;
    this.quadSize = 0.1F * (0.5f + 2.0f * (1.0f - lifeCoeff));
  }

  @Override
  public ParticleRenderType getRenderType() {
    return ParticleRenderTypes.MAGIC_RENDER;
  }

  @Override
  public boolean isAlive() {
    return this.age < this.lifetime;
  }
}
