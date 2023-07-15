package elucent.rootsclassic.component;

import java.util.function.Supplier;
import elucent.rootsclassic.Const;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Const.MODID)
public class ComponentBaseRegistry {

  public static final ResourceLocation registryLocation = new ResourceLocation(Const.MODID, "component");
  public static Supplier<IForgeRegistry<ComponentBase>> COMPONENTS;

  @SubscribeEvent
  public static void onNewRegistry(NewRegistryEvent event) {
    RegistryBuilder<ComponentBase> registryBuilder = new RegistryBuilder<>();
    registryBuilder.setName(registryLocation);
    registryBuilder.allowModification();
    registryBuilder.missing(null);
    COMPONENTS = event.create(registryBuilder);
  }
}
