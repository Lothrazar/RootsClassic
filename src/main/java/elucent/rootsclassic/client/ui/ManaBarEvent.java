package elucent.rootsclassic.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.IManaCapability;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.item.IManaRelatedItem;

public class ManaBarEvent {

  private void drawQuad(BufferBuilder vertexbuffer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    vertexbuffer.vertex(x1 + 0.0F, y1 + 0.0F, 0).uv((minU + 0) * f, (minV + maxV) * f1).endVertex();
    vertexbuffer.vertex(x2 + 0.0F, y2 + 0.0F, 0).uv((minU + maxU) * f, (minV + maxV) * f1).endVertex();
    vertexbuffer.vertex(x3 + 0.0F, y3 + 0.0F, 0).uv((minU + maxU) * f, (minV + 0) * f1).endVertex();
    vertexbuffer.vertex(x4 + 0.0F, y4 + 0.0F, 0).uv((minU + 0) * f, (minV + 0) * f1).endVertex();
  }

  @SubscribeEvent
  public void onGameOverlayRender(RenderGameOverlayEvent.Post event) {
    if (event.getType() != ElementType.TEXT) {
      return;
    }
    PlayerEntity player = Minecraft.getInstance().player;
    boolean showBar = player.getMainHandItem().getItem() instanceof IManaRelatedItem || player.getOffhandItem().getItem() instanceof IManaRelatedItem;
    //		if (player.abilities.isCreativeMode) {
    //		  	showBar = false;
    //		}
    IManaCapability capability = player.getCapability(RootsCapabilityManager.MANA_CAPABILITY, null).orElse(null);
    if (!showBar || capability == null) {
      return;
    }
    if (capability.getMaxMana() > 0) {
      drawManaBar(event, player, capability);
    }
  }

  private void drawManaBar(RenderGameOverlayEvent.Post event, PlayerEntity player, IManaCapability capability) {
    RenderSystem.disableDepthTest();
    RenderSystem.disableCull();
    RenderSystem.pushMatrix();
    RenderSystem.enableBlend();
    Minecraft.getInstance().getTextureManager().bind(Const.manaBar);
    Tessellator tess = Tessellator.getInstance();
    BufferBuilder b = tess.getBuilder();
    int w = event.getWindow().getGuiScaledWidth();
    int h = event.getWindow().getGuiScaledHeight();
    RenderSystem.color4f(1f, 1f, 1f, 1f);
    int manaNumber = Math.round(capability.getMana());
    int maxManaNumber = Math.round(capability.getMaxMana());
    int offsetX = 0;
    b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    int manaBarOffset = RootsConfig.CLIENT.manaBarOffset.get();
    while (maxManaNumber > 0) {
      this.drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 0, 0, 9, 9);
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
        this.drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
        manaNumber -= 4;
        offsetX += 8;
      }
      else {
        if (manaNumber == 4) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
        }
        if (manaNumber == 3) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 16, 16, 9, 9);
        }
        if (manaNumber == 2) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 32, 16, 9, 9);
        }
        if (manaNumber == 1) {
          this.drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 48, 16, 9, 9);
        }
        manaNumber = 0;
      }
    }
    tess.end();
    RenderSystem.disableBlend();
    RenderSystem.popMatrix();
    RenderSystem.enableCull();
    RenderSystem.enableDepthTest();
  }

  @SubscribeEvent
  public void clientTickEnd(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      ClientInfo.ticksInGame++;
    }
  }
}
