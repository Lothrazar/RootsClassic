package elucent.rootsclassic;

import com.mojang.logging.LogUtils;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.ui.ManaBarEvent;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.event.ComponentSpellsEvent;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.recipe.RootsReloadManager;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.research.ResearchManager;
import elucent.rootsclassic.ritual.RitualRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Const.MODID)
public class Roots {

  public static final Logger LOGGER = LogUtils.getLogger();

  public Roots(IEventBus eventBus, Dist dist, ModContainer container) {
	  container.registerConfig(ModConfig.Type.COMMON, RootsConfig.commonSpec);
	  container.registerConfig(ModConfig.Type.CLIENT, RootsConfig.clientSpec);

    eventBus.addListener(this::setup);

	  RitualRegistry.RITUALS.register(eventBus);
	  ComponentRegistry.COMPONENTS.register(eventBus);

	  RootsComponents.COMPONENT_TYPE.register(eventBus);
    RootsRegistry.BLOCKS.register(eventBus);
    RootsRegistry.ITEMS.register(eventBus);
    RootsRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
    RootsRegistry.CREATIVE_MODE_TABS.register(eventBus);
    RootsEntities.ENTITY_TYPES.register(eventBus);
    RootsRecipes.RECIPE_SERIALIZERS.register(eventBus);
    RootsRecipes.RECIPE_TYPES.register(eventBus);
    DropModifier.GLM.register(eventBus);
    ParticleRegistry.PARTICLE_TYPES.register(eventBus);
	  RootsAttachments.ATTACHMENT_TYPES.register(eventBus);

    NeoForge.EVENT_BUS.register(new RootsReloadManager());
    NeoForge.EVENT_BUS.register(new ComponentSpellsEvent());

    eventBus.addListener(RootsEntities::registerEntityAttributes);
    eventBus.addListener(RootsEntities::onSpawnPlacementRegisterEvent);

    if (dist.isClient()) {
      NeoForge.EVENT_BUS.register(new ManaBarEvent());
      eventBus.addListener(ClientHandler::onClientSetup);
      eventBus.addListener(ClientHandler::registerEntityRenders);
      eventBus.addListener(ClientHandler::registerLayerDefinitions);
      eventBus.addListener(ClientHandler::registerItemColors);
      eventBus.addListener(ClientHandler::registerParticleFactories);
      NeoForge.EVENT_BUS.addListener(ResearchManager::onRecipesUpdated);
    }
  }

  private void setup(final FMLCommonSetupEvent event) {
    //Initialize
    event.enqueueWork(MutagenManager::reload);
  }
}
