package elucent.rootsclassic.entity.skeleton;


import org.lwjgl.opengl.GL11;
import elucent.rootsclassic.Const;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;


public class RenderFrozenKnight extends RenderSkeleton {

  private static final ResourceLocation texture = new ResourceLocation(Const.MODID, "textures/entity/skeleton_ghost.png");
  public RenderFrozenKnight(RenderManager rm) {
    super(rm);
  }
  @Override
  protected ResourceLocation getEntityTexture(AbstractSkeleton entity) {
    return texture;
  }
  @Override
  protected void preRenderCallback(AbstractSkeleton entity, float partialTickTime) {
    //allow transparency in textures to be rendered
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    GL11.glEnable(GL11.GL_BLEND);
  }
  @Override
  public void doRender(AbstractSkeleton entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);

  }
  public static class Factory implements IRenderFactory<EntitySkeleton> {
    @Override
    public Render<? super EntitySkeleton> createRenderFor(RenderManager manager) {
      return new RenderFrozenKnight(manager);
    }
  }
}
