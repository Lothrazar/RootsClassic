package elucent.rootsclassic.ritual;

import elucent.rootsclassic.Const;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Const.MODID)
public class RitualBaseRegistry {
	public static final ResourceKey<Registry<RitualEffect>> RITUAL_KEY = ResourceKey.createRegistryKey(Const.modLoc("ritual"));
	public static final Registry<RitualEffect> RITUALS = (new RegistryBuilder<>(RITUAL_KEY)).sync(true).create();

  @SubscribeEvent
  public static void onNewRegistry(NewRegistryEvent event) {
	  event.register(RITUALS);
  }
}
