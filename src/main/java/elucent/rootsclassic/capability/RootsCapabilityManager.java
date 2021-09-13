package elucent.rootsclassic.capability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import elucent.rootsclassic.Const;

public class RootsCapabilityManager {

  @CapabilityInject(IManaCapability.class)
  public static final Capability<IManaCapability> MANA_CAPABILITY = null;

  @SuppressWarnings("deprecated")
  public static void register() {
    CapabilityManager.INSTANCE.register(IManaCapability.class);
  }

  @SubscribeEvent
  public void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof Player) {
      try {
        event.addCapability(new ResourceLocation(Const.MODID, "manacapability"), new ManaCapabilityProvider());
      }
      catch (Exception e1) {
        //        Roots.logger.error("Capability", e1);
      }
    }
  }
}
