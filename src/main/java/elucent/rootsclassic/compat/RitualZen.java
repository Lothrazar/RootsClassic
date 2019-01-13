package elucent.rootsclassic.compat;

import java.util.Arrays;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.RitualManager;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import net.minecraftforge.fml.common.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rootsclassic.Ritual")
@ZenRegister
public class RitualZen {

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void addCraftingRitual(String uniqueName, IItemStack output, int level, double r, double g, double b,
      IItemStack incenses[], IItemStack ingredients[]) {
    RitualCrafting newCraft = new RitualCrafting(uniqueName, level, r, g, b);
    newCraft.setResult(SpellZen.toStack(output));
    newCraft.setIngredients(Arrays.asList(SpellZen.toStacks(ingredients)));
    newCraft.setIncenses(Arrays.asList(SpellZen.toStacks(incenses)));
    RitualManager.addRitual(newCraft);
  }

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setPrimaryColor(String name, double r, double g, double b) {
    RitualBase found = findRitualByName(name);
    found.setPrimaryColor(r, g, b);
  }

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setSecondaryColor(String name, double r, double g, double b) {
    RitualBase found = findRitualByName(name);
    found.setSecondaryColor(r, g, b);
  }

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setLevel(String name, int level) {
    RitualBase found = findRitualByName(name);
    found.setLevel(level);
  }

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
    RitualBase found = findRitualByName(name);
    Roots.logger.info("[ZenScript:Ritual] changing ingredients " + found.getName());
    found.setIngredients(Arrays.asList(SpellZen.toStacks(items)));
  }

  @Optional.Method(modid = "crafttweaker")
  @ZenMethod
  public static void setRitualIncense(String name, IItemStack[] items) {
    RitualBase found = findRitualByName(name);
    Roots.logger.info("[ZenScript:Ritual] changing incense " + found.getName());
    found.setIncenses(Arrays.asList(SpellZen.toStacks(items)));
  }

  private static RitualBase findRitualByName(String name) {
    RitualBase found = RitualManager.getRitualFromName(name);
    if (found == null) {
      String names = "";
      for (RitualBase c : RitualManager.rituals) {
        names += c.getName() + ",";
      }
      Roots.logger.info(names);
      throw new IllegalArgumentException("Invalid ritual[" + name + "], names must be one of: " + names);
    }
    return found;
  }
}
