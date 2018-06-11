package elucent.rootsclassic;

import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Const.MODID, name = Roots.NAME, version = Roots.VERSION)
public class Roots
{
	public static final String NAME = "Roots";
    public static final String VERSION = "0.107";
    
  public static CreativeTabs tab = new CreativeTabs(Const.MODID) {
    	@Override
    	public String getTabLabel(){
    		return "roots";
    	}
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem(){
			return RegistryManager.dustPetal;
		}
	};
    
  @SidedProxy(clientSide = "elucent." + Const.MODID + ".proxy.ClientProxy", serverSide = "elucent." + Const.MODID + ".proxy.ServerProxy")
    public static CommonProxy proxy;
    
  @Instance(Const.MODID)
    public static Roots instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	ConfigManager.load(event);
    	MinecraftForge.EVENT_BUS.register(new EventManager());
    	MinecraftForge.EVENT_BUS.register(new RootsCapabilityManager());
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	proxy.postInit(event);
    }
}
