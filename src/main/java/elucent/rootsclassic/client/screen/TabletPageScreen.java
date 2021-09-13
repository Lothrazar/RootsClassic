package elucent.rootsclassic.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.research.EnumPageType;
import elucent.rootsclassic.research.ResearchBase;
import elucent.rootsclassic.research.ResearchGroup;
import elucent.rootsclassic.research.ResearchPage;
import elucent.rootsclassic.util.RootsUtil;

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
    super(TextComponent.EMPTY);
    this.player = player;
    this.group = g;
    this.research = r;
  }

  @Override
  public boolean keyPressed(int keyCode, int scanCode, int modifiers) { // Used to be keyTyped
    if (keyCode == 256) {
      minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      this.minecraft.setScreen(new TabletScreen(player));
      return true;
    }
    return super.keyPressed(keyCode, scanCode, modifiers);
  }

  @Override
  public boolean shouldCloseOnEsc() {
    return false;
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  @Override
  protected void init() {
    super.init();
    this.addWidget(new Button(20, 20, 20, 60, TextComponent.EMPTY, (button) -> {}));
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
    ArrayList<ResearchPage> researchInfo = research.getInfo();
    if (player.level.isClientSide
        && mouseX >= (width / 2.0f) - 110 && mouseX < (width / 2.0f) + 40
        && mouseY >= (height / 2.0f) - 138 && mouseY < (height / 2.0f) - 40) {
      if (researchInfo.get(currentPage).recipe == EnumPageType.TYPE_MORTAR) {
        //Roots.lang("rootsclassic.recipe.chat") +
        player.sendMessage(new TextComponent(researchInfo.get(currentPage).mortarRecipe.toString()), Util.NIL_UUID);
      }
      else if (researchInfo.get(currentPage).recipe == EnumPageType.TYPE_ALTAR) {
        player.sendMessage(new TranslatableComponent(researchInfo.get(currentPage).altarRecipe.toString()), Util.NIL_UUID);
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
  public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);
    //   super.drawScreen(mouseX, mouseY, partialTicks);
    RenderSystem.pushMatrix();
    RenderSystem.color4f(1, 1, 1, 1);
    ArrayList<ResearchPage> researchInfo = research.getInfo();
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
    ArrayList<String> info;
    String title;
    RenderSystem.color4f(1, 1, 1, 1);
    switch (page.recipe) {
      case TYPE_NULL://text only
        //        Roots.logger.info("null type ");??
        minecraft.getTextureManager().bind(Const.tabletGui);
        this.blit(matrixStack, basePosX, basePosY, 64, 0, 192, 256);
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 32 + i * 11));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RootsUtil.intColor(255, 255, 255)));
      break;
      case TYPE_SMELTING:
        minecraft.getTextureManager().bind(Const.tabletSmelting);
        this.blit(matrixStack, basePosX, basePosY, 0, 0, 192, 256);
        slots.add(new ScreenSlotInstance(page.smeltingRecipe.get(0), (int) basePosX + 56, (int) basePosY + 40));
        slots.add(new ScreenSlotInstance(page.smeltingRecipe.get(1), (int) basePosX + 144, (int) basePosY + 56));
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 104 + i * 11, RootsUtil.intColor(255, 255, 255)));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RootsUtil.intColor(255, 255, 255)));
      break;
      case TYPE_DISPLAY:
        minecraft.getTextureManager().bind(Const.tabletDisplay);
        this.blit(matrixStack, basePosX, basePosY, 0, 0, 192, 256);
        slots.add(new ScreenSlotInstance(page.displayItem, (int) basePosX + 88, (int) basePosY + 48));
        info = page.makeLines(makeInfo());
        for (int i = 0; i < info.size(); i++) {
          textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 80 + i * 11, RootsUtil.intColor(255, 255, 255)));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RootsUtil.intColor(255, 255, 255)));
      break;
      case TYPE_ALTAR:
        minecraft.getTextureManager().bind(Const.tabletAltar);
        this.blit(matrixStack, basePosX, basePosY, 0, 0, 192, 256);
        for (int i = 0; i < page.altarRecipe.getBlocks().size(); i++) {
          minecraft.getTextureManager().bind(Const.tabletAltar);
          int u = 192;
          int v = 240;
          int xShift = 0;
          int yShift = 0;
          this.blit(matrixStack, basePosX + 93, basePosY + 153, 192, 32, 16, 16);
          if (page.altarRecipe.getBlocks().get(i).equals(RootsRegistry.MUNDANE_STANDING_STONE.get().getRegistryName())) {
            u = 192;
            v = 48;
            xShift = 8 * (int) page.altarRecipe.getPositionsRelative().get(i).getX();
            yShift = 8 * (int) page.altarRecipe.getPositionsRelative().get(i).getZ();
          }
          if (page.altarRecipe.getBlocks().get(i).equals(RootsRegistry.ATTUNED_STANDING_STONE.get().getRegistryName())) {
            u = 192;
            v = 64;
            xShift = 8 * (int) page.altarRecipe.getPositionsRelative().get(i).getX();
            yShift = 8 * (int) page.altarRecipe.getPositionsRelative().get(i).getZ();
          }
          this.blit(matrixStack, basePosX + 93 + xShift, basePosY + 153 + yShift, u, v, 16, 16);
        }
        for (int i = 0; i < page.altarRecipe.getIngredients().size(); i++) {
          slots.add(new ScreenSlotInstance(page.altarRecipe.getIngredients().get(i), (int) basePosX + 64 + 24 * i, (int) basePosY + 56));
        }
        for (int i = 0; i < page.altarRecipe.getIncenses().size(); i++) {
          slots.add(new ScreenSlotInstance(page.altarRecipe.getIncenses().get(i), (int) basePosX + 76 + 16 * i, (int) basePosY + 88));
        }
        title = makeTitle();
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RootsUtil.intColor(255, 255, 255)));
      break;
      case TYPE_MORTAR:
        minecraft.getTextureManager().bind(Const.tabletMortar);
        this.blit(matrixStack, basePosX, basePosY, 0, 0, 192, 256);
        title = makeTitle();
        if (page.mortarRecipe != null) {
          for (int i = 0; i < page.mortarRecipe.getIngredients().size(); i++) {
            Ingredient ingredient = page.mortarRecipe.getIngredients().get(i);
            if (ingredient.isEmpty()) {
              slots.add(new ScreenSlotInstance(new ItemStack(Items.BARRIER), (int) basePosX + 24 + i * 16, (int) basePosY + 56));
            }
            else {
              slots.add(new ScreenSlotInstance(getStackFromIngredient(ingredient), (int) basePosX + 24 + i * 16, (int) basePosY + 56));
            }
          }
          slots.add(new ScreenSlotInstance(page.mortarRecipe.assemble(null), (int) basePosX + 144, (int) basePosY + 56));
          info = page.makeLines(makeInfo());
          for (int i = 0; i < info.size(); i++) {
            textLines.add(new ScreenTextInstance(info.get(i), basePosX + 16, basePosY + 96 + i * 11, RootsUtil.intColor(255, 255, 255)));
          }
        }
        else {
          //Disabled?
          title = ChatFormatting.RED + I18n.get("rootsclassic.research.disabled");
        }
        textLines.add(new ScreenTextInstance(title, basePosX + 96 - (this.font.width(title) / 2.0f), basePosY + 12, RootsUtil.intColor(255, 255, 255)));
    }//end of big switch
    for (ScreenSlotInstance s : slots) {
      this.itemRenderer.renderGuiItem(s.getStack(), s.getX(), s.getY());
    }
    for (ScreenTextInstance line : textLines) {
      if (line.isShadow())
        font.drawShadow(matrixStack, line.getLine(), line.getX(), line.getY(), line.getColor());
      else
        font.draw(matrixStack, line.getLine(), (int) line.getX(), (int) line.getY(), line.getColor());
    }
    //TODO: arrows go black on rituals
    minecraft.getTextureManager().bind(Const.tabletGui);
    if (showLeftArrow) {
      if (mouseX >= basePosX + 16 && mouseX < basePosX + 48 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        this.blit(matrixStack, basePosX + 16, basePosY + 224, 32, 80, 32, 16);
      }
      else {
        this.blit(matrixStack, basePosX + 16, basePosY + 224, 32, 64, 32, 16);
      }
    }
    if (showRightArrow) {
      if (mouseX >= basePosX + 144 && mouseX < basePosX + 176 && mouseY >= basePosY + 224 && mouseY < basePosY + 240) {
        this.blit(matrixStack, basePosX + 144, basePosY + 224, 0, 80, 32, 16);
      }
      else {
        this.blit(matrixStack, basePosX + 144, basePosY + 224, 0, 64, 32, 16);
      }
    }
    //tooltips must be AFTER rendering arrow images
    for (ScreenSlotInstance s : slots) {
      if (s.isMouseover(mouseX, mouseY)) {
        this.renderTooltip(matrixStack, s.getStack(), mouseX, mouseY);
      }
    }
    RenderSystem.popMatrix();
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
