package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;


public class RootsItemTagsProvider extends ItemTagsProvider {

	public RootsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Const.MODID);
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
    this.tag(Tags.Items.FOODS_BERRY).addTag(RootsTags.BERRIES);
    this.tag(Tags.Items.FOODS).add(RootsRegistry.OLD_ROOT.get(), RootsRegistry.HEALING_POULTICE.get(), RootsRegistry.FRUIT_SALAD.get());
    this.tag(Tags.Items.FOODS_SOUP).add(RootsRegistry.ROOTY_STEW.get());
		this.addBark(RootsRegistry.ACACIA_BARK.get(), "acacia");
		this.addBark(RootsRegistry.BIRCH_BARK.get(), "birch");
		this.addBark(RootsRegistry.DARK_OAK_BARK.get(), "dark_oak");
		this.addBark(RootsRegistry.JUNGLE_BARK.get(), "jungle");
		this.addBark(RootsRegistry.OAK_BARK.get(), "oak");
		this.addBark(RootsRegistry.SPRUCE_BARK.get(), "spruce");
    this.tag(ItemTags.AXES).add(RootsRegistry.LIVING_AXE.get());
    this.tag(ItemTags.HOES).add(RootsRegistry.LIVING_HOE.get());
    this.tag(ItemTags.PICKAXES).add(RootsRegistry.LIVING_PICKAXE.get());
    this.tag(ItemTags.SHOVELS).add(RootsRegistry.LIVING_SHOVEL.get());
    this.tag(ItemTags.SWORDS).add(RootsRegistry.LIVING_SWORD.get(), RootsRegistry.ENGRAVED_BLADE.get());
    this.tag(ItemTags.HEAD_ARMOR).add(RootsRegistry.SYLVAN_HOOD.get(), RootsRegistry.WILDWOOD_MASK.get());
    this.tag(ItemTags.CHEST_ARMOR).add(RootsRegistry.SYLVAN_ROBE.get(), RootsRegistry.WILDWOOD_PLATE.get());
    this.tag(ItemTags.LEG_ARMOR).add(RootsRegistry.SYLVAN_ROBE.get(), RootsRegistry.WILDWOOD_LEGGINGS.get());
    this.tag(ItemTags.FOOT_ARMOR).add(RootsRegistry.SYLVAN_BOOTS.get(), RootsRegistry.WILDWOOD_BOOTS.get());

    this.tag(RootsTags.LIVING_TOOL_MATERIALS);
    this.tag(RootsTags.ENGRAVED_TOOL_MATERIALS);
    this.tag(RootsTags.REPAIRS_SYLVAN);
    this.tag(RootsTags.REPAIRS_WILDWOOD);
	}

	private void addBark(Item item, String treeType) {
		TagKey<Item> barkTypeTag = ItemTags.create(Const.modLoc("barks/" + treeType));
		this.tag(RootsTags.BARKS).addTag(barkTypeTag);
		this.tag(barkTypeTag).add(item);
	}
}
