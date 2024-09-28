package elucent.rootsclassic.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.IManaCapability;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.item.IManaRelatedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ManaBarEvent {

  public static void onRegisterLayer(RegisterGuiOverlaysEvent event) {
    event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), Const.MANA_OVERLAY.toLanguageKey(),
      ManaBarEvent::onDrawManaBar
    );
  }

  private static void onDrawManaBar(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick,
                                    int screenWidth, int screenHeight) {
    Minecraft mc = Minecraft.getInstance();
    Player player = mc.player;
    if (player == null) return;
    boolean showBar = player.getMainHandItem().getItem() instanceof IManaRelatedItem ||
      player.getOffhandItem().getItem() instanceof IManaRelatedItem;
    //it used to hide mana bar increative but i hated that, i assume ppl did, felt broken.
    //TODO could be a config to bring back (hide mana in creative)
    //		if (player.abilities.isCreativeMode) {
    //		  	showBar = false;
    //		}
    IManaCapability capability = player.getCapability(RootsCapabilityManager.MANA_CAPABILITY, null).orElse(null);
    if (showBar && capability != null && capability.getMaxMana() > 0) {
      int w = mc.getWindow().getGuiScaledWidth();
      int h = mc.getWindow().getGuiScaledHeight();
      drawManaBar(guiGraphics, capability, w, h);
    }
  }

  private static void drawManaBar(GuiGraphics guiGraphics, IManaCapability capability, int w, int h) {
    PoseStack poseStack = guiGraphics.pose();
    RenderSystem.disableDepthTest();
    RenderSystem.disableCull();
    poseStack.pushPose();
    RenderSystem.enableBlend();
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, Const.MANABAR);
    Tesselator tess = Tesselator.getInstance();
    BufferBuilder b = tess.getBuilder();

    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    int manaNumber = Math.round(capability.getMana());
    int maxManaNumber = Math.round(capability.getMaxMana());
    int offsetX = 0;
    b.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
    int manaBarOffset = RootsConfig.Client.manaBarOffset.get();
    while (maxManaNumber > 0) {
      drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 0, 0, 9, 9);
      if (maxManaNumber > 4) {
        maxManaNumber -= 4;
        offsetX += 8;
      } else {
        maxManaNumber = 0;
      }
    }
    offsetX = 0;
    while (manaNumber > 0) {
      if (manaNumber > 4) {
        drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
        manaNumber -= 4;
        offsetX += 8;
      } else {
        if (manaNumber == 4) {
          drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
        }
        if (manaNumber == 3) {
          drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 16, 16, 9, 9);
        }
        if (manaNumber == 2) {
          drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 32, 16, 9, 9);
        }
        if (manaNumber == 1) {
          drawQuad(b, (float) w / 2 + 10 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - (manaBarOffset - 9), (float) w / 2 + 19 + offsetX, h - manaBarOffset, (float) w / 2 + 10 + offsetX, h - manaBarOffset, 48, 16, 9, 9);
        }
        manaNumber = 0;
      }
    }
    tess.end();
    RenderSystem.disableBlend();
    poseStack.popPose();
    RenderSystem.enableCull();
    RenderSystem.enableDepthTest();
  }

  private static void drawQuad(BufferBuilder vertexConsumer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    vertexConsumer.vertex(x1 + 0.0F, y1 + 0.0F, 0).uv((minU) * f, (minV + maxV) * f1).endVertex();
    vertexConsumer.vertex(x2 + 0.0F, y2 + 0.0F, 0).uv((minU + maxU) * f, (minV + maxV) * f1).endVertex();
    vertexConsumer.vertex(x3 + 0.0F, y3 + 0.0F, 0).uv((minU + maxU) * f, (minV) * f1).endVertex();
    vertexConsumer.vertex(x4 + 0.0F, y4 + 0.0F, 0).uv((minU) * f, (minV) * f1).endVertex();
  }

  @SubscribeEvent
  public void clientTickEnd(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      ClientInfo.ticksInGame++;
    }
  }
}
