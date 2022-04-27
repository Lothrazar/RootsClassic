package elucent.rootsclassic.ritual;


import elucent.rootsclassic.Const;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Const.MODID)
public class RitualBaseRegistry {
	public static IForgeRegistry<RitualBase> RITUALS;

	@SubscribeEvent
	public static void onNewRegistry(RegistryEvent.NewRegistry event) {
		RegistryBuilder<RitualBase> registryBuilder = new RegistryBuilder<>();
		registryBuilder.setName(new ResourceLocation(Const.MODID, "ritual"));
		registryBuilder.setType(RitualBase.class);
		registryBuilder.allowModification();
		RITUALS = registryBuilder.create();
	}
}
