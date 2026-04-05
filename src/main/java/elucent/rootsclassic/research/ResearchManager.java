package elucent.rootsclassic.research;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.recipe.RootsRecipeCache;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResearchManager {

  public static List<ResearchGroup> globalResearches = new ArrayList<>();

  public static void onRecipesReceived(RecipesReceivedEvent event) {
    reload(event.getRecipeMap());

    RootsRecipeCache.clear();
    Collection<RecipeHolder<ComponentRecipe>> compressingRecipes = event.getRecipeMap().byType(RootsRecipes.COMPONENT_RECIPE_TYPE.get());
    RootsRecipeCache.componentRecipes.addAll(compressingRecipes);

    Collection<RecipeHolder<RitualRecipe>> matterRecipes = event.getRecipeMap().byType(RootsRecipes.RITUAL_RECIPE_TYPE.get());
    RootsRecipeCache.ritualRecipes.addAll(matterRecipes);
  }

  public static void reload(RecipeMap recipeMap) {
    globalResearches.clear();
    //create first page of tablet book
    globalResearches.add(new ResearchGroup("nature", "Natural Arts")
        .addResearch(new ResearchBase("bark_harvesting", new ItemStack(RootsRegistry.BARK_KNIFE.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.BARK_KNIFE.get()))))
        .addResearch(new ResearchBase("magical_materials", new ItemStack(RootsRegistry.OLD_ROOT.get()))
            .addPage(new ResearchPage())
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.OLD_ROOT.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.VERDANT_SPRIG.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.INFERNAL_BULB.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.DRAGONS_EYE.get()))))
        .addResearch(new ResearchBase("growth_powder", new ItemStack(RootsRegistry.GROWTH_POWDER.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.GROWTH_POWDER.get(), 4))))
        .addResearch(new ResearchBase("mutating_powder", new ItemStack(RootsRegistry.MUTATING_POWDER.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.MUTATING_POWDER.get()))))
        .addResearch(new ResearchBase("berries", new ItemStack(RootsRegistry.REDCURRANT.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.REDCURRANT.get()))))
        .addResearch(new ResearchBase("stew", new ItemStack(RootsRegistry.ROOTY_STEW.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.ROOTY_STEW.get()))))
        .addResearch(new ResearchBase("poultice", new ItemStack(RootsRegistry.HEALING_POULTICE.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.HEALING_POULTICE.get(), 2)))));
    //second page of tablet book
    globalResearches.add(new ResearchGroup("spells", "Spellcraft")
        .addResearch(new ResearchBase("mortar", new ItemStack(RootsRegistry.MORTAR.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.MORTAR.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.PESTLE.get())))
            .addPage(new ResearchPage()))
        .addResearch(new ResearchBase("imbuer", new ItemStack(RootsRegistry.IMBUER.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.IMBUER.get())))
            .addPage(new ResearchPage()))
        .addResearch(new ResearchBase("modifiers", new ItemStack(Items.GLOWSTONE_DUST))
            .addPage(new ResearchPage())
            .addPage(new ResearchPage()))
        .addResearch(new ResearchBase("mana", new ItemStack(RootsRegistry.MANA_RESEARCH_ICON.get()))
            .addPage(new ResearchPage()))
        .addResearch(new ResearchBase("poppy", new ItemStack(Blocks.POPPY, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("poppy")))))
        .addResearch(new ResearchBase("blue_orchid", new ItemStack(Blocks.BLUE_ORCHID, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("blue_orchid")))))
        .addResearch(new ResearchBase("allium", new ItemStack(Blocks.ALLIUM, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("allium")))))
        .addResearch(new ResearchBase("azure_bluet", new ItemStack(Blocks.AZURE_BLUET, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("azure_bluet")))))
        .addResearch(new ResearchBase("red_tulip", new ItemStack(Blocks.RED_TULIP, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("red_tulip")))))
        .addResearch(new ResearchBase("orange_tulip", new ItemStack(Blocks.ORANGE_TULIP, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("orange_tulip")))))
        .addResearch(new ResearchBase("white_tulip", new ItemStack(Blocks.WHITE_TULIP, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("white_tulip")))))
        .addResearch(new ResearchBase("pink_tulip", new ItemStack(Blocks.PINK_TULIP, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("pink_tulip")))))
        .addResearch(new ResearchBase("oxeye_daisy", new ItemStack(Blocks.OXEYE_DAISY, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("oxeye_daisy")))))
        .addResearch(new ResearchBase("dandelion", new ItemStack(Blocks.DANDELION))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("dandelion")))))
        .addResearch(new ResearchBase("sunflower", new ItemStack(Blocks.SUNFLOWER, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("sunflower")))))
        .addResearch(new ResearchBase("lilac", new ItemStack(Blocks.LILAC, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("lilac")))))
        .addResearch(new ResearchBase("rose_bush", new ItemStack(Blocks.ROSE_BUSH, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("rose_bush")))))
        .addResearch(new ResearchBase("peony", new ItemStack(Blocks.PEONY, 1))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("peony")))))
        .addResearch(new ResearchBase("apple", new ItemStack(Items.APPLE))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("apple")))))
        .addResearch(new ResearchBase("lily_pad", new ItemStack(Blocks.LILY_PAD))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("lily_pad")))))
        .addResearch(new ResearchBase("poisonous_potato", new ItemStack(Items.POISONOUS_POTATO))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("poisonous_potato")))))
        .addResearch(new ResearchBase("nether_wart", new ItemStack(Items.NETHER_WART))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("nether_wart")))))
        .addResearch(new ResearchBase("chorus", new ItemStack(Items.CHORUS_FRUIT))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("chorus")))))
        .addResearch(new ResearchBase("radiant_daisy", new ItemStack(RootsRegistry.RADIANT_DAISY.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.RADIANT_DAISY.get())))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("radiant_daisy")))))
        .addResearch(new ResearchBase("flare_orchid", new ItemStack(RootsRegistry.FLARE_ORCHID.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.FLARE_ORCHID.get())))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("flare_orchid")))))
        .addResearch(new ResearchBase("midnight_bloom", new ItemStack(RootsRegistry.MIDNIGHT_BLOOM.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.MIDNIGHT_BLOOM.get())))
            .addPage(new ResearchPage()
                .addMortarRecipe(ComponentRegistry.getSpellFromName(recipeMap, Const.modLoc("midnight_bloom"))))));
    //third page of tablet book
    globalResearches.add(new ResearchGroup("ritual", "Rituals")
        .addResearch(new ResearchBase("ritual", new ItemStack(RootsRegistry.ALTAR.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.ALTAR.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.BRAZIER.get())))
            .addPage(new ResearchPage()))
        .addResearch(new ResearchBase("living_tools", new ItemStack(RootsRegistry.LIVING_SWORD.get()))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/living_pickaxe")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/living_axe")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/living_shovel")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/living_sword")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/living_hoe"))))
        .addResearch(new ResearchBase("grow", new ItemStack(Items.BONE_MEAL, 1))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/grow"))))
        .addResearch(new ResearchBase("standing_stones", new ItemStack(RootsRegistry.MUNDANE_STANDING_STONE.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.MUNDANE_STANDING_STONE.get()))))
        .addResearch(new ResearchBase("animal_summoning", new ItemStack(Items.PORKCHOP))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/pig")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/cow")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/sheep")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/chicken")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/rabbit"))))
        .addResearch(new ResearchBase("crystal_staff", new ItemStack(RootsRegistry.CRYSTAL_STAFF.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.CRYSTAL_STAFF.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/crystal_staff")))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/imbuer")))
            .addPage(new ResearchPage()))
        .addResearch(new ResearchBase("rain_rituals", new ItemStack(Items.WATER_BUCKET))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/cause_rain")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/banish_rain"))))
        .addResearch(new ResearchBase("flare", new ItemStack(Items.BLAZE_POWDER))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/flare"))))
        .addResearch(new ResearchBase("standing_stones2", new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.ATTUNED_STANDING_STONE.get()))))
        .addResearch(new ResearchBase("monster_summoning", new ItemStack(Items.ROTTEN_FLESH))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/zombie")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/skeleton")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/spider")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/creeper")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/cave_spider")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/slime")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/summoning/enderman"))))
        .addResearch(new ResearchBase("sylvan_armor", new ItemStack(RootsRegistry.SYLVAN_HOOD.get()))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/sylvan_hood")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/sylvan_robe")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/sylvan_tunic")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/sylvan_boots"))))
        .addResearch(new ResearchBase("wildwood_armor", new ItemStack(RootsRegistry.WILDWOOD_MASK.get()))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/wildwood_mask")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/wildwood_plate")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/wildwood_leggings")))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/wildwood_boots"))))
        .addResearch(new ResearchBase("powered_stones", new ItemStack(RootsRegistry.ACCELERATOR_STANDING_STONE.get()))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/accelerator_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.ACCELERATOR_STANDING_STONE.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/entangler_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.ENTANGLER_STANDING_STONE.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/grower_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.GROWTH_POWDER.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/healer_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.HEALER_STANDING_STONE.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/igniter_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.IGNITER_STANDING_STONE.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/repulsor_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.REPULSOR_STANDING_STONE.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/vacuum_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.VACUUM_STANDING_STONE.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/aesthetic_standing_stone")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.AESTHETIC_STANDING_STONE.get()))))
        .addResearch(new ResearchBase("mass_breeding", new ItemStack(Items.GOLDEN_CARROT))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/mass_breeding"))))
        .addResearch(new ResearchBase("life_drain", new ItemStack(Items.NETHER_WART))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/life_drain"))))
        .addResearch(new ResearchBase("sacrifice", new ItemStack(Items.IRON_SWORD))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/sacrifice"))))
        .addResearch(new ResearchBase("runic_focus", new ItemStack(RootsRegistry.RUNIC_FOCUS.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.RUNIC_FOCUS.get(), 1)))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/runic_focus")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.CHARGED_RUNIC_FOCUS.get(), 1)))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/crafting/charged_runic_focus"))))
        .addResearch(new ResearchBase("engraved_blade", new ItemStack(RootsRegistry.ENGRAVED_BLADE.get()))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.ENGRAVED_BLADE.get())))
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/engraved_blade")))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.ACACIA_BARK.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.SPRUCE_BARK.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.BIRCH_BARK.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.JUNGLE_BARK.get())))
            .addPage(new ResearchPage()
                .addDisplayItem(new ItemStack(RootsRegistry.DARK_OAK_BARK.get()))))
        .addResearch(new ResearchBase("time_shift", new ItemStack(Items.CLOCK))
            .addPage(new ResearchPage())
            .addPageOf(RitualRegistry.recipeByName(recipeMap, Const.modLoc("ritual/time_shift")))));
  }

  public static ResearchBase getResearch(String groupName, String researchName) {
    for (ResearchGroup globalResearch : globalResearches) {
      if (globalResearch.getName().equals(groupName)) {
        for (int j = 0; j < globalResearch.researches.size(); j++) {
          if (globalResearch.researches.get(j).getName().equals(researchName)) {
            return globalResearch.researches.get(j);
          }
        }
      }
    }
    return null;
  }

  public static ResearchGroup getResearchGroup(String groupName) {
    for (ResearchGroup globalResearch : globalResearches) {
      if (globalResearch.getName().equals(groupName)) {
        return globalResearch;
      }
    }
    return null;
  }
}
