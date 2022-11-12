package elucent.rootsclassic.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RitualRecipe implements Recipe<Container> {
    private final ResourceLocation id;

    private final ItemStack result;

    private final NonNullList<ItemStack> materials;
    private final NonNullList<ItemStack> incenses;

    private final NonNullList<Ingredient> ingredients;

    private final int level;

    private final double red;
    private final double green;
    private final double blue;

    private final RitualCrafting ritual;

    public RitualRecipe(ResourceLocation id, ItemStack result, NonNullList<ItemStack> materials, NonNullList<ItemStack> incenses, int level, double red, double green, double blue) {
        this.id = id;
        this.result = result;
        this.materials = materials;
        this.incenses = incenses;
        this.level = level;
        this.red = red;
        this.green = green;
        this.blue = blue;

        this.ingredients = NonNullList.withSize(materials.size(), Ingredient.EMPTY);
        for (int i = 0; i < materials.size(); i++) {
            ingredients.set(i, Ingredient.of(materials.get(i)));
        }

        ritual = new RitualCrafting(level, red, green, blue).setResult(result);

        materials.forEach(ritual::addIngredient);
        incenses.forEach(ritual::addIncense);
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
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
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

    public boolean matchesIngredients(List<ItemStack> altarInv) {
        if (altarInv.size() != materials.size()) return false;

        var available = new ArrayList<>(altarInv);
        return materials.stream().allMatch(ingredient -> {
            var match = available.stream().filter(it -> ItemStack.matches(it, ingredient)).findFirst();
            match.ifPresent(available::remove);
            return match.isPresent();
        });
    }

    public RitualBase getRitual() {
        return ritual;
    }

    public static class SerializeRitualRecipe extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RitualRecipe> {

        public RitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var ingredients = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            var incenses = readIngredients(GsonHelper.getAsJsonArray(json, "incenses"));
            if (ingredients.isEmpty() && incenses.isEmpty()) {
                throw new JsonParseException("No ingredients for ritual recipe");
            } else {
                var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                var level = GsonHelper.getAsInt(json, "level");
                var red = GsonHelper.getAsDouble(json, "red");
                var green = GsonHelper.getAsDouble(json, "green");
                var blue = GsonHelper.getAsDouble(json, "blue");
                return new RitualRecipe(recipeId, result, ingredients, incenses, level, red, green, blue);
            }
        }

        private static NonNullList<ItemStack> readIngredients(JsonArray ingredientArray) {
            NonNullList<ItemStack> list = NonNullList.create();
            for (int i = 0; i < ingredientArray.size(); ++i) {
                var stack = ShapedRecipe.itemStackFromJson(ingredientArray.get(i).getAsJsonObject());
                if (!stack.isEmpty()) {
                    list.add(stack);
                }
            }
            return list;
        }

        public RitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var level = buffer.readVarInt();
            var red = buffer.readDouble();
            var green = buffer.readDouble();
            var blue = buffer.readDouble();

            var size = buffer.readVarInt();
            var ingredients = NonNullList.withSize(size, ItemStack.EMPTY);
            ingredients.replaceAll(ignored -> buffer.readItem());

            size = buffer.readVarInt();
            var incenses = NonNullList.withSize(size, ItemStack.EMPTY);
            incenses.replaceAll(ignored -> buffer.readItem());

            ItemStack result = buffer.readItem();
            return new RitualRecipe(recipeId, result, ingredients, incenses, level, red, green, blue);
        }

        public void toNetwork(FriendlyByteBuf buffer, RitualRecipe recipe) {
            buffer.writeVarInt(recipe.level);
            buffer.writeDouble(recipe.red);
            buffer.writeDouble(recipe.green);
            buffer.writeDouble(recipe.blue);

            buffer.writeVarInt(recipe.materials.size());
            for (ItemStack stack : recipe.materials) {
                buffer.writeItem(stack);
            }

            buffer.writeVarInt(recipe.incenses.size());
            for (ItemStack stack : recipe.incenses) {
                buffer.writeItem(stack);
            }

            buffer.writeItem(recipe.result);
        }
    }
}
