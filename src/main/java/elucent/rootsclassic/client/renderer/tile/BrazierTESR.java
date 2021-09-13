package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import com.mojang.math.Vector3f;
import elucent.rootsclassic.block.brazier.BrazierTile;

public class BrazierTESR implements BlockEntityRenderer<BrazierTile> {

//  public BrazierTESR(BlockEntityRenderDispatcher rendererDispatcherIn) {
//    super(rendererDispatcherIn);
//  }

  @Override
  public void render(BrazierTile brazierTile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    if (!brazierTile.getHeldItem().isEmpty()) {
      poseStack.pushPose();
      poseStack.translate(0.5, 0.1, 0.5);
      poseStack.scale(0.5F, 0.5F, 0.5F);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(brazierTile.getTicker()));
//      Minecraft.getInstance().getItemRenderer().renderStatic();
      Minecraft.getInstance().getItemRenderer().renderStatic(brazierTile.getHeldItem(),
          TransformType.GROUND,
          combinedLightIn,
          combinedOverlayIn,
          poseStack,
          bufferIn, 0);
      poseStack.popPose();
    }
  }
}
