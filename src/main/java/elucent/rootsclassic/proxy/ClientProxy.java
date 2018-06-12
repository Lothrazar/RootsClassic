package elucent.rootsclassic.proxy;

import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.entity.fx.ParticleMagic;
import elucent.rootsclassic.entity.fx.ParticleMagicAltar;
import elucent.rootsclassic.entity.fx.ParticleMagicAltarLine;
import elucent.rootsclassic.entity.fx.ParticleMagicAura;
import elucent.rootsclassic.entity.fx.ParticleMagicLine;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
    // RegistryManager.registerItemRenderers();
  }

  @Override
  public void init(FMLInitializationEvent event) {
    super.init(event);
    RegistryManager.registerColorHandlers();
  }

  @Override
  public void postInit(FMLPostInitializationEvent event) {
    super.postInit(event);
  }

  @Override
  public void spawnParticleMagicFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    ParticleMagic particle = new ParticleMagic(world, x, y, z, vx, vy, vz, r, g, b);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }

  @Override
  public void spawnParticleMagicLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    ParticleMagicLine particle = new ParticleMagicLine(world, x, y, z, vx, vy, vz, r, g, b);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }

  @Override
  public void spawnParticleMagicAltarLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    ParticleMagicAltarLine particle = new ParticleMagicAltarLine(world, x, y, z, vx, vy, vz, r, g, b);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }

  @Override
  public void spawnParticleMagicAltarFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    ParticleMagicAltar particle = new ParticleMagicAltar(world, x, y, z, vx, vy, vz, r, g, b);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }

  @Override
  public void spawnParticleMagicAuraFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    ParticleMagicAura particle = new ParticleMagicAura(world, x, y, z, vx, vy, vz, r, g, b);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
}
