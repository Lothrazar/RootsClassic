package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.rootsclassic.block.brazier.BrazierTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

public class BrazierTESR extends TileEntityRenderer<BrazierTile> {

  public BrazierTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
    super(rendererDispatcherIn);
  }

  @Override
  public void render(BrazierTile brazierTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
    if (!brazierTile.getHeldItem().isEmpty()) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.5, 0.5, 0.5);
      matrixStackIn.scale(0.5F, 0.5F, 0.5F);
      matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(brazierTile.getTicker()));
      Minecraft.getInstance().getItemRenderer().renderStatic(brazierTile.getHeldItem(), TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
      matrixStackIn.popPose();
    }
  }
}
