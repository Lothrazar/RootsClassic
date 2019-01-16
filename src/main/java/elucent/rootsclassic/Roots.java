package elucent.rootsclassic;

import org.apache.logging.log4j.Logger;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.config.ConfigManager;
import elucent.rootsclassic.config.EventConfigChanged;
import elucent.rootsclassic.event.EventComponentSpells;
import elucent.rootsclassic.event.EventHarvestDrops;
import elucent.rootsclassic.event.EventManaBar;
import elucent.rootsclassic.proxy.CommonProxy;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Const.MODID, useMetadata = true, updateJSON = "https://raw.githubusercontent.com/PrinceOfAmber/RootsClassic/master/update.json", acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12,)", guiFactory = "elucent." + Const.MODID + ".config.IngameConfigFactory")
public class Roots {

  public static CreativeTabs tab = new CreativeTabs(Const.MODID) {

    @Override
    public String getTabLabel() {
      return Const.MODID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
      return new ItemStack(RegistryManager.dustPetal);
    }
  };
  @SidedProxy(clientSide = "elucent." + Const.MODID + ".proxy.ClientProxy", serverSide = "elucent." + Const.MODID + ".proxy.ServerProxy")
  public static CommonProxy proxy;
  @Instance(Const.MODID)
  public static Roots instance;
  public static Logger logger;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    ConfigManager.load(event);
    MinecraftForge.EVENT_BUS.register(new EventComponentSpells());
    MinecraftForge.EVENT_BUS.register(new EventHarvestDrops());
    MinecraftForge.EVENT_BUS.register(new EventManaBar());
    MinecraftForge.EVENT_BUS.register(new RootsCapabilityManager());
    MinecraftForge.EVENT_BUS.register(new EventConfigChanged());
    MinecraftForge.EVENT_BUS.register(RegistryManager.class);
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }

  @EventHandler
  public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
    // https://tutorials.darkhax.net/tutorials/jar_signing/
    String source = (event.getSource() == null) ? "" : event.getSource().getName() + " ";
    String msg = "Roots Classic: Invalid fingerprint detected! The file " + source + "may have been tampered with. This version will NOT be supported by the author!";
    if (logger == null) {
      System.out.println(msg);
    }
    else {
      logger.error(msg);
    }
  }
  public static void chatMessage(EntityPlayer player, String message) {
    if (player.world.isRemote)
      player.sendMessage(new TextComponentString(lang(message)));
  }

  public static void statusMessage(EntityPlayer player, String message) {
    if (player.world.isRemote)
      player.sendStatusMessage(new TextComponentString(lang(message)), true);
  }

  public static String lang(String message) {
    return I18n.format(message);
  }
}
