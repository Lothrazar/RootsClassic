package elucent.rootsclassic.compat;

import java.util.Arrays;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.ComponentRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rootsclassic.Spell")
@ZenRegister
public class SpellZen {

  /**
   * Invalid spell, names must be one of:
   * rosebush,dandelion,chorus,netherwart,peony,sunflower,azurebluet,allium,lilac,whitetulip,redtulip,blueorchid,poppy,poisonouspotato,orangetulip,pinktulip,oxeyedaisy,lilypad,apple,midnightbloom,
   * flareorchid,radiantdaisy,
   * 
   */
  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setSpellItems(String name, IItemStack items[]) {
    if (items.length == 0 || items.length > 4) {
      throw new IllegalArgumentException("Invalid spell ingredients, must be in range [1,4]");
    }
    ComponentRecipe found = findSpellByName(name);
    Roots.logger.info("[ZenScript:Spell] changing recipe " + found.getLocalizedName());
    found.setMaterials(Arrays.asList(toStacks(items)));
  }

  private static ComponentRecipe findSpellByName(String name) {
    ComponentRecipe found = ComponentManager.getSpellFromName(name);
    if (found == null) {
      String names = "";
      for (ComponentRecipe c : ComponentManager.recipes) {
        names += c.getEffectResult() + ",";
      }
      //  Roots.logger.info(names);
      throw new IllegalArgumentException("Invalid spell [" + name + "], names must be one of: " + names);
    }
    return found;
  }

  /**
   * THANKS TO https://github.com/jaredlll08/MTLib/blob/1.12/src/main/java/com/blamejared/mtlib/helpers/InputHelper.java @ https://github.com/jaredlll08/MTLib which is MIT license
   * https://github.com/jaredlll08/MTLib/blob/1.12/LICENSE.md
   */
  @Optional.Method(modid = "crafttweaker")
  public static ItemStack toStack(IItemStack iStack) {
    if (iStack == null) {
      return ItemStack.EMPTY;
    }
    else {
      Object internal = iStack.getInternal();
      if (!(internal instanceof ItemStack)) {
        return ItemStack.EMPTY;
      }
      return (ItemStack) internal;
    }
  }

  /**
   * THANKS TO https://github.com/jaredlll08/MTLib/blob/1.12/src/main/java/com/blamejared/mtlib/helpers/InputHelper.java @ https://github.com/jaredlll08/MTLib which is MIT license
   * https://github.com/jaredlll08/MTLib/blob/1.12/LICENSE.md
   */
  @Optional.Method(modid = "crafttweaker")
  public static ItemStack[] toStacks(IItemStack[] iStack) {
    if (iStack == null) {
      return null;
    }
    else {
      ItemStack[] output = new ItemStack[iStack.length];
      for (int i = 0; i < iStack.length; i++) {
        output[i] = toStack(iStack[i]);
      }
      return output;
    }
  }
}
