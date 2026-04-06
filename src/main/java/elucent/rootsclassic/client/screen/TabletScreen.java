package elucent.rootsclassic.client.screen;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.research.ResearchBase;
import elucent.rootsclassic.research.ResearchGroup;
import elucent.rootsclassic.research.ResearchManager;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

public class TabletScreen extends Screen {

  private int currentGroup = 0;
  private final Player player;

  public TabletScreen(Player player) {
    super(Component.empty());
    this.player = player;
    if (player.getItemInHand(InteractionHand.MAIN_HAND).has(RootsComponents.CURRENT_GROUP)) {
      currentGroup = player.getItemInHand(InteractionHand.MAIN_HAND).getOrDefault(RootsComponents.CURRENT_GROUP, 0);
    }
  }

  public static void openScreen(Player player) {
    Minecraft.getInstance().setScreen(new TabletScreen(player));
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  @Override
  public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
    double mouseX = event.x();
    double mouseY = event.y();
    float basePosX = (width / 2.0f) - 92;
    ResearchGroup group = null;
    ResearchBase base = null;
    for (int i = 0; i < ResearchManager.globalResearches.get(currentGroup).researches.size(); i++) {
      float yShift = (float) Math.floor((double) i / 6);
      float xShift = i % 6;
      if (mouseX >= basePosX + 32 * xShift && mouseX < basePosX + 32 * xShift + 24 && mouseY >= 32 + 40 * yShift && mouseY < 32 + 40 * yShift + 24) {
        group = ResearchManager.globalResearches.get(currentGroup);
        base = ResearchManager.globalResearches.get(currentGroup).researches.get(i);
      }
    }
    if (group != null && base != null) {
      CompoundTag persistentData = player.getPersistentData();
      persistentData.putString("RMOD_researchGroup", group.getName());
      persistentData.putString("RMOD_researchBase", base.getName());
      ResearchBase tempResearch = ResearchManager.getResearch(persistentData.getStringOr("RMOD_researchGroup", ""), persistentData.getStringOr("RMOD_researchBase", ""));
      ResearchGroup tempGroup = ResearchManager.getResearchGroup(persistentData.getStringOr("RMOD_researchGroup", ""));
      persistentData.remove("RMOD_researchGroup");
      persistentData.remove("RMOD_researchBase");
      if (tempResearch != null && tempGroup != null) {
        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        this.minecraft.setScreen(new TabletPageScreen(tempGroup, tempResearch, player));
      }
    }
    if (mouseX >= 32 && mouseX < 64 && mouseY >= height - 48 && mouseY < height - 32) {
      minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      currentGroup--;
      if (currentGroup < 0) {
        currentGroup = ResearchManager.globalResearches.size() - 1;
      }
    }
    if (mouseX >= width - 64 && mouseX < width - 32 && mouseY >= height - 48 && mouseY < height - 32) {
      minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      currentGroup++;
      if (currentGroup == ResearchManager.globalResearches.size()) {
        currentGroup = 0;
      }
    }
    ItemStack heldStack = player.getItemInHand(InteractionHand.MAIN_HAND);
    if (currentGroup != 0) {
      heldStack.set(RootsComponents.CURRENT_GROUP, currentGroup);
    }
    else {
      if (heldStack.has(RootsComponents.CURRENT_GROUP)) {
        heldStack.remove(RootsComponents.CURRENT_GROUP);
      }
    }
    return super.mouseClicked(event, doubleClick);
  }

  public void drawQuad(BufferBuilder bufferBuilder, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    bufferBuilder.addVertex(x4 + 0.0F, y4 + 0.0F, 0.0F).setUv((minU) * f, (minV + maxV) * f1);
    bufferBuilder.addVertex(x3 + 0.0F, y3 + 0.0F, 0.0F).setUv((minU + maxU) * f, (minV + maxV) * f1);
    bufferBuilder.addVertex(x2 + 0.0F, y2 + 0.0F, 0.0F).setUv((minU + maxU) * f, (minV) * f1);
    bufferBuilder.addVertex(x1 + 0.0F, y1 + 0.0F, 0.0F).setUv((minU) * f, (minV) * f1);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
    super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);

    this.player.releaseUsingItem();
    Matrix3x2fStack poseStack = guiGraphics.pose();
    poseStack.pushMatrix();
    float unit = width / 32.0f;
    if (RootsConfig.CLIENT.showTabletWave.get()) {
      for (float i = 0; i < width; i += unit) {
        float height1 = 12.0f * ((float) Math.cos(((ClientInfo.ticksInGame / 36.0) + (i / (width / 4.0))) * Math.PI) + 1.0f);
        int stripX = (int) i;
        int stripEndX = (int) (i + unit);
        int stripW = Math.max(1, stripEndX - stripX);
        int stripH = (int) (24.0f + height1);
        int stripY = height - stripH;
        guiGraphics.blit(
          RenderPipelines.GUI_TEXTURED, Const.TABLETGUI,
          stripX, stripY,
          16.0f, 96.0f,
          stripW, stripH,
          16, 64,
          256, 256
        );
      }
    }
    int basePosX = (int) ((width / 2.0f) - 92);
    String researchName = "rootsclassic.research." + ResearchManager.globalResearches.get(currentGroup).getName();
    for (int i = 0; i < ResearchManager.globalResearches.get(currentGroup).researches.size(); i++) {
      int yShift = (int) (float) Math.floor((double) i / 6);
      int xShift = (int) (i % 6);
      guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Const.TABLETGUI, basePosX + 32 * xShift, 32 + 40 * yShift, 16, 0, 24, 24, 256, 256);
      if (ResearchManager.globalResearches.get(currentGroup).researches.get(i).getIcon() != null) {
        guiGraphics.item(ResearchManager.globalResearches.get(currentGroup).researches.get(i).getIcon(), (int) (basePosX + xShift * 32 + 4), (int) (32 + 40 * yShift + 4));
      }
      if (mouseX >= basePosX + 32 * xShift && mouseX < basePosX + 32 * xShift + 24 && mouseY >= 32 + 40 * yShift && mouseY < 32 + 40 * yShift + 24) {
        String name = I18n.get(researchName + "." + ResearchManager.globalResearches.get(currentGroup).researches.get(i).getName());
        guiGraphics.text(font, name, (int)(basePosX + 32 * xShift + 12 - (font.width(name) / 2.0f)), (int)(32 + 40 * yShift + 25), RootsUtil.intColor(255, 255, 255), true);
      }
    }
    String formattedName = I18n.get(researchName);
    guiGraphics.text(font, formattedName, (int)(width / 2.0f - (font.width(formattedName) / 2.0f)), (int)(height - 16.0f), RootsUtil.intColor(255, 255, 255), true);
    if (mouseX >= 32 && mouseX < 64 && mouseY >= height - 48 && mouseY < height - 32) {
      guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Const.TABLETGUI, 32, height - 48, 32, 80, 32, 16, 256, 256);
    }
    else {
      guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Const.TABLETGUI, 32, height - 48, 32, 64, 32, 16, 256, 256);
    }
    if (mouseX >= width - 64 && mouseX < width - 32 && mouseY >= height - 48 && mouseY < height - 32) {
      guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Const.TABLETGUI, width - 64, height - 48, 0, 80, 32, 16, 256, 256);
    }
    else {
      guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Const.TABLETGUI, width - 64, height - 48, 0, 64, 32, 16, 256, 256);
    }
    poseStack.popMatrix();
  }
}
