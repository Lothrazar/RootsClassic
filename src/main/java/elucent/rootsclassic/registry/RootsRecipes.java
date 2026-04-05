package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RitualRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RootsRecipes {

  public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Const.MODID);
  public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Const.MODID);
  public static final Supplier<RecipeType<ComponentRecipe>> COMPONENT_RECIPE_TYPE = RECIPE_TYPES.register("component", () -> new RecipeType<>() {});
  public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ComponentRecipe>> COMPONENT_SERIALIZER = RECIPE_SERIALIZERS.register("component", () -> ComponentRecipe.SERIALIZER);
  public static final Supplier<RecipeType<RitualRecipe>> RITUAL_RECIPE_TYPE = RECIPE_TYPES.register("ritual", () -> new RecipeType<>() {});
  public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RitualRecipe>> RITUAL_SERIALIZER = RECIPE_SERIALIZERS.register("ritual", () -> RitualRecipe.SERIALIZER);
}
