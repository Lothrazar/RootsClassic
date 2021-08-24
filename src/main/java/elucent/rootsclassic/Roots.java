package elucent.rootsclassic;

import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.ui.ManaBarEvent;
import elucent.rootsclassic.component.ComponentManager;
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
import elucent.rootsclassic.ritual.RitualManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Const.MODID)
public class Roots {

	public static ItemGroup tab = new ItemGroup(Const.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(RootsRegistry.SPELL_POWDER.get());
		}
	};

	public static final Logger LOGGER = LogManager.getLogger();

	public Roots() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RootsConfig.clientSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RootsConfig.commonSpec);
		eventBus.register(RootsConfig.class);

		eventBus.addListener(this::setup);

		RootsRegistry.BLOCKS.register(eventBus);
		RootsRegistry.ITEMS.register(eventBus);
		RootsRegistry.TILE_ENTITIES.register(eventBus);
		RootsEntities.ENTITIES.register(eventBus);
		RootsRecipes.RECIPE_SERIALIZERS.register(eventBus);
		DropModifier.GLM.register(eventBus);

		ParticleRegistry.PARTICLE_TYPES.register(eventBus);

		MinecraftForge.EVENT_BUS.register(new RootsReloadManager());
		MinecraftForge.EVENT_BUS.register(new RootsCapabilityManager());
		MinecraftForge.EVENT_BUS.register(new ComponentSpellsEvent());

		MinecraftForge.EVENT_BUS.addListener(RootsEntities::addSpawns);
		eventBus.addListener(RootsEntities::registerEntityAttributes);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			MinecraftForge.EVENT_BUS.register(new ManaBarEvent());

			eventBus.addListener(ClientHandler::onClientSetup);
			eventBus.addListener(ClientHandler::registerItemColors);

			MinecraftForge.EVENT_BUS.addListener(ResearchManager::onRecipesUpdated);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		RootsCapabilityManager.register();
		RootsEntities.registerSpawnPlacement();

		event.enqueueWork(() -> {
			//Initialize
			MutagenManager.reload();
			ComponentManager.reload();
			RitualManager.reload();
		});
	}
}
