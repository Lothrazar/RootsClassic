package elucent.rootsclassic;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.ComponentRecipe;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.RitualManager;
import net.minecraftforge.fml.common.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rootsclassic.Ritual")
@ZenRegister
public class RitualZen {

  /**
   * Invalid ritual, names must be one of: staffCrafting,sylvanHoodCrafting,sylvanChestCrafting,sylvanLegsCrafting,sylvanBootsCrafting,
   * wildwoodHeadCrafting,wildwoodChestCrafting,wildwoodLegsCrafting,wildwoodBootsCrafting,acceleratorStoneCrafting,standingStone,entanglerStoneCrafting,growerStoneCrafting,healerStoneCrafting,igniterStoneCrafting,repulsorStoneCrafting,vacuumStoneCrafting,runicFocusCrafting,runicFocusCharging,livingPickaxeCrafting,livingAxeCrafting,livingSwordCrafting,livingHoeCrafting,livingShovelCrafting,causerain,banishrain,massbreeding,lifedrain,imbuer,cowSummoning,pigSummoning,sheepSummoning,chickenSummoning,rabbitSummoning,zombieSummoning,skeletonSummoning,spiderSummoning,caveSpiderSummoning,slimeSummoning,creeperSummoning,endermanSummoning,sacrifice,flare,grow,engravedCrafting,timeshift,
   * 
   * 
   */
  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setRitualItems(String name, IItemStack altar[], IItemStack[] incense) {
    //
    ComponentRecipe found = ComponentManager.getRecipe("name");
    if (found == null) {
      String names = "";
      for (RitualBase c : RitualManager.rituals) {
        names += c.getName() + ",";
      }
      Roots.logger.info(names);
      throw new IllegalArgumentException("Invalid ritual, names must be one of: " + names);
    }
  }
}
