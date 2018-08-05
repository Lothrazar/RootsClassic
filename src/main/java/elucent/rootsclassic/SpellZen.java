package elucent.rootsclassic;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraftforge.fml.common.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rootsclassic.Spell")
@ZenRegister
public class SpellZen {

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setSpellItems(String name, IItemStack altar) {
    //
  }
}
