package elucent.rootsclassic.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import elucent.rootsclassic.Const;

public class RootsCapabilityManager {

	@CapabilityInject(IManaCapability.class)
	public static final Capability<IManaCapability> MANA_CAPABILITY = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), new DefaultManaCapability());
	}

	@SubscribeEvent
	public void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity) {
			try {
				event.addCapability(new ResourceLocation(Const.MODID, "manacapability"), new ManaCapabilityProvider());
			} catch (Exception e1) {
				//        Roots.logger.error("Capability", e1);
			}
		}
	}
}
