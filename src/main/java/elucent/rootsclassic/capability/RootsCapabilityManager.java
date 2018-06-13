package elucent.rootsclassic.capability;

import elucent.rootsclassic.Const;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RootsCapabilityManager {

  @CapabilityInject(IManaCapability.class)
  public static final Capability<IManaCapability> manaCapability = null;

  public static void preInit() {
    CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), DefaultManaCapability.class);
  }

  @SubscribeEvent
  public void onAddCapabilities(AttachCapabilitiesEvent e) {
    if (e.getObject() instanceof EntityPlayer) {
      //  ManaCapabilityProvider provider = new ManaCapabilityProvider(!e.getObject().hasCapability(manaCapability, null));
      e.addCapability(new ResourceLocation(Const.MODID, "manacapability"),
          new ManaCapabilityProvider(true));
    }
  }
}
