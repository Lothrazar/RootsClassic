package elucent.rootsclassic.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import elucent.rootsclassic.lootmodifiers.DropModifier.BlockDropModifier;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static elucent.rootsclassic.registry.RootsRegistry.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RootsDataGen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();
		if (event.includeServer()) {
			generator.addProvider(new Loots(generator));
			generator.addProvider(new Recipes(generator));
			generator.addProvider(new GLMProvider(generator));
			BlockTagsProvider provider;
			generator.addProvider(provider = new RootsBlockTags(generator, helper));
			generator.addProvider(new RootsItemTags(generator, provider, helper));
		}
	}

	private static class Loots extends LootTableProvider {

		public Loots(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(Pair.of(RootsBlockLoot::new, LootContextParamSets.BLOCK), Pair.of(RootsEntityLoot::new, LootContextParamSets.ENTITY));
		}

		private static class RootsBlockLoot extends BlockLoot {

			@Override
			protected void addTables() {
				this.dropSelf(MORTAR.get());
				this.dropSelf(ALTAR.get());
				this.dropSelf(BRAZIER.get());
				this.dropSelf(IMBUER.get());
				this.dropSelf(MUNDANE_STANDING_STONE.get());
				this.dropSelf(MIDNIGHT_BLOOM.get());
				this.dropSelf(FLARE_ORCHID.get());
				this.dropSelf(RADIANT_DAISY.get());
				this.add(ATTUNED_STANDING_STONE.get(), this::registerStandingStone);
				this.add(VACUUM_STANDING_STONE.get(), this::registerStandingStone);
				this.add(REPULSOR_STANDING_STONE.get(), this::registerStandingStone);
				this.add(ACCELERATOR_STANDING_STONE.get(), this::registerStandingStone);
				this.add(AESTHETIC_STANDING_STONE.get(), this::registerStandingStone);
				this.add(ENTANGLER_STANDING_STONE.get(), this::registerStandingStone);
				this.add(IGNITER_STANDING_STONE.get(), this::registerStandingStone);
				this.add(GROWER_STANDING_STONE.get(), this::registerStandingStone);
				this.add(HEALER_STANDING_STONE.get(), this::registerStandingStone);
			}

			public LootTable.Builder registerStandingStone(Block standingStone) {
				return createSinglePropConditionTable(standingStone, AttunedStandingStoneBlock.HALF, DoubleBlockHalf.LOWER);
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) RootsRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		private static class RootsEntityLoot extends EntityLoot {

			@Override
			protected void addTables() {
				this.add(RootsEntities.PHANTOM_SKELETON.get(), LootTable.lootTable());
			}

			@Override
			protected boolean isNonLiving(EntityType<?> entityType) {
				return entityType.getCategory() == MobCategory.MISC;
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				Stream<EntityType<?>> entities = RootsEntities.ENTITIES.getEntries().stream().map(RegistryObject::get);
				return (Iterable<EntityType<?>>) entities::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) {
			map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
		}
	}

	private static class GLMProvider extends GlobalLootModifierProvider {

		public GLMProvider(DataGenerator gen) {
			super(gen, Const.MODID);
		}

		@Override
		protected void start() {
			add("rootsclassic_drops", DropModifier.ROOTSCLASSIC_DROPS.get(), new BlockDropModifier(
				new LootItemCondition[]{
					InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))).build()
				}));
		}
	}

	private static class Recipes extends RecipeProvider {

		public Recipes(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
			ShapedRecipeBuilder.shaped(PESTLE.get()).define('X', Blocks.DIORITE).group("pestle")
				.pattern("X  ").pattern(" XX").pattern(" XX").unlockedBy("has_diorite", has(Blocks.DIORITE)).save(consumer);
			ShapedRecipeBuilder.shaped(PESTLE.get()).define('X', Blocks.DIORITE).group("pestle")
				.pattern("  X").pattern("XX ").pattern("XX ").unlockedBy("has_diorite", has(Blocks.DIORITE))
				.save(consumer, Registry.ITEM.getKey(PESTLE.get()) + "2");
			ShapedRecipeBuilder.shaped(MORTAR.get()).define('X', Tags.Items.STONE)
				.pattern("X X").pattern("X X").pattern(" X ").unlockedBy("has_stone", has(Tags.Items.STONE)).save(consumer);
			ShapedRecipeBuilder.shaped(IMBUER.get()).define('X', Tags.Items.RODS_WOODEN).define('L', ItemTags.LOGS).define('S', Blocks.CHISELED_STONE_BRICKS)
				.pattern("X X").pattern("LSL").unlockedBy("has_chiseled_stone_bricks", has(Blocks.CHISELED_STONE_BRICKS)).save(consumer);
			ShapedRecipeBuilder.shaped(MUNDANE_STANDING_STONE.get()).define('S', Tags.Items.STONE).define('B', Blocks.STONE_BRICKS).define('L', Tags.Items.STORAGE_BLOCKS_LAPIS)
				.pattern("SBS").pattern("BLB").pattern("SBS").unlockedBy("has_lapis_block", has(Tags.Items.STORAGE_BLOCKS_LAPIS)).save(consumer);
			ShapedRecipeBuilder.shaped(ATTUNED_STANDING_STONE.get()).define('S', Tags.Items.STONE).define('N', Tags.Items.INGOTS_NETHER_BRICK).define('D', Tags.Items.GEMS_DIAMOND)
				.pattern("SNS").pattern("NDN").pattern("SNS").unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).save(consumer);
			ShapedRecipeBuilder.shaped(BRAZIER.get())
				.define('I', Tags.Items.INGOTS_IRON).define('S', Tags.Items.STRING).define('C', Items.CAULDRON).define('X', Tags.Items.RODS_WOODEN)
				.pattern("ISI").pattern("ICI").pattern("IXI").unlockedBy("has_cauldron", has(Items.CAULDRON)).save(consumer);
			ShapedRecipeBuilder.shaped(ALTAR.get())
				.define('S', Tags.Items.STONE).define('F', Items.POPPY).define('B', VERDANT_SPRIG.get())
				.define('G', Tags.Items.STORAGE_BLOCKS_GOLD).define('C', Blocks.CHISELED_STONE_BRICKS)
				.pattern("BFB").pattern("SGS").pattern(" C ").unlockedBy("has_gold_block", has(Tags.Items.STORAGE_BLOCKS_GOLD)).save(consumer);
			ShapedRecipeBuilder.shaped(BARK_KNIFE.get())
				.define('S', Tags.Items.RODS_WOODEN).define('V', ItemTags.SAPLINGS).define('P', ItemTags.PLANKS)
				.pattern(" VV").pattern("VPV").pattern("SV ").unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);
			ShapedRecipeBuilder.shaped(RUNIC_TABLET.get())
				.define('S', Tags.Items.SEEDS_WHEAT).define('B', Tags.Items.STONE).define('R', OLD_ROOT.get())
				.pattern(" R ").pattern("SBS").pattern(" S ").unlockedBy("has_old_root", has(OLD_ROOT.get())).save(consumer);
			ShapelessRecipeBuilder.shapeless(GROWTH_POWDER.get(), 4)
				.requires(Tags.Items.SEEDS_WHEAT).requires(Items.GRASS).requires(Tags.Items.DUSTS_REDSTONE).requires(PESTLE.get())
				.unlockedBy("has_pestle", has(PESTLE.get())).save(consumer);
			ShapelessRecipeBuilder.shapeless(MUTATING_POWDER.get())
				.requires(GROWTH_POWDER.get()).requires(GROWTH_POWDER.get()).requires(GROWTH_POWDER.get()).requires(GROWTH_POWDER.get())
				.requires(Tags.Items.NETHER_STARS).requires(Tags.Items.CROPS_NETHER_WART).requires(PESTLE.get())
				.unlockedBy("has_pestle", has(PESTLE.get())).save(consumer);
			ShapelessRecipeBuilder.shapeless(ROOTY_STEW.get())
				.requires(Tags.Items.CROPS_WHEAT).requires(Items.BOWL).requires(OLD_ROOT.get())
				.unlockedBy("has_bowl", has(Items.BOWL)).save(consumer);
			ShapelessRecipeBuilder.shapeless(FRUIT_SALAD.get())
				.requires(Items.MELON).requires(Items.MELON).requires(Items.MELON)
				.requires(Items.APPLE).requires(Items.BOWL).requires(ELDERBERRY.get())
				.requires(WHITECURRANT.get()).requires(BLACKCURRANT.get()).requires(REDCURRANT.get())
				.unlockedBy("has_bowl", has(Items.BOWL)).save(consumer);
			ShapelessRecipeBuilder.shapeless(HEALING_POULTICE.get(), 2)
				.requires(REDCURRANT.get()).requires(Items.PAPER).requires(PESTLE.get()).requires(VERDANT_SPRIG.get())
				.unlockedBy("has_pestle", has(PESTLE.get())).save(consumer);
			SimpleCookingRecipeBuilder.smelting(Ingredient.of(DRAGONS_EYE.get()),
					Items.ENDER_PEARL, 1F, 200).unlockedBy("has_dragons_eye", has(DRAGONS_EYE.get()))
				.save(consumer, "rootsclassic:ender_pearl");
		}
	}

	public static class RootsBlockTags extends BlockTagsProvider {
		public RootsBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Const.MODID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(RootsTags.NEEDS_LIVING_TOOL);
			this.tag(RootsTags.NEEDS_ENGRAVED_TOOL);
		}
	}

	public static class RootsItemTags extends ItemTagsProvider {
		public RootsItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
			super(dataGenerator, blockTagsProvider, Const.MODID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(RootsTags.BERRIES).add(NIGHTSHADE.get(), BLACKCURRANT.get(), REDCURRANT.get(), WHITECURRANT.get(), ELDERBERRY.get());

			this.addBark(ACACIA_BARK.get(), "acacia");
			this.addBark(BIRCH_BARK.get(), "birch");
			this.addBark(DARK_OAK_BARK.get(), "dark_oak");
			this.addBark(JUNGLE_BARK.get(), "jungle");
			this.addBark(OAK_BARK.get(), "oak");
			this.addBark(SPRUCE_BARK.get(), "spruce");
		}

		private void addBark(Item item, String treeType) {
			TagKey<Item> barkTypeTag = ItemTags.create(new ResourceLocation(Const.MODID, "barks/" + treeType));
			this.tag(RootsTags.BARKS).addTag(barkTypeTag);
			this.tag(barkTypeTag).add(item);
		}
	}
}
