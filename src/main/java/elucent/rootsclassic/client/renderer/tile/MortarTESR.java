package elucent.rootsclassic.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.rootsclassic.block.mortar.MortarTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class MortarTESR extends TileEntityRenderer<MortarTile> {
	public MortarTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(MortarTile mortarTile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		final ItemStackHandler inventory = mortarTile.inventory;
		BlockPos pos = mortarTile.getPos();
		for(int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			matrixStackIn.push();
			final ItemEntity item = new ItemEntity(Minecraft.getInstance().world, pos.getX(), pos.getY(), pos.getZ(), stack);
			item.hoverStart = 0;
			Random random = mortarTile.getWorld().rand;
			random.setSeed(item.getItem().hashCode());
//			GL11.glTranslated(x, y, z);
			matrixStackIn.translate(0.475 + random.nextFloat() / 20.0, 0.05 + random.nextFloat() / 20.0, 0.475 + random.nextFloat() / 20.0);
			matrixStackIn.scale(0.65F, 0.65F, 0.65F);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(random.nextInt(360)));
			Minecraft.getInstance().getRenderManager().renderEntityStatic(item, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
			matrixStackIn.pop();
		}
	}
}
