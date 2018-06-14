package elucent.rootsclassic;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigManager {

  //WORLD
  public static int oldRootDropChance, verdantSprigDropChance, infernalStemDropChance, dragonsEyeDropChance, berriesDropChance;
  //CLIENT
  public static boolean showTabletWave;
  //SPELLS
  //  public static String[] disabledComponents;
  public static int chargeTicks, staffUses, efficiencyBonus;
  public static int manaBarOffset;
  public static boolean disablePVP;
  private static Configuration config;

  public static void load(FMLPreInitializationEvent event) {
    config = new Configuration(event.getSuggestedConfigurationFile());
    config.load();
    syncAllConfig();
  }

  public static void syncAllConfig() {
    String category = Const.MODID + "." + Configuration.CATEGORY_CLIENT;
    config.addCustomCategoryComment(category, "Settings that affect clientside graphical preferences.");
    manaBarOffset = config.getInt("manaBarOffset", category, 49, 0, 32767, "The number of pixels above the bottom of the screen that the mana bar should be rendered. If it's conflicting with a bar from another mod, raising it by 10 will normally position it right.");
    showTabletWave = config.getBoolean("showTabletWave", category, true, "Toggles the wave effect in the Runic Tablet GUI.");
    category = Const.MODID + ".items";
    config.addCustomCategoryComment(category, "Settings related to actual gameplay-affecting features.");
    oldRootDropChance = config.getInt("oldRootDropChance", category, 40, 0, 32767, "Old Roots will drop from tall grass with a 1/oldRootDropChance probability.");
    verdantSprigDropChance = config.getInt("verdantSprigDropChance", category, 30, 0, 32767, "Verdant Sprigs will drop from grown crops with a 1/verdantSprigDropChance probability.");
    infernalStemDropChance = config.getInt("infernalBulbDropChance", category, 20, 0, 32767, "Infernal Bulbs will drop from nether wart with a 1/infernalBulbDropChance probability.");
    dragonsEyeDropChance = config.getInt("dragonsEyeDropChance", category, 10, 0, 32767, "Dragon's Eyes will drop from chorus flowers with a 1/dragonsEyeDropChance probability.");
    berriesDropChance = config.getInt("berriesDropChance", category, 25, 0, 32767, "Berries will drop from oak leaves with a 1/berriesDropChance probability.");
    category = Const.MODID + ".magic";
    chargeTicks = config.getInt("staffChargeTicks", category, 20, 1, 32767, "The number of ticks required to prepare a spell with a staff.");
    staffUses = config.getInt("staffUses", category, 65, 0, 32767, "The number of uses an unmodified staff will have upon being crafted.");
    efficiencyBonus = config.getInt("efficiencyBonusUses", category, 32, 0, 32767, "The number of additional uses each efficiency modifier gives.");
    disablePVP = config.getBoolean("disablePVP", category, false, "Whether or not damaging spells can affect players.");
    //TODO: spells category to disable component  on loop?
    //    disabledComponents = config.getStringList("disabledComponents", category, new String[] {
    //        "<example>", "<another example>"
    //    }, "A string list of all disabled components. Valid component names include: \"allium\", \"apple\", \"azurebluet\", \"blueorchid\", \"chorus\", \"dandelion\", \"flareorchid\", \"lilac\", \"lilypad\", \"midnightbloom\", \"netherwart\", \"orangetulip\", \"oxeyedaisy\", \"peony\", \"pinktulip\", \"poisonouspotato\", \"poppy\", \"radiantdaisy\", \"redtulip\", \"rosebush\", \"sunflower\", \"whitetulip\"");
    //TODO: rituals component to disable single on loop
    config.save();
  }

  public static Configuration getConfig() {
    return config;
  }
}
