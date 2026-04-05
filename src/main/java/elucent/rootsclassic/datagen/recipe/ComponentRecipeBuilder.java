package elucent.rootsclassic.datagen.recipe;

import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class ComponentRecipeBuilder implements RecipeBuilder {
	private final Identifier effectResult;
	private String group;
	private ItemStackTemplate output = new ItemStackTemplate(RootsRegistry.SPELL_POWDER);
	private final NonNullList<Ingredient> materials = NonNullList.create();
	private boolean needsMixin = true;

	public ComponentRecipeBuilder(Identifier effectResult) {
		this.effectResult = effectResult;
	}

	@Override
	public RecipeBuilder group(@Nullable String groupName) {
		this.group = groupName;
		return this;
	}

	public ComponentRecipeBuilder materials(Ingredient... ingredients) {
		this.materials.addAll(Arrays.asList(ingredients));
		return this;
	}

	public ComponentRecipeBuilder output(ItemStackTemplate output) {
		this.output = output;
		return this;
	}

	public ComponentRecipeBuilder output(Item output) {
		this.output = new ItemStackTemplate(output);
		return this;
	}

	public ComponentRecipeBuilder output(Item output, int count) {
		this.output = new ItemStackTemplate(output, count);
		return this;
	}

	public ComponentRecipeBuilder needsMixin(boolean needsMixin) {
		this.needsMixin = needsMixin;
		return this;
	}

	@Override
	public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		return this;
	}

  @Override
  public ResourceKey<Recipe<?>> defaultId() {
    return RecipeBuilder.getDefaultRecipeId(this.output);
  }

  @Override
  public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
    ComponentRecipe upgradeRecipe = new ComponentRecipe(effectResult,
      Objects.requireNonNullElse(this.group, ""), this.output, materials, needsMixin);
    output.accept(location, upgradeRecipe, null);
  }

  public void save(RecipeOutput output, Identifier identifier) {
    save(output, ResourceKey.create(Registries.RECIPE, identifier));
  }
}
