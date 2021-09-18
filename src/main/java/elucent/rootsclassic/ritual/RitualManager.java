package elucent.rootsclassic.ritual;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.block.altar.AltarTile;
import elucent.rootsclassic.block.brazier.BrazierTile;
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

public class RitualManager {

  public static ArrayList<RitualBase> rituals = new ArrayList<>();

  public static RitualBase findMatchingByIngredients(AltarTile altar) {
    for (RitualBase ritual : RitualManager.rituals) {
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

  public static void addRitual(RitualBase ritual) throws IllegalArgumentException {
    //make sure its unique
    for (RitualBase existing : rituals) {
      //ingredients must be unique
      if (ritual.doIngredientsMatch(existing)) {
        Roots.LOGGER.error("Duplicate ingredients are not allowed for incoming ritual : " + ritual.getName()
            + " and existing ritual " + existing.getName());
      }
    }
    rituals.add(ritual);
  }

  public static void reload() {
    rituals.clear();
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "staff_crafting"), 2, 205, 86, 0)
        .setResult(new ItemStack(RootsRegistry.CRYSTAL_STAFF.get(), 1))
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, -3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, -3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 0)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 0)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, -3)
        .addIncense(new ItemStack(Blocks.COAL_BLOCK, 1))
        .addIncense(new ItemStack(RootsRegistry.ACACIA_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIngredient(new ItemStack(Blocks.DIAMOND_BLOCK, 1))
        .addIngredient(new ItemStack(Items.STICK, 1))
        .addIngredient(new ItemStack(Items.BLAZE_POWDER, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "sylvan_hood_crafting"), 2, 62, 138, 62)
        .setResult(new ItemStack(RootsRegistry.SYLVAN_HOOD.get(), 1))
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, -3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, -3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 0)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 0)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, -3)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 5, 1, 0)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), -5, 1, 0)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 0, 1, 5)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 0, 1, -5)
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.POPPY, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_HELMET, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "sylvan_chest_crafting"), 2, 62, 138, 62)
        .setResult(new ItemStack(RootsRegistry.SYLVAN_ROBE.get(), 1))
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, -3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, -3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 0)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 0)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, 3)
        .addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, -3)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 5, 1, 0)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), -5, 1, 0)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 0, 1, 5)
        .addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 0, 1, -5)
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.POPPY, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_CHESTPLATE, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "sylvan_legs_crafting"), 2, 62, 138, 62)
        .setResult(new ItemStack(RootsRegistry.SYLVAN_TUNIC.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.POPPY, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_LEGGINGS, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "sylvan_boots_crafting"), 2, 62, 138, 62)
        .setResult(new ItemStack(RootsRegistry.SYLVAN_BOOTS.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.POPPY, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_BOOTS, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "wildwood_head_crafting"), 2, 145, 115, 65)
        .setResult(new ItemStack(RootsRegistry.WILDWOOD_MASK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_HELMET, 1))
        .addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "wildwood_chest_crafting"), 2, 145, 115, 65)
        .setResult(new ItemStack(RootsRegistry.WILDWOOD_PLATE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_CHESTPLATE, 1))
        .addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "wildwood_legs_crafting"), 2, 145, 115, 65)
        .setResult(new ItemStack(RootsRegistry.WILDWOOD_LEGGINGS.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_LEGGINGS, 1))
        .addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "wildwood_boots_crafting"), 2, 145, 115, 65)
        .setResult(new ItemStack(RootsRegistry.WILDWOOD_BOOTS.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.OAK_SAPLING, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_BOOTS, 1))
        .addIngredient(new ItemStack(Blocks.OAK_PLANKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "accelerator_stone_crafting"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.ACCELERATOR_STANDING_STONE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RootsRegistry.JUNGLE_BARK.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.STONE_BRICKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "standing_stone"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.AESTHETIC_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.MOSSY_STONE_BRICKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "entangler_stone_crafting"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.ENTANGLER_STANDING_STONE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.CRACKED_STONE_BRICKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "grower_stone_crafting"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.GROWER_STANDING_STONE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.STONE, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "healer_stone_crafting"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.HEALER_STANDING_STONE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Items.GHAST_TEAR, 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get()))
        .addIngredient(new ItemStack(Items.REDSTONE))
        .addIngredient(new ItemStack(Blocks.CHISELED_STONE_BRICKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "igniter_stone_crafting"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.IGNITER_STANDING_STONE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RootsRegistry.ACACIA_BARK.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(Items.REDSTONE, 1))
        .addIngredient(new ItemStack(Blocks.CRACKED_STONE_BRICKS, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "repulsor_stone_crafting"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.REPULSOR_STANDING_STONE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RootsRegistry.SPRUCE_BARK.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.CHISELED_STONE_BRICKS, 1)));
    //just to move 403 wtf
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "vacuum_stone_crafting"), 2, 0, 105, 73)
        .setResult(new ItemStack(RootsRegistry.VACUUM_STANDING_STONE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RootsRegistry.DARK_OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get(), 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.NETHER_BRICKS)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "runic_focus_crafting"), 2, 109, 242, 109)
        .setResult(new ItemStack(RootsRegistry.RUNIC_FOCUS.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.SPRUCE_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.ACACIA_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.JUNGLE_BARK.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Blocks.STONE, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "runic_focus_charging"), 2, 109, 242, 109)
        .setResult(new ItemStack(RootsRegistry.CHARGED_RUNIC_FOCUS.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIngredient(new ItemStack(RootsRegistry.RUNIC_FOCUS.get(), 1))
        .addIngredient(new ItemStack(Items.REDSTONE, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "living_pickaxe_crafting"), 2, 146, 214, 43)
        .setResult(new ItemStack(RootsRegistry.LIVING_PICKAXE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(Items.WOODEN_PICKAXE, 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "living_axe_crafting"), 2, 146, 214, 43)
        .setResult(new ItemStack(RootsRegistry.LIVING_AXE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(Items.WOODEN_AXE, 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "living_sword_crafting"), 2, 146, 214, 43)
        .setResult(new ItemStack(RootsRegistry.LIVING_SWORD.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(Items.WOODEN_SWORD, 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "living_hoe_crafting"), 2, 146, 214, 43)
        .setResult(new ItemStack(RootsRegistry.LIVING_HOE.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(Items.WOODEN_HOE, 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCrafting(new ResourceLocation(Const.MODID, "living_shovel_crafting"), 2, 146, 214, 43)
        .setResult(new ItemStack(RootsRegistry.LIVING_SHOVEL.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(Items.WOODEN_SHOVEL, 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCauseRain(new ResourceLocation(Const.MODID, "cause_rain"), 0, 23, 0, 138)
        .addIncense(new ItemStack(Blocks.VINE, 1))
        .addIncense(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1))
        .addIngredient(new ItemStack(Blocks.LILY_PAD, 1)));
    addRitual(new RitualBanishRain(new ResourceLocation(Const.MODID, "banish_rain"), 0, 204, 159, 35)
        .addIncense(new ItemStack(Blocks.VINE, 1))
        .addIncense(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1))
        .addIngredient(new ItemStack(Items.WHEAT, 1)));
    addRitual(new RitualMassBreed(new ResourceLocation(Const.MODID, "mass_breeding"), 0, 148, 61, 81)
        .addIncense(new ItemStack(Items.CARROT, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.COD, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Items.BONE)));
    addRitual(new RitualLifeDrain(new ResourceLocation(Const.MODID, "life_drain"), 2, 139, 22, 40)
        .addIncense(new ItemStack(Items.WOODEN_SWORD, 1))
        .addIncense(new ItemStack(Items.WOODEN_AXE, 1))
        .addIncense(new ItemStack(RootsRegistry.DARK_OAK_BARK.get(), 1))
        .addIncense(new ItemStack(RootsRegistry.BIRCH_BARK.get(), 1))
        .addIngredient(new ItemStack(Items.BLAZE_POWDER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualImbuer(new ResourceLocation(Const.MODID, "imbuer"))
        .addIngredient(new ItemStack(RootsRegistry.CRYSTAL_STAFF.get(), 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "cow_summoning"), 1, 199, 105, 193)
        .setEntityType(EntityType.COW)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.BEEF, 1))
        .addIngredient(new ItemStack(Items.LEATHER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "pig_summoning"), 1, 199, 105, 193)
        .setEntityType(EntityType.PIG)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.PORKCHOP, 1))
        .addIngredient(new ItemStack(Items.PORKCHOP, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "sheep_summoning"), 1, 199, 105, 193)
        .setEntityType(EntityType.SHEEP)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.MUTTON, 1))
        .addIngredient(new ItemStack(Blocks.WHITE_WOOL, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "chicken_summoning"), 1, 199, 105, 193)
        .setEntityType(EntityType.CHICKEN)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.CHICKEN, 1))
        .addIngredient(new ItemStack(Items.FEATHER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "rabbit_summoning"), 1, 199, 105, 193)
        .setEntityType(EntityType.RABBIT)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.RABBIT, 1))
        .addIngredient(new ItemStack(Items.RABBIT_HIDE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "zombie_summoning"), 2, 58, 2, 84)
        .setEntityType(EntityType.ZOMBIE)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.ROTTEN_FLESH, 1))
        .addIngredient(new ItemStack(Items.ROTTEN_FLESH, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "skeleton_summoning"), 2, 58, 2, 84)
        .setEntityType(EntityType.SKELETON)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.ARROW, 1))
        .addIngredient(new ItemStack(Items.BONE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "spider_summoning"), 2, 58, 2, 84)
        .setEntityType(EntityType.SPIDER)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.STRING, 1))
        .addIngredient(new ItemStack(Items.SPIDER_EYE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "cave_spider_summoning"), 2, 58, 2, 84)
        .setEntityType(EntityType.CAVE_SPIDER)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.STRING, 1))
        .addIngredient(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "slime_summoning"), 2, 58, 2, 84)
        .setEntityType(EntityType.SLIME)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.SLIME_BALL, 1))
        .addIngredient(new ItemStack(Items.SLIME_BALL, 1))
        .addIngredient(new ItemStack(Items.SLIME_BALL, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "creeper_summoning"), 2, 58, 2, 84)
        .setEntityType(EntityType.CREEPER)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.GUNPOWDER, 1))
        .addIngredient(new ItemStack(Items.GUNPOWDER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning(new ResourceLocation(Const.MODID, "enderman_summoning"), 2, 58, 2, 84)
        .setEntityType(EntityType.ENDERMAN)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.ENDER_PEARL, 1))
        .addIngredient(new ItemStack(Items.ENDER_PEARL, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSacrifice(new ResourceLocation(Const.MODID, "sacrifice"), 2, 94, 9, 56)
        .addIncense(new ItemStack(Items.BLAZE_POWDER, 1))
        .addIncense(new ItemStack(RootsRegistry.DARK_OAK_BARK.get(), 1))
        .addIngredient(new ItemStack(Items.FLINT, 1))
        .addIngredient(new ItemStack(Items.IRON_SWORD, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualFlare(new ResourceLocation(Const.MODID, "flare"), 1, 255, 91, 25)
        .addIncense(new ItemStack(Items.GUNPOWDER, 1))
        .addIncense(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1))
        .addIngredient(new ItemStack(Items.FLINT, 1))
        .addIngredient(new ItemStack(Items.COAL, 1))
        .addIngredient(new ItemStack(Items.CHARCOAL, 1)));
    addRitual(new RitualGrow(new ResourceLocation(Const.MODID, "grow"), 0, 82, 212, 47)
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Items.BEETROOT, 1))
        .addIncense(new ItemStack(Items.POTATO, 1))
        .addIncense(new ItemStack(Items.CARROT, 1))
        .addIngredient(new ItemStack(Items.REDSTONE, 1))
        .addIngredient(new ItemStack(Items.BONE_MEAL, 1))
        .addIngredient(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1)));
    addRitual(new RitualEngravedSword(new ResourceLocation(Const.MODID, "engraved_crafting"), 2, 104, 106, 107)
        .setResult(new ItemStack(RootsRegistry.ENGRAVED_BLADE.get()))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE))
        .addIncense(new ItemStack(Items.GOLDEN_CARROT, 1))
        .addIngredient(new ItemStack(Items.STONE_SWORD))
        .addIngredient(new ItemStack(RootsRegistry.RUNIC_FOCUS.get()))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST)));
    addRitual(new RitualTimeShift(new ResourceLocation(Const.MODID, "time_shift"), 1, 240, 245, 88)
        .addIngredient(new ItemStack(Items.CLOCK))
        .addIngredient(new ItemStack(Items.IRON_INGOT))
        .addIncense(new ItemStack(Items.CLOCK))
        .setSecondaryColor(252, 162, 35));
  }

  public static RitualBase getRitualFromName(ResourceLocation name) {
    if (name != null) {
      for (RitualBase ritual : rituals) {
        if (ritual.getName().equals(name)) {
          return ritual;
        }
      }
    }
    return null;
  }

  public static ArrayList<ItemStack> getIncenses(World world, BlockPos pos) {
    ArrayList<ItemStack> test = new ArrayList<>();
    for (int i = -4; i < 5; i++) {
      for (int j = -4; j < 5; j++) {
        if (world.getBlockState(pos.add(i, 0, j)).getBlock() == RootsRegistry.BRAZIER.get()) {
          if (world.getTileEntity(pos.add(i, 0, j)) != null) {
            BrazierTile brazierTile = (BrazierTile) world.getTileEntity(pos.add(i, 0, j));
            if (brazierTile.isBurning()) {
              test.add(brazierTile.getHeldItem());
            }
          }
        }
      }
    }
    return test;
  }
}
