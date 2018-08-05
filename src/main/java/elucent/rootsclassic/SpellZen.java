package elucent.rootsclassic;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.ComponentRecipe;
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
    //
    ComponentRecipe found = ComponentManager.getRecipe("name");
    if (found == null) {
      String names = "";
      for (ComponentRecipe c : ComponentManager.recipes) {
        names += c.getEffectResult() + ",";
      }
      Roots.logger.info(names);
      throw new IllegalArgumentException("Invalid spell, names must be one of: " + names);
    }
  }
}
