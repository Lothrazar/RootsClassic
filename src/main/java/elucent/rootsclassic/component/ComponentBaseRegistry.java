package elucent.rootsclassic.component;

import elucent.rootsclassic.Const;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Const.MODID)
public class ComponentBaseRegistry {
	public static final ResourceKey<Registry<ComponentBase>> COMPONENTS_KEY = ResourceKey.createRegistryKey(Const.modLoc("component"));
	public static final Registry<ComponentBase> COMPONENTS = (new RegistryBuilder<>(COMPONENTS_KEY)).create();

  @SubscribeEvent
  public static void onNewRegistry(NewRegistryEvent event) {
		event.register(COMPONENTS);
  }
}
