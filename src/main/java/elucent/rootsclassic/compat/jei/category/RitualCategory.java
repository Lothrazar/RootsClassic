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
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

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
		this.background = guiHelper.drawableBuilder(backgroundLocation, 0, 0, 94, 100).addPadding(0, 0, 0, 0).build();

		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RootsRegistry.ALTAR.get()));

		this.ingredientBackground = guiHelper.createDrawable(location, 61, 53, 70, 22);
		this.resultBackground = guiHelper.createDrawable(location, 61, 53, 22, 22);
		this.incenseBackground = guiHelper.createDrawable(location, 49, 85, 94, 22);
		this.grid = guiHelper.createDrawable(location, 50, 118, 93, 93);
		this.stone = guiHelper.createDrawable(location, 192, 32, 16, 16);
		this.mundaneStone = guiHelper.createDrawable(location, 192, 48, 16, 16);
		this.attunedStone = guiHelper.createDrawable(location, 192, 64, 16, 16);
		this.localizedName = new TranslatableComponent("rootsclassic.gui.jei.category.ritual");
	}

	@Override
	public RecipeType<RitualWrapper> getRecipeType() {
		return JEIPlugin.RITUAL_TYPE;
	}

	@SuppressWarnings("removal")
	@Override
	public ResourceLocation getUid() {
		return JEIPlugin.RITUAL;
	}

	@SuppressWarnings("removal")
	@Override
	public Class<? extends RitualWrapper> getRecipeClass() {
		return RitualWrapper.class;
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
			ItemStack stack = recipe.getIngredients().get(i);
			builder.addSlot(RecipeIngredientRole.INPUT, 15 + (i * 24), 3).addItemStack(stack);
		}
		for (int i = 0; i < recipe.getIncenses().size(); i++) {
			ItemStack stack = recipe.getIncenses().get(i);
			builder.addSlot(RecipeIngredientRole.CATALYST, 28 + (i * 16), 27).addItemStack(stack);
		}

		if (!recipe.getResult().isEmpty()) {
			builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 67).addItemStack(recipe.getResult());
		}
	}

	@Override
	public void draw(RitualWrapper recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
		IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
		ingredientBackground.draw(stack, 12, 0);
		incenseBackground.draw(stack, 0, 24);
		resultBackground.draw(stack, 64, 64);
		stack.pushPose();

		stack.scale(0.5F, 0.5F, 1);
		grid.draw(stack, 20, 100);

		int basePosX = 63;
		int basePosY = 135;
		final List<Block> blocks = recipe.getBlocks();
		final List<BlockPos> relativePosition = recipe.getPositionsRelative();
		for (int i = 0; i < blocks.size(); i++) {
			int xShift = 0;
			int yShift = 0;
			stone.draw(stack, basePosX, basePosY);
			if (blocks.get(i).equals(RootsRegistry.MUNDANE_STANDING_STONE.get())) {
				xShift = 8 * (int) relativePosition.get(i).getX();
				yShift = 8 * (int) relativePosition.get(i).getZ();
				mundaneStone.draw(stack, basePosX + xShift, basePosY + yShift);
			}
			if (blocks.get(i).equals(RootsRegistry.ATTUNED_STANDING_STONE.get())) {
				xShift = 8 * (int) relativePosition.get(i).getX();
				yShift = 8 * (int) relativePosition.get(i).getZ();
				attunedStone.draw(stack, basePosX + xShift, basePosY + yShift);
			}
		}
		stack.popPose();
	}
}
