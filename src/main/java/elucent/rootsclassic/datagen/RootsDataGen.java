package elucent.rootsclassic.datagen;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.datagen.server.RootsRecipeProvider;
import elucent.rootsclassic.lootmodifiers.DropModifier.BlockDropModifier;
import elucent.rootsclassic.registry.RootsDamageTypes;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static elucent.rootsclassic.registry.RootsRegistry.ACACIA_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.ACCELERATOR_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.AESTHETIC_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.ALTAR;
import static elucent.rootsclassic.registry.RootsRegistry.ATTUNED_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.BIRCH_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.BLACKCURRANT;
import static elucent.rootsclassic.registry.RootsRegistry.BRAZIER;
import static elucent.rootsclassic.registry.RootsRegistry.DARK_OAK_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.ELDERBERRY;
import static elucent.rootsclassic.registry.RootsRegistry.ENTANGLER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.FLARE_ORCHID;
import static elucent.rootsclassic.registry.RootsRegistry.GROWER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.HEALER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.IGNITER_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.IMBUER;
import static elucent.rootsclassic.registry.RootsRegistry.JUNGLE_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.MIDNIGHT_BLOOM;
import static elucent.rootsclassic.registry.RootsRegistry.MORTAR;
import static elucent.rootsclassic.registry.RootsRegistry.MUNDANE_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.NIGHTSHADE;
import static elucent.rootsclassic.registry.RootsRegistry.OAK_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.RADIANT_DAISY;
import static elucent.rootsclassic.registry.RootsRegistry.REDCURRANT;
import static elucent.rootsclassic.registry.RootsRegistry.REPULSOR_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.SPRUCE_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.VACUUM_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.WHITECURRANT;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class RootsDataGen {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    ExistingFileHelper helper = event.getExistingFileHelper();
    if (event.includeServer()) {
      generator.addProvider(event.includeServer(), new Loots(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new RootsRecipeProvider(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new GLMProvider(packOutput, lookupProvider));
      BlockTagsProvider provider;
      generator.addProvider(event.includeServer(), provider = new RootsBlockTags(packOutput, lookupProvider, helper));
      generator.addProvider(event.includeServer(), new RootsItemTags(packOutput, lookupProvider, provider.contentsGetter(), helper));
			generator.addProvider(event.includeServer(), new DataMaps(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
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

	private static class DataMaps extends DataMapProvider {
		public DataMaps(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider);
		}

		@Override
		protected void gather() {
			final float LEAVES = 0.3F;
			final float FLOWER = 0.65F;

			final var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
			compostables.add(BLACKCURRANT, new Compostable(LEAVES), false);
			compostables.add(REDCURRANT, new Compostable(LEAVES), false);
			compostables.add(WHITECURRANT, new Compostable(LEAVES), false);
			compostables.add(NIGHTSHADE, new Compostable(FLOWER), false);
			compostables.add(ELDERBERRY, new Compostable(FLOWER), false);
		}
	}

  private static class Loots extends LootTableProvider {

    public Loots(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
      super(packOutput, Set.of(), List.of(
          new SubProviderEntry(RootsBlockLoot::new, LootContextParamSets.BLOCK),
          new SubProviderEntry(RootsEntityLoot::new, LootContextParamSets.ENTITY)
      ), lookupProvider);
    }

    private static class RootsBlockLoot extends BlockLootSubProvider {

      protected RootsBlockLoot(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
      }

      @Override
      public void generate() {
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
        return RootsRegistry.BLOCKS.getEntries().stream().map(holder -> (Block)holder.value())::iterator;
      }
    }

    private static class RootsEntityLoot extends EntityLootSubProvider {

	    protected RootsEntityLoot(HolderLookup.Provider provider) {
		    super(FeatureFlags.REGISTRY.allFlags(), provider);
	    }

	    @Override
      public void generate() {
        this.add(RootsEntities.PHANTOM_SKELETON.get(), LootTable.lootTable());
      }

      @Override
      protected boolean canHaveLootTable(EntityType<?> entityType) {
        return entityType.getCategory() != MobCategory.MISC;
      }

      @Override
      protected Stream<EntityType<?>> getKnownEntityTypes() {
        return RootsEntities.ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get);
      }
    }

	  @Override
	  protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
		  super.validate(writableregistry, validationcontext, problemreporter$collector);
	  }
  }

  private static class GLMProvider extends GlobalLootModifierProvider {

    public GLMProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
      super(packOutput, lookupProvider, Const.MODID);
    }

    @Override
    protected void start() {
	    this.add("rootsclassic_drops", new BlockDropModifier(
		    new LootItemCondition[]{
			    InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR))).build()
		    }));
    }
  }

  public static class RootsBlockTags extends BlockTagsProvider {

    public RootsBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, Const.MODID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
      this.tag(RootsTags.INCORRECT_FOR_LIVING_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
      this.tag(RootsTags.INCORRECT_FOR_ENGRAVED_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
    }
  }

  public static class RootsItemTags extends ItemTagsProvider {

    public RootsItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, blockTagProvider, Const.MODID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
      this.tag(RootsTags.BERRIES).add(NIGHTSHADE.get(), BLACKCURRANT.get(), REDCURRANT.get(), WHITECURRANT.get(), ELDERBERRY.get());
      this.addBark(ACACIA_BARK.get(), "acacia");
      this.addBark(BIRCH_BARK.get(), "birch");
      this.addBark(DARK_OAK_BARK.get(), "dark_oak");
      this.addBark(JUNGLE_BARK.get(), "jungle");
      this.addBark(OAK_BARK.get(), "oak");
      this.addBark(SPRUCE_BARK.get(), "spruce");
    }

    private void addBark(Item item, String treeType) {
      TagKey<Item> barkTypeTag = ItemTags.create(Const.modLoc("barks/" + treeType));
      this.tag(RootsTags.BARKS).addTag(barkTypeTag);
      this.tag(barkTypeTag).add(item);
    }
  }
}
