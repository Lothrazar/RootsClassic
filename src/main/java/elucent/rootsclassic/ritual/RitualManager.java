package elucent.rootsclassic.ritual;

import java.util.ArrayList;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.block.altar.TileEntityAltar;
import elucent.rootsclassic.block.brazier.TileEntityBrazier;
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
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualManager {

  public static ArrayList<RitualBase> rituals = new ArrayList<RitualBase>();

  public static RitualBase findMatchingByIngredients(TileEntityAltar altar) {
    for (RitualBase ritual : RitualManager.rituals) {
      //      if (ritual.getName().equals("healerStoneCrafting")) {
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
      if (Util.itemListsMatchWithSize(ritual.getIngredients(), altar.getInventory())) {
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
        Roots.logger.error("Duplicate ingredients are not allowed for incoming ritual : " + ritual.getName()
            + " and existing ritual " + existing.getName());
      }
    }
    rituals.add(ritual);
  }

  public static void init() {
    addRitual(new RitualCrafting("staffCrafting", 2, 205, 86, 0)
        .setResult(new ItemStack(RegistryManager.crystalStaff, 1))
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, 0)
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, 0)
        .addRitualPillar(RegistryManager.standingStoneT1, 0, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 0, 0, -3)
        .addIncense(new ItemStack(Blocks.COAL_BLOCK, 1))
        .addIncense(new ItemStack(RegistryManager.acaciaTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIngredient(new ItemStack(Blocks.DIAMOND_BLOCK, 1))
        .addIngredient(new ItemStack(Items.STICK, 1))
        .addIngredient(new ItemStack(Items.BLAZE_POWDER, 1)));
    addRitual(new RitualCrafting("sylvanHoodCrafting", 2, 62, 138, 62)
        .setResult(new ItemStack(RegistryManager.druidRobesHead, 1))
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, 0)
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, 0)
        .addRitualPillar(RegistryManager.standingStoneT1, 0, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 0, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT2, 5, 1, 0)
        .addRitualPillar(RegistryManager.standingStoneT2, -5, 1, 0)
        .addRitualPillar(RegistryManager.standingStoneT2, 0, 1, 5)
        .addRitualPillar(RegistryManager.standingStoneT2, 0, 1, -5)
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.RED_FLOWER, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_HELMET, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting("sylvanChestCrafting", 2, 62, 138, 62)
        .setResult(new ItemStack(RegistryManager.druidRobesChest, 1))
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 3, 0, 0)
        .addRitualPillar(RegistryManager.standingStoneT1, -3, 0, 0)
        .addRitualPillar(RegistryManager.standingStoneT1, 0, 0, 3)
        .addRitualPillar(RegistryManager.standingStoneT1, 0, 0, -3)
        .addRitualPillar(RegistryManager.standingStoneT2, 5, 1, 0)
        .addRitualPillar(RegistryManager.standingStoneT2, -5, 1, 0)
        .addRitualPillar(RegistryManager.standingStoneT2, 0, 1, 5)
        .addRitualPillar(RegistryManager.standingStoneT2, 0, 1, -5)
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.RED_FLOWER, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_CHESTPLATE, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting("sylvanLegsCrafting", 2, 62, 138, 62)
        .setResult(new ItemStack(RegistryManager.druidRobesLegs, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.RED_FLOWER, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_LEGGINGS, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting("sylvanBootsCrafting", 2, 62, 138, 62)
        .setResult(new ItemStack(RegistryManager.druidRobesBoots, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE, 1))
        .addIncense(new ItemStack(Blocks.RED_FLOWER, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.LEATHER_BOOTS, 1))
        .addIngredient(new ItemStack(Blocks.VINE, 1)));
    addRitual(new RitualCrafting("wildwoodHeadCrafting", 2, 145, 115, 65)
        .setResult(new ItemStack(RegistryManager.druidArmorHead, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.SAPLING, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_HELMET, 1))
        .addIngredient(new ItemStack(Blocks.PLANKS, 1, 0)));
    addRitual(new RitualCrafting("wildwoodChestCrafting", 2, 145, 115, 65)
        .setResult(new ItemStack(RegistryManager.druidArmorChest, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.SAPLING, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_CHESTPLATE, 1))
        .addIngredient(new ItemStack(Blocks.PLANKS, 1)));
    addRitual(new RitualCrafting("wildwoodLegsCrafting", 2, 145, 115, 65)
        .setResult(new ItemStack(RegistryManager.druidArmorLegs, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.SAPLING, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_LEGGINGS, 1))
        .addIngredient(new ItemStack(Blocks.PLANKS, 1)));
    addRitual(new RitualCrafting("wildwoodBootsCrafting", 2, 145, 115, 65)
        .setResult(new ItemStack(RegistryManager.druidArmorBoots, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Blocks.SAPLING, 1, 0))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Items.IRON_BOOTS, 1))
        .addIngredient(new ItemStack(Blocks.PLANKS, 1)));
    addRitual(new RitualCrafting("acceleratorStoneCrafting", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneAccelerator, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RegistryManager.jungleTreeBark, 1, 0))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.STONEBRICK, 1, 0)));
    addRitual(new RitualCrafting("standingStone", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneAesthetic, 1))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.STONEBRICK, 1, 1)));
    addRitual(new RitualCrafting("entanglerStoneCrafting", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneEntangler, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1, 0))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.STONEBRICK, 1, 2)));
    addRitual(new RitualCrafting("growerStoneCrafting", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneGrower, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1, 0))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.STONE, 1, 0)));
    addRitual(new RitualCrafting("healerStoneCrafting", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneHealer, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Items.GHAST_TEAR, 1, 0))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2))
        .addIngredient(new ItemStack(Items.REDSTONE))
        .addIngredient(new ItemStack(Blocks.STONEBRICK, 1, 3)));
    addRitual(new RitualCrafting("igniterStoneCrafting", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneIgniter, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RegistryManager.acaciaTreeBark, 1, 0))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2, 1))
        .addIngredient(new ItemStack(Items.REDSTONE, 1))
        .addIngredient(new ItemStack(Blocks.STONEBRICK, 1, 2)));
    addRitual(new RitualCrafting("repulsorStoneCrafting", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneRepulsor, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RegistryManager.spruceTreeBark, 1, 0))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.STONEBRICK, 1, 3)));
    //just to move 403 wtf
    addRitual(new RitualCrafting("vacuumStoneCrafting", 2, 0, 105, 73)
        .setResult(new ItemStack(RegistryManager.standingStoneVacuum, 1))
        .addIncense(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(RegistryManager.darkOakTreeBark, 1, 0))
        .addIngredient(new ItemStack(RegistryManager.standingStoneT2, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Blocks.NETHER_BRICK)));
    addRitual(new RitualCrafting("runicFocusCrafting", 2, 109, 242, 109)
        .setResult(new ItemStack(RegistryManager.runicFocus, 1, 0))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.spruceTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.acaciaTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.jungleTreeBark, 1))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIngredient(new ItemStack(Items.DIAMOND, 1))
        .addIngredient(new ItemStack(Blocks.STONE, 1)));
    addRitual(new RitualCrafting("runicFocusCharging", 2, 109, 242, 109)
        .setResult(new ItemStack(RegistryManager.runicFocus, 1, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIngredient(new ItemStack(RegistryManager.runicFocus, 1, 0))
        .addIngredient(new ItemStack(Items.REDSTONE, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1)));
    addRitual(new RitualCrafting("livingPickaxeCrafting", 2, 146, 214, 43)
        .setResult(new ItemStack(RegistryManager.livingPickaxe, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIngredient(new ItemStack(Items.WOODEN_PICKAXE, 1))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET)));
    addRitual(new RitualCrafting("livingAxeCrafting", 2, 146, 214, 43)
        .setResult(new ItemStack(RegistryManager.livingAxe, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIngredient(new ItemStack(Items.WOODEN_AXE, 1))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCrafting("livingSwordCrafting", 2, 146, 214, 43)
        .setResult(new ItemStack(RegistryManager.livingSword, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIngredient(new ItemStack(Items.WOODEN_SWORD, 1))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCrafting("livingHoeCrafting", 2, 146, 214, 43)
        .setResult(new ItemStack(RegistryManager.livingHoe, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIngredient(new ItemStack(Items.WOODEN_HOE, 1))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCrafting("livingShovelCrafting", 2, 146, 214, 43)
        .setResult(new ItemStack(RegistryManager.livingShovel, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.oakTreeBark, 1))
        .addIngredient(new ItemStack(Items.WOODEN_SHOVEL, 1))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1))
        .addIngredient(new ItemStack(Items.GOLD_NUGGET, 1)));
    addRitual(new RitualCauseRain("causerain", 0, 23, 0, 138)
        .addIncense(new ItemStack(Blocks.VINE, 1))
        .addIncense(new ItemStack(RegistryManager.oldRoot, 1))
        .addIngredient(new ItemStack(Blocks.WATERLILY, 1)));
    addRitual(new RitualCauseRain("banishrain", 0, 204, 159, 35)
        .addIncense(new ItemStack(Blocks.VINE, 1))
        .addIncense(new ItemStack(RegistryManager.oldRoot, 1))
        .addIngredient(new ItemStack(Items.WHEAT, 1)));
    addRitual(new RitualMassBreed("massbreeding", 0, 148, 61, 81)
        .addIncense(new ItemStack(Items.CARROT, 1))
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.FISH, 1))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST, 1))
        .addIngredient(new ItemStack(Items.BONE)));
    addRitual(new RitualLifeDrain("lifedrain", 2, 139, 22, 40)
        .addIncense(new ItemStack(Items.WOODEN_SWORD, 1))
        .addIncense(new ItemStack(Items.WOODEN_AXE, 1))
        .addIncense(new ItemStack(RegistryManager.darkOakTreeBark, 1))
        .addIncense(new ItemStack(RegistryManager.birchTreeBark, 1))
        .addIngredient(new ItemStack(Items.BLAZE_POWDER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualImbuer()
        .addIngredient(new ItemStack(RegistryManager.crystalStaff, 1))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1)));
    addRitual(new RitualSummoning("cowSummoning", 1, 199, 105, 193)
        .setResult(EntityCow.class)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.BEEF, 1))
        .addIngredient(new ItemStack(Items.LEATHER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("pigSummoning", 1, 199, 105, 193)
        .setResult(EntityPig.class)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.PORKCHOP, 1))
        .addIngredient(new ItemStack(Items.PORKCHOP, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("sheepSummoning", 1, 199, 105, 193)
        .setResult(EntitySheep.class)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.MUTTON, 1))
        .addIngredient(new ItemStack(Blocks.WOOL, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("chickenSummoning", 1, 199, 105, 193)
        .setResult(EntityChicken.class)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.CHICKEN, 1))
        .addIngredient(new ItemStack(Items.FEATHER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("rabbitSummoning", 1, 199, 105, 193)
        .setResult(EntityRabbit.class)
        .addIncense(new ItemStack(Items.WHEAT_SEEDS, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.RABBIT, 1))
        .addIngredient(new ItemStack(Items.RABBIT_HIDE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("zombieSummoning", 2, 58, 2, 84)
        .setResult(EntityZombie.class)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.ROTTEN_FLESH, 1))
        .addIngredient(new ItemStack(Items.ROTTEN_FLESH, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("skeletonSummoning", 2, 58, 2, 84)
        .setResult(EntitySkeleton.class)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.ARROW, 1))
        .addIngredient(new ItemStack(Items.BONE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("spiderSummoning", 2, 58, 2, 84)
        .setResult(EntitySpider.class)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.STRING, 1))
        .addIngredient(new ItemStack(Items.SPIDER_EYE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("caveSpiderSummoning", 2, 58, 2, 84)
        .setResult(EntityCaveSpider.class)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.STRING, 1))
        .addIngredient(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("slimeSummoning", 2, 58, 2, 84)
        .setResult(EntitySlime.class)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.SLIME_BALL, 1))
        .addIngredient(new ItemStack(Items.SLIME_BALL, 1))
        .addIngredient(new ItemStack(Items.SLIME_BALL, 1)));
    addRitual(new RitualSummoning("creeperSummoning", 2, 58, 2, 84)
        .setResult(EntityCreeper.class)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.GUNPOWDER, 1))
        .addIngredient(new ItemStack(Items.GUNPOWDER, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSummoning("endermanSummoning", 2, 58, 2, 84)
        .setResult(EntityEnderman.class)
        .addIncense(new ItemStack(Items.COAL, 1))
        .addIncense(new ItemStack(Items.NETHER_WART, 1))
        .addIngredient(new ItemStack(Items.ENDER_PEARL, 1))
        .addIngredient(new ItemStack(Items.ENDER_PEARL, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualSacrifice("sacrifice", 2, 94, 9, 56)
        .addIncense(new ItemStack(Items.BLAZE_POWDER, 1))
        .addIncense(new ItemStack(RegistryManager.darkOakTreeBark, 1, 0))
        .addIngredient(new ItemStack(Items.FLINT, 1))
        .addIngredient(new ItemStack(Items.IRON_SWORD, 1))
        .addIngredient(new ItemStack(Items.BONE, 1)));
    addRitual(new RitualFlare("flare", 1, 255, 91, 25)
        .addIncense(new ItemStack(Items.GUNPOWDER, 1))
        .addIncense(new ItemStack(RegistryManager.infernalStem, 1))
        .addIngredient(new ItemStack(Items.FLINT, 1))
        .addIngredient(new ItemStack(Items.COAL, 1))
        .addIngredient(new ItemStack(Items.COAL, 1, 1)));
    addRitual(new RitualGrow("grow", 0, 82, 212, 47)
        .addIncense(new ItemStack(Items.WHEAT, 1))
        .addIncense(new ItemStack(Items.BEETROOT, 1))
        .addIncense(new ItemStack(Items.POTATO, 1))
        .addIncense(new ItemStack(Items.CARROT, 1))
        .addIngredient(new ItemStack(Items.REDSTONE, 1))
        .addIngredient(new ItemStack(Items.DYE, 1, 15))
        .addIngredient(new ItemStack(RegistryManager.verdantSprig, 1)));
    addRitual(new RitualEngravedSword("engravedCrafting", 2, 104, 106, 107)
        .setResult(new ItemStack(RegistryManager.engravedSword))
        .addIncense(new ItemStack(Items.GOLDEN_APPLE))
        .addIncense(new ItemStack(Items.GOLDEN_CARROT, 1))
        .addIngredient(new ItemStack(Items.STONE_SWORD))
        .addIngredient(new ItemStack(RegistryManager.runicFocus))
        .addIngredient(new ItemStack(Items.GLOWSTONE_DUST)));
    addRitual(new RitualTimeShift("timeshift", 1, 240, 245, 88)
        .addIngredient(new ItemStack(Items.CLOCK))
        .addIngredient(new ItemStack(Items.IRON_INGOT))
        .setSecondaryColor(252, 162, 35));
  }

  public static RitualBase getRitualFromName(String name) {
    for (int i = 0; i < rituals.size(); i++) {
      if (rituals.get(i).getName() == name) {
        return rituals.get(i);
      }
    }
    return null;
  }

  public static ArrayList<ItemStack> getIncenses(World world, BlockPos pos) {
    ArrayList<ItemStack> test = new ArrayList<ItemStack>();
    for (int i = -4; i < 5; i++) {
      for (int j = -4; j < 5; j++) {
        if (world.getBlockState(pos.add(i, 0, j)).getBlock() == RegistryManager.brazier) {
          if (world.getTileEntity(pos.add(i, 0, j)) != null) {
            TileEntityBrazier teb = (TileEntityBrazier) world.getTileEntity(pos.add(i, 0, j));
            if (teb.isBurning()) {
              test.add(teb.getHeldItem());
            }
          }
        }
      }
    }
    return test;
  }
}
