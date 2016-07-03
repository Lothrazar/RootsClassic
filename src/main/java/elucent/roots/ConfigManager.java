package elucent.roots;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigManager {
	public static int oldRootDropChance, verdantSprigDropChance, infernalStemDropChance, dragonsEyeDropChance, berriesDropChance;
	public static boolean showTabletWave;
	public static int chargeTicks;
	public static int manaBarOffset;
	
	public static void load(FMLPreInitializationEvent event){
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	config.addCustomCategoryComment("client", "Settings that affect clientside graphical preferences.");
    	config.addCustomCategoryComment("world", "Settings related to things in the world.");
    	config.addCustomCategoryComment("spells", "Settings that affect various aspects of spells and spell casting.");
    	oldRootDropChance = config.getInt("oldRootDropChance", "world", 40, 0, 999, "Old Roots will drop from tall grass with a 1/oldRootDropChance probability.");
    	verdantSprigDropChance = config.getInt("verdantSprigDropChance", "world", 30, 0, 999, "Verdant Sprigs will drop from grown crops with a 1/verdantSprigDropChance probability.");
    	infernalStemDropChance = config.getInt("infernalBulbDropChance", "world", 20, 0, 999, "Infernal Bulbs will drop from nether wart with a 1/infernalBulbDropChance probability.");
    	dragonsEyeDropChance = config.getInt("dragonsEyeDropChance", "world", 10, 0, 999, "Dragon's Eyes will drop from chorus flowers with a 1/dragonsEyeDropChance probability.");
    	berriesDropChance = config.getInt("berriesDropChance", "world", 25, 0, 999, "Berries will drop from oak leaves with a 1/berriesDropChance probability.");
    	showTabletWave = config.getBoolean("showTabletWave", "client", true, "Toggles the wave effect in the Runic Tablet GUI.");
    	chargeTicks = config.getInt("staffChargeTicks", "spells", 20, 1, 999, "The number of ticks required to prepare a spell with a staff.");
    	manaBarOffset = config.getInt("manaBarOffset", "client", 49, 0, 999, "The number of pixels above the bottom of the screen that the mana bar should be rendered. If it's conflicting with a bar from another mod, raising it by 10 will normally position it right.");
    	config.save();
	}
}
