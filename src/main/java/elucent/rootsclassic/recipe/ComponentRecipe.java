package elucent.rootsclassic.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elucent.rootsclassic.item.SpellPowderItem;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;

public class ComponentRecipe implements IRecipe<IInventory> {
	private static final int MAX_INGREDIENTS = 4;

	private final ResourceLocation id;
	private final ResourceLocation effectResult;
	private final String group;
	private final ItemStack recipeOutput;
	private final NonNullList<Ingredient> materials;
	private final boolean needsMixin;

	public ComponentRecipe(ResourceLocation idIn, ResourceLocation effectResult, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn, boolean needsMixin) {
		this.id = idIn;
		this.effectResult = effectResult;
		this.group = groupIn;
		this.recipeOutput = recipeOutputIn;
		this.materials = recipeItemsIn;
		this.needsMixin = needsMixin;
	}

	public ComponentRecipe(ResourceLocation idIn, ResourceLocation effectResult, String groupIn, NonNullList<Ingredient> recipeItemsIn, boolean needsMixin) {
		this(idIn, effectResult, groupIn, new ItemStack(RootsRegistry.SPELL_POWDER.get()), recipeItemsIn, needsMixin);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	public ResourceLocation getEffectResult() {
		return effectResult;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RootsRecipes.COMPONENT_SERIALIZER.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public ItemStack getCraftingResult(IInventory inventory) {
		ItemStack outputStack = getRecipeOutput();
		if(outputStack.getItem() instanceof SpellPowderItem) {
			SpellPowderItem.createData(outputStack, this.getEffectResult(), inventory);
		}
		return outputStack;
	}

	@Override
	public boolean canFit(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return recipeOutput.copy();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return materials;
	}

	@Override
	public IRecipeType<?> getType() {
		return RootsRecipes.COMPONENT_RECIPE_TYPE;
	}

	public boolean needsMixin() {
		return needsMixin;
	}

	public TranslationTextComponent getLocalizedName() {
		return new TranslationTextComponent("rootsclassic.component." + this.getId());
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(getLocalizedName() + ": ");
		for (Ingredient mat : getIngredients()) {
			if(!mat.hasNoMatchingItems()) {
				s.append(Arrays.toString(mat.getMatchingStacks())).append(" ");
			} else {
				s.append("One of the ingredients has no matching ItemStack's");
			}
		}
		return s.toString();
	}

	@Override
	public boolean matches(IInventory inventory, World worldIn) {
		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();

		int i = 0;
		for (int j = 0; j < inventory.getSizeInventory(); j++) {
			ItemStack stack = inventory.getStackInSlot(j);
			if (!stack.isEmpty() && !isSupplementItem(stack)) {
				++i;
				inputs.add(stack);
			}
		}
		return i == this.materials.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.materials) != null;
	}

	/**
	 * True if not part of a recipe but just a recipe booster
	 *
	 * @param stack
	 * @return
	 */
	private boolean isSupplementItem(ItemStack stack) {
		if(getRecipeOutput().getItem() instanceof SpellPowderItem) {
			return stack.getItem() == RootsRegistry.OLD_ROOT.get() ||
					stack.getItem() == RootsRegistry.VERDANT_SPRIG.get() ||
					stack.getItem() == RootsRegistry.INFERNAL_BULB.get() ||
					stack.getItem() == RootsRegistry.DRAGONS_EYE.get() ||
					stack.getItem() == Items.GLOWSTONE_DUST ||
					stack.getItem() == Items.REDSTONE ||
					stack.getItem() == Items.GUNPOWDER;
		} else {
			return false;
		}
	}

	public static int getModifierCapacity(IInventory inventory) {
		int maxCapacity = -1;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack.getItem() == RootsRegistry.OLD_ROOT.get() && maxCapacity < 0) {
				maxCapacity = 0;
			}
			if (stack.getItem() == RootsRegistry.VERDANT_SPRIG.get() && maxCapacity < 1) {
				maxCapacity = 1;
			}
			if (stack.getItem() == RootsRegistry.INFERNAL_BULB.get() && maxCapacity < 2) {
				maxCapacity = 2;
			}
			if (stack.getItem() == RootsRegistry.DRAGONS_EYE.get() && maxCapacity < 3) {
				maxCapacity = 3;
			}
		}
		return maxCapacity;
	}

	public static int getModifierCount(IInventory inventory) {
		int count = 0;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack.getItem() == Items.GLOWSTONE_DUST) {
				count++;
			} else if (stack.getItem() == Items.REDSTONE) {
				count++;
			} else if (stack.getItem() == Items.GUNPOWDER) {
				count++;
			}
		}
		return count;
	}

	public static class SerializeComponentRecipe extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ComponentRecipe> {
		public ComponentRecipe read(ResourceLocation recipeId, JsonObject json) {
			String s = JSONUtils.getString(json, "group", "");
			NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
			if (nonnulllist.isEmpty()) {
				throw new JsonParseException("No ingredients for component recipe");
			} else if (nonnulllist.size() > MAX_INGREDIENTS) {
				throw new JsonParseException("Too many ingredients for component recipe the max is " + MAX_INGREDIENTS);
			} else {
				boolean needsMixin = JSONUtils.getBoolean(json, "needs_mixin", true);
				String effect = JSONUtils.getString(json, "effect");
				ResourceLocation effectResult = ResourceLocation.tryCreate(effect);
				ItemStack itemstack;
				if(JSONUtils.hasField(json, "result")) {
					itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
					return new ComponentRecipe(recipeId, effectResult, s, itemstack, nonnulllist, needsMixin);
				} else {
					return new ComponentRecipe(recipeId, effectResult, s, nonnulllist, needsMixin);
				}
			}
		}

		private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();

			for(int i = 0; i < ingredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.deserialize(ingredientArray.get(i));
				if (!ingredient.hasNoMatchingItems()) {
					nonnulllist.add(ingredient);
				}
			}

			return nonnulllist;
		}

		public ComponentRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			String s = buffer.readString(32767);
			int i = buffer.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

			for(int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.read(buffer));
			}

			boolean needsMixin = buffer.readBoolean();
			ResourceLocation effectResult = buffer.readResourceLocation();
			ItemStack itemstack = buffer.readItemStack();
			return new ComponentRecipe(recipeId, effectResult, s, itemstack, nonnulllist, needsMixin);
		}

		public void write(PacketBuffer buffer, ComponentRecipe recipe) {
			buffer.writeString(recipe.group);
			buffer.writeVarInt(recipe.materials.size());

			for(Ingredient ingredient : recipe.materials) {
				ingredient.write(buffer);
			}

			buffer.writeBoolean(recipe.needsMixin);
			buffer.writeResourceLocation(recipe.effectResult);
			buffer.writeItemStack(recipe.recipeOutput);
		}
	}
}
