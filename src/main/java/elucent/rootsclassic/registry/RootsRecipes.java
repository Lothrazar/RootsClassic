package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.ComponentRecipe.SerializeComponentRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RootsRecipes {
  public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Const.MODID);

  public static final RecipeType<ComponentRecipe> COMPONENT_RECIPE_TYPE = RecipeType.register(new ResourceLocation(Const.MODID, "component").toString());
  public static final RegistryObject<SerializeComponentRecipe> COMPONENT_SERIALIZER = RECIPE_SERIALIZERS.register("component", SerializeComponentRecipe::new);
}
