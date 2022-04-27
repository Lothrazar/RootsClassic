package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.ComponentRecipe.SerializeComponentRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RootsRecipes {
  public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Const.MODID);
  public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Const.MODID);


  public static final RegistryObject<RecipeType<ComponentRecipe>> COMPONENT_RECIPE_TYPE = RECIPE_TYPES.register("component", () -> new RecipeType<>() {
  });
  public static final RegistryObject<SerializeComponentRecipe> COMPONENT_SERIALIZER = RECIPE_SERIALIZERS.register("component", SerializeComponentRecipe::new);
}
