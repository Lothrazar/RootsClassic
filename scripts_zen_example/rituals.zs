import mods.rootsclassic.Ritual;
import crafttweaker.api.resource.ResourceLocation;
import crafttweaker.api.item.ItemStack;

Ritual.setRitualIngredients(<resource:rootsclassic:living_sword_crafting>, [<item:minecraft:dirt>,<item:minecraft:stone>]);
Ritual.setRitualIncense(<resource:rootsclassic:living_sword_crafting>, [<item:minecraft:grass>,<item:minecraft:sand>]);

//unique name, recipe output, level
// r,g,b colours
// incense list, ingredient list

//level zero means no standing stones, only Altar+Brazier
Ritual.addCraftingRitual("simple", <item:minecraft:prismarine_shard>, 0,
		 51,51,255,
 [<item:minecraft:dirt>,<item:minecraft:dirt>], [<item:minecraft:string>,<item:minecraft:emerald>] );

//level 1 means the
Ritual.addCraftingRitual("test1", <item:minecraft:netherrack>, 1,
		 255,255,0,
 [<item:minecraft:grass>,<item:minecraft:grass>], [<item:minecraft:lapis_lazuli>,<item:minecraft:string>,<item:minecraft:emerald> ] );

//level 2 is the biggest ritual with the Attuned stones
Ritual.addCraftingRitual("test2", <item:minecraft:ghast_tear>, 2,
		 255,0,0,
 [<item:minecraft:feather>,<item:minecraft:egg>], [<item:minecraft:diamond>,<item:minecraft:string>,<item:minecraft:emerald> ] );
 
// other examples
Ritual.setPrimaryColor(<resource:crafttweaker:test2>, 255,0,0); 
Ritual.setSecondaryColor(<resource:rootsclassic:igniter_stone_crafting>, 255,0,100);

//change level for the standing stone requirements
Ritual.setLevel(<resource:rootsclassic:igniter_stone_crafting>, 0);