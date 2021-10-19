package elucent.rootsclassic.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;
import elucent.rootsclassic.Const;

public class PhantomSkeletonRenderer extends SkeletonRenderer {

  private static final ResourceLocation TEXTURE = new ResourceLocation(Const.MODID, "textures/entity/skeleton_ghost.png");

  public PhantomSkeletonRenderer(EntityRendererManager rendererManager) {
    super(rendererManager);
  }

  @Override
  public ResourceLocation getTextureLocation(AbstractSkeletonEntity entity) {
    return TEXTURE;
  }

  @Override
  protected void scale(AbstractSkeletonEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
    //allow transparency in textures to be rendered
    RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
    RenderSystem.enableBlend();
  }
}
