package elucent.rootsclassic.client.particles.factory;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.ParticleRenderType;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import org.lwjgl.opengl.GL11;

public class ParticleRenderTypes {

  public static final ParticleRenderType MAGIC_RENDER = new ParticleRenderType() {

    @Override
    public void begin(BufferBuilder buffer, TextureManager textureManager) {
      RenderSystem.disableAlphaTest();
      RenderSystem.enableBlend();
      RenderSystem.alphaFunc(516, 0.3f);
      RenderSystem.enableCull();
      textureManager.bind(TextureAtlas.LOCATION_PARTICLES);
      RenderSystem.depthMask(false);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA.value, DestFactor.ONE_MINUS_SRC_ALPHA.value);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public void end(Tesselator tessellator) {
      tessellator.end();
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.depthMask(true);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA.value, DestFactor.ONE_MINUS_SRC_ALPHA.value);
      RenderSystem.disableCull();
      RenderSystem.alphaFunc(516, 0.1F);
    }

    @Override
    public String toString() {
      return "rootsclassic:magic";
    }
  };
}
