package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class RootsBlockTagsProvider extends BlockTagsProvider {

	public RootsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Const.MODID);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(RootsTags.INCORRECT_FOR_LIVING_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
		this.tag(RootsTags.INCORRECT_FOR_ENGRAVED_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
			RootsRegistry.MUNDANE_STANDING_STONE.get(),
			RootsRegistry.ATTUNED_STANDING_STONE.get(),
			RootsRegistry.VACUUM_STANDING_STONE.get(),
			RootsRegistry.REPULSOR_STANDING_STONE.get(),
			RootsRegistry.ACCELERATOR_STANDING_STONE.get(),
			RootsRegistry.AESTHETIC_STANDING_STONE.get(),
			RootsRegistry.ENTANGLER_STANDING_STONE.get(),
			RootsRegistry.IGNITER_STANDING_STONE.get(),
			RootsRegistry.GROWER_STANDING_STONE.get(),
			RootsRegistry.ALTAR.get(),
			RootsRegistry.MORTAR.get()
		);
	}
}
