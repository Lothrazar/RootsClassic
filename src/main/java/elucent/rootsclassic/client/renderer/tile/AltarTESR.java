package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.rootsclassic.block.altar.AltarTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;

public class AltarTESR extends TileEntityRenderer<AltarTile> {

	public AltarTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(AltarTile altarTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BlockPos pos = altarTile.getPos();
		ArrayList<ItemStack> renderItems = new ArrayList<>();
		for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
			renderItems.add(altarTile.inventory.getStackInSlot(i));
		}
		for (int i = 0; i < altarTile.inventory.getSlots(); i++) {
			matrixStackIn.push();
			ItemEntity item = new ItemEntity(Minecraft.getInstance().world, pos.getX(), pos.getY(), pos.getZ(), renderItems.get(i));
			item.hoverStart = 0;
			double shifted = altarTile.getTicker() + i * (360.0 / renderItems.size());
			matrixStackIn.translate(0.5, 1.0 + 0.1 * Math.sin(Math.toRadians((shifted * 4.0))), 0.5);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)shifted));
			matrixStackIn.translate(-0.5, 0, 0);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)shifted));

			Minecraft.getInstance().getRenderManager().renderEntityStatic(item, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
			matrixStackIn.pop();
		}
	}
}
