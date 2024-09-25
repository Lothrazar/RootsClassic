package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class RootsDataMapProvider  extends DataMapProvider {
	public RootsDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather() {
		final float LEAVES = 0.3F;
		final float FLOWER = 0.65F;

		final var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
		compostables.add(RootsRegistry.BLACKCURRANT, new Compostable(LEAVES), false);
		compostables.add(RootsRegistry.REDCURRANT, new Compostable(LEAVES), false);
		compostables.add(RootsRegistry.WHITECURRANT, new Compostable(LEAVES), false);
		compostables.add(RootsRegistry.NIGHTSHADE, new Compostable(FLOWER), false);
		compostables.add(RootsRegistry.ELDERBERRY, new Compostable(FLOWER), false);
	}
}
