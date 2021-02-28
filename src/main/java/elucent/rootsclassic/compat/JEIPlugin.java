package elucent.rootsclassic.compat;

import elucent.rootsclassic.RegistryManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements mezz.jei.api.IModPlugin {

  @SuppressWarnings("deprecation")
  @Override
  public void register(mezz.jei.api.IModRegistry registry) {
    for (Item item : RegistryManager.itemList) {
      //YES its deprecated. but the official wiki says to do this 
      //https://github.com/mezz/JustEnoughItems/wiki/Recipes-Overview
      registry.addDescription(new ItemStack(item), item.getTranslationKey() + ".guide");
    }
  }
}
