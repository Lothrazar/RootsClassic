package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class RootsBlockTagsProvider extends BlockTagsProvider {

	public RootsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Const.MODID, existingFileHelper);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(RootsTags.INCORRECT_FOR_LIVING_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
		this.tag(RootsTags.INCORRECT_FOR_ENGRAVED_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
	}
}
