package elucent.rootsclassic.ritual;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.altar.AltarBlockEntity;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.rituals.RitualBanishRain;
import elucent.rootsclassic.ritual.rituals.RitualCauseRain;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import elucent.rootsclassic.ritual.rituals.RitualEngravedSword;
import elucent.rootsclassic.ritual.rituals.RitualFlare;
import elucent.rootsclassic.ritual.rituals.RitualGrow;
import elucent.rootsclassic.ritual.rituals.RitualImbuer;
import elucent.rootsclassic.ritual.rituals.RitualLifeDrain;
import elucent.rootsclassic.ritual.rituals.RitualMassBreed;
import elucent.rootsclassic.ritual.rituals.RitualSacrifice;
import elucent.rootsclassic.ritual.rituals.RitualSummoning;
import elucent.rootsclassic.ritual.rituals.RitualTimeShift;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class RitualRegistry {
	public static final DeferredRegister<RitualBase> RITUALS = DeferredRegister.create(RitualBaseRegistry.registryLocation, Const.MODID);

	public static final RegistryObject<RitualBase> STAFF_CRAFTING = RITUALS.register("staff_crafting", () ->
		new RitualCrafting(1, 205, 86, 0)
			.setResult(new ItemStack(RootsRegistry.CRYSTAL_STAFF.get(), 1))
			.addIncense(new ItemStack(Blocks.COAL_BLOCK, 1))
			.addIncense(new ItemStack(RootsRegistry.ACACIA_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIngredient(new ItemStack(Blocks.DIAMOND_BLOCK, 1))
			.addIngredient(new ItemStack(Items.STICK, 1))
			.addIngredient(new ItemStack(Items.BLAZE_POWDER, 1)));

	public static final RegistryObject<RitualBase> SYLVAN_HOOD_CRAFTING = RITUALS.register("sylvan_hood_crafting", () ->
		new RitualCrafting(2, 62, 138, 62)
			.setResult(new ItemStack(RootsRegistry.SYLVAN_HOOD.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
			.addIncense(new ItemStack(Blocks.POPPY, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.LEATHER_HELMET, 1))
			.addIngredient(new ItemStack(Blocks.VINE, 1)));

	public static final RegistryObject<RitualBase> SYLVAN_CHEST_CRAFTING = RITUALS.register("sylvan_chest_crafting", () ->
		new RitualCrafting(2, 62, 138, 62)
			.setResult(new ItemStack(RootsRegistry.SYLVAN_ROBE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
			.addIncense(new ItemStack(Blocks.POPPY, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.LEATHER_CHESTPLATE, 1))
			.addIngredient(new ItemStack(Blocks.VINE, 1)));

	public static final RegistryObject<RitualBase> SYLVAN_LEGS_CRAFTING = RITUALS.register("sylvan_legs_crafting", () ->
		new RitualCrafting(2, 62, 138, 62)
			.setResult(new ItemStack(RootsRegistry.SYLVAN_TUNIC.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
			.addIncense(new ItemStack(Blocks.POPPY, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.LEATHER_LEGGINGS, 1))
			.addIngredient(new ItemStack(Blocks.VINE, 1)));

	public static final RegistryObject<RitualBase> SYLVAN_BOOTS_CRAFTING = RITUALS.register("sylvan_boots_crafting", () ->
		new RitualCrafting(2, 62, 138, 62)
			.setResult(new ItemStack(RootsRegistry.SYLVAN_BOOTS.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
			.addIncense(new ItemStack(Blocks.POPPY, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.LEATHER_BOOTS, 1))
			.addIngredient(new ItemStack(Blocks.VINE, 1)));

	public static final RegistryObject<RitualBase> WILDWOOD_HEAD_CRAFTING = RITUALS.register("wildwood_head_crafting", () ->
		new RitualCrafting(2, 145, 115, 65)
			.setResult(new ItemStack(RootsRegistry.WILDWOOD_MASK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.IRON_HELMET, 1))
			.addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));

	public static final RegistryObject<RitualBase> WILDWOOD_CHEST_CRAFTING = RITUALS.register("wildwood_chest_crafting", () ->
		new RitualCrafting(2, 145, 115, 65)
			.setResult(new ItemStack(RootsRegistry.WILDWOOD_PLATE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.IRON_CHESTPLATE, 1))
			.addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));

	public static final RegistryObject<RitualBase> WILDWOOD_LEGS_CRAFTING = RITUALS.register("wildwood_legs_crafting", () ->
		new RitualCrafting(2, 145, 115, 65)
			.setResult(new ItemStack(RootsRegistry.WILDWOOD_LEGGINGS.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.IRON_LEGGINGS, 1))
			.addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));

	public static final RegistryObject<RitualBase> WILDWOOD_BOOTS_CRAFTING = RITUALS.register("wildwood_boots_crafting", () ->
		new RitualCrafting(2, 145, 115, 65)
			.setResult(new ItemStack(RootsRegistry.WILDWOOD_BOOTS.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Items.IRON_BOOTS, 1))
			.addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));

	public static final RegistryObject<RitualBase> ACCELERATOR_STONE_CRAFTING = RITUALS.register("accelerator_stone_crafting", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.ACCELERATOR_STANDING_STONE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(RootsRegistry.JUNGLE_BARK.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
			.addIngredient(new ItemStack(Blocks.STONE_BRICKS, 1)));

	public static final RegistryObject<RitualBase> STANDING_STONE = RITUALS.register("standing_stone", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.AESTHETIC_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
			.addIngredient(new ItemStack(Blocks.MOSSY_STONE_BRICKS, 1)));

	public static final RegistryObject<RitualBase> ENTANGLER_STONE_CRAFTING = RITUALS.register("entangler_stone_crafting", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.ENTANGLER_STANDING_STONE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
			.addIngredient(new ItemStack(Blocks.CRACKED_STONE_BRICKS, 1)));

	public static final RegistryObject<RitualBase> GROWER_STONE_CRAFTING = RITUALS.register("grower_stone_crafting", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.GROWER_STANDING_STONE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
			.addIngredient(new ItemStack(Blocks.STONE, 1)));

	public static final RegistryObject<RitualBase> HEALER_STONE_CRAFTING = RITUALS.register("healer_stone_crafting", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.HEALER_STANDING_STONE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(Items.GHAST_TEAR, 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get()))
			.addIngredient(new ItemStack(Items.REDSTONE))
			.addIngredient(new ItemStack(Blocks.CHISELED_STONE_BRICKS, 1)));

	public static final RegistryObject<RitualBase> IGNITER_STONE_CRAFTING = RITUALS.register("igniter_stone_crafting", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.IGNITER_STANDING_STONE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(RootsRegistry.ACACIA_BARK.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(Items.REDSTONE, 1))
			.addIngredient(new ItemStack(Blocks.CRACKED_STONE_BRICKS, 1)));
	public static final RegistryObject<RitualBase> REPULSOR_STONE_CRAFTING = RITUALS.register("repulsor_stone_crafting", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.REPULSOR_STANDING_STONE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(RootsRegistry.SPRUCE_BARK.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
			.addIngredient(new ItemStack(Blocks.CHISELED_STONE_BRICKS, 1)));
	//just to move 403 wtf

	public static final RegistryObject<RitualBase> VACUUM_STONE_CRAFTING = RITUALS.register("vacuum_stone_crafting", () ->
		new RitualCrafting(2, 0, 105, 73)
			.setResult(new ItemStack(RootsRegistry.VACUUM_STANDING_STONE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(RootsRegistry.DARK_OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
			.addIngredient(new ItemStack(Blocks.NETHER_BRICKS)));

	public static final RegistryObject<RitualBase> RUNIC_FOCUS_CRAFTING = RITUALS.register("runic_focus_crafting", () ->
		new RitualCrafting(2, 109, 242, 109)
			.setResult(new ItemStack(RootsRegistry.RUNIC_FOCUS.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.SPRUCE_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.ACACIA_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.JUNGLE_BARK.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIngredient(new ItemStack(Items.DIAMOND, 1))
			.addIngredient(new ItemStack(Blocks.STONE, 1)));

	public static final RegistryObject<RitualBase> RUNIC_FOCUS_CHARGING = RITUALS.register("runic_focus_charging", () ->
		new RitualCrafting(0, 109, 242, 109)
			.setResult(new ItemStack(RootsRegistry.CHARGED_RUNIC_FOCUS.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIngredient(new ItemStack(RootsRegistry.RUNIC_FOCUS.get(), 1))
			.addIngredient(new ItemStack(Items.REDSTONE, 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1)));

	public static final RegistryObject<RitualBase> LIVING_PICKAXE_CRAFTING = RITUALS.register("living_pickaxe_crafting", () ->
		new RitualCrafting(0, 146, 214, 43)
			.setResult(new ItemStack(RootsRegistry.LIVING_PICKAXE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(Items.WOODEN_PICKAXE, 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIngredient(new ItemStack(Items.GOLD_NUGGET)));

	public static final RegistryObject<RitualBase> LIVING_AXE_CRAFTING = RITUALS.register("living_axe_crafting", () ->
		new RitualCrafting(0, 146, 214, 43)
			.setResult(new ItemStack(RootsRegistry.LIVING_AXE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(Items.WOODEN_AXE, 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));

	public static final RegistryObject<RitualBase> LIVING_SWORD_CRAFTING = RITUALS.register("living_sword_crafting", () ->
		new RitualCrafting(0, 146, 214, 43)
			.setResult(new ItemStack(RootsRegistry.LIVING_SWORD.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(Items.WOODEN_SWORD, 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));

	public static final RegistryObject<RitualBase> LIVING_HOE_CRAFTING = RITUALS.register("living_hoe_crafting", () ->
		new RitualCrafting(0, 146, 214, 43)
			.setResult(new ItemStack(RootsRegistry.LIVING_HOE.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(Items.WOODEN_HOE, 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));

	public static final RegistryObject<RitualBase> LIVING_SHOVEL_CRAFTING = RITUALS.register("living_shovel_crafting", () ->
		new RitualCrafting(0, 146, 214, 43)
			.setResult(new ItemStack(RootsRegistry.LIVING_SHOVEL.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(Items.WOODEN_SHOVEL, 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
			.addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));

	public static final RegistryObject<RitualBase> CAUSE_RAIN = RITUALS.register("cause_rain", () ->
		new RitualCauseRain(1, 23, 0, 138)
			.addIncense(new ItemStack(Blocks.VINE, 1))
			.addIncense(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1))
			.addIngredient(new ItemStack(Blocks.LILY_PAD, 1)));

	public static final RegistryObject<RitualBase> BANISH_RAIN = RITUALS.register("banish_rain", () ->
		new RitualBanishRain(1, 204, 159, 35)
			.addIncense(new ItemStack(Blocks.VINE, 1))
			.addIncense(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1))
			.addIngredient(new ItemStack(Items.WHEAT, 1)));

	public static final RegistryObject<RitualBase> MASS_BREEDING = RITUALS.register("mass_breeding", () ->
		new RitualMassBreed(2, 148, 61, 81)
			.addIncense(new ItemStack(Items.CARROT, 1))
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
			.addIncense(new ItemStack(Items.COD, 1))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
			.addIngredient(new ItemStack(Items.BONE)));

	public static final RegistryObject<RitualBase> LIFE_DRAIN = RITUALS.register("life_drain", () ->
		new RitualLifeDrain(2, 139, 22, 40)
			.addIncense(new ItemStack(Items.WOODEN_SWORD, 1))
			.addIncense(new ItemStack(Items.WOODEN_AXE, 1))
			.addIncense(new ItemStack(RootsRegistry.DARK_OAK_BARK.get(), 1))
			.addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
			.addIngredient(new ItemStack(Items.BLAZE_POWDER, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> IMBUER = RITUALS.register("imbuer", () ->
		new RitualImbuer()
			.addIngredient(new ItemStack(RootsRegistry.CRYSTAL_STAFF.get(), 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1)));

	public static final RegistryObject<RitualBase> COW_SUMMONING = RITUALS.register("cow_summoning", () ->
		new RitualSummoning(1, 199, 105, 193)
			.setEntityType(EntityType.COW)
			.addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.BEEF, 1))
			.addIngredient(new ItemStack(Items.LEATHER, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> PIG_SUMMONING = RITUALS.register("pig_summoning", () ->
		new RitualSummoning(1, 199, 105, 193)
			.setEntityType(EntityType.PIG)
			.addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.PORKCHOP, 1))
			.addIngredient(new ItemStack(Items.PORKCHOP, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> SHEEP_SUMMONING = RITUALS.register("sheep_summoning", () ->
		new RitualSummoning(1, 199, 105, 193)
			.setEntityType(EntityType.SHEEP)
			.addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.MUTTON, 1))
			.addIngredient(new ItemStack(Blocks.WHITE_WOOL, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> CHICKEN_SUMMONING = RITUALS.register("chicken_summoning", () ->
		new RitualSummoning(1, 199, 105, 193)
			.setEntityType(EntityType.CHICKEN)
			.addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.CHICKEN, 1))
			.addIngredient(new ItemStack(Items.FEATHER, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> RABBIT_SUMMONING = RITUALS.register("rabbit_summoning", () ->
		new RitualSummoning(1, 199, 105, 193)
			.setEntityType(EntityType.RABBIT)
			.addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.RABBIT, 1))
			.addIngredient(new ItemStack(Items.RABBIT_HIDE, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> ZOMBIE_SUMMONING = RITUALS.register("zombie_summoning", () ->
		new RitualSummoning(2, 58, 2, 84)
			.setEntityType(EntityType.ZOMBIE)
			.addIncense(new ItemStack(Items.COAL, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.ROTTEN_FLESH, 1))
			.addIngredient(new ItemStack(Items.ROTTEN_FLESH, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> SKELETON_SUMMONING = RITUALS.register("skeleton_summoning", () ->
		new RitualSummoning(2, 58, 2, 84)
			.setEntityType(EntityType.SKELETON)
			.addIncense(new ItemStack(Items.COAL, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.ARROW, 1))
			.addIngredient(new ItemStack(Items.BONE, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> SPIDER_SUMMONING = RITUALS.register("spider_summoning", () ->
		new RitualSummoning(2, 58, 2, 84)
			.setEntityType(EntityType.SPIDER)
			.addIncense(new ItemStack(Items.COAL, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.STRING, 1))
			.addIngredient(new ItemStack(Items.SPIDER_EYE, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> CAVE_SPIDER_SUMMONING = RITUALS.register("cave_spider_summoning", () ->
		new RitualSummoning(2, 58, 2, 84)
			.setEntityType(EntityType.CAVE_SPIDER)
			.addIncense(new ItemStack(Items.COAL, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.STRING, 1))
			.addIngredient(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> SLIME_SUMMONING = RITUALS.register("slime_summoning", () ->
		new RitualSummoning(2, 58, 2, 84)
			.setEntityType(EntityType.SLIME)
			.addIncense(new ItemStack(Items.COAL, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.SLIME_BALL, 1))
			.addIngredient(new ItemStack(Items.SLIME_BALL, 1))
			.addIngredient(new ItemStack(Items.SLIME_BALL, 1)));

	public static final RegistryObject<RitualBase> CREEPER_SUMMONING = RITUALS.register("creeper_summoning", () ->
		new RitualSummoning(2, 58, 2, 84)
			.setEntityType(EntityType.CREEPER)
			.addIncense(new ItemStack(Items.COAL, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.GUNPOWDER, 1))
			.addIngredient(new ItemStack(Items.GUNPOWDER, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> ENDERMAN_SUMMONING = RITUALS.register("enderman_summoning", () ->
		new RitualSummoning(2, 58, 2, 84)
			.setEntityType(EntityType.ENDERMAN)
			.addIncense(new ItemStack(Items.COAL, 1))
			.addIncense(new ItemStack(Items.NETHER_WART, 1))
			.addIngredient(new ItemStack(Items.ENDER_PEARL, 1))
			.addIngredient(new ItemStack(Items.ENDER_PEARL, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> SACRIFICE = RITUALS.register("sacrifice", () ->
		new RitualSacrifice(2, 94, 9, 56)
			.addIncense(new ItemStack(Items.BLAZE_POWDER, 1))
			.addIncense(new ItemStack(RootsRegistry.DARK_OAK_BARK.get(), 1))
			.addIngredient(new ItemStack(Items.FLINT, 1))
			.addIngredient(new ItemStack(Items.IRON_SWORD, 1))
			.addIngredient(new ItemStack(Items.BONE, 1)));

	public static final RegistryObject<RitualBase> FLARE = RITUALS.register("flare", () ->
		new RitualFlare(1, 255, 91, 25)
			.addIncense(new ItemStack(Items.GUNPOWDER, 1))
			.addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
			.addIngredient(new ItemStack(Items.FLINT, 1))
			.addIngredient(new ItemStack(Items.COAL, 1))
			.addIngredient(new ItemStack(Items.CHARCOAL, 1)));

	public static final RegistryObject<RitualBase> GROW = RITUALS.register("grow", () ->
		new RitualGrow(0, 82, 212, 47)
			.addIncense(new ItemStack(Items.WHEAT, 1))
			.addIncense(new ItemStack(Items.BEETROOT, 1))
			.addIncense(new ItemStack(Items.POTATO, 1))
			.addIncense(new ItemStack(Items.CARROT, 1))
			.addIngredient(new ItemStack(Items.REDSTONE, 1))
			.addIngredient(new ItemStack(Items.BONE_MEAL, 1))
			.addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1)));

	public static final RegistryObject<RitualBase> ENGRAVED_CRAFTING = RITUALS.register("engraved_crafting", () ->
		new RitualEngravedSword(2, 104, 106, 107)
			.setResult(new ItemStack(RootsRegistry.ENGRAVED_BLADE.get()))
			.addIncense(new ItemStack(Items.GOLDEN_APPLE))
			.addIncense(new ItemStack(Items.GOLDEN_CARROT, 1))
			.addIngredient(new ItemStack(Items.STONE_SWORD))
			.addIngredient(new ItemStack(RootsRegistry.RUNIC_FOCUS.get()))
			.addIngredient(new ItemStack(Items.GLOWSTONE_DUST)));

	public static final RegistryObject<RitualBase> TIME_SHIFT = RITUALS.register("time_shift", () ->
		new RitualTimeShift(1, 240, 245, 88)
			.addIngredient(new ItemStack(Items.CLOCK))
			.addIngredient(new ItemStack(Items.IRON_INGOT))
			.addIncense(new ItemStack(Items.CLOCK))
			.setSecondaryColor(252, 162, 35));


	public static RitualBase findMatchingByIngredients(AltarBlockEntity altar) {
		for (RitualBase ritual : RitualBaseRegistry.RITUALS.get().getValues()) {
			//      if (ritual.getName().equals("healer_stone_crafting")) {
			//        Roots.logger.info("healer stone?");
			//        for (ItemStack s : ritual.getIngredients()) {
			//          Roots.logger.info(s.getDisplayName());
			//        }
			//        Roots.logger.info("==== ");
			//        for (ItemStack s : altar.getInventory()) {
			//          Roots.logger.info(s.getDisplayName());
			//        }
			//        Roots.logger.info("----");
			//      }
			List<ItemStack> altarInv = new ArrayList<>();
			for (int i = 0; i < altar.inventory.getSlots(); i++) {
				altarInv.add(altar.inventory.getStackInSlot(i));
			}
			if (RootsUtil.itemListsMatchWithSize(ritual.getIngredients(), altarInv)) {
				return ritual;
			}
		}
		return null;
	}

	public static ArrayList<ItemStack> getIncenses(Level level, BlockPos pos) {
		ArrayList<ItemStack> test = new ArrayList<>();
		for (int i = -4; i < 5; i++) {
			for (int j = -4; j < 5; j++) {
				if (level.getBlockState(pos.offset(i, 0, j)).getBlock() == RootsRegistry.BRAZIER.get()) {
					if (level.getBlockEntity(pos.offset(i, 0, j)) instanceof BrazierBlockEntity brazierBlockEntity) {
						if (brazierBlockEntity.isBurning()) {
							test.add(brazierBlockEntity.getHeldItem());
						}
					}
				}
			}
		}
		return test;
	}
}
