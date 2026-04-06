package elucent.rootsclassic.datagen.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class RootsLanguageProvider extends LanguageProvider {
  public RootsLanguageProvider(PackOutput packOutput) {
    super(packOutput, Const.MODID, "en_us");
  }

  @Override
  protected void addTranslations() {
    this.add("itemGroup.rootsclassic", "Roots Classic");

    this.addItem(RootsRegistry.ACACIA_BARK, "Acacia Bark");
    this.addItem(RootsRegistry.BARK_KNIFE, "Bark Knife");
    this.addItem(RootsRegistry.BIRCH_BARK, "Birch Bark");
    this.addItem(RootsRegistry.BLACKCURRANT, "Blackcurrant");
    this.addItem(RootsRegistry.CHARGED_RUNIC_FOCUS, "Charged Runic Focus");
    this.addItem(RootsRegistry.CRYSTAL_STAFF, "Crystal Staff");
    this.addItem(RootsRegistry.DARK_OAK_BARK, "Dark Oak Bark");
    this.addItem(RootsRegistry.DRAGONS_EYE, "Dragon's Eye");
    this.addItem(RootsRegistry.ELDERBERRY, "Elderberry");
    this.addItem(RootsRegistry.ENGRAVED_BLADE, "Engraved Blade");
    this.addItem(RootsRegistry.FRUIT_SALAD, "Fruit Salad");
    this.addItem(RootsRegistry.GROWTH_POWDER, "Growth Powder");
    this.addItem(RootsRegistry.HEALING_POULTICE, "Healing Poultice");
    this.addItem(RootsRegistry.INFERNAL_BULB, "Infernal Bulb");
    this.addItem(RootsRegistry.JUNGLE_BARK, "Jungle Bark");
    this.addItem(RootsRegistry.LIVING_AXE, "Living Axe");
    this.addItem(RootsRegistry.LIVING_HOE, "Living Hoe");
    this.addItem(RootsRegistry.LIVING_PICKAXE, "Living Pickaxe");
    this.addItem(RootsRegistry.LIVING_SHOVEL, "Living Shovel");
    this.addItem(RootsRegistry.LIVING_SWORD, "Living Sword");
    this.addItem(RootsRegistry.MANA_RESEARCH_ICON, "Mana Research Icon");
    this.addItem(RootsRegistry.MUTATING_POWDER, "Mutating Powder");
    this.addItem(RootsRegistry.NIGHTSHADE, "Nightshade");
    this.addItem(RootsRegistry.OAK_BARK, "Oak Bark");
    this.addItem(RootsRegistry.OLD_ROOT, "Old Root");
    this.addItem(RootsRegistry.PESTLE, "Pestle");
    this.addItem(RootsRegistry.REDCURRANT, "Redcurrant");
    this.addItem(RootsRegistry.ROOTY_STEW, "Rooty Stew");
    this.addItem(RootsRegistry.RUNIC_FOCUS, "Runic Focus");
    this.addItem(RootsRegistry.RUNIC_TABLET, "Runic Tablet");
    this.addItem(RootsRegistry.SPELL_POWDER, "Spell Powder");
    this.addItem(RootsRegistry.SPRUCE_BARK, "Spruce Bark");
    this.addItem(RootsRegistry.STAFF, "Staff");
    this.addItem(RootsRegistry.SYLVAN_BOOTS, "Sylvan Boots");
    this.addItem(RootsRegistry.SYLVAN_HOOD, "Sylvan Hood");
    this.addItem(RootsRegistry.SYLVAN_ROBE, "Sylvan Robe");
    this.addItem(RootsRegistry.SYLVAN_TUNIC, "Sylvan Tunic");
    this.addItem(RootsRegistry.VERDANT_SPRIG, "Verdant Sprig");
    this.addItem(RootsRegistry.WHITECURRANT, "Whitecurrant");
    this.addItem(RootsRegistry.WILDWOOD_BOOTS, "Wildwood Boots");
    this.addItem(RootsRegistry.WILDWOOD_LEGGINGS, "Wildwood Leggings");
    this.addItem(RootsRegistry.WILDWOOD_MASK, "Wildwood Mask");
    this.addItem(RootsRegistry.WILDWOOD_PLATE, "Wildwood Plate");
    this.add("item.rootsclassic.acacia_bark.guide", "Use a Bark Knife on a log.");
    this.add("item.rootsclassic.bark_knife.guide", "Used to obtain bark from logs.");
    this.add("item.rootsclassic.bark_knife.tooltip", "Peels bark from logs to be used in magic rituals");
    this.add("item.rootsclassic.birch_bark.guide", "Use a Bark Knife on a log.");
    this.add("item.rootsclassic.blackcurrant.guide", "Spell component; see Runic Tablet.");
    this.add("item.rootsclassic.charged_runic_focus.guide", "Stores energy for Roots Rituals; see Runic Tablet.");
    this.add("item.rootsclassic.crystal_staff.guide", "Advanced spellcasting staff for the most powerful of magic.  See Runic Tablet for crafting instructions.");
    this.add("item.rootsclassic.dark_oak_bark.guide", "Use a Bark Knife on a log.");
    this.add("item.rootsclassic.dragons_eye.guide", "Spell component; see Runic Tablet.");
    this.add("item.rootsclassic.elderberry.guide", "A pugnant mysterious berry; see Runic Tablet.");
    this.add("item.rootsclassic.engraved_blade.guide", "A mysterious blade with unknown powers.");
    this.add("item.rootsclassic.fruit_salad.guide", "Delicious and filling.");
    this.add("item.rootsclassic.growth_powder.guide", "Use to grow grass on dirt.");
    this.add("item.rootsclassic.healing_poultice.guide", "Instant healing when eaten.");
    this.add("item.rootsclassic.infernal_bulb.guide", "Spell component; see Runic Tablet.");
    this.add("item.rootsclassic.jungle_bark.guide", "Use a Bark Knife on a log.");
    this.add("item.rootsclassic.living_axe.guide", "Self-repairing tool; obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.living_hoe.guide", "Self-repairing tool; obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.living_pickaxe.guide", "Self-repairing tool; obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.living_shovel.guide", "Self-repairing tool; obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.living_sword.guide", "Self-repairing tool; obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.mana_research_icon.guide", "Just an icon.");
    this.add("item.rootsclassic.mutating_powder.guide", "Mutates flora in magical ways; see Runic Tablet.");
    this.add("item.rootsclassic.nightshade.guide", "A pugnant mysterious berry; see Runic Tablet.");
    this.add("item.rootsclassic.oak_bark.guide", "Use a Bark Knife on a log.");
    this.add("item.rootsclassic.old_root.guide", "Basic spell component; drops from tall grass; see Runic Tablet.");
    this.add("item.rootsclassic.pestle.guide", "Used with Mortar to create spells, see Runic Tablet.");
    this.add("item.rootsclassic.redcurrant.guide", "Spell component; see Runic Tablet.");
    this.add("item.rootsclassic.rooty_stew.guide", "Hearty Food.");
    this.add("item.rootsclassic.runic_focus.guide", "Stores energy for Roots Rituals; see Runic Tablet.");
    this.add("item.rootsclassic.runic_tablet.guide", "The guidebook for Roots Classic that explains all spells and rituals.");
    this.add("item.rootsclassic.spell_powder.guide", "A combination of ingredients that makes up a spell, see Runic Tablet.");
    this.add("item.rootsclassic.spruce_bark.guide", "Use a Bark Knife on a log.");
    this.add("item.rootsclassic.staff.guide", "Created using Spell Powder on the Imbuer, see Runic Tablet.");
    this.add("item.rootsclassic.sylvan_boots.guide", "Obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.sylvan_hood.guide", "Obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.sylvan_robe.guide", "Obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.sylvan_tunic.guide", "Obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.verdant_sprig.guide", "Spell component; see Runic Tablet.");
    this.add("item.rootsclassic.whitecurrant.guide", "Spell component; see Runic Tablet.");
    this.add("item.rootsclassic.wildwood_boots.guide", "Obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.wildwood_leggings.guide", "Obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.wildwood_mask.guide", "Obtained using a magic ritual; see Runic Tablet.");
    this.add("item.rootsclassic.wildwood_plate.guide", "Obtained using a magic ritual; see Runic Tablet.");

    this.addBlock(RootsRegistry.ACCELERATOR_STANDING_STONE, "Accelerating Standing Stone");
    this.addBlock(RootsRegistry.AESTHETIC_STANDING_STONE, "Standing Stone");
    this.addBlock(RootsRegistry.ALTAR, "Casting Altar");
    this.addBlock(RootsRegistry.ATTUNED_STANDING_STONE, "Attuned Standing Stone");
    this.addBlock(RootsRegistry.BRAZIER, "Incense Brazier");
    this.addBlock(RootsRegistry.ENTANGLER_STANDING_STONE, "Entangling Standing Stone");
    this.addBlock(RootsRegistry.FLARE_ORCHID, "Flare Orchid");
    this.addBlock(RootsRegistry.GROWER_STANDING_STONE, "Growing Standing Stone");
    this.addBlock(RootsRegistry.HEALER_STANDING_STONE, "Healing Standing Stone");
    this.addBlock(RootsRegistry.IGNITER_STANDING_STONE, "Igniting Standing Stone");
    this.addBlock(RootsRegistry.IMBUER, "Imbuer");
    this.addBlock(RootsRegistry.MIDNIGHT_BLOOM, "Midnight Bloom");
    this.addBlock(RootsRegistry.MUNDANE_STANDING_STONE, "Mundane Standing Stone");
    this.addBlock(RootsRegistry.RADIANT_DAISY, "Radiant Daisy");
    this.addBlock(RootsRegistry.REPULSOR_STANDING_STONE, "Repulsing Standing Stone");
    this.addBlock(RootsRegistry.VACUUM_STANDING_STONE, "Vacuum Standing Stone");
    this.addBlock(RootsRegistry.MORTAR, "Mortar");
    this.add("block.rootsclassic.accelerator_standing_stone.guide", "Speed beacon, crafting ritual defined in Runic Tablet.");
    this.add("block.rootsclassic.aesthetic_standing_stone.guide", "Standing Stone for decoration");
    this.add("block.rootsclassic.altar.guide", "Center piece for casting rituals; see Runic Tablet.");
    this.add("block.rootsclassic.attuned_standing_stone.guide", "Advanced ritual stone, used to create ritual patterns as shown in the Runic Tablet.");
    this.add("block.rootsclassic.brazier.guide", "Holds incense to burn during rituals; see Runic Tablet.");
    this.add("block.rootsclassic.entangler_standing_stone.guide", "Slowness beacon, crafting ritual defined in Runic Tablet.");
    this.add("block.rootsclassic.flare_orchid.guide", "Advanced spell component; see Runic Tablet.");
    this.add("block.rootsclassic.grower_standing_stone.guide", "Growth booster for your plants, crafting ritual defined in Runic Tablet.");
    this.add("block.rootsclassic.healer_standing_stone.guide", "Regeneration beacon, crafting ritual defined in Runic Tablet.");
    this.add("block.rootsclassic.igniter_standing_stone.guide", "Burn your enemies, crafting ritual defined in Runic Tablet.");
    this.add("block.rootsclassic.imbuer.guide", "Create any magical Staff using Spell Powder, see Runic Tablet.");
    this.add("block.rootsclassic.midnight_bloom.guide", "Advanced spell component; see Runic Tablet.");
    this.add("block.rootsclassic.mortar.guide", "Used with Pestle to create spells, see Runic Tablet.");
    this.add("block.rootsclassic.mundane_standing_stone.guide", "Basic ritual stone, used to create ritual patterns as shown in the Runic Tablet.");
    this.add("block.rootsclassic.radiant_daisy.guide", "Advanced spell component; see Runic Tablet.");
    this.add("block.rootsclassic.repulsor_standing_stone.guide", "Repulse nearby stuff, crafting ritual defined in Runic Tablet.");
    this.add("block.rootsclassic.vacuum_standing_stone.guide", "Vacuum nearby stuff, crafting ritual defined in Runic Tablet.");

    this.add("rootsclassic.attribute.fullset", "When full set equipped:");
    this.add("rootsclassic.attribute.equipped", "When equipped:");
    this.add("rootsclassic.attribute.increasedregen", "Increased Health Regeneration");
    this.add("rootsclassic.attribute.increasedmanaregen", "Increased Terra Regeneration");
    this.add("rootsclassic.attribute.potency", "Potency");

    this.add("rootsclassic.tooltip.spelltypeheading", "Type");
    this.add("rootsclassic.tooltip.spellpotency", "potency");
    this.add("rootsclassic.tooltip.spellefficiency", "efficiency");
    this.add("rootsclassic.tooltip.spellsize", "size");
    this.add("rootsclassic.tooltip.usesremaining", "uses remaining");
    this.add("rootsclassic.tooltip.spikes", "Spikes");
    this.add("rootsclassic.tooltip.forceful", "Forceful");
    this.add("rootsclassic.tooltip.holy", "Holy");
    this.add("rootsclassic.tooltip.aquatic", "Aquatic");
    this.add("rootsclassic.tooltip.shadowstep", "Shadow Step");

    this.add("rootsclassic.error.unset", "Unbound");

    this.add("rootsclassic.error.noritual.ingredients", "No ritual found with these central ingredients");

    this.add("rootsclassic.error.noritual.stones", "Ritual found but stones are not placed correctly");

    this.add("rootsclassic.error.noritual.incense", "Ritual found, but brazier ingredients are missing or not lit");

    this.add("rootsclassic.ritual.started", "Ritual started");

    this.add("rootsclassic.component.rootsclassic:allium", "Rending Strike");
    this.add("rootsclassic.component.rootsclassic:apple", "Nature's Cure");
    this.add("rootsclassic.component.rootsclassic:azure_bluet", "Shatter");
    this.add("rootsclassic.component.rootsclassic:blue_orchid", "Earth Spike");
    this.add("rootsclassic.component.rootsclassic:chorus", "Ender Warp");
    this.add("rootsclassic.component.rootsclassic:dandelion", "Dandelion Winds");
    this.add("rootsclassic.component.rootsclassic:flare_orchid", "Combustion");
    this.add("rootsclassic.component.rootsclassic:lilac", "Growth");
    this.add("rootsclassic.component.rootsclassic:lily_pad", "Water Blast");
    this.add("rootsclassic.component.rootsclassic:midnight_bloom", "Time Stop");
    this.add("rootsclassic.component.rootsclassic:nether_wart", "Inferno");
    this.add("rootsclassic.component.rootsclassic:orange_tulip", "Shielding");
    this.add("rootsclassic.component.rootsclassic:oxeye_daisy", "Acceleration");
    this.add("rootsclassic.component.rootsclassic:peony", "Regeneration");
    this.add("rootsclassic.component.rootsclassic:pink_tulip", "Life Drain");
    this.add("rootsclassic.component.rootsclassic:poisonous_potato", "Electric Spark");
    this.add("rootsclassic.component.rootsclassic:poppy", "Insanity");
    this.add("rootsclassic.component.rootsclassic:radiant_daisy", "Shining Ray");
    this.add("rootsclassic.component.rootsclassic:red_tulip", "Devil's Flower");
    this.add("rootsclassic.component.rootsclassic:rose_bush", "Rose's Thorns");
    this.add("rootsclassic.component.rootsclassic:sunflower", "Solar Smite");
    this.add("rootsclassic.component.rootsclassic:white_tulip", "Blistering Cold");

    this.add("rootsclassic.mod.spikes", "Spikes");
    this.add("rootsclassic.mod.holy", "Holy");
    this.add("rootsclassic.mod.forceful", "Forceful");
    this.add("rootsclassic.mod.aquatic", "Aquatic");
    this.add("rootsclassic.mod.shadowstep", "Shadow Step");

    this.add("rootsclassic.research.disabled", "Disabled By Config!");

    this.add("rootsclassic.research.nature", "Natural Arts");

    this.add("rootsclassic.research.nature.bark_harvesting", "Bark Harvesting");
    this.add("rootsclassic.research.nature.bark_harvesting.page1title", "Simple Materials");
    this.add("rootsclassic.research.nature.bark_harvesting.page1info", "Trees are very important to natural magic. Using some wood and saplings, you can create a knife capable of softly harvesting the bark from trees, which can be used in recipes and rituals. Right-click a log block to harvest bark from it, with a chance of breaking the log block.");

    this.add("rootsclassic.research.nature.magical_materials", "Rare Materials");
    this.add("rootsclassic.research.nature.magical_materials.page1title", "Roots of Magic");
    this.add("rootsclassic.research.nature.magical_materials.page1info", "In the world around you, you may find several rare plants and herbs that are crucial for many aspects of natural magic.");
    this.add("rootsclassic.research.nature.magical_materials.page2title", "Old Roots");
    this.add("rootsclassic.research.nature.magical_materials.page2info", "Old roots can be found at a 1/40 chance when breaking tall grass. They are needed for a lot of early magic, as they are required for spells without modifiers. They can also be eaten to restore a very small amount of hunger.");
    this.add("rootsclassic.research.nature.magical_materials.page3title", "Verdant Sprigs");
    this.add("rootsclassic.research.nature.magical_materials.page3info", "Verdant sprigs will drop at a 1/30 chance when harvesting fully-grown crops of all kinds. They have innate life force that persists after being harvested, so their uses often relate to bringing life to objects.");
    this.add("rootsclassic.research.nature.magical_materials.page4title", "Infernal Bulbs");
    this.add("rootsclassic.research.nature.magical_materials.page4info", "Infernal Bulbs will drop at a 1/20 chance from grown nether wart. Infernal bulbs are imbued with the fire of their home in the nether, so they are useful when a large amount of energy is needed. They can also be used as fairly effective furnace fuel, or given to skeletons to make them withered.");
    this.add("rootsclassic.research.nature.magical_materials.page5title", "Dragon's Eyes");
    this.add("rootsclassic.research.nature.magical_materials.page5info", "Dragon's eyes will drop at a 1/10 chance from chorus flowers. They are imbued with the same eldritch energy as the ender dragon itself. In addition to spell-related uses, they can be eaten for a more powerful chorus fruit effect, or smelted to yield one ender pearl.");

    this.add("rootsclassic.research.nature.growth_powder", "Growth Powder");
    this.add("rootsclassic.research.nature.growth_powder.page1title", "Lawn Care");
    this.add("rootsclassic.research.nature.growth_powder.page1info", "This simple mixture can grow grass on dirt. Simply right-click to throw the powder at a block within 4 blocks of you, growing grass in a small area.");

    this.add("rootsclassic.research.nature.mutating_powder", "Mutating Powder");
    this.add("rootsclassic.research.nature.mutating_powder.page1title", "New Plants");
    this.add("rootsclassic.research.nature.mutating_powder.page1info", "Using the power of a nether star, you have created a powder capable of mutating new kinds of flora. This powder is only used in very specific circumstances, specified in the spell components section of this tablet.");

    this.add("rootsclassic.research.nature.berries", "Berries");
    this.add("rootsclassic.research.nature.berries.page1title", "Foraging");
    this.add("rootsclassic.research.nature.berries.page1info", "When breaking some leaves by hand, you may occasionally find a random berry. There are five different kinds of berries, each one with different properties. Some may even be used in spells or rituals later on...");

    this.add("rootsclassic.research.nature.stew", "Rooty Stew");
    this.add("rootsclassic.research.nature.stew.page1title", "Soup For You");
    this.add("rootsclassic.research.nature.stew.page1info", "While Old Roots can be eaten alone, they're not very appetizing. By cooking it with some wheat in a bowl, you can create a delicious stew capable of replenishing much more hunger.");

    this.add("rootsclassic.research.nature.poultice", "Healing Poultice");
    this.add("rootsclassic.research.nature.poultice.page1title", "Medicine");
    this.add("rootsclassic.research.nature.poultice.page1info", "Verdant Sprigs are commonly used for their life-related properties. By applying the pulp of a verdant sprig to your wounds with these poultices, you can heal a small amount of health.");

    this.add("rootsclassic.research.spells", "Spellcraft");

    this.add("rootsclassic.research.spells.mortar", "The Mortar");
    this.add("rootsclassic.research.spells.mortar.page1title", "The Mortar");
    this.add("rootsclassic.research.spells.mortar.page1info", "The flora in the world around you seem to have vast untapped power. What better way to access that than mashing them into a pulp?");
    this.add("rootsclassic.research.spells.mortar.page2title", "The Pestle");
    this.add("rootsclassic.research.spells.mortar.page2info", "Of course, to mash the aforementioned plants into a pulp, you're going to need something to mash with. This pestle, fashioned out of a smooth white stone, should do the task nicely.");
    this.add("rootsclassic.research.spells.mortar.page3title", "Usage");
    this.add("rootsclassic.research.spells.mortar.page3info", "Using the mortar and pestle is quite simple. Detailed in this tablet are many recipes for different spells. You will need all of these ingredients, as well as a particular base ingredient. To create the most basic possible spell, simply place in the spell's recipe, as well as an old root. This will allow for a spell with no modifiers.");

    this.add("rootsclassic.research.spells.imbuer", "Imbuing a Staff");
    this.add("rootsclassic.research.spells.imbuer.page1title", "The Imbuer");
    this.add("rootsclassic.research.spells.imbuer.page1info", "Your mortar and pestle yield a fine Spell Powder for use in spells, but it can't really be used on its own. With this device, you channel natural energies from that powder into a branch of wood. Simply place in a stick, place in your powder, and wait for your staff to pop out.");
    this.add("rootsclassic.research.spells.imbuer.page2title", "Staff Mechanics");
    this.add("rootsclassic.research.spells.imbuer.page2info", "Magic staves are very simple to use. Imbued with the power of nature, all you need to do is hold right-click to charge up a spell, and after about a second release right-click to cast it. Staves have limited uses, and will be destroyed completely after their charges are used up. The number of charges can be increased with the use of the efficiency modifier.");

    this.add("rootsclassic.research.spells.modifiers", "Modifiers");
    this.add("rootsclassic.research.spells.modifiers.page1title", "More Bang For Your Buck");
    this.add("rootsclassic.research.spells.modifiers.page1info", "Mixing some modifiers into your spells can make them much more powerful. Potency modifiers (glowstone dust) will improve the strength of a spell's effects. Efficiency modifiers (redstone dust) will improve the casting cost and number of uses a spell has. Size modifiers (gunpowder) will improve the area a spell effects.");
    this.add("rootsclassic.research.spells.modifiers.page2title", "Base Ingredients");
    this.add("rootsclassic.research.spells.modifiers.page2info", "For a spell to have modifiers, though, certain base ingredients are required. Old Roots will allow a spell to be crafted, but permit no modifiers. Verdant Sprigs can support one modifier slot, Infernal Bulbs can support two, and Dragon's Eyes can support three. These ingredients must be added first, before any other modifiers are added to the mortar.");

    this.add("rootsclassic.research.spells.mana", "Terra");
    this.add("rootsclassic.research.spells.mana.page1title", "Casting Costs");
    this.add("rootsclassic.research.spells.mana.page1info", "When you hold your new staff in your hand, you notice a power awakened within you, as well as a conveniently placed bar on your screen. These leaves represent a life force deep within you, called Terra, that powers your spells. All spells will cost a certain amount of this energy, but it will regenerate fairly quickly over time.");

    this.add("rootsclassic.research.spells.poppy", "Spell Component: Insanity");
    this.add("rootsclassic.research.spells.poppy.page1title", "Betrayal");
    this.add("rootsclassic.research.spells.poppy.page1info", "Poppies can naturally contain mind-altering compounds, so it follows that their abilities in a spell would involve the mind. Using the above recipe, you can create a spell that will cause nearby monsters to attack other nearby monsters.");

    this.add("rootsclassic.research.spells.blue_orchid", "Spell Component: Earth Spike");
    this.add("rootsclassic.research.spells.blue_orchid.page1title", "The Best Offense...");
    this.add("rootsclassic.research.spells.blue_orchid.page1info", "The blue orchid seems to be related to the earth and ground. Using the above recipe, you can create a spell that will create an earth spike on a nearby block that you look at. The spike will throw mobs into the air, but can also be used as a quick defensive wall or as a way to duplicate simple materials like dirt, stone, sand, or gravel.");

    this.add("rootsclassic.research.spells.allium", "Spell Component: Rending Strike");
    this.add("rootsclassic.research.spells.allium.page1title", "Equipment Breaker");
    this.add("rootsclassic.research.spells.allium.page1info", "Alliums contain a modicum of destructive energy. Using the above recipe, you can create a spell that will deal simple damage to its targets. In addition to this, if the target is holding a tool or wearing armor, their equipment will be damaged severely.");

    this.add("rootsclassic.research.spells.azure_bluet", "Spell Component: Shatter");
    this.add("rootsclassic.research.spells.azure_bluet.page1title", "Mining with Magic");
    this.add("rootsclassic.research.spells.azure_bluet.page1info", "The azure bluet contains latent energy capable of shattering stone. Using the above recipe, you create a spell capable of breaking a small area of blocks that you target. The spell has a mining level equivalent to an iron tool, increasable with potency modifiers.");

    this.add("rootsclassic.research.spells.red_tulip", "Spell Component: Devil's Flower");
    this.add("rootsclassic.research.spells.red_tulip.page1title", "Summoning");
    this.add("rootsclassic.research.spells.red_tulip.page1info", "The allure of the red tulip seems to draw rather...sinister attention. Using the above recipe, you can summon a spectral skeleton to attack a nearby mob.");

    this.add("rootsclassic.research.spells.orange_tulip", "Spell Component: Shielding");
    this.add("rootsclassic.research.spells.orange_tulip.page1title", "Force Field");
    this.add("rootsclassic.research.spells.orange_tulip.page1info", "The orange tulip is associated with protection and resistance. Using the above recipe, you can create a spell that will repel mobs and stop projectiles while holding-right click on the spell.");

    this.add("rootsclassic.research.spells.white_tulip", "Spell Component: Blistering Cold");
    this.add("rootsclassic.research.spells.white_tulip.page1title", "Frozen Solid");
    this.add("rootsclassic.research.spells.white_tulip.page1info", "The white tulip contains magical essences of cold and ice. Using the above recipe, you can free this cold power, creating a spell that will both damage and slow hit targets.");

    this.add("rootsclassic.research.spells.pink_tulip", "Spell Component: Life Drain");
    this.add("rootsclassic.research.spells.pink_tulip.page1title", "Vampirism");
    this.add("rootsclassic.research.spells.pink_tulip.page1info", "You have found that the pink tulip can closely link with the life force of living creatures. Using the above recipe, you can create a spell capable of damaging hit entities, while simultaneously stealing some of their life force to regenerate yours.");

    this.add("rootsclassic.research.spells.oxeye_daisy", "Spell Component: Acceleration");
    this.add("rootsclassic.research.spells.oxeye_daisy.page1title", "Overclocked");
    this.add("rootsclassic.research.spells.oxeye_daisy.page1info", "The oxeye daisy is attuned to the passage of time itself. Using the above recipe, you can create a spell capable of increasing the tick rate of furnaces, brewing stands, or other devices that you target with your spell. By default, the spell will triple the rate of these devices.");

    this.add("rootsclassic.research.spells.dandelion", "Spell Component: Dandelion Winds");
    this.add("rootsclassic.research.spells.dandelion.page1title", "Gust");
    this.add("rootsclassic.research.spells.dandelion.page1info", "You have discovered that the dandelion has powers relating to the wind. Using the above recipe, you can create a spell capable of blasting back entities in front of you.");

    this.add("rootsclassic.research.spells.sunflower", "Spell Component: Solar Smite");
    this.add("rootsclassic.research.spells.sunflower.page1title", "Bane of the Undead");
    this.add("rootsclassic.research.spells.sunflower.page1info", "A sunflower spends its entire life trying to absorb the sun's rays. Using the above recipe, you can tap into a bit of that stored sunlight, doing some damage to normal mobs, but dealing a massive amount of extra damage to undead monsters, as well as igniting them.");

    this.add("rootsclassic.research.spells.lilac", "Spell Component: Growth");
    this.add("rootsclassic.research.spells.lilac.page1title", "Magical Fertilizer");
    this.add("rootsclassic.research.spells.lilac.page1info", "You have discovered that lilacs contain a spark of growth energy deep within them. Using the above recipe, you can create a spell capable of growing nearby plant life. The spell will target a growable plants around the targeted block, and has a chance of applying a bonemeal effect to each one.");

    this.add("rootsclassic.research.spells.rose_bush", "Spell Component: Rose's Thorns");
    this.add("rootsclassic.research.spells.rose_bush.page1title", "Natural Defenses");
    this.add("rootsclassic.research.spells.rose_bush.page1info", "You have discovered a way to capture the prickliness of a rose bush in your spells. Using the above recipe, you can create a spell capable of dealing raw damage to targets. Do note that this spell will not cause knockback.");

    this.add("rootsclassic.research.spells.peony", "Spell Component: Regeneration");
    this.add("rootsclassic.research.spells.peony.page1title", "Gentle Touch");
    this.add("rootsclassic.research.spells.peony.page1info", "You have found that peonies have a gentle healing character to them. Using the above recipe, you can create a spell that will simply heal you a small amount.");

    this.add("rootsclassic.research.spells.apple", "Spell Component: Nature's Cure");
    this.add("rootsclassic.research.spells.apple.page1title", "Remedy");
    this.add("rootsclassic.research.spells.apple.page1info", "You have discovered that apples have some level of curative properties. Using the above recipe, you can create a spell that will take any negative effects currently on you and transform them to their positive variants, if such variants exist.");

    this.add("rootsclassic.research.spells.lily_pad", "Spell Component: Water Blast");
    this.add("rootsclassic.research.spells.lily_pad.page1title", "Fire Extinguisher");
    this.add("rootsclassic.research.spells.lily_pad.page1info", "Being an aquatic plant, you'd expect lily pads to have some kind of power relating to the water. Using the above recipe, you can create a spell that will create a burst of temporary water 4 blocks in front of you. This spell is quite useful for extinguishing fires, or pushing back nearby mobs.");

    this.add("rootsclassic.research.spells.poisonous_potato", "Spell Component: Electric Spark");
    this.add("rootsclassic.research.spells.poisonous_potato.page1title", "Lightning Strike");
    this.add("rootsclassic.research.spells.poisonous_potato.page1info", "The innate charge of a potato combined with the harmfulness of its poison create a highly destructive spell. Using the above recipe, you can create a spell that will simply strike lightning on a nearby block you look at.");

    this.add("rootsclassic.research.spells.nether_wart", "Spell Component: Inferno");
    this.add("rootsclassic.research.spells.nether_wart.page1title", "Fireball");
    this.add("rootsclassic.research.spells.nether_wart.page1info", "Nether wart has absorbed a lot from its fiery home in the nether. Using the above recipe, you can create a spell that will both damage and ignite hit targets.");

    this.add("rootsclassic.research.spells.chorus", "Spell Component: Ender Warp");
    this.add("rootsclassic.research.spells.chorus.page1title", "Teleportation");
    this.add("rootsclassic.research.spells.chorus.page1info", "Just eating a chorus fruit on its own is enough to grant a random teleport. Using the above recipe, you can create a spell with greater control over this ability, letting the user teleport forward, even through walls or when falling.");

    this.add("rootsclassic.research.spells.radiant_daisy", "Spell Component: Radiance");
    this.add("rootsclassic.research.spells.radiant_daisy.page1title", "Flower of Light");
    this.add("rootsclassic.research.spells.radiant_daisy.page1info", "Using the mutating flower in the overworld, you create a shining white flower. To create, plant an oxeye daisy. At noon, toss down a glowstone block and one prismarine crystal next to it, and while under the night vision effect, use the powder on the flower.");
    this.add("rootsclassic.research.spells.radiant_daisy.page2title", "Piercing Rays");
    this.add("rootsclassic.research.spells.radiant_daisy.page2info", "The new flower you have created contains the very essence of the glowing daylight of the overworld. Using the above recipe, you can create a spell that will do very high damage to struck creatures in a large beam in front of you.");

    this.add("rootsclassic.research.spells.flare_orchid", "Spell Component: Combustion");
    this.add("rootsclassic.research.spells.flare_orchid.page1title", "Fiery Flora");
    this.add("rootsclassic.research.spells.flare_orchid.page1info", "Using the mutating powder in the nether, you create a burning red flower. To create, plant a blue orchid in the nether. Make a ring of netherrack around the flower. While under the fire resistance effect, toss a blaze rod and lava bucket near the flower, then use the powder on the flower.");
    this.add("rootsclassic.research.spells.flare_orchid.page2title", "Destruction");
    this.add("rootsclassic.research.spells.flare_orchid.page2info", "The new flower you have created is bursting with the chaotic fire of the nether. Using the above recipe, you can use it to create a spell that will allow you to create large explosions on blocks that you look at.");

    this.add("rootsclassic.research.spells.midnight_bloom", "Spell Component: Time Stop");
    this.add("rootsclassic.research.spells.midnight_bloom.page1title", "Ender Flower");
    this.add("rootsclassic.research.spells.midnight_bloom.page1info", "Using the mutating powder in the end, you create a flower black as night. To create, plant a poppy two blocks above obsidian in the end. While under the slowness effect, toss a coal block next to the flower and use the powder on the flower.");
    this.add("rootsclassic.research.spells.midnight_bloom.page2title", "Chronomage");
    this.add("rootsclassic.research.spells.midnight_bloom.page2info", "The new flower you have created is bursting with powers beyond your control. Using the above recipe, you can create a spell that will stop time for all entities in an area around you. They will not update until the spell ends, during which time you can move, attack, or gloat at them.");

    this.add("rootsclassic.research.ritual", "Rituals");

    this.add("rootsclassic.research.ritual.ritual", "Basic Ritual Mechanics");
    this.add("rootsclassic.research.ritual.ritual.page1title", "The Altar");
    this.add("rootsclassic.research.ritual.ritual.page1info", "With your acquisition of materials from deep within the earth, you have created a new structure that allows you to cast more powerful magic than ever before. Different rituals may be performed at the altar, each one requiring different components and conditions to operate.");
    this.add("rootsclassic.research.ritual.ritual.page2title", "Incense");
    this.add("rootsclassic.research.ritual.ritual.page2info", "Most rituals, if not all, require incense to function. Incense braziers will only be counted if they are within a 9x9 area around the altar, and on the same level. Items may be placed within the brazier, and ignited with a flint and steel. An incense brazier can be extinguished by sneak-right-clicking with an empty hand.");
    this.add("rootsclassic.research.ritual.ritual.page3title", "Basic Casting");
    this.add("rootsclassic.research.ritual.ritual.page3info", "A typical ritual is cast as follows: first, necessary items are placed on the altar, and necessary incense items are ignited. Make sure you don't have any extra ingredients in the altar, or extra incenses in the air, or the ritual will fail. Then, sneak-right-click the altar with an empty hand to begin the ritual.");

    this.add("rootsclassic.research.ritual.living_tools", "Living Tools");
    this.add("rootsclassic.research.ritual.living_tools.page1title", "Self-Repairing");
    this.add("rootsclassic.research.ritual.living_tools.page1info", "Your rituals are capable of channeling life energy into previously dead objects. Using this, you can grow newer, stronger forms for your wooden tools. Adding a little gold for reinforcement, as well as a verdant sprig to grow upon, will grant you tools of similar strength to iron that repair slowly over time.");
    this.add("rootsclassic.research.ritual.living_tools.page2title", "Ritual: Living Pickaxe");
    this.add("rootsclassic.research.ritual.living_tools.page3title", "Ritual: Living Axe");
    this.add("rootsclassic.research.ritual.living_tools.page4title", "Ritual: Living Shovel");
    this.add("rootsclassic.research.ritual.living_tools.page5title", "Ritual: Living Sword");
    this.add("rootsclassic.research.ritual.living_tools.page6title", "Ritual: Living Hoe");

    this.add("rootsclassic.research.ritual.grow", "Growth Ritual");
    this.add("rootsclassic.research.ritual.grow.page1title", "Spark of Life");
    this.add("rootsclassic.research.ritual.grow.page1info", "With the simple life-channeling abilities of the altar, you have devised a ritual that will aid the growth of your crops. This ritual will scan for crops in a 35x35x9 area and randomly apply a bonemeal-like effect to a few of them.");
    this.add("rootsclassic.research.ritual.grow.page2title", "Ritual: Grow");

    this.add("rootsclassic.research.ritual.standing_stones", "Standing Stones");
    this.add("rootsclassic.research.ritual.standing_stones.page1title", "Engraved Stones");
    this.add("rootsclassic.research.ritual.standing_stones.page1info", "In order to gain access to more powerful rituals, you need a better way to channel life energy from the world around you. By carving runes into this stone structure, you give it the much-needed ability to draw in power from the world around it to fuel your magical desires.");

    this.add("rootsclassic.research.ritual.animal_summoning", "Animal Reanimation");
    this.add("rootsclassic.research.ritual.animal_summoning.page1title", "Life Giver");
    this.add("rootsclassic.research.ritual.animal_summoning.page1info", "With the enhanced channeling abilities of the standing stones, you believe that you could imbue the corpse of a dead creature with enough healing energy to bring it back to life. Given the flesh and bones of a passive mob, these rituals will bring that mob back to life on top of the altar.");
    this.add("rootsclassic.research.ritual.animal_summoning.page2title", "Ritual: Pig Summoning");
    this.add("rootsclassic.research.ritual.animal_summoning.page3title", "Ritual: Cow Summoning");
    this.add("rootsclassic.research.ritual.animal_summoning.page4title", "Ritual: Sheep Summoning");
    this.add("rootsclassic.research.ritual.animal_summoning.page5title", "Ritual: Chicken Summoning");
    this.add("rootsclassic.research.ritual.animal_summoning.page6title", "Ritual: Rabbit Summoning");

    this.add("rootsclassic.research.ritual.crystal_staff", "The Crystal Staff");
    this.add("rootsclassic.research.ritual.crystal_staff.page1title", "Reusable Magic");
    this.add("rootsclassic.research.ritual.crystal_staff.page1info", "For too long, you have been bound by the limited number of uses of your typical staff. Using rare crystals from deep within the earth, you believe you have created a way to store spells indefinitely: the crystal staff. Not only can this staff keep spells indefinitely, it can also store up to four at a time.");
    this.add("rootsclassic.research.ritual.crystal_staff.page2title", "Ritual: Crystal Forge");
    this.add("rootsclassic.research.ritual.crystal_staff.page3title", "Adding Spells");
    this.add("rootsclassic.research.ritual.crystal_staff.page3info", "Adding spells to the staff requires a separate ritual. In addition to this ritual's displayed components, you may add up to four additional Spell Powders as incenses. Each of these will be added to the staff, overwriting previous spells if necessary. Simply sneak and right-click to cycle through the staff's spells.");
    this.add("rootsclassic.research.ritual.crystal_staff.page4title", "Ritual: Crystal Imbue");
    this.add("rootsclassic.research.ritual.crystal_staff.page5title", "Limitations");
    this.add("rootsclassic.research.ritual.crystal_staff.page5info", "However, this new staff has one major limitation: it must be used while standing on natural blocks like dirt, grass, leaves, or log blocks. Otherwise, the staff will tap into the user's own life force to cast spells.");

    this.add("rootsclassic.research.ritual.rain_rituals", "Downfall Control");
    this.add("rootsclassic.research.ritual.rain_rituals.page1title", "Weather Magic");
    this.add("rootsclassic.research.ritual.rain_rituals.page1info", "With a few simple natural ingredients, you have discovered two rituals, one that will summon rain to the world and another to banish it.");
    this.add("rootsclassic.research.ritual.rain_rituals.page2title", "Ritual: Summon Rain");
    this.add("rootsclassic.research.ritual.rain_rituals.page3title", "Ritual: Banish Rain");

    this.add("rootsclassic.research.ritual.flare", "Fire Blast");
    this.add("rootsclassic.research.ritual.flare.page1title", "Offensive Rituals");
    this.add("rootsclassic.research.ritual.flare.page1info", "Not all of your rituals are for happy, fun purposes like toggling the rain or bringing your pet pig back to life. The flare ritual uses some fiery materials from the nether as well as some volatile fuels to burn mobs inside a 45x45x17 area around the altar, except for players immediately near the altar.");
    this.add("rootsclassic.research.ritual.flare.page2title", "Ritual: Flare");

    this.add("rootsclassic.research.ritual.standing_stones2", "Enhanced Standing Stones");
    this.add("rootsclassic.research.ritual.standing_stones2.page1title", "The Next Level");
    this.add("rootsclassic.research.ritual.standing_stones2.page1info", "One again, you've hit a wall. Your standing stones are powerful, but you are sure that there is a lot of more powerful magic that still eludes you. With a trip to the nether, however, you have discovered that by using some more energetic materials, you can create stones far more effective at channeling life energy.");

    this.add("rootsclassic.research.ritual.monster_summoning", "Monster Reanimation");
    this.add("rootsclassic.research.ritual.monster_summoning.page1title", "Undeath");
    this.add("rootsclassic.research.ritual.monster_summoning.page1info", "With a more powerful altar, you think you might be able to apply your previous reanimation rituals to some more powerful and sinister targets. By supplying a bone and some related drops, you can summon forth a mob of your choice on top of the altar.");
    this.add("rootsclassic.research.ritual.monster_summoning.page2title", "Ritual: Zombie Summoning");
    this.add("rootsclassic.research.ritual.monster_summoning.page3title", "Ritual: Skeleton Summoning");
    this.add("rootsclassic.research.ritual.monster_summoning.page4title", "Ritual: Spider Summoning");
    this.add("rootsclassic.research.ritual.monster_summoning.page5title", "Ritual: Creeper Summoning");
    this.add("rootsclassic.research.ritual.monster_summoning.page6title", "Ritual: Cave Spider Summoning");
    this.add("rootsclassic.research.ritual.monster_summoning.page7title", "Ritual: Slime Summoning");
    this.add("rootsclassic.research.ritual.monster_summoning.page8title", "Ritual: Enderman Summoning");

    this.add("rootsclassic.research.ritual.sylvan_armor", "Sylvan Armor");
    this.add("rootsclassic.research.ritual.sylvan_armor.page1title", "Druidic Robes");
    this.add("rootsclassic.research.ritual.sylvan_armor.page1info", "With your new, stronger altar, you might want your apparel to follow suit. Using some leather armor as a base, you have managed to create a form of magically-cooperative robe. This equipment doesn't have the best protection, but it will auto-repair and boost the efficiency modifier of your spells by two.");
    this.add("rootsclassic.research.ritual.sylvan_armor.page2title", "Ritual: Sylvan Hood");
    this.add("rootsclassic.research.ritual.sylvan_armor.page3title", "Ritual: Sylvan Robe");
    this.add("rootsclassic.research.ritual.sylvan_armor.page4title", "Ritual: Sylvan Tunic");
    this.add("rootsclassic.research.ritual.sylvan_armor.page5title", "Ritual: Sylvan Boots");

    this.add("rootsclassic.research.ritual.wildwood_armor", "Wildwood Armor");
    this.add("rootsclassic.research.ritual.wildwood_armor.page1title", "Wooden Armor");
    this.add("rootsclassic.research.ritual.wildwood_armor.page1info", "Fancy robes are nice and all, but sometimes you just want raw protection. The wildwood armor, built off of iron armor as a base, satisfies that purpose nicely. It has protection in-between iron and diamond armor, but also auto-repairs and increases your natural regeneration, even when out of food.");
    this.add("rootsclassic.research.ritual.wildwood_armor.page2title", "Wildwood Mask");
    this.add("rootsclassic.research.ritual.wildwood_armor.page3title", "Wildwood Plate");
    this.add("rootsclassic.research.ritual.wildwood_armor.page4title", "Wildwood Leggings");
    this.add("rootsclassic.research.ritual.wildwood_armor.page5title", "Wildwood Boots");

    this.add("rootsclassic.research.ritual.powered_stones", "Energized Stones");
    this.add("rootsclassic.research.ritual.powered_stones.page1title", "Simple Yet Effective");
    this.add("rootsclassic.research.ritual.powered_stones.page1info", "You've used standing stones to channel life energy before, but never on their own. However, using the innate power within an infernal bulb and the regenerative effects of a ritual, you have created standing stones that will perform a simple purpose in an area around them forever, without cost.");
    this.add("rootsclassic.research.ritual.powered_stones.page2title", "Ritual: Accelerator Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page3title", "Accelerator");
    this.add("rootsclassic.research.ritual.powered_stones.page3info", "The accelerator stone will grant all nearby creatures the Speed II potion effect.");
    this.add("rootsclassic.research.ritual.powered_stones.page4title", "Ritual: Entangler Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page5title", "Entangler");
    this.add("rootsclassic.research.ritual.powered_stones.page5info", "The Entangler stone will grant all nearby creatures the Slowness II potion effect.");
    this.add("rootsclassic.research.ritual.powered_stones.page6title", "Ritual: Grower Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page7title", "Grower");
    this.add("rootsclassic.research.ritual.powered_stones.page7info", "The grower stone will slightly boost the growth speed of nearby crops.");
    this.add("rootsclassic.research.ritual.powered_stones.page8title", "Ritual: Healer Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page9title", "Healer");
    this.add("rootsclassic.research.ritual.powered_stones.page9info", "The healer stone will grant the regeneration potion effect to all nearby creatures.");
    this.add("rootsclassic.research.ritual.powered_stones.page10title", "Ritual: Igniter Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page11title", "Igniter");
    this.add("rootsclassic.research.ritual.powered_stones.page11info", "The igniter stone will light nearby creatures on fire for a few seconds.");
    this.add("rootsclassic.research.ritual.powered_stones.page12title", "Ritual: Repulsor Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page13title", "Repulsor");
    this.add("rootsclassic.research.ritual.powered_stones.page13info", "The repulsor stone will push nearby dropped items away from itself.");
    this.add("rootsclassic.research.ritual.powered_stones.page14title", "Ritual: Vacuum Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page15title", "Vacuum");
    this.add("rootsclassic.research.ritual.powered_stones.page15info", "The vacuum stone will pull nearby dropped items in towards itself.");
    this.add("rootsclassic.research.ritual.powered_stones.page16title", "Ritual: Standing Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page17title", "Standing Stone");
    this.add("rootsclassic.research.ritual.powered_stones.page17info", "A basic standing stone. Particles can be colored using Rose red, Lapis Lazuli, Cactus Green and reset using bone meal.");

    this.add("rootsclassic.research.ritual.mass_breeding", "Mass Breeding");
    this.add("rootsclassic.research.ritual.mass_breeding.page1title", "Animal Farm");
    this.add("rootsclassic.research.ritual.mass_breeding.page1info", "By burning common breeding items as incense, you have developed a ritual that will cause all breedable mobs within a 45x45x17 box to enter breeding mode.");
    this.add("rootsclassic.research.ritual.mass_breeding.page2title", "Ritual: Mass Breeding");

    this.add("rootsclassic.research.ritual.life_drain", "Life Drain");
    this.add("rootsclassic.research.ritual.life_drain.page1title", "Taking It Back");
    this.add("rootsclassic.research.ritual.life_drain.page1info", "The life force present in hostile mobs is corrupt and tainted. With this ritual, you have developed a means of taking this life force for more beneficial purposes. This ritual will damage all hostile mobs in a large area, and divide the damage dealt evenly among nearby players in the form of healing.");
    this.add("rootsclassic.research.ritual.life_drain.page2title", "Ritual: Life Drain");

    this.add("rootsclassic.research.ritual.sacrifice", "Sacrifice");
    this.add("rootsclassic.research.ritual.sacrifice.page1title", "A Supernatural Deal");
    this.add("rootsclassic.research.ritual.sacrifice.page1info", "You are simply fed up with going on long adventures searching for specific plants to complete your spells. With this ritual, you have developed a sinister solution. When this ritual finishes its particle light show, it will instantly kill a mob placed near it. In exchange, a random plant may be dropped from the altar. The source of these drops remains unknown to you...");
    this.add("rootsclassic.research.ritual.sacrifice.page2title", "Ritual: Sacrifice");

    this.add("rootsclassic.research.ritual.runic_focus", "Runic Foci");
    this.add("rootsclassic.research.ritual.runic_focus.page1title", "Stored Power");
    this.add("rootsclassic.research.ritual.runic_focus.page1info", "By engraving this stone with some runes, and placing a rare gem at its core, you have created an item that will store up life energy. It can be charged with a ritual at an altar, and then either discharged or incorporated into other items in rituals or other processes.");
    this.add("rootsclassic.research.ritual.runic_focus.page2title", "Ritual: Runic Focus");
    this.add("rootsclassic.research.ritual.runic_focus.page3title", "Charging It Up");
    this.add("rootsclassic.research.ritual.runic_focus.page3info", "To actually charge the focus, you need some energetic dust from the overworld, as well as some energetic dust from the nether. Using the fiery energies of an infernal stem, you can force the energy from the dusts into the focus, along with the life energy that accompanies all rituals.");
    this.add("rootsclassic.research.ritual.runic_focus.page4title", "Ritual: Runic Focus Charging");

    this.add("rootsclassic.research.ritual.engraved_blade", "Engraved Blade");
    this.add("rootsclassic.research.ritual.engraved_blade.page1title", "Modular Weapon");
    this.add("rootsclassic.research.ritual.engraved_blade.page1info", "Using the properties of a Runic Focus, you have created a new weapon. During the crafting of this weapon you can burn up to four different kinds of barks as additional incenses to add different buffs to the sword. You can mix and match these buffs as you please, and they will stack if you put more than one on at a time.");
    this.add("rootsclassic.research.ritual.engraved_blade.page2title", "Ritual: Engraved Blade");
    this.add("rootsclassic.research.ritual.engraved_blade.page3title", "Spikes");
    this.add("rootsclassic.research.ritual.engraved_blade.page3info", "Each acacia bark you burn as incense during the sword's crafting will add some generic damage to the sword.");
    this.add("rootsclassic.research.ritual.engraved_blade.page4title", "Forceful");
    this.add("rootsclassic.research.ritual.engraved_blade.page4info", "Each spruce bark you burn as incense during the sword's crafting will add additional knockback to the sword's attacks.");
    this.add("rootsclassic.research.ritual.engraved_blade.page5title", "Holy");
    this.add("rootsclassic.research.ritual.engraved_blade.page5info", "Each birch bark you burn as incense during the sword's crafting will add some bonus damage whenever the sword attacks undead creatures.");
    this.add("rootsclassic.research.ritual.engraved_blade.page6title", "Aquatic");
    this.add("rootsclassic.research.ritual.engraved_blade.page6info", "Each jungle bark you burn as incense during the sword's crafting will add a small amount of drowning damage to the sword's damage output.");
    this.add("rootsclassic.research.ritual.engraved_blade.page7title", "Shadow Step");
    this.add("rootsclassic.research.ritual.engraved_blade.page7info", "Each dark oak bark you burn as incense during the sword's crafting will increase your chance to dodge attacks made against you when holding the sword.");

    this.add("rootsclassic.research.ritual.time_shift", "Time Shift");
    this.add("rootsclassic.research.ritual.time_shift.page1title", "Timey Wimey Stuff");
    this.add("rootsclassic.research.ritual.time_shift.page1info", "After many hours you have discovered that by burning common time keeping devices you can move time by an amount proportional to the amount of clocks burned as incense.");
    this.add("rootsclassic.research.ritual.time_shift.page2title", "Ritual: Time Shift");

    this.add("rootsclassic.clearpotionsitem.tooltip", "Clears potions when eaten");
    this.add("rootsclassic.healingitem.tooltip", "Heals player when eaten");
    this.add("rootsclassic.poisonitem.tooltip", "Poisonous when eaten");

    this.add("rootsclassic.mortar.invalid", "Recipe invalid; verify items and order");
    this.add("rootsclassic.mortar.disabled", "Recipe disabled");
    this.add("rootsclassic.mortar.mixin", "Recipe found but missing a Rare Material mixin");

    this.add("entity.rootsclassic.skeleton_phantom", "Phantom Skeleton");
    this.add("entity.rootsclassic.tile_accelerator", "Tile Accelerator");
    this.add("entity.rootsclassic.entity_accelerator", "Entity Accelerator");

    this.add("rootsclassic.brazier.burning.added", "Item added");
    this.add("rootsclassic.brazier.burning.empty", "Empty");
    this.add("rootsclassic.brazier.burning.off", "Not Burning");
    this.add("rootsclassic.brazier.burning.on", "Brazier is now burning");

    this.add("rootsclassic.gui.jei.category.mortar", "Mortar");
    this.add("rootsclassic.gui.jei.category.ritual", "Ritual");

    this.add("death.attack.rootsclassic.generic", "%1$s died");
    this.add("death.attack.rootsclassic.generic.player", "%1$s died because of %2$s");
    this.add("death.attack.rootsclassic.fire", "%1$s burned to death");
    this.add("death.attack.rootsclassic.fire.player", "%1$s burned to death because of %2$s");
    this.add("death.attack.rootsclassic.wither", "%1$s withered away");
    this.add("death.attack.rootsclassic.wither.player", "%1$s ithered away because of %2$s");

    this.add("rootsclassic.jei.tooltip.crafting", "");
    this.add("rootsclassic.jei.tooltip.engraved_crafting", "");
    this.add("rootsclassic.jei.tooltip.imbuer", "Imbuer");
    this.add("rootsclassic.jei.tooltip.life_drain", "Life Drain");
    this.add("rootsclassic.jei.tooltip.cause_rain", "Cause Rain");
    this.add("rootsclassic.jei.tooltip.banish_rain", "Banish Rain");
    this.add("rootsclassic.jei.tooltip.sacrifice", "Sacrifice");
    this.add("rootsclassic.jei.tooltip.mass_breeding", "Mass Breeding");
    this.add("rootsclassic.jei.tooltip.flare", "Flare");
    this.add("rootsclassic.jei.tooltip.time_shift", "Shift Time");
    this.add("rootsclassic.jei.tooltip.summoning", "Summons %s");

    this.add("rootsclassic.configuration.dragonsEyeDropChance", "Dragon's Eye Drop Chance");
    this.add("rootsclassic.configuration.disablePVP", "Disable PVP");
    this.add("rootsclassic.configuration.ticksPerManaRegen", "Ticks per Mana Regen");
    this.add("rootsclassic.configuration.staffUsesEfficiency", "Staff Uses Efficiency");
    this.add("rootsclassic.configuration.client", "Client");
    this.add("rootsclassic.configuration.infernalStemDropChance", "Infernal Stem Drop Chance");
    this.add("rootsclassic.configuration.staffUses", "Staff Uses");
    this.add("rootsclassic.configuration.barkKnifeBlockStripChance", "Bark Knife Block Strip Chance");
    this.add("rootsclassic.configuration.oldRootDropChance", "Old Root Drop Chance");
    this.add("rootsclassic.configuration.staffUsesBasic", "Staff Uses Basic");
    this.add("rootsclassic.configuration.staffChargeTicks", "Staff Charge Ticks");
    this.add("rootsclassic.configuration.showTabletWave", "Show Tablet Wave");
    this.add("rootsclassic.configuration.manaBarOffset", "Mana Bar Offset");
    this.add("rootsclassic.configuration.items", "Items");
    this.add("rootsclassic.configuration.berriesDropChance", "Berries Drop Chance");
    this.add("rootsclassic.configuration.efficiencyBonusUses", "Efficiency Bonus Uses");
    this.add("rootsclassic.configuration.magic", "Magic");
    this.add("rootsclassic.configuration.verdantSprigDropChance", "Verdant Sprig Drop Chance");
  }
}