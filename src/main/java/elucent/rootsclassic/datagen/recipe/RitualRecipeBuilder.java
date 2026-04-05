package elucent.rootsclassic.datagen.recipe;

import elucent.rootsclassic.recipe.RitualRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public class RitualRecipeBuilder implements RecipeBuilder {
	public final Identifier effectId;
	public CompoundTag effectConfig = null;
	private final NonNullList<Ingredient> materials = NonNullList.create();
	private final NonNullList<Ingredient> incenses = NonNullList.create();
	public int level = 0;
	public String color = "";
	public String secondaryColor = "";

	public RitualRecipeBuilder(Identifier effectId) {
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
  public ResourceKey<Recipe<?>> defaultId() {
    return ResourceKey.create(Registries.RECIPE, effectId);
  }

	@Override
	public void save(RecipeOutput recipeOutput) {
		this.save(recipeOutput, effectId.getNamespace());
	}

	@Override
  public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
		RitualRecipe upgradeRecipe = new RitualRecipe(effectId, Optional.ofNullable(effectConfig), materials, incenses, level, color, secondaryColor);
    output.accept(location, upgradeRecipe, null);
	}

  public void save(RecipeOutput output, Identifier identifier) {
    save(output, ResourceKey.create(Registries.RECIPE, identifier));
  }
}
