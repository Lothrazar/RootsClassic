package elucent.rootsclassic.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.jei.JEIPlugin;
import elucent.rootsclassic.compat.jei.wrapper.RitualWrapper;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class RitualCategory implements IRecipeCategory<RitualWrapper> {

  private final static ResourceLocation backgroundLocation = new ResourceLocation(Const.MODID, "textures/gui/jei/compat.png");
  private final static ResourceLocation location = new ResourceLocation(Const.MODID, "textures/gui/tabletaltar.png");
  private final IDrawable background;
  private final IDrawable icon;
  private final IDrawableStatic ingredientBackground;
  private final IDrawableStatic incenseBackground;
  private final IDrawableStatic resultBackground;
  private final IDrawableStatic grid;
  private final IDrawableStatic stone;
  private final IDrawableStatic mundaneStone;
  private final IDrawableStatic attunedStone;
  private final Component localizedName;

  public RitualCategory(IGuiHelper guiHelper) {
    this.background = guiHelper.drawableBuilder(backgroundLocation, 0, 0, 94, 110).addPadding(0, 0, 0, 0).build();
    this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RootsRegistry.ALTAR.get()));
    this.ingredientBackground = guiHelper.createDrawable(location, 61, 53, 70, 22);
    this.resultBackground = guiHelper.createDrawable(location, 61, 53, 22, 22);
    this.incenseBackground = guiHelper.createDrawable(location, 49, 85, 94, 22);
    this.grid = guiHelper.createDrawable(location, 50, 118, 93, 93);
    this.stone = guiHelper.createDrawable(location, 192, 32, 16, 16);
    this.mundaneStone = guiHelper.createDrawable(location, 192, 48, 16, 16);
    this.attunedStone = guiHelper.createDrawable(location, 192, 64, 16, 16);
    this.localizedName = Component.translatable("rootsclassic.gui.jei.category.ritual");
  }

  @Override
  public RecipeType<RitualWrapper> getRecipeType() {
    return JEIPlugin.RITUAL_TYPE;
  }

  @Override
  public IDrawable getBackground() {
    return background;
  }

  @Override
  public IDrawable getIcon() {
    return icon;
  }

  @Override
  public Component getTitle() {
    return localizedName;
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, RitualWrapper recipe, IFocusGroup focuses) {
    for (int i = 0; i < recipe.getIngredients().size(); i++) {
      Ingredient ingredient = recipe.getIngredients().get(i);
      builder.addSlot(RecipeIngredientRole.INPUT, 15 + (i * 24), 3).addIngredients(ingredient);
    }
    for (int i = 0; i < recipe.getIncenses().size(); i++) {
      Ingredient ingredient = recipe.getIncenses().get(i);
      builder.addSlot(RecipeIngredientRole.CATALYST, 28 + (i * 16), 27).addIngredients(ingredient);
    }
    if (!recipe.getResult().isEmpty()) {
      builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 67).addItemStack(recipe.getResult());
    }
  }

  @Override
  public void draw(RitualWrapper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
    IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    ingredientBackground.draw(guiGraphics, 12, 0);
    incenseBackground.draw(guiGraphics, 0, 24);
    resultBackground.draw(guiGraphics, 64, 64);
    PoseStack poseStack = guiGraphics.pose();
    poseStack.pushPose();
    poseStack.scale(0.5F, 0.5F, 1);
    grid.draw(guiGraphics, 20, 100);
    int basePosX = 63;
    int basePosY = 135;
    recipe.getPillars().forEach((pos, block) -> {
      int xShift = 0;
      int yShift = 0;
      stone.draw(guiGraphics, basePosX, basePosY);
      if (block.equals(RootsRegistry.MUNDANE_STANDING_STONE.get())) {
        xShift = 8 * pos.getX();
        yShift = 8 * pos.getZ();
        mundaneStone.draw(guiGraphics, basePosX + xShift, basePosY + yShift);
      }
      if (block.equals(RootsRegistry.ATTUNED_STANDING_STONE.get())) {
        xShift = 8 * pos.getX();
        yShift = 8 * pos.getZ();
        attunedStone.draw(guiGraphics, basePosX + xShift, basePosY + yShift);
      }
    });
    poseStack.popPose();
    Minecraft minecraft = Minecraft.getInstance();
    Font font = minecraft.font;
    var infoText = recipe.getInfoText();
    guiGraphics.drawString(font, infoText, (int) ((94 - font.width(infoText)) / 2F), 100, 0x8b8b8b);
    //>>>>>>> trunk/1.19
  }
}
