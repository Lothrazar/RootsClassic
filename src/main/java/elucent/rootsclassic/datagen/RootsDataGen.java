package elucent.rootsclassic.datagen;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.datagen.client.RootsEquipmentAssetProvider;
import elucent.rootsclassic.datagen.client.RootsLanguageProvider;
import elucent.rootsclassic.datagen.client.RootsModelProvider;
import elucent.rootsclassic.datagen.server.RootsBlockTagsProvider;
import elucent.rootsclassic.datagen.server.RootsDataMapProvider;
import elucent.rootsclassic.datagen.server.RootsDatapackProvider;
import elucent.rootsclassic.datagen.server.RootsGLMProvider;
import elucent.rootsclassic.datagen.server.RootsItemTagsProvider;
import elucent.rootsclassic.datagen.server.RootsLootsProvider;
import elucent.rootsclassic.datagen.server.RootsRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class RootsDataGen {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent.Client event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
      generator.addProvider(true, new RootsLanguageProvider(packOutput));
      generator.addProvider(true, new RootsModelProvider(packOutput));
      generator.addProvider(true, new RootsEquipmentAssetProvider(packOutput));

      generator.addProvider(true, new RootsLootsProvider(packOutput, lookupProvider));
      generator.addProvider(true, new RootsRecipeProvider.Runner(packOutput, lookupProvider));
      generator.addProvider(true, new RootsGLMProvider(packOutput, lookupProvider));
      generator.addProvider(true, new RootsBlockTagsProvider(packOutput, lookupProvider));
      generator.addProvider(true, new RootsItemTagsProvider(packOutput, lookupProvider));
			generator.addProvider(true, new RootsDataMapProvider(packOutput, lookupProvider));


      generator.addProvider(true, new RootsDatapackProvider(
        packOutput,
        event.getLookupProvider(),
        Set.of(Const.MODID)
      ));
  }
}
