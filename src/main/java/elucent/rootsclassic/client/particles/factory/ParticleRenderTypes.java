package elucent.rootsclassic.client.particles.factory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.jetbrains.annotations.Nullable;

public class ParticleRenderTypes {
	public static final ParticleRenderType MAGIC_RENDER = new ParticleRenderType() {

		@Nullable
		@Override
		public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
			RenderSystem.enableBlend();
			RenderSystem.enableCull();
			RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
			RenderSystem.depthMask(false);
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
		}

		@Override
		public String toString() {
			return "rootsclassic:magic";
		}
	};
}
