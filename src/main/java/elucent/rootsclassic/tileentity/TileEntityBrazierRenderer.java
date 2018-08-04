package elucent.rootsclassic.tileentity;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TileEntityBrazierRenderer extends TileEntitySpecialRenderer<TileEntityBrazier> {

  @Override
  public void render(TileEntityBrazier teb, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
    if (teb.getHeldItem() != null) {
      GL11.glPushMatrix();
      EntityItem item = new EntityItem(Minecraft.getMinecraft().world, x, y, z, teb.getHeldItem());
      item.hoverStart = 0;
      GL11.glTranslated(x + 0.5, y + 0.1, z + 0.5);
      GL11.glScaled(0.5, 0.5, 0.5);
      GL11.glRotated(teb.getTicker(), 0, 1, 0);
      Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
      GL11.glPopMatrix();
    }
  }
}
