package elucent.roots.tileentity;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAltarRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		if (te instanceof TileEntityAltar){
			TileEntityAltar tem = (TileEntityAltar)te;
			ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
			
			for (int i = 0; i < tem.inventory.size(); i ++){
				renderItems.add(tem.inventory.get(i));
			}
			
			for (int i = 0; i < renderItems.size(); i ++){
				GL11.glPushMatrix();
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld,x,y,z,renderItems.get(i));
				item.hoverStart = 0;
				double shifted = tem.ticker + i * (360.0/renderItems.size());
				Random random = new Random();
				random.setSeed(item.getEntityItem().hashCode());
				GL11.glTranslated(x+0.5, y+1.0+0.1*Math.sin(Math.toRadians((shifted*4.0))), z+0.5);
				GL11.glRotated(shifted, 0, 1, 0);
				GL11.glTranslated(-0.5,0,0);
				GL11.glRotated(shifted, 0, 1, 0);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
				GL11.glPopMatrix();
			}
		}
	}
}
