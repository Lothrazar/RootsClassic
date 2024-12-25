package elucent.rootsclassic.client.screen;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.library.util.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.research.EnumPageType;
import elucent.rootsclassic.research.ResearchBase;
import elucent.rootsclassic.research.ResearchGroup;
import elucent.rootsclassic.research.ResearchPage;
import elucent.rootsclassic.ritual.RitualPillars;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class TabletPageScreen extends Screen {

  private int currentPage = 0;
  private ResearchBase research = null;
  private ResearchGroup group = null;
  private boolean showRightArrow = false;
  private boolean showLeftArrow = true;
  private final Player player;
  private int matchingStacksCurrent = 0;
  private int matchingStacksMax = 0;
  private int matchingStacks2Current = 0;
  private int matchingStacks2Max = 0;

  public TabletPageScreen(ResearchGroup g, ResearchBase r, Player player) {
    super(Component.empty());
    this.player = player;
    this.group = g;
    this.research = r;
  }

  @Override
  public void onClose() {
    minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    this.minecraft.setScreen(new TabletScreen(player));
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  @Override
  protected void init() {
    super.init();
    this.addRenderableWidget(new Button.Builder(Component.empty(), (button) -> {}).bounds(20, 20, 20, 60).build());
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    float basePosX = (width / 2.0f) - 96;
    float basePosY = (height / 2.0f) - 128;
    if (showLeftArrow) {
      if (mouseX >= basePosX + 16 && mouseX < basePosX + 48 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        this.currentPage--;
      }
    }
    if (showRightArrow) {
      if (mouseX >= basePosX + 144 && mouseX < basePosX + 176 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        this.currentPage++;
      }
    }
    List<ResearchPage> researchInfo = research.getInfo();
    if (player.level().isClientSide
        && mouseX >= (width / 2.0f) - 110 && mouseX < (width / 2.0f) + 40
        && mouseY >= (height / 2.0f) - 138 && mouseY < (height / 2.0f) - 40) {
      if (researchInfo.get(currentPage).recipe == EnumPageType.TYPE_MORTAR) {
        //Roots.lang("rootsclassic.recipe.chat") +
        player.sendSystemMessage(Component.translatable(researchInfo.get(currentPage).mortarRecipe.toString()));
      }
      else if (researchInfo.get(currentPage).recipe == EnumPageType.TYPE_ALTAR) {
        player.sendSystemMessage(Component.translatable(researchInfo.get(currentPage).altarRecipe.toString()));
      }
    }
    return super.mouseClicked(mouseX, mouseY, button);
  }

  private String makeTitle() {
    return I18n.get("rootsclassic.research." + group.getName() + "." + research.getName() + ".page" + (this.currentPage + 1) + "title");
  }

  private String makeInfo() {
    return I18n.get("rootsclassic.research." + group.getName() + "." + research.getName() + ".page" + (this.currentPage + 1) + "info");
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics);
    //   super.drawScreen(mouseX, mouseY, partialTicks);
    PoseStack ps = guiGraphics.pose();
    ps.pushPose();
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    List<ResearchPage> researchInfo = research.getInfo();
    this.showLeftArrow = this.currentPage != 0;
    this.showRightArrow = this.currentPage != researchInfo.size() - 1;
    int basePosX = (int) ((width / 2.0f) - 96);
    int basePosY = (int) ((height / 2.0f) - 128);
    if (research == null || researchInfo == null || researchInfo.size() <= currentPage) {
      //in case current page out of sync with recipe, don't error out and crash
      return;
    }
    List<ScreenSlotInstance> slots = new ArrayList<>();
    List<ScreenTextInstance> textLines = new ArrayList<>();
    ResearchPage page = researchInfo.get(currentPage);
    List<String> info;
    String title;
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    switch (page.recipe) {
      case TYPE_NULL -> {//text only
        //        Roots.logger.info("null type ");??
        //        RenderSystem.setShaderTexture(0, Const.tabletGui);
        guiGraphics.blit(Const.TABLETGUI, basePosX, basePosY, 64, 0, 192, 256);
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 32 + i * 11));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RenderUtil.intColor(255, 255, 255)));
      }
      case TYPE_SMELTING -> {
        //        RenderSystem.setShaderTexture(0, Const.tabletSmelting);
        guiGraphics.blit(Const.TABLETSMELTING, basePosX, basePosY, 0, 0, 192, 256);
        slots.add(new ScreenSlotInstance(page.smeltingRecipe.get(0), basePosX + 56, basePosY + 40));
        slots.add(new ScreenSlotInstance(page.smeltingRecipe.get(1), basePosX + 144, basePosY + 56));
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 104 + i * 11, RenderUtil.intColor(255, 255, 255)));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RenderUtil.intColor(255, 255, 255)));
      }
      case TYPE_DISPLAY -> {
        //        RenderSystem.setShaderTexture(0, Const.tabletDisplay);
        guiGraphics.blit(Const.TABLETDISPLAY, basePosX, basePosY, 0, 0, 192, 256);
        slots.add(new ScreenSlotInstance(page.displayItem, basePosX + 88, basePosY + 48));
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 80 + i * 11, RenderUtil.intColor(255, 255, 255)));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RenderUtil.intColor(255, 255, 255)));
      }
      case TYPE_ALTAR -> {
        //        RenderSystem.setShaderTexture(0, Const.tabletAltar);
        guiGraphics.blit(Const.TABLETALTAR, basePosX, basePosY, 0, 0, 192, 256);
        RitualPillars.getRitualPillars(page.altarRecipe.level).forEach((pos, block) -> {
          RenderSystem.setShaderTexture(0, Const.TABLETALTAR);
          int u = 192;
          int v = 240;
          int xShift = 0;
          int yShift = 0;
          guiGraphics.blit(Const.TABLETALTAR, basePosX + 93, basePosY + 153, 192, 32, 16, 16);
          if (block.equals(RootsRegistry.MUNDANE_STANDING_STONE.get())) {
            v = 48;
            xShift = 8 * pos.getX();
            yShift = 8 * pos.getZ();
          }
          if (block.equals(RootsRegistry.ATTUNED_STANDING_STONE.get())) {
            v = 64;
            xShift = 8 * pos.getX();
            yShift = 8 * pos.getZ();
          }
          guiGraphics.blit(Const.TABLETALTAR, basePosX + 93 + xShift, basePosY + 153 + yShift, u, v, 16, 16);
        });
        for (int i = 0; i < page.altarRecipe.getIngredients().size(); i++) {
          var stack = page.altarRecipe.getIngredients().get(i).getItems()[0];
          slots.add(new ScreenSlotInstance(stack, basePosX + 64 + 24 * i, basePosY + 56));
        }
        for (int i = 0; i < page.altarRecipe.getIncenses().size(); i++) {
          var stack = page.altarRecipe.getIncenses().get(i).getItems()[0];
          slots.add(new ScreenSlotInstance(stack, basePosX + 76 + 16 * i, basePosY + 88));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RenderUtil.intColor(255, 255, 255)));
      }
      case TYPE_MORTAR -> {
        //        RenderSystem.setShaderTexture(0, Const.tabletMortar);
        guiGraphics.blit(Const.TABLETMORTAR, basePosX, basePosY, 0, 0, 192, 256);
        title = makeTitle();
        if (page.mortarRecipe != null) {
          for (int i = 0; i < page.mortarRecipe.getIngredients().size(); i++) {
            Ingredient ingredient = page.mortarRecipe.getIngredients().get(i);
            if (ingredient.isEmpty()) {
              slots.add(new ScreenSlotInstance(new ItemStack(Items.BARRIER), basePosX + 24 + i * 16, basePosY + 56));
            }
            else {
              slots.add(new ScreenSlotInstance(getStackFromIngredient(ingredient), basePosX + 24 + i * 16, basePosY + 56));
            }
          }
          ClientLevel level = minecraft.level;
          if (level != null) {
            RegistryAccess registryAccess = level.registryAccess();
            slots.add(new ScreenSlotInstance(page.mortarRecipe.assemble(new SimpleContainer(), registryAccess), basePosX + 144, basePosY + 56));
          }
          info = page.makeLines(makeInfo());
          for (int i = 0; i < info.size(); i++) {
            textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 96 + i * 11, RenderUtil.intColor(255, 255, 255)));
          }
        }
        else {
          //Disabled?
          title = ChatFormatting.RED + I18n.get("rootsclassic.research.disabled");
        }
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RenderUtil.intColor(255, 255, 255)));
      }
    }//end of big switch
    for (ScreenSlotInstance s : slots) {
      guiGraphics.renderItem(s.getStack(), s.getX(), s.getY());
    }
    for (ScreenTextInstance line : textLines) {
      if (line.isShadow())
        guiGraphics.drawString(font, line.getLine(), line.getX(), line.getY(), line.getColor(), true);
      else
        guiGraphics.drawString(font, line.getLine(), line.getX(), line.getY(), line.getColor(), false);
    }
    //TODO: arrows go black on rituals
    RenderSystem.setShaderTexture(0, Const.TABLETGUI);
    if (showLeftArrow) {
      if (mouseX >= basePosX + 16 && mouseX < basePosX + 48 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        guiGraphics.blit(Const.TABLETGUI, basePosX + 16, basePosY + 224, 32, 80, 32, 16);
      }
      else {
        guiGraphics.blit(Const.TABLETGUI, basePosX + 16, basePosY + 224, 32, 64, 32, 16);
      }
    }
    if (showRightArrow) {
      if (mouseX >= basePosX + 144 && mouseX < basePosX + 176 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        guiGraphics.blit(Const.TABLETGUI, basePosX + 144, basePosY + 224, 0, 80, 32, 16);
      }
      else {
        guiGraphics.blit(Const.TABLETGUI, basePosX + 144, basePosY + 224, 0, 64, 32, 16);
      }
    }
    //tooltips must be AFTER rendering arrow images
    for (ScreenSlotInstance s : slots) {
      if (s.isMouseover(mouseX, mouseY)) {
        guiGraphics.renderTooltip(font, s.getStack(), mouseX, mouseY);
      }
    }
    ps.popPose();
  }

  public ItemStack getStackFromIngredient(Ingredient ingredient) {
    ItemStack[] matchingStacks = ingredient.getItems();
    if (matchingStacksMax != matchingStacks.length) {
      matchingStacksMax = matchingStacks.length;
    }
    if (ClientInfo.ticksInGame % 20 == 0) {
      matchingStacksCurrent++;
    }
    if (matchingStacksCurrent >= (matchingStacksMax - 1)) {
      matchingStacksCurrent = 0;
    }
    return matchingStacks[matchingStacksCurrent];
  }

  public ItemStack getStackFrom2ndIngredient(Ingredient ingredient) {
    ItemStack[] matchingStacks = ingredient.getItems();
    if (matchingStacks2Max != matchingStacks.length) {
      matchingStacks2Max = matchingStacks.length;
    }
    if (ClientInfo.ticksInGame % 20 == 0) {
      matchingStacks2Current++;
    }
    if (matchingStacks2Current >= (matchingStacks2Max - 1)) {
      matchingStacks2Current = 0;
    }
    return matchingStacks[matchingStacks2Current];
  }
}
