package elucent.rootsclassic;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.ui.ManaBarEvent;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.event.ComponentSpellsEvent;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.recipe.RootsReloadManager;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.research.ResearchManager;
import elucent.rootsclassic.ritual.RitualRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Const.MODID)
public class Roots {

  public static final Logger LOGGER = LogUtils.getLogger();

  public Roots() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    RootsConfig cfg = new RootsConfig();
    cfg.setupMain();
    cfg.setupClient();
    eventBus.register(cfg);
    eventBus.addListener(this::setup);
    RootsRegistry.BLOCKS.register(eventBus);
    RootsRegistry.ITEMS.register(eventBus);
    RootsRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
    RootsRegistry.CREATIVE_MODE_TABS.register(eventBus);
    RootsEntities.ENTITY_TYPES.register(eventBus);
    RitualRegistry.RITUALS.register(eventBus);
    ComponentRegistry.COMPONENTS.register(eventBus);
    RootsRecipes.RECIPE_SERIALIZERS.register(eventBus);
    RootsRecipes.RECIPE_TYPES.register(eventBus);
    DropModifier.GLM.register(eventBus);
    ParticleRegistry.PARTICLE_TYPES.register(eventBus);
    MinecraftForge.EVENT_BUS.register(new RootsReloadManager());
    eventBus.addListener(RootsCapabilityManager::registerCapabilities);
    MinecraftForge.EVENT_BUS.register(new RootsCapabilityManager());
    MinecraftForge.EVENT_BUS.register(new ComponentSpellsEvent());
    eventBus.addListener(RootsEntities::registerEntityAttributes);
    eventBus.addListener(RootsEntities::onSpawnPlacementRegisterEvent);
    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
      MinecraftForge.EVENT_BUS.register(new ManaBarEvent());
      eventBus.addListener(ClientHandler::onClientSetup);
      eventBus.addListener(ClientHandler::registerEntityRenders);
      eventBus.addListener(ClientHandler::registerLayerDefinitions);
      eventBus.addListener(ClientHandler::registerItemColors);
      eventBus.addListener(ClientHandler::registerParticleFactories);
      MinecraftForge.EVENT_BUS.addListener(ResearchManager::onRecipesUpdated);
    });
  }

  private void setup(final FMLCommonSetupEvent event) {
    RootsRegistry.registerCompostables();
    //Initialize
    event.enqueueWork(MutagenManager::reload);
  }
}
