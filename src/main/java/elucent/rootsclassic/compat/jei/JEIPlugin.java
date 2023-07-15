package elucent.rootsclassic.compat.jei;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.jei.category.MortarCategory;
import elucent.rootsclassic.compat.jei.category.RitualCategory;
import elucent.rootsclassic.compat.jei.wrapper.RitualWrapper;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

  public static final ResourceLocation PLUGIN_UID = new ResourceLocation(Const.MODID, "main");
  public static final ResourceLocation MORTAR = new ResourceLocation(Const.MODID, "mortar");
  public static final RecipeType<ComponentRecipe> MORTAR_TYPE = RecipeType.create(Const.MODID, "mortar", ComponentRecipe.class);
  public static final ResourceLocation RITUAL = new ResourceLocation(Const.MODID, "ritual");
  public static final RecipeType<RitualWrapper> RITUAL_TYPE = RecipeType.create(Const.MODID, "ritual", RitualWrapper.class);
  @Nullable
  private IRecipeCategory<ComponentRecipe> mortarCategory;
  @Nullable
  private IRecipeCategory<RitualWrapper> ritualCategory;

  @Override
  public ResourceLocation getPluginUid() {
    return PLUGIN_UID;
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
    registration.addRecipeCatalyst(new ItemStack(RootsRegistry.MORTAR.get()), MORTAR_TYPE);
    registration.addRecipeCatalyst(new ItemStack(RootsRegistry.ALTAR.get()), RITUAL_TYPE);
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    IJeiHelpers jeiHelpers = registration.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    registration.addRecipeCategories(
        mortarCategory = new MortarCategory(guiHelper),
        ritualCategory = new RitualCategory(guiHelper));
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    for (RegistryObject<Item> registryObject : RootsRegistry.ITEMS.getEntries()) {
      Item item = registryObject.get();
      if (item != null) {
        registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, Component.translatable(item.getDescriptionId() + ".guide"));
      }
    }
    assert MORTAR_TYPE != null;
    assert RITUAL_TYPE != null;
    ClientLevel world = Objects.requireNonNull(Minecraft.getInstance().level);
    registration.addRecipes(MORTAR_TYPE, world.getRecipeManager().getAllRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get()));
    registration.addRecipes(RITUAL_TYPE, getRituals(world));
  }

  public List<RitualWrapper> getRituals(Level world) {
    var ritualRecipes = world.getRecipeManager().getAllRecipesFor(RootsRecipes.RITUAL_RECIPE_TYPE.get());
    return ritualRecipes.stream().map(RitualWrapper::new).toList();
  }
}
