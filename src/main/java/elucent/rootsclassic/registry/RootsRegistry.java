package elucent.rootsclassic.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SoupItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.Roots;
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
import elucent.rootsclassic.block.altar.AltarTile;
import elucent.rootsclassic.block.brazier.BlockBrazier;
import elucent.rootsclassic.block.brazier.BrazierTile;
import elucent.rootsclassic.block.flowers.FlareOrchidBlock;
import elucent.rootsclassic.block.flowers.MidnightBloomBlock;
import elucent.rootsclassic.block.flowers.RadiantDaisyBlock;
import elucent.rootsclassic.block.imbuer.ImbuerBlock;
import elucent.rootsclassic.block.imbuer.ImbuerTile;
import elucent.rootsclassic.block.mortar.MortarBlock;
import elucent.rootsclassic.block.mortar.MortarTile;
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
import elucent.rootsclassic.item.SpellPowderItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.item.SylvanArmorItem;
import elucent.rootsclassic.item.WildwoodArmorItem;
import elucent.rootsclassic.tile.AcceleratorStandingStoneTile;
import elucent.rootsclassic.tile.AestheticStandingStoneTile;
import elucent.rootsclassic.tile.EntanglerStandingStoneTile;
import elucent.rootsclassic.tile.GrowerStandingStoneTile;
import elucent.rootsclassic.tile.HealerStandingStone;
import elucent.rootsclassic.tile.IgniterStandingStoneTile;
import elucent.rootsclassic.tile.RepulsorStandingStoneTile;
import elucent.rootsclassic.tile.VacuumStandingStoneTile;

public class RootsRegistry {

  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Const.MODID);
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Const.MODID);
  public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Const.MODID);
  /**
   * Blocks
   */
  //registerBlock(druidChalice = new BlockDruidChalice(), "druidChalice");
  public static final RegistryObject<Block> MORTAR = BLOCKS.register("mortar", () -> new MortarBlock(AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<MortarTile>> MORTAR_TILE = TILE_ENTITIES.register("mortar", () -> TileEntityType.Builder.create(MortarTile::new,
      RootsRegistry.MORTAR.get()).build(null));
  public static final RegistryObject<Block> ALTAR = BLOCKS.register("altar", () -> new AltarBlock(AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<AltarTile>> ALTAR_TILE = TILE_ENTITIES.register("altar", () -> TileEntityType.Builder.create(AltarTile::new,
      RootsRegistry.ALTAR.get()).build(null));
  public static final RegistryObject<Block> BRAZIER = BLOCKS.register("brazier", () -> new BlockBrazier(AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<BrazierTile>> BRAZIER_TILE = TILE_ENTITIES.register("brazier", () -> TileEntityType.Builder.create(BrazierTile::new,
      RootsRegistry.BRAZIER.get()).build(null));
  public static final RegistryObject<Block> IMBUER = BLOCKS.register("imbuer", () -> new ImbuerBlock(AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<ImbuerTile>> IMBUER_TILE = TILE_ENTITIES.register("imbuer", () -> TileEntityType.Builder.create(ImbuerTile::new,
      RootsRegistry.IMBUER.get()).build(null));
  public static final RegistryObject<Block> MUNDANE_STANDING_STONE = BLOCKS.register("mundane_standing_stone", () -> new MundaneStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<Block> ATTUNED_STANDING_STONE = BLOCKS.register("attuned_standing_stone", () -> new AttunedStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<Block> VACUUM_STANDING_STONE = BLOCKS.register("vacuum_standing_stone", () -> new VacuumStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<VacuumStandingStoneTile>> VACUUM_STANDING_STONE_TILE = TILE_ENTITIES.register("vacuum_standing_stone", () -> TileEntityType.Builder.create(VacuumStandingStoneTile::new, RootsRegistry.VACUUM_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> REPULSOR_STANDING_STONE = BLOCKS.register("repulsor_standing_stone", () -> new RepulsorStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<RepulsorStandingStoneTile>> REPULSOR_STANDING_STONE_TILE = TILE_ENTITIES.register("repulsor_standing_stone", () -> TileEntityType.Builder.create(RepulsorStandingStoneTile::new, RootsRegistry.REPULSOR_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> ACCELERATOR_STANDING_STONE = BLOCKS.register("accelerator_standing_stone", () -> new AcceleratorStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<AcceleratorStandingStoneTile>> ACCELERATOR_STANDING_STONE_TILE = TILE_ENTITIES.register("accelerator_standing_stone", () -> TileEntityType.Builder.create(AcceleratorStandingStoneTile::new, RootsRegistry.ACCELERATOR_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> AESTHETIC_STANDING_STONE = BLOCKS.register("aesthetic_standing_stone", () -> new AestheticStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<AestheticStandingStoneTile>> AESTHETIC_STANDING_STONE_TILE = TILE_ENTITIES.register("aesthetic_standing_stone", () -> TileEntityType.Builder.create(AestheticStandingStoneTile::new, RootsRegistry.AESTHETIC_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> ENTANGLER_STANDING_STONE = BLOCKS.register("entangler_standing_stone", () -> new EntanglerStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<EntanglerStandingStoneTile>> ENTANGLER_STANDING_STONE_TILE = TILE_ENTITIES.register("entangler_standing_stone", () -> TileEntityType.Builder.create(EntanglerStandingStoneTile::new, RootsRegistry.ENTANGLER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> IGNITER_STANDING_STONE = BLOCKS.register("igniter_standing_stone", () -> new IgniterStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<IgniterStandingStoneTile>> IGNITER_STANDING_STONE_TILE = TILE_ENTITIES.register("igniter_standing_stone", () -> TileEntityType.Builder.create(IgniterStandingStoneTile::new, RootsRegistry.IGNITER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> GROWER_STANDING_STONE = BLOCKS.register("grower_standing_stone", () -> new GrowerStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<GrowerStandingStoneTile>> GROWER_STANDING_STONE_TILE = TILE_ENTITIES.register("grower_standing_stone", () -> TileEntityType.Builder.create(GrowerStandingStoneTile::new, RootsRegistry.GROWER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> HEALER_STANDING_STONE = BLOCKS.register("healer_standing_stone", () -> new HealerStandingStoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)));
  public static final RegistryObject<TileEntityType<HealerStandingStone>> HEALER_STANDING_STONE_TILE = TILE_ENTITIES.register("healer_standing_stone", () -> TileEntityType.Builder.create(HealerStandingStone::new, RootsRegistry.HEALER_STANDING_STONE.get()).build(null));
  public static final RegistryObject<Block> MIDNIGHT_BLOOM = BLOCKS.register("midnight_bloom", () -> new MidnightBloomBlock(AbstractBlock.Properties.create(Material.PLANTS)));
  public static final RegistryObject<Block> FLARE_ORCHID = BLOCKS.register("flare_orchid", () -> new FlareOrchidBlock(AbstractBlock.Properties.create(Material.PLANTS)));
  public static final RegistryObject<Block> RADIANT_DAISY = BLOCKS.register("radiant_daisy", () -> new RadiantDaisyBlock(AbstractBlock.Properties.create(Material.PLANTS)));
  /**
   * Items
   */
  public static final RegistryObject<Item> BARK_KNIFE = ITEMS.register("bark_knife", () -> new DruidKnifeItem(itemBuilder().maxDamage(64).group(Roots.tab)));
  public static final RegistryObject<Item> SPELL_POWDER = ITEMS.register("spell_powder", () -> new SpellPowderItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> PESTLE = ITEMS.register("pestle", () -> new PestleItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> STAFF = ITEMS.register("staff", () -> new StaffItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> CRYSTAL_STAFF = ITEMS.register("crystal_staff", () -> new CrystalStaffItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> OLD_ROOT = ITEMS.register("old_root", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.OLD_ROOT).group(Roots.tab)));
  public static final RegistryObject<Item> VERDANT_SPRIG = ITEMS.register("verdant_sprig", () -> new Item(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> INFERNAL_BULB = ITEMS.register("infernal_bulb", () -> new InfernalBulbItem(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> DRAGONS_EYE = ITEMS.register("dragons_eye", () -> new DragonsEyeItem(itemBuilder().group(Roots.tab).food(RootsFoods.DRAGONS_EYE)));
  public static final RegistryObject<Item> OAK_BARK = ITEMS.register("oak_bark", () -> new Item(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> SPRUCE_BARK = ITEMS.register("spruce_bark", () -> new Item(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> BIRCH_BARK = ITEMS.register("birch_bark", () -> new Item(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> JUNGLE_BARK = ITEMS.register("jungle_bark", () -> new Item(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> ACACIA_BARK = ITEMS.register("acacia_bark", () -> new Item(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> DARK_OAK_BARK = ITEMS.register("dark_oak_bark", () -> new Item(itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> LIVING_SWORD = ITEMS.register("living_sword", () -> new LivingSwordItem(RootsItemTier.LIVING, 3, -2.4F, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> LIVING_SHOVEL = ITEMS.register("living_shovel", () -> new LivingShovelItem(RootsItemTier.LIVING, 1.5F, -3.0F, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> LIVING_PICKAXE = ITEMS.register("living_pickaxe", () -> new LivingPickaxeItem(RootsItemTier.LIVING, 1, -2.8F, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> LIVING_AXE = ITEMS.register("living_axe", () -> new LivingAxeItem(RootsItemTier.LIVING, 7.0F, -3.2F, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> LIVING_HOE = ITEMS.register("living_hoe", () -> new LivingHoeItem(RootsItemTier.LIVING, -1, -2.0F, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> SYLVAN_HOOD = ITEMS.register("sylvan_hood", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.HEAD, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> SYLVAN_ROBE = ITEMS.register("sylvan_robe", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.CHEST, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> SYLVAN_TUNIC = ITEMS.register("sylvan_tunic", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.LEGS, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> SYLVAN_BOOTS = ITEMS.register("sylvan_boots", () -> new SylvanArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.FEET, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> WILDWOOD_MASK = ITEMS.register("wildwood_mask", () -> new WildwoodArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.HEAD, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> WILDWOOD_PLATE = ITEMS.register("wildwood_plate", () -> new WildwoodArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.CHEST, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> WILDWOOD_LEGGINGS = ITEMS.register("wildwood_leggings", () -> new WildwoodArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.LEGS, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> WILDWOOD_BOOTS = ITEMS.register("wildwood_boots", () -> new WildwoodArmorItem(RootsArmorMaterial.SYLVAN, EquipmentSlotType.FEET, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> RUNIC_TABLET = ITEMS.register("runic_tablet", () -> new RunicTabletItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> GROWTH_POWDER = ITEMS.register("growth_powder", () -> new GrowthPowderItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> MUTATING_POWDER = ITEMS.register("mutating_powder", () -> new MutatingPowderItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> NIGHTSHADE = ITEMS.register("nightshade", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.NIGHTSHADE).group(Roots.tab)));
  public static final RegistryObject<Item> BLACKCURRANT = ITEMS.register("blackcurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.BLACKCURRANT).group(Roots.tab)));
  public static final RegistryObject<Item> REDCURRANT = ITEMS.register("redcurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.REDCURRANT).group(Roots.tab)));
  public static final RegistryObject<Item> WHITECURRANT = ITEMS.register("whitecurrant", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.WHITECURRANT).group(Roots.tab)));
  public static final RegistryObject<Item> ELDERBERRY = ITEMS.register("elderberry", () -> new RootsFoodItem(itemBuilder().food(RootsFoods.ELDERBERRY).group(Roots.tab)));
  public static final RegistryObject<Item> HEALING_POULTICE = ITEMS.register("healing_poultice", () -> new RootsFoodItem(itemBuilder().maxStackSize(8).food(RootsFoods.HEALING_POULTICE).group(Roots.tab)));
  public static final RegistryObject<Item> ROOTY_STEW = ITEMS.register("rooty_stew", () -> new SoupItem(itemBuilder().maxStackSize(1).food(RootsFoods.ROOTY_STEW).group(Roots.tab)));
  public static final RegistryObject<Item> FRUIT_SALAD = ITEMS.register("fruit_salad", () -> new SoupItem(itemBuilder().maxStackSize(1).food(RootsFoods.FRUIT_SALAD).group(Roots.tab)));
  public static final RegistryObject<Item> RUNIC_FOCUS = ITEMS.register("runic_focus", () -> new RunicFocusItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> CHARGED_RUNIC_FOCUS = ITEMS.register("charged_runic_focus", () -> new RunicFocusItem(itemBuilder().maxStackSize(1).group(Roots.tab)));
  public static final RegistryObject<Item> ENGRAVED_BLADE = ITEMS.register("engraved_blade", () -> new EngravedBladeItem(RootsItemTier.ENGRAVED, 3, -3.0F, itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> MANA_RESEARCH_ICON = ITEMS.register("mana_research_icon", () -> new Item(itemBuilder()));
  //TODO Register an egg for the phantom skeleton 		EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, PhantomSkeletonEntity.NAME), RootsUtil.intColor(87, 58, 134), 0xA0A0A0);
  /**
   * Block Items
   */
  public static final RegistryObject<Item> MORTAR_ITEM = ITEMS.register("mortar", () -> new BlockItem(RootsRegistry.MORTAR.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> ALTAR_ITEM = ITEMS.register("altar", () -> new BlockItem(RootsRegistry.ALTAR.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> BRAZIER_ITEM = ITEMS.register("brazier", () -> new BlockItem(RootsRegistry.BRAZIER.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> IMBUER_ITEM = ITEMS.register("imbuer", () -> new BlockItem(RootsRegistry.IMBUER.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> MUNDANE_STANDING_STONE_ITEM = ITEMS.register("mundane_standing_stone", () -> new BlockItem(RootsRegistry.MUNDANE_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> ATTUNED_STANDING_STONE_ITEM = ITEMS.register("attuned_standing_stone", () -> new BlockItem(RootsRegistry.ATTUNED_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> VACUUM_STANDING_STONE_ITEM = ITEMS.register("vacuum_standing_stone", () -> new BlockItem(RootsRegistry.VACUUM_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> REPULSOR_STANDING_STONE_ITEM = ITEMS.register("repulsor_standing_stone", () -> new BlockItem(RootsRegistry.REPULSOR_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> ACCELERATOR_STANDING_STONE_ITEM = ITEMS.register("accelerator_standing_stone", () -> new BlockItem(RootsRegistry.ACCELERATOR_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> AESTHETIC_STANDING_STONE_ITEM = ITEMS.register("aesthetic_standing_stone", () -> new BlockItem(RootsRegistry.AESTHETIC_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> ENTANGLER_STANDING_STONE_ITEM = ITEMS.register("entangler_standing_stone", () -> new BlockItem(RootsRegistry.ENTANGLER_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> IGNITER_STANDING_STONE_ITEM = ITEMS.register("igniter_standing_stone", () -> new BlockItem(RootsRegistry.IGNITER_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> GROWER_STANDING_STONE_ITEM = ITEMS.register("grower_standing_stone", () -> new BlockItem(RootsRegistry.GROWER_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> HEALER_STANDING_STONE_ITEM = ITEMS.register("healer_standing_stone", () -> new BlockItem(RootsRegistry.HEALER_STANDING_STONE.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> MIDNIGHT_BLOOM_ITEM = ITEMS.register("midnight_bloom", () -> new BlockItem(RootsRegistry.MIDNIGHT_BLOOM.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> FLARE_ORCHID_ITEM = ITEMS.register("flare_orchid", () -> new BlockItem(RootsRegistry.FLARE_ORCHID.get(), itemBuilder().group(Roots.tab)));
  public static final RegistryObject<Item> RADIANT_DAISY_ITEM = ITEMS.register("radiant_daisy", () -> new BlockItem(RootsRegistry.RADIANT_DAISY.get(), itemBuilder().group(Roots.tab)));

  private static Item.Properties itemBuilder() {
    return new Item.Properties();
  }
}
