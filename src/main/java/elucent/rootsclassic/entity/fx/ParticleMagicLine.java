package elucent.rootsclassic.entity.fx;

import java.util.Random;
import elucent.rootsclassic.Const;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleMagicLine extends Particle {

  Random random = new Random();
  public double colorR = 0;
  public double colorG = 0;
  public double colorB = 0;
  public int lifetime = 8;
  public ResourceLocation texture = Const.magicParticle;

  public ParticleMagicLine(World worldIn, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
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
    this.setRBGColorF(1, 1, 1);
    this.particleMaxAge = 8;
    this.particleGravity = 0.0f;
    this.motionX = (vx - x) / this.particleMaxAge;
    this.motionY = (vy - y) / this.particleMaxAge;
    this.motionZ = (vz - z) / this.particleMaxAge;
    TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
    this.setParticleTexture(sprite);
  }

  @Override
  public boolean shouldDisableDepth() { //was isTransparent in 1.10 
    return true;
  }

  @Override
  public int getFXLayer() {
    return 1;
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    float lifeCoeff = ((float) this.particleMaxAge - (float) this.particleAge) / this.particleMaxAge;
    this.particleRed = Math.min(1.0f, (float) colorR * (1.5f - lifeCoeff) + lifeCoeff);
    this.particleGreen = Math.min(1.0f, (float) colorG * (1.5f - lifeCoeff) + lifeCoeff);
    this.particleBlue = Math.min(1.0f, (float) colorB * (1.5f - lifeCoeff) + lifeCoeff);
    this.particleAlpha = lifeCoeff;
  }
}
