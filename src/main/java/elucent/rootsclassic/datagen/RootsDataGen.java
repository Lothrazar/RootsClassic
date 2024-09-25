package elucent.rootsclassic.datagen;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.datagen.server.RootsBlockTagsProvider;
import elucent.rootsclassic.datagen.server.RootsDataMapProvider;
import elucent.rootsclassic.datagen.server.RootsGLMProvider;
import elucent.rootsclassic.datagen.server.RootsItemTagsProvider;
import elucent.rootsclassic.datagen.server.RootsLootsProvider;
import elucent.rootsclassic.datagen.server.RootsRecipeProvider;
import elucent.rootsclassic.registry.RootsDamageTypes;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class RootsDataGen {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    ExistingFileHelper helper = event.getExistingFileHelper();
    if (event.includeServer()) {
      generator.addProvider(true, new RootsLootsProvider(packOutput, lookupProvider));
      generator.addProvider(true, new RootsRecipeProvider(packOutput, lookupProvider));
      generator.addProvider(true, new RootsGLMProvider(packOutput, lookupProvider));
      BlockTagsProvider provider;
      generator.addProvider(true, provider = new RootsBlockTagsProvider(packOutput, lookupProvider, helper));
      generator.addProvider(true, new RootsItemTagsProvider(packOutput, lookupProvider, provider.contentsGetter(), helper));
			generator.addProvider(true, new RootsDataMapProvider(packOutput, lookupProvider));
      generator.addProvider(true, new DatapackBuiltinEntriesProvider(
          packOutput, CompletableFuture.supplyAsync(RootsDataGen::getProvider), Set.of(Const.MODID)));
    }
  }

	private static RegistrySetBuilder.PatchedRegistries getProvider() {
    final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
    registryBuilder.add(Registries.DAMAGE_TYPE, context -> {
      context.register(RootsDamageTypes.GENERIC, new DamageType(Const.MODID + ".generic", 0.0F));
      context.register(RootsDamageTypes.FIRE, new DamageType(Const.MODID + ".fire", 0.1F, DamageEffects.BURNING));
      context.register(RootsDamageTypes.WITHER, new DamageType(Const.MODID + ".wither", 0.0F));
      context.register(RootsDamageTypes.CACTUS, new DamageType(Const.MODID + ".cactus", 0.1F));
    });
	  RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
	  Cloner.Factory cloner$factory = new Cloner.Factory();
	  net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(cloner$factory::addCodec));
	  return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
  }
}
