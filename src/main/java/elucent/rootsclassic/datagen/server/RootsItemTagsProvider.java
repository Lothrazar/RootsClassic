package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;


public class RootsItemTagsProvider extends ItemTagsProvider {

	public RootsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	                             CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTagProvider, Const.MODID, existingFileHelper);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(RootsTags.BERRIES).add(
			RootsRegistry.NIGHTSHADE.get(),
			RootsRegistry.BLACKCURRANT.get(),
			RootsRegistry.REDCURRANT.get(),
			RootsRegistry.WHITECURRANT.get(),
			RootsRegistry.ELDERBERRY.get()
		);
		this.addBark(RootsRegistry.ACACIA_BARK.get(), "acacia");
		this.addBark(RootsRegistry.BIRCH_BARK.get(), "birch");
		this.addBark(RootsRegistry.DARK_OAK_BARK.get(), "dark_oak");
		this.addBark(RootsRegistry.JUNGLE_BARK.get(), "jungle");
		this.addBark(RootsRegistry.OAK_BARK.get(), "oak");
		this.addBark(RootsRegistry.SPRUCE_BARK.get(), "spruce");
	}

	private void addBark(Item item, String treeType) {
		TagKey<Item> barkTypeTag = ItemTags.create(Const.modLoc("barks/" + treeType));
		this.tag(RootsTags.BARKS).addTag(barkTypeTag);
		this.tag(barkTypeTag).add(item);
	}
}
