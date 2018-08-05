package elucent.rootsclassic;

import java.util.Arrays;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
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
   * wildwoodHeadCrafting,wildwoodChestCrafting,wildwoodLegsCrafting,wildwoodBootsCrafting, acceleratorStoneCrafting,standingStone,entanglerStoneCrafting,growerStoneCrafting,
   * healerStoneCrafting,igniterStoneCrafting,repulsorStoneCrafting,vacuumStoneCrafting,runicFocusCrafting,runicFocusCharging,livingPickaxeCrafting,livingAxeCrafting,livingSwordCrafting,livingHoeCrafting,livingShovelCrafting,causerain,banishrain,massbreeding,lifedrain,imbuer,cowSummoning,pigSummoning,sheepSummoning,chickenSummoning,rabbitSummoning,zombieSummoning,skeletonSummoning,spiderSummoning,caveSpiderSummoning,slimeSummoning,creeperSummoning,endermanSummoning,sacrifice,flare,grow,engravedCrafting,timeshift,
   * 
   * 
   */
  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setRitualIngredients(String name, IItemStack items[]) {
    //
    RitualBase found = RitualManager.getRitualFromName(name);
    if (found == null) {
      String names = "";
      for (RitualBase c : RitualManager.rituals) {
        names += c.getName() + ",";
      }
      //    Roots.logger.info(names);
      throw new IllegalArgumentException("Invalid ritual[" + name + "], names must be one of: " + names);
    }
    if (items.length == 0 || items.length > 3) {
      throw new IllegalArgumentException("Invalid ritual ingredients, must be in range [1,3]");
    }
    Roots.logger.info("[ZenScript:Ritual] changing ingredients " + found.getName());
    found.setIngredients(Arrays.asList(SpellZen.toStacks(items)));
  }

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setRitualIncense(String name, IItemStack[] items) {
    //
    RitualBase found = RitualManager.getRitualFromName(name);
    if (found == null) {
      String names = "";
      for (RitualBase c : RitualManager.rituals) {
        names += c.getName() + ",";
      }
      //    Roots.logger.info(names);
      throw new IllegalArgumentException("Invalid ritual[" + name + "], names must be one of: " + names);
    }

    if (items.length == 0 || items.length > 4) {
      throw new IllegalArgumentException("Invalid ritual incense, must be in range [1,4]");
    }
    Roots.logger.info("[ZenScript:Ritual] changing incense " + found.getName());
    found.setIncenses(Arrays.asList(SpellZen.toStacks(items)));

  }
}
