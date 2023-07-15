package elucent.rootsclassic.datagen;

import static elucent.rootsclassic.registry.RootsRegistry.ACACIA_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.ACCELERATOR_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.AESTHETIC_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.ALTAR;
import static elucent.rootsclassic.registry.RootsRegistry.ATTUNED_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.BARK_KNIFE;
import static elucent.rootsclassic.registry.RootsRegistry.BIRCH_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.BLACKCURRANT;
import static elucent.rootsclassic.registry.RootsRegistry.BRAZIER;
import static elucent.rootsclassic.registry.RootsRegistry.DARK_OAK_BARK;
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
import static elucent.rootsclassic.registry.RootsRegistry.JUNGLE_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.MIDNIGHT_BLOOM;
import static elucent.rootsclassic.registry.RootsRegistry.MORTAR;
import static elucent.rootsclassic.registry.RootsRegistry.MUNDANE_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.MUTATING_POWDER;
import static elucent.rootsclassic.registry.RootsRegistry.NIGHTSHADE;
import static elucent.rootsclassic.registry.RootsRegistry.OAK_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.OLD_ROOT;
import static elucent.rootsclassic.registry.RootsRegistry.PESTLE;
import static elucent.rootsclassic.registry.RootsRegistry.RADIANT_DAISY;
import static elucent.rootsclassic.registry.RootsRegistry.REDCURRANT;
import static elucent.rootsclassic.registry.RootsRegistry.REPULSOR_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.ROOTY_STEW;
import static elucent.rootsclassic.registry.RootsRegistry.RUNIC_TABLET;
import static elucent.rootsclassic.registry.RootsRegistry.SPRUCE_BARK;
import static elucent.rootsclassic.registry.RootsRegistry.VACUUM_STANDING_STONE;
import static elucent.rootsclassic.registry.RootsRegistry.VERDANT_SPRIG;
import static elucent.rootsclassic.registry.RootsRegistry.WHITECURRANT;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.lootmodifiers.DropModifier.BlockDropModifier;
import elucent.rootsclassic.registry.RootsDamageTypes;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RootsDataGen {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    ExistingFileHelper helper = event.getExistingFileHelper();
    if (event.includeServer()) {
      generator.addProvider(event.includeServer(), new Loots(packOutput));
      generator.addProvider(event.includeServer(), new Recipes(packOutput));
      generator.addProvider(event.includeServer(), new GLMProvider(packOutput));
      BlockTagsProvider provider;
      generator.addProvider(event.includeServer(), provider = new RootsBlockTags(packOutput, lookupProvider, helper));
      generator.addProvider(event.includeServer(), new RootsItemTags(packOutput, lookupProvider, provider.contentsGetter(), helper));
      generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
          packOutput, CompletableFuture.supplyAsync(RootsDataGen::getProvider), Set.of(Const.MODID)));
    }
  }

  private static HolderLookup.Provider getProvider() {
    final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
    registryBuilder.add(Registries.DAMAGE_TYPE, context -> {
      context.register(RootsDamageTypes.GENERIC, new DamageType(Const.MODID + ".generic", 0.0F));
      context.register(RootsDamageTypes.FIRE, new DamageType(Const.MODID + ".fire", 0.1F, DamageEffects.BURNING));
      context.register(RootsDamageTypes.WITHER, new DamageType(Const.MODID + ".wither", 0.0F));
      context.register(RootsDamageTypes.CACTUS, new DamageType(Const.MODID + ".cactus", 0.1F));
    });
    RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
    return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
  }

  private static class Loots extends LootTableProvider {

    public Loots(PackOutput packOutput) {
      super(packOutput, Set.of(), List.of(
          new SubProviderEntry(RootsBlockLoot::new, LootContextParamSets.BLOCK),
          new SubProviderEntry(RootsEntityLoot::new, LootContextParamSets.ENTITY)));
    }

    private static class RootsBlockLoot extends BlockLootSubProvider {

      protected RootsBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
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
        return RootsRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
      }
    }

    private static class RootsEntityLoot extends EntityLootSubProvider {

      protected RootsEntityLoot() {
        super(FeatureFlags.REGISTRY.allFlags());
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
        return RootsEntities.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
      }
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) {
      map.forEach((name, table) -> table.validate(validationtracker));
    }
  }

  private static class GLMProvider extends GlobalLootModifierProvider {

    public GLMProvider(PackOutput packOutput) {
      super(packOutput, Const.MODID);
    }

    @Override
    protected void start() {
      add("rootsclassic_drops", new BlockDropModifier(
          new LootItemCondition[] {
              InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))).build()
          }));
    }
  }

  private static class Recipes extends RecipeProvider {

    public Recipes(PackOutput packOutput) {
      super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PESTLE.get()).define('X', Blocks.DIORITE).group("pestle")
          .pattern("X  ").pattern(" XX").pattern(" XX").unlockedBy("has_diorite", has(Blocks.DIORITE)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PESTLE.get()).define('X', Blocks.DIORITE).group("pestle")
          .pattern("  X").pattern("XX ").pattern("XX ").unlockedBy("has_diorite", has(Blocks.DIORITE))
          .save(consumer, ForgeRegistries.ITEMS.getKey(PESTLE.get()) + "2");
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MORTAR.get()).define('X', Tags.Items.STONE)
          .pattern("X X").pattern("X X").pattern(" X ").unlockedBy("has_stone", has(Tags.Items.STONE)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IMBUER.get()).define('X', Tags.Items.RODS_WOODEN).define('L', ItemTags.LOGS).define('S', Blocks.CHISELED_STONE_BRICKS)
          .pattern("X X").pattern("LSL").unlockedBy("has_chiseled_stone_bricks", has(Blocks.CHISELED_STONE_BRICKS)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MUNDANE_STANDING_STONE.get()).define('S', Tags.Items.STONE).define('B', Blocks.STONE_BRICKS).define('L', Tags.Items.STORAGE_BLOCKS_LAPIS)
          .pattern("SBS").pattern("BLB").pattern("SBS").unlockedBy("has_lapis_block", has(Tags.Items.STORAGE_BLOCKS_LAPIS)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ATTUNED_STANDING_STONE.get()).define('S', Tags.Items.STONE).define('N', Tags.Items.INGOTS_NETHER_BRICK).define('D', Tags.Items.GEMS_DIAMOND)
          .pattern("SNS").pattern("NDN").pattern("SNS").unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BRAZIER.get())
          .define('I', Tags.Items.INGOTS_IRON).define('S', Tags.Items.STRING).define('C', Items.CAULDRON).define('X', Tags.Items.RODS_WOODEN)
          .pattern("ISI").pattern("ICI").pattern("IXI").unlockedBy("has_cauldron", has(Items.CAULDRON)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ALTAR.get())
          .define('S', Tags.Items.STONE).define('F', Items.POPPY).define('B', VERDANT_SPRIG.get())
          .define('G', Tags.Items.STORAGE_BLOCKS_GOLD).define('C', Blocks.CHISELED_STONE_BRICKS)
          .pattern("BFB").pattern("SGS").pattern(" C ").unlockedBy("has_gold_block", has(Tags.Items.STORAGE_BLOCKS_GOLD)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, BARK_KNIFE.get())
          .define('S', Tags.Items.RODS_WOODEN).define('V', ItemTags.SAPLINGS).define('P', ItemTags.PLANKS)
          .pattern(" VV").pattern("VPV").pattern("SV ").unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RUNIC_TABLET.get())
          .define('S', Tags.Items.SEEDS_WHEAT).define('B', Tags.Items.STONE).define('R', OLD_ROOT.get())
          .pattern(" R ").pattern("SBS").pattern(" S ").unlockedBy("has_old_root", has(OLD_ROOT.get())).save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, GROWTH_POWDER.get(), 4)
          .requires(Tags.Items.SEEDS_WHEAT).requires(Items.GRASS).requires(Tags.Items.DUSTS_REDSTONE).requires(PESTLE.get())
          .unlockedBy("has_pestle", has(PESTLE.get())).save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MUTATING_POWDER.get())
          .requires(GROWTH_POWDER.get()).requires(GROWTH_POWDER.get()).requires(GROWTH_POWDER.get()).requires(GROWTH_POWDER.get())
          .requires(Tags.Items.NETHER_STARS).requires(Tags.Items.CROPS_NETHER_WART).requires(PESTLE.get())
          .unlockedBy("has_pestle", has(PESTLE.get())).save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ROOTY_STEW.get())
          .requires(Tags.Items.CROPS_WHEAT).requires(Items.BOWL).requires(OLD_ROOT.get())
          .unlockedBy("has_bowl", has(Items.BOWL)).save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FRUIT_SALAD.get())
          .requires(Items.MELON).requires(Items.MELON).requires(Items.MELON)
          .requires(Items.APPLE).requires(Items.BOWL).requires(ELDERBERRY.get())
          .requires(WHITECURRANT.get()).requires(BLACKCURRANT.get()).requires(REDCURRANT.get())
          .unlockedBy("has_bowl", has(Items.BOWL)).save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HEALING_POULTICE.get(), 2)
          .requires(REDCURRANT.get()).requires(Items.PAPER).requires(PESTLE.get()).requires(VERDANT_SPRIG.get())
          .unlockedBy("has_pestle", has(PESTLE.get())).save(consumer);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(DRAGONS_EYE.get()), RecipeCategory.MISC,
          Items.ENDER_PEARL, 1F, 200).unlockedBy("has_dragons_eye", has(DRAGONS_EYE.get()))
          .save(consumer, "rootsclassic:ender_pearl");
    }
  }

  public static class RootsBlockTags extends BlockTagsProvider {

    public RootsBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, Const.MODID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
      this.tag(RootsTags.NEEDS_LIVING_TOOL);
      this.tag(RootsTags.NEEDS_ENGRAVED_TOOL);
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
      TagKey<Item> barkTypeTag = ItemTags.create(new ResourceLocation(Const.MODID, "barks/" + treeType));
      this.tag(RootsTags.BARKS).addTag(barkTypeTag);
      this.tag(barkTypeTag).add(item);
    }
  }
}
