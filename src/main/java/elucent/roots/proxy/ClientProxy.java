package elucent.roots.proxy;

import elucent.roots.RegistryManager;
import elucent.roots.entity.fx.ParticleMagic;
import elucent.roots.entity.fx.ParticleMagicAltar;
import elucent.roots.entity.fx.ParticleMagicAltarLine;
import elucent.roots.entity.fx.ParticleMagicAura;
import elucent.roots.entity.fx.ParticleMagicLine;
import elucent.roots.item.ItemStaff;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		RegistryManager.registerItemRenderers();
	}
	
	public void init(FMLInitializationEvent event){
		super.init(event);
		RegistryManager.registerColorHandlers();
	}
	
	public void postInit(FMLPostInitializationEvent event){
		super.postInit(event);
	}
	
	@Override
	public void spawnParticleMagicFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		ParticleMagic particle = new ParticleMagic(world,x,y,z,vx,vy,vz,r,g,b);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
	
	@Override
	public void spawnParticleMagicLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		ParticleMagicLine particle = new ParticleMagicLine(world,x,y,z,vx,vy,vz,r,g,b);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
	
	@Override
	public void spawnParticleMagicAltarLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		ParticleMagicAltarLine particle = new ParticleMagicAltarLine(world,x,y,z,vx,vy,vz,r,g,b);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
	
	@Override
	public void spawnParticleMagicAltarFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		ParticleMagicAltar particle = new ParticleMagicAltar(world,x,y,z,vx,vy,vz,r,g,b);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
	
	@Override
	public void spawnParticleMagicAuraFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		ParticleMagicAura particle = new ParticleMagicAura(world,x,y,z,vx,vy,vz,r,g,b);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
}
