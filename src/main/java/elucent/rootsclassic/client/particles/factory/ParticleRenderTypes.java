package elucent.rootsclassic.client.particles.factory;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class ParticleRenderTypes {
	public static final IParticleRenderType MAGIC_RENDER = new IParticleRenderType() {
		@Override
		public void beginRender(BufferBuilder buffer, TextureManager textureManager) {
			RenderSystem.disableAlphaTest();

			RenderSystem.enableBlend();
			RenderSystem.alphaFunc(516, 0.3f);
			RenderSystem.enableCull();
			textureManager.bindTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);
			RenderSystem.depthMask(false);
			RenderSystem.blendFunc(SourceFactor.SRC_ALPHA.param, DestFactor.ONE_MINUS_SRC_ALPHA.param);

			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		}

		@Override
		public void finishRender(Tessellator tessellator) {
			tessellator.draw();
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();

			RenderSystem.depthMask(true);
			RenderSystem.blendFunc(SourceFactor.SRC_ALPHA.param, DestFactor.ONE_MINUS_SRC_ALPHA.param);
			RenderSystem.disableCull();

			RenderSystem.alphaFunc(516, 0.1F);
		}

		@Override
		public String toString() {
			return "rootsclassic:magic";
		}
	};
}
