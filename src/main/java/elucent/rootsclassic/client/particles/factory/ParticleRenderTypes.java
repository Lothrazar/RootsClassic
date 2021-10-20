package elucent.rootsclassic.client.particles.factory;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;

public class ParticleRenderTypes {
  public static final ParticleRenderType MAGIC_RENDER = new ParticleRenderType() {

    @Override
    public void begin(BufferBuilder buffer, TextureManager textureManager) {
      RenderSystem.enableBlend();
      RenderSystem.enableCull();
      RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
      RenderSystem.depthMask(false);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public void end(Tesselator tessellator) {
      tessellator.end();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(true);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA.value, DestFactor.ONE_MINUS_SRC_ALPHA.value);
      RenderSystem.disableCull();
    }

    @Override
    public String toString() {
      return "rootsclassic:magic";
    }
  };
}
