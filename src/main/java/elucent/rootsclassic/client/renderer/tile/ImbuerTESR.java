package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Vector3f;
import elucent.rootsclassic.block.imbuer.ImbuerTile;

public class ImbuerTESR implements BlockEntityRenderer<ImbuerTile> {

//  public ImbuerTESR(BlockEntityRenderDispatcher rendererDispatcherIn) {
//    super(rendererDispatcherIn);
//  }

  @Override
  public void render(ImbuerTile imbuerTile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    final ItemStack stickStack = imbuerTile.getStick();
    if (!stickStack.isEmpty()) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.5, 0.3125, 0.5);
      matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(imbuerTile.spin));
      Minecraft.getInstance().getItemRenderer().renderStatic(stickStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn,0);
      matrixStackIn.popPose();
    }
    final ItemStack dustStack = imbuerTile.getSpellPowder();
    if (!dustStack.isEmpty()) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.5, 0.125, 0.0);
      matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90));
      Minecraft.getInstance().getItemRenderer().renderStatic(dustStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn,0);
      matrixStackIn.popPose();
    }
  }
}
