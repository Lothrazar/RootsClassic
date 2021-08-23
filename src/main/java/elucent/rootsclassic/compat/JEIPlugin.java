package elucent.rootsclassic.compat;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

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
			if(item != null) {
				registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, new TranslationTextComponent(item.getTranslationKey() + ".guide"));
			}
		}
	}
}
