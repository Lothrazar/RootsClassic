package elucent.rootsclassic.block.imbuer;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class TileEntityImbuerRenderer extends TileEntitySpecialRenderer<TileEntityImbuer> {

  @Override
  public void render(TileEntityImbuer tei, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    if (tei.stick != null) {
      GL11.glPushMatrix();
      EntityItem item = new EntityItem(Minecraft.getMinecraft().world, x, y, z, tei.stick);
      item.hoverStart = 0;
      GL11.glTranslated(x + 0.5, y + 0.3125, z + 0.5);
      GL11.glRotated(tei.spin, 0, 1.0, 0);
      Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
      GL11.glPopMatrix();
    }
    if (tei.dust != null) {
      GL11.glPushMatrix();
      EntityItem item = new EntityItem(Minecraft.getMinecraft().world, x, y, z, tei.dust);
      item.hoverStart = 0;
      GL11.glTranslated(x + 0.5, y + 0.125, z);
      GL11.glRotated(90, 1.0, 0, 0);
      Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
      GL11.glPopMatrix();
    }
  }
}
