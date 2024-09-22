package elucent.rootsclassic.datagen.recipe;

import elucent.rootsclassic.recipe.RitualRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class RitualRecipeBuilder implements RecipeBuilder {
	public final ResourceLocation effectId;
	public CompoundTag effectConfig = null;
	private final NonNullList<Ingredient> materials = NonNullList.create();
	private final NonNullList<Ingredient> incenses = NonNullList.create();
	public int level = 0;
	public String color = "";
	public String secondaryColor = "";

	public RitualRecipeBuilder(ResourceLocation effectId) {
		this.effectId = effectId;
	}

	public RitualRecipeBuilder config(CompoundTag config) {
		this.effectConfig = config;
		return this;
	}

	public RitualRecipeBuilder materials(Ingredient... ingredients) {
		this.materials.addAll(Arrays.asList(ingredients));
		return this;
	}

	public RitualRecipeBuilder incenses(Ingredient... ingredients) {
		this.incenses.addAll(Arrays.asList(ingredients));
		return this;
	}

	public RitualRecipeBuilder level(int level) {
		this.level = level;
		return this;
	}

	public RitualRecipeBuilder color(String color) {
		this.color = color;
		return this;
	}

	public RitualRecipeBuilder secondaryColor(String color) {
		this.secondaryColor = color;
		return this;
	}

	@Override
	public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		return this;
	}

	@Override
	public RecipeBuilder group(@Nullable String groupName) {
		return this;
	}

	@Override
	public Item getResult() {
		return Items.AIR;
	}

	@Override
	public void save(RecipeOutput recipeOutput) {
		this.save(recipeOutput, effectId.getNamespace());
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		RitualRecipe upgradeRecipe = new RitualRecipe(effectId, effectConfig, materials, incenses, level, color, secondaryColor);
		recipeOutput.accept(id, upgradeRecipe, null);
	}
}
