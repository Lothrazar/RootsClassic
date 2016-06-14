package elucent.roots.tileentity;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityBrazierRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		if (te instanceof TileEntityBrazier){
			TileEntityBrazier teb = (TileEntityBrazier)te;
			ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
			
			if (teb.heldItem != null){
				GL11.glPushMatrix();
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld,x,y,z,teb.heldItem);
				item.hoverStart = 0;
				GL11.glTranslated(x+0.5, y+0.1, z+0.5);
				GL11.glScaled(0.5,0.5,0.5);
				GL11.glRotated(teb.ticker,0,1,0);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
				GL11.glPopMatrix();
			}
		}
	}
}
