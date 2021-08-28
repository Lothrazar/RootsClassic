package elucent.rootsclassic.datagen;

import static elucent.rootsclassic.registry.RootsRegistry.ACCELERATOR_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.AESTHETIC_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.ALTAR;
import static elucent.rootsclassic.registry.RootsRegistry.ATTUNED_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.BARK_KNIFE;
import static elucent.rootsclassic.registry.RootsRegistry.BLACKCURRANT;
import static elucent.rootsclassic.registry.RootsRegistry.BRAZIER;
import static elucent.rootsclassic.registry.RootsRegistry.DRAGONS_EYE;
import static elucent.rootsclassic.registry.RootsRegistry.ELDERBERRY;
import static elucent.rootsclassic.registry.RootsRegistry.ENTANGLER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.FLARE_ORCHID;
import static elucent.rootsclassic.registry.RootsRegistry.FRUIT_SALAD;
import static elucent.rootsclassic.registry.RootsRegistry.GROWER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.GROWTH_POWDER;
import static elucent.rootsclassic.registry.RootsRegistry.HEALER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.HEALING_POULTICE;
import static elucent.rootsclassic.registry.RootsRegistry.IGNITER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.IMBUER;
import static elucent.rootsclassic.registry.RootsRegistry.MIDNIGHT_BLOOM;
import static elucent.rootsclassic.registry.RootsRegistry.MORTAR;
import static elucent.rootsclassic.registry.RootsRegistry.MUNDANE_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.MUTATING_POWDER;
import static elucent.rootsclassic.registry.RootsRegistry.OLD_ROOT;
import static elucent.rootsclassic.registry.RootsRegistry.PESTLE;
import static elucent.rootsclassic.registry.RootsRegistry.RADIANT_DAISY;
import static elucent.rootsclassic.registry.RootsRegistry.REDCURRANT;
import static elucent.rootsclassic.registry.RootsRegistry.REPULSOR_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.ROOTY_STEW;
import static elucent.rootsclassic.registry.RootsRegistry.RUNIC_TABLET;
import static elucent.rootsclassic.registry.RootsRegistry.VACUUM_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.VERDANT_SPRIG;
import static elucent.rootsclassic.registry.RootsRegistry.WHITECURRANT;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.Inverted;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import elucent.rootsclassic.lootmodifiers.DropModifier.BlockDropModifier;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RootsDataGen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
//		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new Loots(generator));
			generator.addProvider(new Recipes(generator));
			generator.addProvider(new GLMProvider(generator));
		}
		if (event.includeClient()) {
//			generator.addProvider(new Language(generator));
//			generator.addProvider(new BlockStates(generator, helper));
//			generator.addProvider(new ItemModels(generator, helper));
		}
	}

	private static class Loots extends LootTableProvider {
		public Loots(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
			return ImmutableList.of(Pair.of(RootsBlockLoot::new, LootParameterSets.BLOCK), Pair.of(RootsEntityLoot::new, LootParameterSets.ENTITY));
		}

		private class RootsBlockLoot extends BlockLootTables {
			@Override
			protected void addTables() {
				this.registerDropSelfLootTable(MORTAR.get());
				this.registerDropSelfLootTable(ALTAR.get());
				this.registerDropSelfLootTable(BRAZIER.get());
				this.registerDropSelfLootTable(IMBUER.get());
				this.registerDropSelfLootTable(MUNDANE_STANDING_STONE.get());
				this.registerDropSelfLootTable(MIDNIGHT_BLOOM.get());
				this.registerDropSelfLootTable(FLARE_ORCHID.get());
				this.registerDropSelfLootTable(RADIANT_DAISY.get());
				this.registerLootTable(ATTUNED_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(VACUUM_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(REPULSOR_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(ACCELERATOR_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(AESTHETIC_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(ENTANGLER_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(IGNITER_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(GROWER_STANDING_STONE.get(), this::registerStandingStone);
				this.registerLootTable(HEALER_STANDING_STONE.get(), this::registerStandingStone);
			}

			public LootTable.Builder registerStandingStone(Block standingStone) {
				return droppingWhen(standingStone, AttunedStandingStoneBlock.HALF, DoubleBlockHalf.LOWER);
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) RootsRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		private class RootsEntityLoot extends EntityLootTables {
			@Override
			protected void addTables() {
				this.registerLootTable(RootsEntities.PHANTOM_SKELETON.get(), LootTable.builder());
			}

			@Override
			protected boolean isNonLiving(EntityType<?> entityType) {
				return entityType.getClassification() == EntityClassification.MISC;
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				Stream<EntityType<?>> entities = RootsEntities.ENTITIES.getEntries().stream().map(RegistryObject::get);
				return (Iterable<EntityType<?>>) entities::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
			map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
		}
	}

	private static class GLMProvider extends GlobalLootModifierProvider {

		public GLMProvider(DataGenerator gen) {
			super(gen, Const.MODID);
		}

		@Override
		protected void start() {
			add("rootsclassic_drops", DropModifier.ROOTSCLASSIC_DROPS.get(), new BlockDropModifier(
					new ILootCondition[] {
							Inverted.builder(MatchTool.builder(ItemPredicate.Builder.create().tag(Tags.Items.SHEARS))).build()
					})
			);
		}
	}

	private static class Recipes extends RecipeProvider {
		public Recipes(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
			ShapedRecipeBuilder.shapedRecipe(PESTLE.get()).key('X', Blocks.DIORITE).setGroup("pestle")
					.patternLine("X  ").patternLine(" XX").patternLine(" XX").addCriterion("has_diorite", hasItem(Blocks.DIORITE)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(PESTLE.get()).key('X', Blocks.DIORITE).setGroup("pestle")
					.patternLine("  X").patternLine("XX ").patternLine("XX ").addCriterion("has_diorite", hasItem(Blocks.DIORITE))
					.build(consumer, Registry.ITEM.getKey(PESTLE.get()) + "2");
			ShapedRecipeBuilder.shapedRecipe(MORTAR.get()).key('X', Tags.Items.STONE)
					.patternLine("X X").patternLine("X X").patternLine(" X ").addCriterion("has_stone", hasItem(Tags.Items.STONE)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(IMBUER.get()).key('X', Tags.Items.RODS_WOODEN).key('L', ItemTags.LOGS).key('S', Blocks.CHISELED_STONE_BRICKS)
					.patternLine("X X").patternLine("LSL").addCriterion("has_chiseled_stone_bricks", hasItem(Blocks.CHISELED_STONE_BRICKS)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(MUNDANE_STANDING_STONE.get()).key('S', Tags.Items.STONE).key('B', Blocks.STONE_BRICKS).key('L', Tags.Items.STORAGE_BLOCKS_LAPIS)
					.patternLine("SBS").patternLine("BLB").patternLine("SBS").addCriterion("has_lapis_block", hasItem(Tags.Items.STORAGE_BLOCKS_LAPIS)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(ATTUNED_STANDING_STONE.get()).key('S', Tags.Items.STONE).key('N', Tags.Items.INGOTS_NETHER_BRICK).key('D', Tags.Items.GEMS_DIAMOND)
					.patternLine("SNS").patternLine("NDN").patternLine("SNS").addCriterion("has_diamond", hasItem(Tags.Items.GEMS_DIAMOND)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(BRAZIER.get())
					.key('I', Tags.Items.INGOTS_IRON).key('S', Tags.Items.STRING).key('C', Items.CAULDRON).key('X', Tags.Items.RODS_WOODEN)
					.patternLine("ISI").patternLine("ICI").patternLine("IXI").addCriterion("has_cauldron", hasItem(Items.CAULDRON)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(ALTAR.get())
					.key('S', Tags.Items.STONE).key('F', Items.POPPY).key('B', VERDANT_SPRIG.get())
					.key('G', Tags.Items.STORAGE_BLOCKS_GOLD).key('C', Blocks.CHISELED_STONE_BRICKS)
					.patternLine("BFB").patternLine("SGS").patternLine(" C ").addCriterion("has_gold_block", hasItem(Tags.Items.STORAGE_BLOCKS_GOLD)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(BARK_KNIFE.get())
					.key('S', Tags.Items.RODS_WOODEN).key('V', ItemTags.SAPLINGS).key('P', ItemTags.PLANKS)
					.patternLine(" VV").patternLine("VPV").patternLine("SV ").addCriterion("has_sapling", hasItem(ItemTags.SAPLINGS)).build(consumer);
			ShapedRecipeBuilder.shapedRecipe(RUNIC_TABLET.get())
					.key('S', Tags.Items.SEEDS_WHEAT).key('B', Tags.Items.STONE).key('R', OLD_ROOT.get())
					.patternLine(" R ").patternLine("SBS").patternLine(" S ").addCriterion("has_old_root", hasItem(OLD_ROOT.get())).build(consumer);

			ShapelessRecipeBuilder.shapelessRecipe(GROWTH_POWDER.get(), 4)
					.addIngredient(Tags.Items.SEEDS_WHEAT).addIngredient(Items.GRASS).addIngredient(Tags.Items.DUSTS_REDSTONE).addIngredient(PESTLE.get())
					.addCriterion("has_pestle", hasItem(PESTLE.get())).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(MUTATING_POWDER.get())
					.addIngredient(GROWTH_POWDER.get()).addIngredient(GROWTH_POWDER.get()).addIngredient(GROWTH_POWDER.get()).addIngredient(GROWTH_POWDER.get())
					.addIngredient(Tags.Items.NETHER_STARS).addIngredient(Tags.Items.CROPS_NETHER_WART).addIngredient(PESTLE.get())
					.addCriterion("has_pestle", hasItem(PESTLE.get())).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(ROOTY_STEW.get())
					.addIngredient(Tags.Items.CROPS_WHEAT).addIngredient(Items.BOWL).addIngredient(OLD_ROOT.get())
					.addCriterion("has_bowl", hasItem(Items.BOWL)).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(FRUIT_SALAD.get())
					.addIngredient(Items.MELON).addIngredient(Items.MELON).addIngredient(Items.MELON)
					.addIngredient(Items.APPLE).addIngredient(Items.BOWL).addIngredient(ELDERBERRY.get())
					.addIngredient(WHITECURRANT.get()).addIngredient(BLACKCURRANT.get()).addIngredient(REDCURRANT.get())
					.addCriterion("has_bowl", hasItem(Items.BOWL)).build(consumer);
			ShapelessRecipeBuilder.shapelessRecipe(HEALING_POULTICE.get(), 2)
					.addIngredient(REDCURRANT.get()).addIngredient(Items.PAPER).addIngredient(PESTLE.get()).addIngredient(VERDANT_SPRIG.get())
					.addCriterion("has_pestle", hasItem(PESTLE.get())).build(consumer);

			CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(DRAGONS_EYE.get()),
					Items.ENDER_PEARL, 1F, 200).addCriterion("has_dragons_eye", hasItem(DRAGONS_EYE.get()))
					.build(consumer, "rootsclassic:ender_pearl");
		}

		@Override
		protected void saveRecipeAdvancement(DirectoryCache cache, JsonObject advancementJson, Path path) {
			// Nope
		}
	}
}
