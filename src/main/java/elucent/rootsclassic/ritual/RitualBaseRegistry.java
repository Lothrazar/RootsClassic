package elucent.rootsclassic.ritual;

import elucent.rootsclassic.Const;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Const.MODID)
public class RitualBaseRegistry {
	public static final ResourceLocation registryLocation = new ResourceLocation(Const.MODID, "ritual");

	public static Supplier<IForgeRegistry<RitualEffect<?>>> RITUALS;

	@SubscribeEvent
	public static void onNewRegistry(NewRegistryEvent event) {
		RegistryBuilder<RitualEffect<?>> registryBuilder = new RegistryBuilder<>();
		registryBuilder.setName(registryLocation);
		registryBuilder.setType((Class<RitualEffect<?>>) (Class<?>) RitualEffect.class);
		registryBuilder.allowModification();
		registryBuilder.missing(null);
		RITUALS = event.create(registryBuilder);
	}
}
