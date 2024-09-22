package elucent.rootsclassic.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.attachment.IMana;
import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.item.IManaRelatedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

public class ManaBarEvent {

  private void drawQuad(BufferBuilder vertexConsumer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    vertexConsumer.addVertex(x1 + 0.0F, y1 + 0.0F, 0).setUv((minU) * f, (minV + maxV) * f1);
    vertexConsumer.addVertex(x2 + 0.0F, y2 + 0.0F, 0).setUv((minU + maxU) * f, (minV + maxV) * f1);
    vertexConsumer.addVertex(x3 + 0.0F, y3 + 0.0F, 0).setUv((minU + maxU) * f, (minV) * f1);
    vertexConsumer.addVertex(x4 + 0.0F, y4 + 0.0F, 0).setUv((minU) * f, (minV) * f1);
  }

  @SubscribeEvent
  public void onGameOverlayRender(RenderGuiLayerEvent.Post event) {
    Player player = Minecraft.getInstance().player;
    boolean showBar = player.getMainHandItem().getItem() instanceof IManaRelatedItem || player.getOffhandItem().getItem() instanceof IManaRelatedItem;
    //it used to hide mana bar increative but i hated that, i assume ppl did, felt broken.
    //TODO could be a config to bring back (hide mana in creative) 
    //		if (player.abilities.isCreativeMode) {
    //		  	showBar = false;
    //		}
    ManaAttachment capability = player.getData(RootsAttachments.MANA);
    if (showBar && capability != null && capability.getMaxMana() > 0) {
      drawManaBar(event, player, capability);
    }
  }

  private void drawManaBar(RenderGuiLayerEvent.Post event, Player player, IMana capability) {
    PoseStack poseStack = event.getGuiGraphics().pose();
    RenderSystem.disableDepthTest();
    RenderSystem.disableCull();
    poseStack.pushPose();
    RenderSystem.enableBlend();
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, Const.MANABAR);
    Tesselator tess = Tesselator.getInstance();
		final Minecraft mc = Minecraft.getInstance();
    int w = mc.getWindow().getGuiScaledWidth();
    int h = mc.getWindow().getGuiScaledHeight();
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    int manaNumber = Math.round(capability.getMana());
    int maxManaNumber = Math.round(capability.getMaxMana());
    int offsetX = 0;
    BufferBuilder b = tess.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
    int manaBarOffset = RootsConfig.CLIENT.manaBarOffset.get();
    while (maxManaNumber > 0) {
      this.drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 0, 0, 9, 9);
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
        this.drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
        manaNumber -= 4;
        offsetX += 8;
      }
      else {
        if (manaNumber == 4) {
          this.drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
        }
        if (manaNumber == 3) {
          this.drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 16, 16, 9, 9);
        }
        if (manaNumber == 2) {
          this.drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 32, 16, 9, 9);
        }
        if (manaNumber == 1) {
          this.drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 48, 16, 9, 9);
        }
        manaNumber = 0;
      }
    }
	  BufferUploader.drawWithShader(b.buildOrThrow());
    RenderSystem.disableBlend();
    poseStack.popPose();
    RenderSystem.enableCull();
    RenderSystem.enableDepthTest();
  }

  @SubscribeEvent
  public void clientTickEnd(ClientTickEvent.Post event) {
	  ClientInfo.ticksInGame++;
  }
}
