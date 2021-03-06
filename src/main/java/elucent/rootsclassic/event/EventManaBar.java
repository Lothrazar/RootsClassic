package elucent.rootsclassic.event;

import org.lwjgl.opengl.GL11;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.config.ConfigManager;
import elucent.rootsclassic.item.IManaRelatedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventManaBar {

  @SideOnly(Side.CLIENT)
  private void drawQuad(BufferBuilder vertexbuffer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    vertexbuffer.pos(x1 + 0.0F, y1 + 0.0F, 0).tex((minU + 0) * f, (minV + maxV) * f1).endVertex();
    vertexbuffer.pos(x2 + 0.0F, y2 + 0.0F, 0).tex((minU + maxU) * f, (minV + maxV) * f1).endVertex();
    vertexbuffer.pos(x3 + 0.0F, y3 + 0.0F, 0).tex((minU + maxU) * f, (minV + 0) * f1).endVertex();
    vertexbuffer.pos(x4 + 0.0F, y4 + 0.0F, 0).tex((minU + 0) * f, (minV + 0) * f1).endVertex();
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onGameOverlayRender(RenderGameOverlayEvent.Post event) {
    if (event.getType() != ElementType.TEXT) {
      return;
    }
    EntityPlayer player = Minecraft.getMinecraft().player;
    boolean showBar = false;
    if (player.getHeldItemMainhand().getItem() instanceof IManaRelatedItem) {
      showBar = true;
    }
    if (player.getHeldItemOffhand().getItem() instanceof IManaRelatedItem) {
      showBar = true;
    }
    //    if (player.capabilities.isCreativeMode) {
    //      showBar = false;
    //    }
    if (!showBar || !player.hasCapability(RootsCapabilityManager.manaCapability, null)) {
      return;
    }
    if (player.getCapability(RootsCapabilityManager.manaCapability, null).getMaxMana() > 0) {
      drawManaBar(event, player);
    }
  }

  private void drawManaBar(RenderGameOverlayEvent.Post event, EntityPlayer player) {
    GlStateManager.disableDepth();
    GlStateManager.disableCull();
    GlStateManager.pushMatrix();
    Minecraft.getMinecraft().getTextureManager().bindTexture(Const.manaBar);
    Tessellator tess = Tessellator.getInstance();
    BufferBuilder b = tess.getBuffer();
    int w = event.getResolution().getScaledWidth();
    int h = event.getResolution().getScaledHeight();
    GlStateManager.color(1f, 1f, 1f, 1f);
    int manaNumber = Math.round(player.getCapability(RootsCapabilityManager.manaCapability, null).getMana());
    int maxManaNumber = Math.round(player.getCapability(RootsCapabilityManager.manaCapability, null).getMaxMana());
    int offsetX = 0;
    b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    while (maxManaNumber > 0) {
      this.drawQuad(b, w / 2 + 10 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - ConfigManager.manaBarOffset, w / 2 + 10 + offsetX, h - ConfigManager.manaBarOffset, 0, 0, 9, 9);
      if (maxManaNumber > 4) {
        maxManaNumber -= 4;
        offsetX += 8;
      }
      else {
        maxManaNumber = 0;
      }
    }
    offsetX = 0;
    while (manaNumber > 0) {
      if (manaNumber > 4) {
        this.drawQuad(b, w / 2 + 10 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - ConfigManager.manaBarOffset, w / 2 + 10 + offsetX, h - ConfigManager.manaBarOffset, 0, 16, 9, 9);
        manaNumber -= 4;
        offsetX += 8;
      }
      else {
        if (manaNumber == 4) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - ConfigManager.manaBarOffset, w / 2 + 10 + offsetX, h - ConfigManager.manaBarOffset, 0, 16, 9, 9);
        }
        if (manaNumber == 3) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - ConfigManager.manaBarOffset, w / 2 + 10 + offsetX, h - ConfigManager.manaBarOffset, 16, 16, 9, 9);
        }
        if (manaNumber == 2) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - ConfigManager.manaBarOffset, w / 2 + 10 + offsetX, h - ConfigManager.manaBarOffset, 32, 16, 9, 9);
        }
        if (manaNumber == 1) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - (ConfigManager.manaBarOffset - 9), w / 2 + 19 + offsetX, h - ConfigManager.manaBarOffset, w / 2 + 10 + offsetX, h - ConfigManager.manaBarOffset, 48, 16, 9, 9);
        }
        manaNumber = 0;
      }
    }
    tess.draw();
    GlStateManager.popMatrix();
    GlStateManager.enableCull();
    GlStateManager.enableDepth();
  }
}
