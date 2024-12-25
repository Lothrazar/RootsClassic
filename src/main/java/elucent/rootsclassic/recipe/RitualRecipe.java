package elucent.rootsclassic.recipe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.ritual.RitualEffect;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

@SuppressWarnings("rawtypes")
public class RitualRecipe<C> implements Recipe<Container> {

  private final ResourceLocation id;
  private final NonNullList<Ingredient> materials;
  private final NonNullList<Ingredient> incenses;
  public final int level;
  public final Color color;
  public final Color secondaryColor;
  public final RitualEffect<C> effect;
  public final C effectConfig;

  public RitualRecipe(ResourceLocation id, RitualEffect<C> effect, C effectConfig, NonNullList<Ingredient> materials, NonNullList<Ingredient> incenses, int level, Color color, Color secondaryColor) {
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
  public ItemStack assemble(Container inventory, RegistryAccess ra) {
    return getResultItem();
  }

  @Override
  public ItemStack getResultItem(RegistryAccess ra) {
    return getResultItem();
  }

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

  public Component getLocalizedName() {
    return Component.translatable(Const.MODID + ".ritual." + this.getId());
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

  public MutableComponent getInfoText() {
    return effect.getInfoText(effectConfig);
  }

  @SuppressWarnings("unchecked")
  public static class SerializeRitualRecipe implements RecipeSerializer<RitualRecipe<?>> {

    @Override
    public RitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
      var ingredients = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
      var incenses = readIngredients(GsonHelper.getAsJsonArray(json, "incenses"));
      if (ingredients.isEmpty() && incenses.isEmpty()) {
        throw new JsonParseException("No ingredients for ritual recipe");
      }
      else {
        var level = GsonHelper.getAsInt(json, "level");
        if (level < 0 || level > 2) throw new IllegalArgumentException("Level must be 0, 1 or 2");
        var effectId = new ResourceLocation(GsonHelper.getAsString(json, "effect"));
        var effect = RitualBaseRegistry.RITUALS.get().getValue(effectId);
        var effectConfig = effect.fromJSON(json);
        var color = Color.decode(GsonHelper.getAsString(json, "color"));
        var secondaryColor = json.has("secondaryColor") ? Color.decode(GsonHelper.getAsString(json, "secondaryColor")) : color;
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

    @Override
    public RitualRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
      var level = buffer.readVarInt();
      var color = new Color(buffer.readVarInt());
      var secondaryColor = new Color(buffer.readVarInt());
      var size = buffer.readVarInt();
      var ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
      ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
      size = buffer.readVarInt();
      var incenses = NonNullList.withSize(size, Ingredient.EMPTY);
      incenses.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
      var effectId = buffer.readResourceLocation();
      var effect = RitualBaseRegistry.RITUALS.get().getValue(effectId);
      var effectConfig = effect.fromNetwork(buffer);
      return new RitualRecipe(recipeId, effect, effectConfig, ingredients, incenses, level, color, secondaryColor);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, RitualRecipe recipe) {
      buffer.writeVarInt(recipe.level);
      buffer.writeVarInt(recipe.color.getRGB());
      buffer.writeVarInt(recipe.secondaryColor.getRGB());
      buffer.writeCollection((List<Ingredient>) recipe.materials, (buf, it) -> it.toNetwork(buf));
      buffer.writeCollection((List<Ingredient>) recipe.incenses, (buf, it) -> it.toNetwork(buf));
      buffer.writeResourceLocation(RitualBaseRegistry.RITUALS.get().getKey(recipe.effect));
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
    return effect.incenseMatches(incenseFromNearby, this);
  }

  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    effect.doEffect(levelAccessor, pos, inventory, incenses, effectConfig);
  }

  @Override
  public String toString() {
    return "RitualRecipe [id=" + id + ", level=" + level + ", effect=" + effect + "]";
  }
}