package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import elucent.rootsclassic.block.imbuer.ImbuerTile;

public class ImbuerTESR extends TileEntityRenderer<ImbuerTile> {

	public ImbuerTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(ImbuerTile imbuerTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		final ItemStack stickStack = imbuerTile.getStick();
		if (!stickStack.isEmpty()) {
			matrixStackIn.push();

			matrixStackIn.translate(0.5, 0.3125, 0.5);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(imbuerTile.spin));
			Minecraft.getInstance().getItemRenderer().renderItem(stickStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);

			matrixStackIn.pop();
		}
		final ItemStack dustStack = imbuerTile.getSpellPowder();
		if (!dustStack.isEmpty()) {
			matrixStackIn.push();

			matrixStackIn.translate(0.5, 0.125, 0.0);
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90));
			Minecraft.getInstance().getItemRenderer().renderItem(dustStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);

			matrixStackIn.pop();
		}
	}
}
