package elucent.rootsclassic.capability;

import elucent.rootsclassic.Const;
import net.minecraft.entity.Entity;
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
    //yes this breakpoint gets hit on game start
    //    CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), DefaultManaCapability.class);
    CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), DefaultManaCapability::new);
  }

  @SubscribeEvent
  public void onEntityConstruct(AttachCapabilitiesEvent<Entity> e) {
    if (e.getObject() instanceof EntityPlayer) {
      try {
        e.addCapability(new ResourceLocation(Const.MODID, "manacapability"), new ManaCapabilityProvider(true));
      }
      catch (Exception e1) {
        //        Roots.logger.error("Capability", e1);
      }
    }
  }
}
