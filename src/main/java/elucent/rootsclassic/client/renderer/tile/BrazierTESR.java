package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.rootsclassic.block.brazier.BrazierTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class BrazierTESR extends TileEntityRenderer<BrazierTile> {

	public BrazierTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(BrazierTile brazierTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (!brazierTile.getHeldItem().isEmpty()) {
			BlockPos pos = brazierTile.getPos();
			matrixStackIn.push();
			ItemEntity item = new ItemEntity(Minecraft.getInstance().world, pos.getX(), pos.getY(), pos.getZ(), brazierTile.getHeldItem());
			item.hoverStart = 0;
			GL11.glTranslated(0.5, 0.1, 0.5);
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glRotated(brazierTile.getTicker(), 0, 1, 0);
			Minecraft.getInstance().getRenderManager().renderEntityStatic(item, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
			matrixStackIn.pop();
		}
	}
}
