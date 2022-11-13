package elucent.rootsclassic.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.ritual.RitualEffect;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RitualRecipe<C> implements Recipe<Container> {
	private final ResourceLocation id;

	private final NonNullList<Ingredient> materials;
	private final NonNullList<Ingredient> incenses;

	public final int level;

	public final BlockPos color;
	public final BlockPos secondaryColor;

	public final RitualEffect<C> effect;
	public final C effectConfig;

	public RitualRecipe(ResourceLocation id, RitualEffect<C> effect, C effectConfig, NonNullList<Ingredient> materials, NonNullList<Ingredient> incenses, int level, BlockPos color, BlockPos secondaryColor) {
		this.id = id;
		this.materials = materials;
		this.incenses = incenses;
		this.level = level;
		this.effect = effect;
		this.effectConfig = effectConfig;
		this.color = color;
		this.secondaryColor = secondaryColor;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RootsRecipes.RITUAL_SERIALIZER.get();
	}

	@Override
	public ItemStack assemble(Container inventory) {
		return getResultItem();
	}

	@Override
	public ItemStack getResultItem() {
		return effect.getResult(effectConfig).copy();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return materials;
	}

	@Override
	public RecipeType<?> getType() {
		return RootsRecipes.RITUAL_RECIPE_TYPE.get();
	}

	public TranslatableComponent getLocalizedName() {
		return new TranslatableComponent(Const.MODID + ".ritual." + this.getId());
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return false;
	}

	@Override
	public boolean matches(Container inventory, Level worldIn) {
		return false;
	}

	public List<Ingredient> getIncenses() {
		return incenses;
	}

	public static class SerializeRitualRecipe extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RitualRecipe<?>> {

		public RitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			var ingredients = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
			var incenses = readIngredients(GsonHelper.getAsJsonArray(json, "incenses"));
			if (ingredients.isEmpty() && incenses.isEmpty()) {
				throw new JsonParseException("No ingredients for ritual recipe");
			} else {
				var level = GsonHelper.getAsInt(json, "level");
				if (level < 0 || level > 2) throw new IllegalArgumentException("Level must be 0, 1 or 2");

				var effectId = new ResourceLocation(GsonHelper.getAsString(json, "effect"));
				var effect = RitualBaseRegistry.RITUALS.get().getValue(effectId);
				var effectConfig = effect.fromJSON(json);

				var colorJson = GsonHelper.getAsJsonObject(json, "color");
				var color = new BlockPos(GsonHelper.getAsInt(colorJson, "red"), GsonHelper.getAsInt(colorJson, "blue"), GsonHelper.getAsInt(colorJson, "green"));

				var secondaryColorJson = json.has("secondaryColor") ? GsonHelper.getAsJsonObject(json, "secondaryColor") : colorJson;
				var secondaryColor = new BlockPos(GsonHelper.getAsInt(secondaryColorJson, "red"), GsonHelper.getAsInt(secondaryColorJson, "blue"), GsonHelper.getAsInt(secondaryColorJson, "green"));

				return new RitualRecipe(recipeId, effect, effectConfig, ingredients, incenses, level, color, secondaryColor);
			}
		}

		private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
			NonNullList<Ingredient> list = NonNullList.create();
			for (int i = 0; i < ingredientArray.size(); ++i) {
				var stack = Ingredient.fromJson(ingredientArray.get(i).getAsJsonObject());
				if (!stack.isEmpty()) {
					list.add(stack);
				}
			}
			return list;
		}

		public RitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			var level = buffer.readVarInt();

			var color = buffer.readBlockPos();
			var secondaryColor = buffer.readBlockPos();

			var size = buffer.readVarInt();
			var ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
			ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buffer));

			size = buffer.readVarInt();
			var incenses = NonNullList.withSize(size, Ingredient.EMPTY);
			incenses.replaceAll(ignored -> Ingredient.fromNetwork(buffer));

			var effectId = new ResourceLocation(buffer.readUtf());
			var effect = RitualBaseRegistry.RITUALS.get().getValue(effectId);
			var effectConfig = effect.fromNetwork(buffer);

			return new RitualRecipe(recipeId, effect, effectConfig, ingredients, incenses, level, color, secondaryColor);
		}

		public void toNetwork(FriendlyByteBuf buffer, RitualRecipe recipe) {
			buffer.writeVarInt(recipe.level);

			buffer.writeBlockPos(recipe.color);
			buffer.writeBlockPos(recipe.secondaryColor);

			buffer.writeCollection((List<Ingredient>) recipe.materials, (buf, it) -> it.toNetwork(buf));
			buffer.writeCollection((List<Ingredient>) recipe.incenses, (buf, it) -> it.toNetwork(buf));

			buffer.writeUtf(recipe.effect.getRegistryName().toString());
			recipe.effect.toNetwork(recipe.effectConfig, buffer);
		}
	}

	public boolean incenseMatches(Level levelAccessor, BlockPos pos) {
		ArrayList<ItemStack> incenseFromNearby = new ArrayList<>();
		List<BrazierBlockEntity> braziers = RitualPillars.getRecipeBraziers(levelAccessor, pos);
		for (BrazierBlockEntity brazier : braziers) {
			if (!brazier.getHeldItem().isEmpty()) {
				//              Roots.logger.info("found brazier item " + brazier.getHeldItem());
				incenseFromNearby.add(brazier.getHeldItem());
			}
		}
		return RootsUtil.matchesIngredients(incenseFromNearby, incenses);
	}


	public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
		effect.doEffect(levelAccessor, pos, inventory, incenses, effectConfig);
	}

}
