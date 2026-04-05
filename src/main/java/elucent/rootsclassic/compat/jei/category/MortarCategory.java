package elucent.rootsclassic.compat.jei.category;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.jei.JEIPlugin;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class MortarCategory implements IRecipeCategory<ComponentRecipe> {

  private final static Identifier backgroundLocation = Const.modLoc("textures/gui/tabletmortar.png");
  private final IDrawable background;
  private final IDrawable icon;
  private final Component localizedName;

  public MortarCategory(IGuiHelper guiHelper) {
    this.background = guiHelper.drawableBuilder(backgroundLocation, 21, 30, 142, 45).addPadding(0, 0, 0, 0).build();
    this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RootsRegistry.MORTAR.get()));
    this.localizedName = Component.translatable("rootsclassic.gui.jei.category.mortar");
  }

  @Override
  public IRecipeType<ComponentRecipe> getRecipeType() {
    return JEIPlugin.MORTAR_TYPE;
  }

  @Override
  public int getWidth() {
    return 142;
  }

  @Override
  public int getHeight() {
    return 45;
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
  public void setRecipe(IRecipeLayoutBuilder builder, ComponentRecipe recipe, IFocusGroup focuses) {
    for (int i = 0; i < recipe.getIngredients().size(); i++) {
      Ingredient ingredient = recipe.getIngredients().get(i);
      builder.addSlot(RecipeIngredientRole.INPUT, 3 + (i * 16), 26).add(ingredient);
    }
    builder.addSlot(RecipeIngredientRole.OUTPUT, 123, 26).add(recipe.assemble(new SingleRecipeInput(ItemStack.EMPTY)));
  }

  @Override
  public void draw(ComponentRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
    IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
  }
}
