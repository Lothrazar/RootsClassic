package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.rootsclassic.block.imbuer.ImbuerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class ImbuerTESR extends TileEntityRenderer<ImbuerTile> {

	public ImbuerTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(ImbuerTile imbuerTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BlockPos pos = imbuerTile.getPos();
		final ItemStack stickStack = imbuerTile.getStick();
		if (!stickStack.isEmpty()) {
			matrixStackIn.push();

			ItemEntity item = new ItemEntity(Minecraft.getInstance().world, pos.getX(), pos.getY(), pos.getZ(), stickStack);
			item.hoverStart = 0;
			matrixStackIn.translate(0.5, 0.3125, 0.5);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(imbuerTile.spin));
			Minecraft.getInstance().getRenderManager().renderEntityStatic(item, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, combinedLightIn);

			matrixStackIn.pop();
		}
		final ItemStack dustStack = imbuerTile.getSpellPowder();
		if (!dustStack.isEmpty()) {
			matrixStackIn.push();

			ItemEntity item = new ItemEntity(Minecraft.getInstance().world, pos.getX(), pos.getY(), pos.getZ(), dustStack);
			item.hoverStart = 0;
			matrixStackIn.translate(0.5, 0.125, 0.0);
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90));
			Minecraft.getInstance().getRenderManager().renderEntityStatic(item, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, combinedLightIn);

			matrixStackIn.pop();
		}
	}
}
