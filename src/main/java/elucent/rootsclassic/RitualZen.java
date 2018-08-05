package elucent.rootsclassic;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraftforge.fml.common.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rootsclassic.Ritual")
@ZenRegister
public class RitualZen {

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setRitualItems(String name, IItemStack altar, IItemStack incense) {
    //
  }
}
