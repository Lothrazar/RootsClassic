package elucent.rootsclassic.registry;

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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class RootsRegistry {

  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Const.MODID);
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Const.MODID);
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Const.MODID);
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Const.MODID);
  /**
   * Blocks
   */
  //registerBlock(druidChalice = new BlockDruidChalice(), "druidChalice");
  public static final DeferredBlock<MortarBlock> MORTAR = BLOCKS.register("mortar", () -> new MortarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MortarBlockEntity>> MORTAR_TILE = BLOCK_ENTITY_TYPES.register("mortar", () -> BlockEntityType.Builder.of(MortarBlockEntity::new,
      RootsRegistry.MORTAR.get()).build(null));
  public static final DeferredBlock<AltarBlock> ALTAR = BLOCKS.register("altar", () -> new AltarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR_TILE = BLOCK_ENTITY_TYPES.register("altar", () -> BlockEntityType.Builder.of(AltarBlockEntity::new,
      RootsRegistry.ALTAR.get()).build(null));
  public static final DeferredBlock<BlockBrazier> BRAZIER = BLOCKS.register("brazier", () -> new BlockBrazier(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BrazierBlockEntity>> BRAZIER_TILE = BLOCK_ENTITY_TYPES.register("brazier", () -> BlockEntityType.Builder.of(BrazierBlockEntity::new,
      RootsRegistry.BRAZIER.get()).build(null));
  public static final DeferredBlock<ImbuerBlock> IMBUER = BLOCKS.register("imbuer", () -> new ImbuerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ImbuerBlockEntity>> IMBUER_TILE = BLOCK_ENTITY_TYPES.register("imbuer", () -> BlockEntityType.Builder.of(ImbuerBlockEntity::new,
      RootsRegistry.IMBUER.get()).build(null));
  public static final DeferredBlock<MundaneStandingStoneBlock> MUNDANE_STANDING_STONE = BLOCKS.register("mundane_standing_stone", () -> new MundaneStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredBlock<AttunedStandingStoneBlock> ATTUNED_STANDING_STONE = BLOCKS.register("attuned_standing_stone", () -> new AttunedStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredBlock<VacuumStandingStoneBlock> VACUUM_STANDING_STONE = BLOCKS.register("vacuum_standing_stone", () -> new VacuumStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VacuumStandingStoneTile>> VACUUM_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("vacuum_standing_stone", () -> BlockEntityType.Builder.of(VacuumStandingStoneTile::new, RootsRegistry.VACUUM_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<RepulsorStandingStoneBlock> REPULSOR_STANDING_STONE = BLOCKS.register("repulsor_standing_stone", () -> new RepulsorStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RepulsorStandingStoneTile>> REPULSOR_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("repulsor_standing_stone", () -> BlockEntityType.Builder.of(RepulsorStandingStoneTile::new, RootsRegistry.REPULSOR_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<AcceleratorStandingStoneBlock> ACCELERATOR_STANDING_STONE = BLOCKS.register("accelerator_standing_stone", () -> new AcceleratorStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcceleratorStandingStoneTile>> ACCELERATOR_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("accelerator_standing_stone", () -> BlockEntityType.Builder.of(AcceleratorStandingStoneTile::new, RootsRegistry.ACCELERATOR_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<AestheticStandingStoneBlock> AESTHETIC_STANDING_STONE = BLOCKS.register("aesthetic_standing_stone", () -> new AestheticStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AestheticStandingStoneTile>> AESTHETIC_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("aesthetic_standing_stone", () -> BlockEntityType.Builder.of(AestheticStandingStoneTile::new, RootsRegistry.AESTHETIC_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<EntanglerStandingStoneBlock> ENTANGLER_STANDING_STONE = BLOCKS.register("entangler_standing_stone", () -> new EntanglerStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EntanglerStandingStoneTile>> ENTANGLER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("entangler_standing_stone", () -> BlockEntityType.Builder.of(EntanglerStandingStoneTile::new, RootsRegistry.ENTANGLER_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<IgniterStandingStoneBlock> IGNITER_STANDING_STONE = BLOCKS.register("igniter_standing_stone", () -> new IgniterStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IgniterStandingStoneTile>> IGNITER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("igniter_standing_stone", () -> BlockEntityType.Builder.of(IgniterStandingStoneTile::new, RootsRegistry.IGNITER_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<GrowerStandingStoneBlock> GROWER_STANDING_STONE = BLOCKS.register("grower_standing_stone", () -> new GrowerStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowerStandingStoneTile>> GROWER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("grower_standing_stone", () -> BlockEntityType.Builder.of(GrowerStandingStoneTile::new, RootsRegistry.GROWER_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<HealerStandingStoneBlock> HEALER_STANDING_STONE = BLOCKS.register("healer_standing_stone", () -> new HealerStandingStoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).strength(1.0F)));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HealerStandingStone>> HEALER_STANDING_STONE_TILE = BLOCK_ENTITY_TYPES.register("healer_standing_stone", () -> BlockEntityType.Builder.of(HealerStandingStone::new, RootsRegistry.HEALER_STANDING_STONE.get()).build(null));
  public static final DeferredBlock<MidnightBloomBlock> MIDNIGHT_BLOOM = BLOCKS.register("midnight_bloom", () -> new MidnightBloomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
  public static final DeferredBlock<FlareOrchidBlock> FLARE_ORCHID = BLOCKS.register("flare_orchid", () -> new FlareOrchidBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
  public static final DeferredBlock<RadiantDaisyBlock> RADIANT_DAISY = BLOCKS.register("radiant_daisy", () -> new RadiantDaisyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
  /**
   * Items
   */
  public static final DeferredItem<DruidKnifeItem> BARK_KNIFE = ITEMS.register("bark_knife", () -> new DruidKnifeItem(itemBuilder().durability(64)));
  public static final DeferredItem<SpellPowderItem> SPELL_POWDER = ITEMS.register("spell_powder", () -> new SpellPowderItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<PestleItem> PESTLE = ITEMS.register("pestle", () -> new PestleItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<StaffItem> STAFF = ITEMS.register("staff", () -> new StaffItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<CrystalStaffItem> CRYSTAL_STAFF = ITEMS.register("crystal_staff", () -> new CrystalStaffItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<RootsFoodItem> OLD_ROOT = ITEMS.register("old_root", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.OLD_ROOT)));
  public static final DeferredItem<Item> VERDANT_SPRIG = ITEMS.registerSimpleItem("verdant_sprig");
  public static final DeferredItem<InfernalBulbItem> INFERNAL_BULB = ITEMS.register("infernal_bulb", () -> new InfernalBulbItem(itemBuilder()));
  public static final DeferredItem<DragonsEyeItem> DRAGONS_EYE = ITEMS.register("dragons_eye", () -> new DragonsEyeItem(itemBuilder().food(RootsFoods.DRAGONS_EYE)));
  public static final DeferredItem<Item> OAK_BARK = ITEMS.registerSimpleItem("oak_bark");
  public static final DeferredItem<Item> SPRUCE_BARK = ITEMS.registerSimpleItem("spruce_bark");
  public static final DeferredItem<Item> BIRCH_BARK = ITEMS.registerSimpleItem("birch_bark");
  public static final DeferredItem<Item> JUNGLE_BARK = ITEMS.registerSimpleItem("jungle_bark");
  public static final DeferredItem<Item> ACACIA_BARK = ITEMS.registerSimpleItem("acacia_bark");
  public static final DeferredItem<Item> DARK_OAK_BARK = ITEMS.registerSimpleItem("dark_oak_bark");
  public static final DeferredItem<LivingSwordItem> LIVING_SWORD = ITEMS.register("living_sword", () -> new LivingSwordItem(RootsItemTier.LIVING, 3, -2.4F, itemBuilder()));
  public static final DeferredItem<LivingShovelItem> LIVING_SHOVEL = ITEMS.register("living_shovel", () -> new LivingShovelItem(RootsItemTier.LIVING, 1.5F, -3.0F, itemBuilder()));
  public static final DeferredItem<LivingPickaxeItem> LIVING_PICKAXE = ITEMS.register("living_pickaxe", () -> new LivingPickaxeItem(RootsItemTier.LIVING, 1, -2.8F, itemBuilder()));
  public static final DeferredItem<LivingAxeItem> LIVING_AXE = ITEMS.register("living_axe", () -> new LivingAxeItem(RootsItemTier.LIVING, 7.0F, -3.2F, itemBuilder()));
  public static final DeferredItem<LivingHoeItem> LIVING_HOE = ITEMS.register("living_hoe", () -> new LivingHoeItem(RootsItemTier.LIVING, -1, -2.0F, itemBuilder()));
  public static final DeferredItem<SylvanArmorItem> SYLVAN_HOOD = ITEMS.register("sylvan_hood", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.HELMET, itemBuilder()));
  public static final DeferredItem<SylvanArmorItem> SYLVAN_ROBE = ITEMS.register("sylvan_robe", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.CHESTPLATE, itemBuilder()));
  public static final DeferredItem<SylvanArmorItem> SYLVAN_TUNIC = ITEMS.register("sylvan_tunic", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.LEGGINGS, itemBuilder()));
  public static final DeferredItem<SylvanArmorItem> SYLVAN_BOOTS = ITEMS.register("sylvan_boots", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, ArmorItem.Type.BOOTS, itemBuilder()));
  public static final DeferredItem<WildwoodArmorItem> WILDWOOD_MASK = ITEMS.register("wildwood_mask", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.HELMET, itemBuilder()));
  public static final DeferredItem<WildwoodArmorItem> WILDWOOD_PLATE = ITEMS.register("wildwood_plate", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.CHESTPLATE, itemBuilder()));
  public static final DeferredItem<WildwoodArmorItem> WILDWOOD_LEGGINGS = ITEMS.register("wildwood_leggings", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.LEGGINGS, itemBuilder()));
  public static final DeferredItem<WildwoodArmorItem> WILDWOOD_BOOTS = ITEMS.register("wildwood_boots", () -> new WildwoodArmorItem(RootsArmorMaterial.WILDWOOD, ArmorItem.Type.BOOTS, itemBuilder()));
  public static final DeferredItem<RunicTabletItem> RUNIC_TABLET = ITEMS.register("runic_tablet", () -> new RunicTabletItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<GrowthPowderItem> GROWTH_POWDER = ITEMS.register("growth_powder", () -> new GrowthPowderItem(itemBuilder()));
  public static final DeferredItem<MutatingPowderItem> MUTATING_POWDER = ITEMS.register("mutating_powder", () -> new MutatingPowderItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<RootsFoodItem> NIGHTSHADE = ITEMS.register("nightshade", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.NIGHTSHADE)));
  public static final DeferredItem<RootsFoodItem> BLACKCURRANT = ITEMS.register("blackcurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.BLACKCURRANT)));
  public static final DeferredItem<RootsFoodItem> REDCURRANT = ITEMS.register("redcurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.REDCURRANT)));
  public static final DeferredItem<RootsFoodItem> WHITECURRANT = ITEMS.register("whitecurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.WHITECURRANT)));
  public static final DeferredItem<RootsFoodItem> ELDERBERRY = ITEMS.register("elderberry", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.ELDERBERRY)));
  public static final DeferredItem<RootsFoodItem> HEALING_POULTICE = ITEMS.register("healing_poultice", () -> new RootsFoodItem(itemBuilder().stacksTo(8).food(RootsFoods.HEALING_POULTICE)));
  public static final DeferredItem<Item> ROOTY_STEW = ITEMS.register("rooty_stew", () -> new Item(itemBuilder().stacksTo(1).food(RootsFoods.ROOTY_STEW)));
  public static final DeferredItem<Item> FRUIT_SALAD = ITEMS.register("fruit_salad", () -> new Item(itemBuilder().stacksTo(1).food(RootsFoods.FRUIT_SALAD)));
  public static final DeferredItem<RunicFocusItem> RUNIC_FOCUS = ITEMS.register("runic_focus", () -> new RunicFocusItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<RunicFocusItem> CHARGED_RUNIC_FOCUS = ITEMS.register("charged_runic_focus", () -> new RunicFocusItem(itemBuilder().stacksTo(1)));
  public static final DeferredItem<EngravedBladeItem> ENGRAVED_BLADE = ITEMS.register("engraved_blade", () -> new EngravedBladeItem(RootsItemTier.ENGRAVED, 3, -3.0F, itemBuilder()));
  public static final DeferredItem<Item> MANA_RESEARCH_ICON = ITEMS.registerSimpleItem("mana_research_icon");
  //TODO Register an egg for the phantom skeleton 		EntityRegistry.registerEgg(Const.modLoc(PhantomSkeletonEntity.NAME), RootsUtil.intColor(87, 58, 134), 0xA0A0A0);
  /**
   * Block Items
   */
  public static final DeferredItem<BlockItem> MORTAR_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.MORTAR);
  public static final DeferredItem<BlockItem> ALTAR_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.ALTAR);
  public static final DeferredItem<BlockItem> BRAZIER_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.BRAZIER);
  public static final DeferredItem<BlockItem> IMBUER_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.IMBUER);
  public static final DeferredItem<BlockItem> MUNDANE_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.MUNDANE_STANDING_STONE);
  public static final DeferredItem<BlockItem> ATTUNED_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.ATTUNED_STANDING_STONE);
  public static final DeferredItem<BlockItem> VACUUM_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.VACUUM_STANDING_STONE);
  public static final DeferredItem<BlockItem> REPULSOR_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.REPULSOR_STANDING_STONE);
  public static final DeferredItem<BlockItem> ACCELERATOR_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.ACCELERATOR_STANDING_STONE);
  public static final DeferredItem<BlockItem> AESTHETIC_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.AESTHETIC_STANDING_STONE);
  public static final DeferredItem<BlockItem> ENTANGLER_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.ENTANGLER_STANDING_STONE);
  public static final DeferredItem<BlockItem> IGNITER_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.IGNITER_STANDING_STONE);
  public static final DeferredItem<BlockItem> GROWER_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.GROWER_STANDING_STONE);
  public static final DeferredItem<BlockItem> HEALER_STANDING_STONE_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.HEALER_STANDING_STONE);
  public static final DeferredItem<BlockItem> MIDNIGHT_BLOOM_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.MIDNIGHT_BLOOM);
  public static final DeferredItem<BlockItem> FLARE_ORCHID_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.FLARE_ORCHID);
  public static final DeferredItem<BlockItem> RADIANT_DAISY_ITEM = ITEMS.registerSimpleBlockItem(RootsRegistry.RADIANT_DAISY);

  private static Item.Properties itemBuilder() {
    return new Item.Properties();
  }

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ROOTS_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
      .icon(() -> new ItemStack(RootsRegistry.SPELL_POWDER.get()))
      .title(Component.translatable("itemGroup.rootsclassic"))
      .displayItems((displayParameters, output) -> {
        List<ItemStack> stacks = RootsRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get()))
            .filter(stack -> !stack.is(RootsRegistry.MANA_RESEARCH_ICON.get())).toList();
        output.acceptAll(stacks);
      }).build());
}
