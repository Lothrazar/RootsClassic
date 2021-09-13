package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Vector3f;
import elucent.rootsclassic.block.altar.AltarTile;

public class AltarTESR implements BlockEntityRenderer<AltarTile> {
//
//  public AltarTESR(BlockEntityRenderDispatcher rendererDispatcherIn) {
//    super(rendererDispatcherIn);
//  }

  @Override
  public void render(AltarTile altarTile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    ArrayList<ItemStack> renderItems = new ArrayList<>();
    for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
      renderItems.add(altarTile.inventory.getStackInSlot(i));
    }
    for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
      matrixStackIn.pushPose();
      double shifted = altarTile.getTicker() + i * (360.0 / renderItems.size());
      matrixStackIn.translate(0.5, 1.0 + 0.1 * Math.sin(Math.toRadians((shifted * 4.0))), 0.5);
      matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((float) shifted));
      matrixStackIn.translate(-0.5, 0, 0);
      matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((float) shifted));
      Minecraft.getInstance().getItemRenderer().renderStatic(renderItems.get(i), TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn,0);
      matrixStackIn.popPose();
    }
  }
}
