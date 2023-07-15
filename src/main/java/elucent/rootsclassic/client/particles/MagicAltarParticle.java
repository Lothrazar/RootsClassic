package elucent.rootsclassic.client.particles;

import com.lothrazar.library.render.type.ParticleRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class MagicAltarParticle extends TextureSheetParticle {

  public double colorR = 0;
  public double colorG = 0;
  public double colorB = 0;

  public MagicAltarParticle(ClientLevel levelAccessor, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, SpriteSet sprite) {
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
    this.lifetime = 8;
    this.xd = vx;
    this.yd = vy;
    this.zd = vz;
    this.quadSize = 0.05f;
    this.pickSprite(sprite);
  }

  @Override
  public void tick() {
    super.tick();
    this.xd *= 0.9;
    this.yd += 0.01;
    this.zd *= 0.9;
    if (random.nextInt(4) >= 2 && this.age > 0) {
      this.age--;
    }
    float lifeCoeff = ((float) this.lifetime - (float) this.age) / this.lifetime;
    this.rCol = Math.min(1.0f, (float) colorR * (1.5f - lifeCoeff) + lifeCoeff);
    this.gCol = Math.min(1.0f, (float) colorG * (1.5f - lifeCoeff) + lifeCoeff);
    this.bCol = Math.min(1.0f, (float) colorB * (1.5f - lifeCoeff) + lifeCoeff);
    this.alpha = lifeCoeff;
    this.quadSize = 0.1F * lifeCoeff;
    if (lifeCoeff > 0.5) {
      this.quadSize = 0.1F * (0.5f + 4.0f * (1.0f - lifeCoeff));
    }
    if (lifeCoeff <= 0.5) {
      this.quadSize = 0.1F * (5.0f * (lifeCoeff));
    }
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
