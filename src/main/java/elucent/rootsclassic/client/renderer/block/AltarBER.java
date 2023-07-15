package elucent.rootsclassic.client.renderer.block;

import java.util.ArrayList;
import java.util.List;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import elucent.rootsclassic.block.altar.AltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AltarBER implements BlockEntityRenderer<AltarBlockEntity> {

  public AltarBER(BlockEntityRendererProvider.Context context) {}

  @Override
  public void render(AltarBlockEntity altarTile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    List<ItemStack> renderItems = new ArrayList<>();
    for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
      renderItems.add(altarTile.inventory.getStackInSlot(i));
    }
    for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
      poseStack.pushPose();
      double shifted = altarTile.getTicker() + i * (360.0 / renderItems.size());
      poseStack.translate(0.5, 1.0 + 0.1 * Math.sin(Math.toRadians((shifted * 4.0))), 0.5);
      poseStack.mulPose(Axis.YP.rotationDegrees((float) shifted));
      poseStack.translate(-0.5, 0, 0);
      poseStack.mulPose(Axis.YP.rotationDegrees((float) shifted));
      Minecraft.getInstance().getItemRenderer().renderStatic(renderItems.get(i), ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, altarTile.getLevel(), 0);
      poseStack.popPose();
    }
  }
}
