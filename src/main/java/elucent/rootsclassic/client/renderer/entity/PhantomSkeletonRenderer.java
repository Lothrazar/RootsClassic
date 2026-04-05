package elucent.rootsclassic.client.renderer.entity;

import elucent.rootsclassic.Const;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class PhantomSkeletonRenderer extends SkeletonRenderer {

  private static final Identifier TEXTURE = Const.modLoc("textures/entity/skeleton_ghost.png");

  public PhantomSkeletonRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public Identifier getTextureLocation(SkeletonRenderState state) {
    return TEXTURE;
  }

  @Override
  protected @Nullable RenderType getRenderType(SkeletonRenderState state, boolean isBodyVisible, boolean forceTransparent, boolean appearGlowing) {
    Identifier texture = this.getTextureLocation(state);
    return RenderTypes.entityTranslucentCullItemTarget(texture);
  }
}
