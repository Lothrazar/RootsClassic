package elucent.roots.proxy;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.component.ComponentManager;
import elucent.roots.gui.GuiHandler;
import elucent.roots.mutation.MutagenManager;
import elucent.roots.research.ResearchManager;
import elucent.roots.ritual.RitualManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event){
		RegistryManager.init();
		RegistryManager.registerRecipes();
	}
	
	public void init(FMLInitializationEvent event){
		ComponentManager.init();
		RitualManager.init();
		MutagenManager.init();
	}
	
	public void postInit(FMLPostInitializationEvent event){
		Util.initOres();
		Util.initNaturalBlocks();
		NetworkRegistry.INSTANCE.registerGuiHandler(Roots.instance, new GuiHandler());
		ResearchManager.init();
	}
	
	public void spawnParticleMagicFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicAltarLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicAltarFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicAuraFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
}
