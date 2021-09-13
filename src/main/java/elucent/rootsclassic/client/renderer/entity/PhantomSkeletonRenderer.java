package elucent.rootsclassic.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.resources.ResourceLocation;
import elucent.rootsclassic.Const;

public class PhantomSkeletonRenderer extends SkeletonRenderer {

  private static final ResourceLocation TEXTURE = new ResourceLocation(Const.MODID, "textures/entity/skeleton_ghost.png");

  public PhantomSkeletonRenderer(EntityRenderDispatcher rendererManager) {
    super(rendererManager);
  }

  @Override
  public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
    return TEXTURE;
  }

  @Override
  protected void scale(AbstractSkeleton entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    //allow transparency in textures to be rendered
    RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
    RenderSystem.enableBlend();
  }
}
