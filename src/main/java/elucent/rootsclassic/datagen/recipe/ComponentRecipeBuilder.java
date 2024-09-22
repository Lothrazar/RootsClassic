package elucent.rootsclassic.datagen.recipe;

import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class ComponentRecipeBuilder implements RecipeBuilder {
	private final ResourceLocation effectResult;
	private String group;
	private ItemStack output = RootsRegistry.SPELL_POWDER.toStack();
	private final NonNullList<Ingredient> materials = NonNullList.create();
	private boolean needsMixin = true;

	public ComponentRecipeBuilder(ResourceLocation effectResult) {
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

	public ComponentRecipeBuilder output(ItemStack output) {
		this.output = output;
		return this;
	}

	public ComponentRecipeBuilder output(Item output) {
		this.output = new ItemStack(output);
		return this;
	}

	public ComponentRecipeBuilder output(Item output, int count) {
		this.output = new ItemStack(output, count);
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
	public Item getResult() {
		return output != null ? output.getItem() : Items.AIR;
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		ComponentRecipe upgradeRecipe = new ComponentRecipe(effectResult,
			Objects.requireNonNullElse(this.group, ""), output, materials, needsMixin);
		recipeOutput.accept(id, upgradeRecipe, null);
	}
}
