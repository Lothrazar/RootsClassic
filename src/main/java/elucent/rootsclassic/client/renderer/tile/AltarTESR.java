package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import elucent.rootsclassic.block.altar.AltarTile;

public class AltarTESR extends TileEntityRenderer<AltarTile> {

  public AltarTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
    super(rendererDispatcherIn);
  }

  @Override
  public void render(AltarTile altarTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
    ArrayList<ItemStack> renderItems = new ArrayList<>();
    for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
      renderItems.add(altarTile.inventory.getStackInSlot(i));
    }
    for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
      matrixStackIn.push();
      double shifted = altarTile.getTicker() + i * (360.0 / renderItems.size());
      matrixStackIn.translate(0.5, 1.0 + 0.1 * Math.sin(Math.toRadians((shifted * 4.0))), 0.5);
      matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float) shifted));
      matrixStackIn.translate(-0.5, 0, 0);
      matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float) shifted));
      Minecraft.getInstance().getItemRenderer().renderItem(renderItems.get(i), TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
      matrixStackIn.pop();
    }
  }
}
