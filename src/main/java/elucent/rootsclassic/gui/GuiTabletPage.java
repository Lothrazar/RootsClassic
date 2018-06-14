package elucent.rootsclassic.gui;

import java.util.ArrayList;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.research.EnumRecipeType;
import elucent.rootsclassic.research.ResearchBase;
import elucent.rootsclassic.research.ResearchGroup;
import elucent.rootsclassic.research.ResearchPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GuiTabletPage extends GuiScreen {

  public double mouseX = 0;
  public double mouseY = 0;
  public double smoothMouseX = 0;
  public double smoothMouseY = 0;
  public int layer = 0;
  public double cycle = 0;
  public int currentPage = 0;
  public ResearchBase research = null;
  public ResearchGroup group = null;
  boolean showRightArrow = false;
  boolean showLeftArrow = true;
  EntityPlayer player = null;

  public GuiTabletPage(ResearchGroup g, ResearchBase r, EntityPlayer player) {
    this.player = player;
    group = g;
    research = r;
  }

  @Override
  public void keyTyped(char typedChar, int keyCode) {
    if (keyCode == 1) {
      player.openGui(Roots.instance, 1, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
    }
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

  @Override
  public void initGui() {
    this.addButton(new GuiButton(7, 20, 20, 20, 60, ""));
  }

  @Override
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    float basePosX = (width / 2.0f) - 96;
    float basePosY = (height / 2.0f) - 128;
    if (showLeftArrow) {
      if (mouseX >= basePosX + 16 && mouseX < basePosX + 48 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        this.currentPage--;
      }
    }
    if (showRightArrow) {
      if (mouseX >= basePosX + 144 && mouseX < basePosX + 176 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        this.currentPage++;
      }
    }
    if (player.world.isRemote && research.info.get(currentPage).recipe == EnumRecipeType.TYPE_MORTAR) {
      if (mouseX >= (width / 2.0f) - 110 && mouseX < (width / 2.0f) + 40
          && mouseY >= (height / 2.0f) - 138 && mouseY < (height / 2.0f) - 40) {

        player.sendMessage(//TextFormatting.RED + 
            new TextComponentString(//I18n.format("roots.recipe.chat") +
                research.info.get(currentPage).mortarRecipe.toString()));
      }
    }
  }

  public void drawQuad(BufferBuilder vertexbuffer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    vertexbuffer.pos(x4 + 0.0F, y4 + 0.0F, this.zLevel).tex((minU + 0) * f, (minV + maxV) * f1).endVertex();
    vertexbuffer.pos(x3 + 0.0F, y3 + 0.0F, this.zLevel).tex((minU + maxU) * f, (minV + maxV) * f1).endVertex();
    vertexbuffer.pos(x2 + 0.0F, y2 + 0.0F, this.zLevel).tex((minU + maxU) * f, (minV + 0) * f1).endVertex();
    vertexbuffer.pos(x1 + 0.0F, y1 + 0.0F, this.zLevel).tex((minU + 0) * f, (minV + 0) * f1).endVertex();
  }

  private String makeTitle() {
    //    if (page.mortarRecipe.disabled) {
    //      title = TextFormatting.RED + I18n.format("roots.research.disabled.name");
    return I18n.format("roots.research." + group.name + "." + research.name + ".page" + (this.currentPage + 1) + "title.name");
  }

  private String makeInfo() {
    return I18n.format("roots.research." + group.name + "." + research.name + ".page" + (this.currentPage + 1) + "info");
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    GlStateManager.color(1, 1, 1, 1);
    RenderHelper.disableStandardItemLighting();
    RenderHelper.enableGUIStandardItemLighting();
    if (this.currentPage == 0) {
      this.showLeftArrow = false;
    }
    else {
      this.showLeftArrow = true;
    }
    if (this.currentPage == this.research.info.size() - 1) {
      this.showRightArrow = false;
    }
    else {
      this.showRightArrow = true;
    }
    cycle += 4.0;
    this.drawDefaultBackground();
    // Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletGui);
    this.mouseX = mouseX;
    this.mouseY = mouseY;
    float basePosX = (width / 2.0f) - 96;
    float basePosY = (height / 2.0f) - 128;
    ResearchPage page = research.info.get(currentPage);

    ArrayList<String> info;
    String title;
    GlStateManager.color(1, 1, 1, 1);
    switch (page.recipe) {
      case TYPE_NULL:
        Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletGui);
        this.drawTexturedModalRect(basePosX, basePosY, 64, 0, 192, 256);
        title = makeTitle();
        fontRenderer.drawStringWithShadow(title, basePosX + 96 - (this.fontRenderer.getStringWidth(title) / 2.0f), basePosY + 12, Util.intColor(255, 255, 255));
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          fontRenderer.drawStringWithShadow(info.get(i), basePosX + 16, basePosY + 32 + i * 11, Util.intColor(255, 255, 255));
        }
      break;
      case TYPE_CRAFTING:
        Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletCrafting);
        this.drawTexturedModalRect(basePosX, basePosY, 0, 0, 192, 256);
        if (page.craftingRecipe.get(0) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(0), (int) basePosX + 32, (int) basePosY + 32);
        }
        if (page.craftingRecipe.get(1) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(1), (int) basePosX + 56, (int) basePosY + 32);
        }
        if (page.craftingRecipe.get(2) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(2), (int) basePosX + 80, (int) basePosY + 32);
        }
        if (page.craftingRecipe.get(3) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(3), (int) basePosX + 32, (int) basePosY + 56);
        }
        if (page.craftingRecipe.get(4) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(4), (int) basePosX + 56, (int) basePosY + 56);
        }
        if (page.craftingRecipe.get(5) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(5), (int) basePosX + 80, (int) basePosY + 56);
        }
        if (page.craftingRecipe.get(6) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(6), (int) basePosX + 32, (int) basePosY + 80);
        }
        if (page.craftingRecipe.get(7) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(7), (int) basePosX + 56, (int) basePosY + 80);
        }
        if (page.craftingRecipe.get(8) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(8), (int) basePosX + 80, (int) basePosY + 80);
        }
        if (page.craftingRecipe.get(9) != null) {
          this.itemRender.renderItemIntoGUI(page.craftingRecipe.get(9), (int) basePosX + 144, (int) basePosY + 56);
        }
        //"roots.research." + group.name + "." + research.name + ".page" + (this.currentPage + 1) + "title.name"
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          fontRenderer.drawStringWithShadow(info.get(i), basePosX + 16, basePosY + 104 + i * 11, Util.intColor(255, 255, 255));
        }
        title = makeTitle();
        fontRenderer.drawStringWithShadow(title, basePosX + 96 - (this.fontRenderer.getStringWidth(title) / 2.0f), basePosY + 12, Util.intColor(255, 255, 255));
      break;
      case TYPE_SMELTING:
        Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletSmelting);
        this.drawTexturedModalRect(basePosX, basePosY, 0, 0, 192, 256);
        if (page.smeltingRecipe.get(0) != null) {
          this.itemRender.renderItemIntoGUI(page.smeltingRecipe.get(0), (int) basePosX + 56, (int) basePosY + 40);
        }
        if (page.smeltingRecipe.get(1) != null) {
          this.itemRender.renderItemIntoGUI(page.smeltingRecipe.get(1), (int) basePosX + 144, (int) basePosY + 56);
        }
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          fontRenderer.drawStringWithShadow(info.get(i), basePosX + 16, basePosY + 104 + i * 11, Util.intColor(255, 255, 255));
        }
        title = makeTitle();
        fontRenderer.drawStringWithShadow(title, basePosX + 96 - (this.fontRenderer.getStringWidth(title) / 2.0f), basePosY + 12, Util.intColor(255, 255, 255));
      break;
      case TYPE_DISPLAY:
        Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletDisplay);
        this.drawTexturedModalRect(basePosX, basePosY, 0, 0, 192, 256);
        if (page.displayItem != null) {
          this.itemRender.renderItemIntoGUI(page.displayItem, (int) basePosX + 88, (int) basePosY + 48);
        }
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          fontRenderer.drawStringWithShadow(info.get(i), basePosX + 16, basePosY + 80 + i * 11, Util.intColor(255, 255, 255));
        }
        title = makeTitle();
        fontRenderer.drawStringWithShadow(title, basePosX + 96 - (this.fontRenderer.getStringWidth(title) / 2.0f), basePosY + 12, Util.intColor(255, 255, 255));
      break;
      case TYPE_ALTAR:
        Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletAltar);
        this.drawTexturedModalRect(basePosX, basePosY, 0, 0, 192, 256);
        for (int i = 0; i < page.altarRecipe.blocks.size(); i++) {
          Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletAltar);
          int u = 192;
          int v = 240;
          int xShift = 0;
          int yShift = 0;
          this.drawTexturedModalRect(basePosX + 93, basePosY + 153, 192, 32, 16, 16);
          if (page.altarRecipe.blocks.get(i) == RegistryManager.standingStoneT1) {
            u = 192;
            v = 48;
            xShift = 8 * page.altarRecipe.positions.get(i).getX();
            yShift = 8 * page.altarRecipe.positions.get(i).getZ();
          }
          if (page.altarRecipe.blocks.get(i) == RegistryManager.standingStoneT2) {
            u = 192;
            v = 64;
            xShift = 8 * page.altarRecipe.positions.get(i).getX();
            yShift = 8 * page.altarRecipe.positions.get(i).getZ();
          }
          this.drawTexturedModalRect(basePosX + 93 + xShift, basePosY + 153 + yShift, u, v, 16, 16);
        }
        for (int i = 0; i < page.altarRecipe.ingredients.size(); i++) {
          if (page.altarRecipe.ingredients.get(i) != null) {
            this.itemRender.renderItemIntoGUI(page.altarRecipe.ingredients.get(i), (int) basePosX + 64 + 24 * i, (int) basePosY + 56);
          }
        }
        for (int i = 0; i < page.altarRecipe.incenses.size(); i++) {
          if (page.altarRecipe.incenses.get(i) != null) {
            this.itemRender.renderItemIntoGUI(page.altarRecipe.incenses.get(i), (int) basePosX + 76 + 16 * i, (int) basePosY + 88);
          }
        }
        title = makeTitle();
        fontRenderer.drawStringWithShadow(title, basePosX + 96 - (this.fontRenderer.getStringWidth(title) / 2.0f), basePosY + 12, Util.intColor(255, 255, 255));
      break;
      case TYPE_MORTAR:
        //  System.out.println("MORTAR : put a chat button here");
        Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletMortar);
        this.drawTexturedModalRect(basePosX, basePosY, 0, 0, 192, 256);
        for (int i = 0; i < page.mortarRecipe.getMaterials().size(); i++) {
          this.itemRender.renderItemIntoGUI(page.mortarRecipe.getMaterials().get(i), (int) basePosX + 24 + i * 16, (int) basePosY + 56);
        }
        this.itemRender.renderItemIntoGUI(new ItemStack(RegistryManager.dustPetal), (int) basePosX + 144, (int) basePosY + 56);
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          fontRenderer.drawStringWithShadow(info.get(i), basePosX + 16, basePosY + 96 + i * 11, Util.intColor(255, 255, 255));
        }
        if (page.mortarRecipe.isDisabled()) {
          title = TextFormatting.RED + I18n.format("roots.research.disabled.name");
        }
        else {
          title = makeTitle();
        }
        fontRenderer.drawStringWithShadow(title, basePosX + 96 - (this.fontRenderer.getStringWidth(title) / 2.0f), basePosY + 12, Util.intColor(255, 255, 255));
    }
    Minecraft.getMinecraft().getTextureManager().bindTexture(Const.tabletGui);
    if (showLeftArrow) {
      if (mouseX >= basePosX + 16 && mouseX < basePosX + 48 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        this.drawTexturedModalRect(basePosX + 16, basePosY + 224, 32, 80, 32, 16);
      }
      else {
        this.drawTexturedModalRect(basePosX + 16, basePosY + 224, 32, 64, 32, 16);
      }
    }
    if (showRightArrow) {
      if (mouseX >= basePosX + 144 && mouseX < basePosX + 176 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        this.drawTexturedModalRect(basePosX + 144, basePosY + 224, 0, 80, 32, 16);
      }
      else {
        this.drawTexturedModalRect(basePosX + 144, basePosY + 224, 0, 64, 32, 16);
      }
    }
    GlStateManager.enableBlend();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder vertexbuffer = tessellator.getBuffer();
    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
    tessellator.draw();
    GlStateManager.disableBlend();
    RenderHelper.enableStandardItemLighting();
  }
}
