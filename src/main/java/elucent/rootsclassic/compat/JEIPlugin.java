package elucent.rootsclassic.compat;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fmllegacy.RegistryObject;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

  public static final ResourceLocation PLUGIN_UID = new ResourceLocation(Const.MODID, "main");

  @Override
  public ResourceLocation getPluginUid() {
    return PLUGIN_UID;
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    for (RegistryObject<Item> registryObject : RootsRegistry.ITEMS.getEntries()) {
      Item item = registryObject.get();
      if (item != null) {
        registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, new TranslatableComponent(item.getDescriptionId() + ".guide"));
      }
    }
  }
}
