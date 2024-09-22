package elucent.rootsclassic.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import elucent.rootsclassic.Const;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class PhantomSkeletonRenderer extends SkeletonRenderer {

  private static final ResourceLocation TEXTURE = Const.modLoc("textures/entity/skeleton_ghost.png");

  public PhantomSkeletonRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
    return TEXTURE;
  }

  @Override
  protected void scale(LivingEntity livingEntity, PoseStack poseStack, float partialTickTime) {
	  super.scale(livingEntity, poseStack, partialTickTime);
	  RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	  RenderSystem.enableBlend();
  }
}
