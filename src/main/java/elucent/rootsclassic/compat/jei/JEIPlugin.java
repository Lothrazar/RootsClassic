package elucent.rootsclassic.compat.jei;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.jei.category.MortarCategory;
import elucent.rootsclassic.compat.jei.category.RitualCategory;
import elucent.rootsclassic.compat.jei.wrapper.RitualWrapper;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RootsRecipeCache;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.Nullable;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

  public static final Identifier PLUGIN_UID = Const.modLoc("main");
  public static final Identifier MORTAR = Const.modLoc("mortar");
  public static final IRecipeType<ComponentRecipe> MORTAR_TYPE = IRecipeType.create(Const.MODID, "mortar", ComponentRecipe.class);
  public static final Identifier RITUAL = Const.modLoc("ritual");
  public static final IRecipeType<RitualWrapper> RITUAL_TYPE = IRecipeType.create(Const.MODID, "ritual", RitualWrapper.class);
  @Nullable
  private IRecipeCategory<ComponentRecipe> mortarCategory;
  @Nullable
  private IRecipeCategory<RitualWrapper> ritualCategory;

  @Override
  public Identifier getPluginUid() {
    return PLUGIN_UID;
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
    registration.addCraftingStation(MORTAR_TYPE, new ItemStack(RootsRegistry.MORTAR.get()));
    registration.addCraftingStation(RITUAL_TYPE, new ItemStack(RootsRegistry.ALTAR.get()));
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
    for (DeferredHolder<Item, ? extends Item> registryObject : RootsRegistry.ITEMS.getEntries()) {
      Item item = registryObject.get();
      if (item != null) {
        registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, Component.translatable(item.getDescriptionId() + ".guide"));
      }
    }
    assert MORTAR_TYPE != null;
    assert RITUAL_TYPE != null;
    registration.addRecipes(MORTAR_TYPE, RootsRecipeCache.getComponentRecipes()
	    .stream().map(RecipeHolder::value).toList());
    registration.addRecipes(RITUAL_TYPE, RootsRecipeCache.getRitualRecipes().stream()
      .map(holder -> new RitualWrapper(holder.value())).toList());
  }
}
