package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.items.ItemStackHandler;
import elucent.rootsclassic.block.mortar.MortarTile;

public class MortarTESR extends TileEntityRenderer<MortarTile> {
	public MortarTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(MortarTile mortarTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		final ItemStackHandler inventory = mortarTile.inventory;
		for(int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(!stack.isEmpty()) {
				matrixStackIn.push();
				Random random = new Random(stack.hashCode());
				matrixStackIn.translate(0.475 + random.nextFloat() / 20.0, 0.05 + random.nextFloat() / 20.0, 0.475 + random.nextFloat() / 20.0);
				matrixStackIn.scale(0.65F, 0.65F, 0.65F);
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(random.nextInt(360)));
				Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
				matrixStackIn.pop();
			}
		}
	}
}
