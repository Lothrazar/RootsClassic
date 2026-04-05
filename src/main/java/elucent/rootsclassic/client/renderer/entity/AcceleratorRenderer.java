package elucent.rootsclassic.client.renderer.entity;

import elucent.rootsclassic.client.renderer.entity.state.AcceleratorRenderState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class AcceleratorRenderer<T extends Entity> extends EntityRenderer<T, AcceleratorRenderState> {

  public AcceleratorRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
    return false;
  }

  @Override
  public AcceleratorRenderState createRenderState() {
    return new AcceleratorRenderState();
  }
}
