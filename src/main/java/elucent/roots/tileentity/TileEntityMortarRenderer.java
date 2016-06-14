package elucent.roots.tileentity;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMortarRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		if (te instanceof TileEntityMortar){
			TileEntityMortar tem = (TileEntityMortar)te;
			ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
			
			for (int i = 0; i < tem.inventory.size(); i ++){
				renderItems.add(tem.inventory.get(i));
			}
			
			for (int i = 0; i < renderItems.size(); i ++){
				GL11.glPushMatrix();
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld,x,y,z,renderItems.get(i));
				item.hoverStart = 0;
				Random random = new Random();
				random.setSeed(item.getEntityItem().hashCode());
				GL11.glTranslated(x, y, z);
				GL11.glTranslated(0.475+random.nextFloat()/20.0, 0.05+random.nextFloat()/20.0, 0.475+random.nextFloat()/20.0);
				GL11.glScaled(0.65,0.65,0.65);
				GL11.glRotated(random.nextInt(360),0,1,0);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
				GL11.glPopMatrix();
			}
		}
	}
	
}
