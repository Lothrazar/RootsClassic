package elucent.rootsclassic.registry;

import java.util.List;
import com.lothrazar.library.registry.RecipeCompostFactory;
import com.lothrazar.library.registry.RecipeCompostFactory.FillValues;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.AcceleratorStandingStoneBlock;
import elucent.rootsclassic.block.AestheticStandingStoneBlock;
import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.block.EntanglerStandingStoneBlock;
import elucent.rootsclassic.block.GrowerStandingStoneBlock;
import elucent.rootsclassic.block.HealerStandingStoneBlock;
import elucent.rootsclassic.block.IgniterStandingStoneBlock;
import elucent.rootsclassic.block.MundaneStandingStoneBlock;
import elucent.rootsclassic.block.RepulsorStandingStoneBlock;
import elucent.rootsclassic.block.VacuumStandingStoneBlock;
import elucent.rootsclassic.block.altar.AltarBlock;
import elucent.rootsclassic.block.altar.AltarBlockEntity;
import elucent.rootsclassic.block.brazier.BlockBrazier;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.block.flowers.FlareOrchidBlock;
import elucent.rootsclassic.block.flowers.MidnightBloomBlock;
import elucent.rootsclassic.block.flowers.RadiantDaisyBlock;
import elucent.rootsclassic.block.imbuer.ImbuerBlock;
import elucent.rootsclassic.block.imbuer.ImbuerBlockEntity;
import elucent.rootsclassic.block.mortar.MortarBlock;
import elucent.rootsclassic.block.mortar.MortarBlockEntity;
import elucent.rootsclassic.blockentity.AcceleratorStandingStoneTile;
import elucent.rootsclassic.blockentity.AestheticStandingStoneTile;
import elucent.rootsclassic.blockentity.EntanglerStandingStoneTile;
import elucent.rootsclassic.blockentity.GrowerStandingStoneTile;
import elucent.rootsclassic.blockentity.HealerStandingStone;
import elucent.rootsclassic.blockentity.IgniterStandingStoneTile;
import elucent.rootsclassic.blockentity.RepulsorStandingStoneTile;
import elucent.rootsclassic.blockentity.VacuumStandingStoneTile;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.item.DragonsEyeItem;
import elucent.rootsclassic.item.DruidKnifeItem;
import elucent.rootsclassic.item.EngravedBladeItem;
import elucent.rootsclassic.item.GrowthPowderItem;
import elucent.rootsclassic.item.InfernalBulbItem;
import elucent.rootsclassic.item.LivingAxeItem;
import elucent.rootsclassic.item.LivingHoeItem;
import elucent.rootsclassic.item.LivingPickaxeItem;
import elucent.rootsclassic.item.LivingShovelItem;
import elucent.rootsclassic.item.LivingSwordItem;
import elucent.rootsclassic.item.MutatingPowderItem;
import elucent.rootsclassic.item.PestleItem;
import elucent.rootsclassic.item.RootsFoodItem;
import elucent.rootsclassic.item.RunicFocusItem;
import elucent.rootsclassic.item.RunicTabletItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.item.SylvanArmorItem;
import elucent.rootsclassic.item.WildwoodArmorItem;
import elucent.rootsclassic.item.powder.SpellPowderItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RootsRegistry {

  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Const.MODID);
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Const.MODID);
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Const.MODID);
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Const.MODID);
  /**
   * Blocks
   */
  //registerBlock(druidChalice = new BlockDruidChalice(), "druidChalice");
  public static final RegistryObject<Block> MORTAR = BLOCKS.register("mortar", () -> new MortarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<MortarBlockEntity>> MORTAR_TILE = BLOCK_ENTITY_TYPES.register("mortar", () -> BlockEntityType.Builder.of(MortarBlockEntity::new,
      RootsRegistry.MORTAR.get()).build(null));
  public static final RegistryObject<Block> ALTAR = BLOCKS.register("altar", () -> new AltarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<AltarBlockEntity>> ALTAR_TILE = BLOCK_ENTITY_TYPES.register("altar", () -> BlockEntityType.Builder.of(AltarBlockEntity::new,
      RootsRegistry.ALTAR.get()).build(null));
  public static final RegistryObject<Block> BRAZIER = BLOCKS.register("brazier", () -> new BlockBrazier(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<BrazierBlockEntity>> BRAZIER_TILE = BLOCK_ENTITY_TYPES.register("brazier", () -> BlockEntityType.Builder.of(BrazierBlockEntity::new,
      RootsRegistry.BRAZIER.get()).build(null));
  public static final RegistryObject<Block> IMBUER = BLOCKS.register("imbuer", () -> new ImbuerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<ImbuerBlockEntity>> IMBUER_TILE = BLOCK_ENTITY_TYPES.register("imbuer", () -> BlockEntityType.Builder.of(ImbuerBlockEntity::new,
      RootsRegistry.IMBUER.get()).build(null));
  public static final RegistryObject<Block> MUNDANE_STANDING_STONE = BLOCKS.register("mundane_standing_stone", () -> new MundaneStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<Block> ATTUNED_STANDING_STONE = BLOCKS.register("attuned_standing_stone", () -> new AttunedStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<Block> VACUUM_STANDING_STONE = BLOCKS.register("vacuum_standing_stone", () -> new VacuumStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<VacuumStandingStoneTile>> VACUUM_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("vacuum_standing_stone", () -> BlockEntityType.Builder.of(VacuumStandingStoneTile::new, RootsRegistry.VACUUM_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> REPULSOR_STANDING_STONE = BLOCKS.register("repulsor_standing_stone", () -> new RepulsorStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<RepulsorStandingStoneTile>> REPULSOR_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("repulsor_standing_stone", () -> BlockEntityType.Builder.of(RepulsorStandingStoneTile::new, RootsRegistry.REPULSOR_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> ACCELERATOR_STANDING_STONE = BLOCKS.register("accelerator_standing_stone", () -> new AcceleratorStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<AcceleratorStandingStoneTile>> ACCELERATOR_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("accelerator_standing_stone", () -> BlockEntityType.Builder.of(AcceleratorStandingStoneTile::new, RootsRegistry.ACCELERATOR_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> AESTHETIC_STANDING_STONE = BLOCKS.register("aesthetic_standing_stone", () -> new AestheticStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<AestheticStandingStoneTile>> AESTHETIC_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("aesthetic_standing_stone", () -> BlockEntityType.Builder.of(AestheticStandingStoneTile::new, RootsRegistry.AESTHETIC_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> ENTANGLER_STANDING_STONE = BLOCKS.register("entangler_standing_stone", () -> new EntanglerStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<EntanglerStandingStoneTile>> ENTANGLER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("entangler_standing_stone", () -> BlockEntityType.Builder.of(EntanglerStandingStoneTile::new, RootsRegistry.ENTANGLER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> IGNITER_STANDING_STONE = BLOCKS.register("igniter_standing_stone", () -> new IgniterStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<IgniterStandingStoneTile>> IGNITER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("igniter_standing_stone", () -> BlockEntityType.Builder.of(IgniterStandingStoneTile::new, RootsRegistry.IGNITER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> GROWER_STANDING_STONE = BLOCKS.register("grower_standing_stone", () -> new GrowerStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<GrowerStandingStoneTile>> GROWER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("grower_standing_stone", () -> BlockEntityType.Builder.of(GrowerStandingStoneTile::new, RootsRegistry.GROWER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> HEALER_STANDING_STONE = BLOCKS.register("healer_standing_stone", () -> new HealerStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final RegistryObject<BlockEntityType<HealerStandingStone>> HEALER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("healer_standing_stone", () -> BlockEntityType.Builder.of(HealerStandingStone::new, RootsRegistry.HEALER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> MIDNIGHT_BLOOM = BLOCKS.register("midnight_bloom", () -> new MidnightBloomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
  public static final RegistryObject<Block> FLARE_ORCHID = BLOCKS.register("flare_orchid", () -> new FlareOrchidBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
  public static final RegistryObject<Block> RADIANT_DAISY = BLOCKS.register("radiant_daisy", () -> new RadiantDaisyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
  /**
   * Items
   */
  public static final RegistryObject<Item> BARK_KNIFE = ITEMS.register("bark_knife", () -> new DruidKnifeItem(itemBuilder().durability(64)));
  public static final RegistryObject<Item> SPELL_POWDER = ITEMS.register("spell_powder", () -> new SpellPowderItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> PESTLE = ITEMS.register("pestle", () -> new PestleItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> STAFF = ITEMS.register("staff", () -> new StaffItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> CRYSTAL_STAFF = ITEMS.register("crystal_staff", () -> new CrystalStaffItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> OLD_ROOT = ITEMS.register("old_root", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.OLD_ROOT)));
  public static final RegistryObject<Item> VERDANT_SPRIG = ITEMS.register("verdant_sprig", () -> new Item(itemBuilder()));
  public static final RegistryObject<Item> INFERNAL_BULB = ITEMS.register("infernal_bulb", () -> new InfernalBulbItem(itemBuilder()));
  public static final RegistryObject<Item> DRAGONS_EYE = ITEMS.register("dragons_eye", () -> new DragonsEyeItem(itemBuilder().food(RootsFoods.DRAGONS_EYE)));
  public static final RegistryObject<Item> OAK_BARK = ITEMS.register("oak_bark", () -> new Item(itemBuilder()));
  public static final RegistryObject<Item> SPRUCE_BARK = ITEMS.register("spruce_bark", () -> new Item(itemBuilder()));
  public static final RegistryObject<Item> BIRCH_BARK = ITEMS.register("birch_bark", () -> new Item(itemBuilder()));
  public static final RegistryObject<Item> JUNGLE_BARK = ITEMS.register("jungle_bark", () -> new Item(itemBuilder()));
  public static final RegistryObject<Item> ACACIA_BARK = ITEMS.register("acacia_bark", () -> new Item(itemBuilder()));
  public static final RegistryObject<Item> DARK_OAK_BARK = ITEMS.register("dark_oak_bark", () -> new Item(itemBuilder()));
  public static final RegistryObject<Item> LIVING_SWORD = ITEMS.register("living_sword", () -> new LivingSwordItem(RootsItemTier.LIVING, 3, -2.4F, itemBuilder()));
  public static final RegistryObject<Item> LIVING_SHOVEL = ITEMS.register("living_shovel", () -> new LivingShovelItem(RootsItemTier.LIVING, 1.5F, -3.0F, itemBuilder()));
  public static final RegistryObject<Item> LIVING_PICKAXE = ITEMS.register("living_pickaxe", () -> new LivingPickaxeItem(RootsItemTier.LIVING, 1, -2.8F, itemBuilder()));
  public static final RegistryObject<Item> LIVING_AXE = ITEMS.register("living_axe", () -> new LivingAxeItem(RootsItemTier.LIVING, 7.0F, -3.2F, itemBuilder()));
  public static final RegistryObject<Item> LIVING_HOE = ITEMS.register("living_hoe", () -> new LivingHoeItem(RootsItemTier.LIVING, -1, -2.0F, itemBuilder()));
  public static final RegistryObject<Item> SYLVAN_HOOD = ITEMS.register("sylvan_hood", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.HELMET, itemBuilder()));
  public static final RegistryObject<Item> SYLVAN_ROBE = ITEMS.register("sylvan_robe", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.CHESTPLATE, itemBuilder()));
  public static final RegistryObject<Item> SYLVAN_TUNIC = ITEMS.register("sylvan_tunic", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.LEGGINGS, itemBuilder()));
  public static final RegistryObject<Item> SYLVAN_BOOTS = ITEMS.register("sylvan_boots", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.BOOTS, itemBuilder()));
  public static final RegistryObject<Item> WILDWOOD_MASK = ITEMS.register("wildwood_mask", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.HELMET, itemBuilder()));
  public static final RegistryObject<Item> WILDWOOD_PLATE = ITEMS.register("wildwood_plate", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.CHESTPLATE, itemBuilder()));
  public static final RegistryObject<Item> WILDWOOD_LEGGINGS = ITEMS.register("wildwood_leggings", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.LEGGINGS, itemBuilder()));
  public static final RegistryObject<Item> WILDWOOD_BOOTS = ITEMS.register("wildwood_boots", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.BOOTS, itemBuilder()));
  public static final RegistryObject<Item> RUNIC_TABLET = ITEMS.register("runic_tablet", () -> new RunicTabletItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> GROWTH_POWDER = ITEMS.register("growth_powder", () -> new GrowthPowderItem(itemBuilder()));
  public static final RegistryObject<Item> MUTATING_POWDER = ITEMS.register("mutating_powder", () -> new MutatingPowderItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> NIGHTSHADE = ITEMS.register("nightshade", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.NIGHTSHADE)));
  public static final RegistryObject<Item> BLACKCURRANT = ITEMS.register("blackcurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.BLACKCURRANT)));
  public static final RegistryObject<Item> REDCURRANT = ITEMS.register("redcurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.REDCURRANT)));
  public static final RegistryObject<Item> WHITECURRANT = ITEMS.register("whitecurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.WHITECURRANT)));
  public static final RegistryObject<Item> ELDERBERRY = ITEMS.register("elderberry", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.ELDERBERRY)));
  public static final RegistryObject<Item> HEALING_POULTICE = ITEMS.register("healing_poultice", () -> new RootsFoodItem(itemBuilder().stacksTo(8).food(RootsFoods.HEALING_POULTICE)));
  public static final RegistryObject<Item> ROOTY_STEW = ITEMS.register("rooty_stew", () -> new BowlFoodItem(itemBuilder().stacksTo(1).food(RootsFoods.ROOTY_STEW)));
  public static final RegistryObject<Item> FRUIT_SALAD = ITEMS.register("fruit_salad", () -> new BowlFoodItem(itemBuilder().stacksTo(1).food(RootsFoods.FRUIT_SALAD)));
  public static final RegistryObject<Item> RUNIC_FOCUS = ITEMS.register("runic_focus", () -> new RunicFocusItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> CHARGED_RUNIC_FOCUS = ITEMS.register("charged_runic_focus", () -> new RunicFocusItem(itemBuilder().stacksTo(1)));
  public static final RegistryObject<Item> ENGRAVED_BLADE = ITEMS.register("engraved_blade", () -> new EngravedBladeItem(RootsItemTier.ENGRAVED, 3, -3.0F, itemBuilder()));
  public static final RegistryObject<Item> MANA_RESEARCH_ICON = ITEMS.register("mana_research_icon", () -> new Item(itemBuilder()));
  //TODO Register an egg for the phantom skeleton 		EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, PhantomSkeletonEntity.NAME), RootsUtil.intColor(87, 58, 134), 0xA0A0A0);
  /**
   * Block Items
   */
  public static final RegistryObject<Item> MORTAR_ITEM = ITEMS.register("mortar", () -> new BlockItem(RootsRegistry.MORTAR.get(), itemBuilder()));
  public static final RegistryObject<Item> ALTAR_ITEM = ITEMS.register("altar", () -> new BlockItem(RootsRegistry.ALTAR.get(), itemBuilder()));
  public static final RegistryObject<Item> BRAZIER_ITEM = ITEMS.register("brazier", () -> new BlockItem(RootsRegistry.BRAZIER.get(), itemBuilder()));
  public static final RegistryObject<Item> IMBUER_ITEM = ITEMS.register("imbuer", () -> new BlockItem(RootsRegistry.IMBUER.get(), itemBuilder()));
  public static final RegistryObject<Item> MUNDANE_STANDING_STONE_ITEM = ITEMS.register("mundane_standing_stone", () -> new BlockItem(RootsRegistry.MUNDANE_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> ATTUNED_STANDING_STONE_ITEM = ITEMS.register("attuned_standing_stone", () -> new BlockItem(RootsRegistry.ATTUNED_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> VACUUM_STANDING_STONE_ITEM = ITEMS.register("vacuum_standing_stone", () -> new BlockItem(RootsRegistry.VACUUM_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> REPULSOR_STANDING_STONE_ITEM = ITEMS.register("repulsor_standing_stone", () -> new BlockItem(RootsRegistry.REPULSOR_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> ACCELERATOR_STANDING_STONE_ITEM = ITEMS.register("accelerator_standing_stone", () -> new BlockItem(RootsRegistry.ACCELERATOR_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> AESTHETIC_STANDING_STONE_ITEM = ITEMS.register("aesthetic_standing_stone", () -> new BlockItem(RootsRegistry.AESTHETIC_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> ENTANGLER_STANDING_STONE_ITEM = ITEMS.register("entangler_standing_stone", () -> new BlockItem(RootsRegistry.ENTANGLER_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> IGNITER_STANDING_STONE_ITEM = ITEMS.register("igniter_standing_stone", () -> new BlockItem(RootsRegistry.IGNITER_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> GROWER_STANDING_STONE_ITEM = ITEMS.register("grower_standing_stone", () -> new BlockItem(RootsRegistry.GROWER_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> HEALER_STANDING_STONE_ITEM = ITEMS.register("healer_standing_stone", () -> new BlockItem(RootsRegistry.HEALER_STANDING_STONE.get(), itemBuilder()));
  public static final RegistryObject<Item> MIDNIGHT_BLOOM_ITEM = ITEMS.register("midnight_bloom", () -> new BlockItem(RootsRegistry.MIDNIGHT_BLOOM.get(), itemBuilder()));
  public static final RegistryObject<Item> FLARE_ORCHID_ITEM = ITEMS.register("flare_orchid", () -> new BlockItem(RootsRegistry.FLARE_ORCHID.get(), itemBuilder()));
  public static final RegistryObject<Item> RADIANT_DAISY_ITEM = ITEMS.register("radiant_daisy", () -> new BlockItem(RootsRegistry.RADIANT_DAISY.get(), itemBuilder()));

  private static Item.Properties itemBuilder() {
    return new Item.Properties();
  }

  public static final RegistryObject<CreativeModeTab> ROOTS_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
      .icon(() -> new ItemStack(RootsRegistry.SPELL_POWDER.get()))
      .title(Component.translatable("itemGroup.rootsclassic"))
      .displayItems((displayParameters, output) -> {
        List<ItemStack> stacks = RootsRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get()))
            .filter(stack -> !stack.is(RootsRegistry.MANA_RESEARCH_ICON.get())).toList();
        output.acceptAll(stacks);
      }).build());

  public static void registerCompostables() {
    RecipeCompostFactory.put(BLACKCURRANT.get(), FillValues.LEAVES);
    RecipeCompostFactory.put(REDCURRANT.get(), FillValues.LEAVES);
    RecipeCompostFactory.put(WHITECURRANT.get(), FillValues.LEAVES);
    RecipeCompostFactory.put(NIGHTSHADE.get(), FillValues.FLOWER);
    RecipeCompostFactory.put(ELDERBERRY.get(), FillValues.FLOWER);
  }
}
