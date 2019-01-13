import mods.rootsclassic.Ritual;


Ritual.setRitualIngredients("livingSwordCrafting", [<minecraft:dirt>,<minecraft:stone>]);
Ritual.setRitualIncense("livingSwordCrafting", [<minecraft:grass>,<minecraft:sand>]);

//crafting rituals
//unique name, recipe output, level
// r,g,b colours
// incense list, ingredient list

//level zero means no standing stones, only Altar+Brazier
Ritual.addCraftingRitual("simple", <minecraft:prismarine_shard>, 0, 
	 51,51,255,
	[<minecraft:dirt>,<minecraft:dirt>], [<minecraft:string>,<minecraft:emerald>]);

//level 1 means the 
Ritual.addCraftingRitual("test1", <minecraft:netherrack>, 1, 
	 255,255,0,
	[<minecraft:grass>,<minecraft:grass>], [<minecraft:dye:4>,<minecraft:string>,<minecraft:emerald> ]);

//level 2 is the biggest ritual with the Attuned stones
Ritual.addCraftingRitual("test2", <minecraft:ghast_tear>, 2, 
	 255,0,0,
	[<minecraft:feather>,<minecraft:egg>], [<minecraft:diamond>,<minecraft:string>,<minecraft:emerald> ]);
 