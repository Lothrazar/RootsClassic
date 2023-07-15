package elucent.rootsclassic.capability;

import elucent.rootsclassic.Const;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RootsCapabilityManager {

  public static final Capability<IManaCapability> MANA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.register(IManaCapability.class);
  }

  @SubscribeEvent
  public void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof Player) {
      event.addCapability(new ResourceLocation(Const.MODID, "manacapability"), new ManaCapability());
    }
  }
}
