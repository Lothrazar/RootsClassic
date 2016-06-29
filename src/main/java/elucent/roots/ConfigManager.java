package elucent.roots;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigManager {
	public static int oldRootDropChance, verdantSprigDropChance, infernalStemDropChance, dragonsEyeDropChance;
	public static boolean showTabletWave;
	
	public static void load(FMLPreInitializationEvent event){
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	config.addCustomCategoryComment("client", "Settings that affect clientside graphical preferences.");
    	config.addCustomCategoryComment("world", "Settings related to things in the world.");
    	oldRootDropChance = config.getInt("oldRootDropChance", "world", 40, 0, 999, "Old Roots will drop from tall grass with a 1/oldRootDropChance probability.");
    	verdantSprigDropChance = config.getInt("verdantSprigDropChance", "world", 30, 0, 999, "Verdant Sprigs will drop from grown crops with a 1/verdantSprigDropChance probability.");
    	infernalStemDropChance = config.getInt("infernalBulbDropChance", "world", 20, 0, 999, "Infernal Bulbs will drop from nether wart with a 1/infernalBulbDropChance probability.");
    	dragonsEyeDropChance = config.getInt("dragonsEyeDropChance", "world", 10, 0, 999, "Dragon's Eyes will drop from chorus flowers with a 1/dragonsEyeDropChance probability.");
    	showTabletWave = config.getBoolean("showTabletWave", "client", true, "Toggles the wave effect in the Runic Tablet GUI.");
    	config.save();
	}
}
