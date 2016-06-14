package elucent.roots.component;

import java.util.ArrayList;
import java.util.List;

import elucent.roots.RegistryManager;
import elucent.roots.component.components.ComponentAllium;
import elucent.roots.component.components.ComponentApple;
import elucent.roots.component.components.ComponentAzureBluet;
import elucent.roots.component.components.ComponentBlueOrchid;
import elucent.roots.component.components.ComponentChorus;
import elucent.roots.component.components.ComponentDandelion;
import elucent.roots.component.components.ComponentFlareOrchid;
import elucent.roots.component.components.ComponentLilac;
import elucent.roots.component.components.ComponentLilyPad;
import elucent.roots.component.components.ComponentMidnightBloom;
import elucent.roots.component.components.ComponentNetherWart;
import elucent.roots.component.components.ComponentOrangeTulip;
import elucent.roots.component.components.ComponentOxeyeDaisy;
import elucent.roots.component.components.ComponentPeony;
import elucent.roots.component.components.ComponentPinkTulip;
import elucent.roots.component.components.ComponentPoisonousPotato;
import elucent.roots.component.components.ComponentPoppy;
import elucent.roots.component.components.ComponentRadiantDaisy;
import elucent.roots.component.components.ComponentRedTulip;
import elucent.roots.component.components.ComponentRose;
import elucent.roots.component.components.ComponentSunflower;
import elucent.roots.component.components.ComponentWhiteTulip;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ComponentManager {
	public static ArrayList<ComponentBase> components = new ArrayList<ComponentBase>();
	public static ArrayList<ComponentRecipe> recipes = new ArrayList<ComponentRecipe>();
	
	public static void init(){
		components.add(new ComponentRose().setPrimaryColor(192, 0, 72).setSecondaryColor(0, 200, 48).setTextColor(TextFormatting.GREEN));
		components.add(new ComponentDandelion().setPrimaryColor(255,217,102).setSecondaryColor(240,159,10).setTextColor(TextFormatting.YELLOW));
		components.add(new ComponentChorus().setPrimaryColor(95,57,95).setSecondaryColor(225,215,225).setTextColor(TextFormatting.DARK_PURPLE));
		components.add(new ComponentNetherWart().setPrimaryColor(255,76,36).setSecondaryColor(255,174,0).setTextColor(TextFormatting.GOLD));
		components.add(new ComponentPeony().setPrimaryColor(255,102,178).setSecondaryColor(255,51,153).setTextColor(TextFormatting.DARK_PURPLE));
		components.add(new ComponentSunflower().setPrimaryColor(255,255,128).setSecondaryColor(255,255,255).setTextColor(TextFormatting.WHITE));
		components.add(new ComponentLilac().setPrimaryColor(112,80,112).setSecondaryColor(0,112,24).setTextColor(TextFormatting.GREEN));
		components.add(new ComponentAzureBluet().setPrimaryColor(240,240,255).setSecondaryColor(86,86,96).setTextColor(TextFormatting.GRAY));
		components.add(new ComponentAllium().setPrimaryColor(255,202,255).setSecondaryColor(51,30,50).setTextColor(TextFormatting.DARK_PURPLE));
		components.add(new ComponentWhiteTulip().setPrimaryColor(255,255,255).setSecondaryColor(136,252,255).setTextColor(TextFormatting.AQUA));
		components.add(new ComponentRedTulip().setPrimaryColor(128,16,16).setSecondaryColor(128,16,64).setTextColor(TextFormatting.DARK_RED));
		components.add(new ComponentPoppy().setPrimaryColor(255,0,0).setSecondaryColor(50,50,50).setTextColor(TextFormatting.RED));
		components.add(new ComponentBlueOrchid().setPrimaryColor(68,39,26).setSecondaryColor(162,153,150).setTextColor(TextFormatting.GRAY));
		components.add(new ComponentPoisonousPotato().setPrimaryColor(172,255,81).setSecondaryColor(81,181,255).setTextColor(TextFormatting.YELLOW));
		components.add(new ComponentOrangeTulip().setPrimaryColor(255,181,70).setSecondaryColor(255,255,0).setTextColor(TextFormatting.GOLD));
		components.add(new ComponentPinkTulip().setPrimaryColor(255,0,51).setSecondaryColor(255,0,249).setTextColor(TextFormatting.LIGHT_PURPLE));
		components.add(new ComponentOxeyeDaisy().setPrimaryColor(255,254,206).setSecondaryColor(52,0,74).setTextColor(TextFormatting.WHITE));
		components.add(new ComponentLilyPad().setPrimaryColor(36,255,167).setSecondaryColor(8,0,255).setTextColor(TextFormatting.BLUE));
		components.add(new ComponentApple().setPrimaryColor(255,43,89).setSecondaryColor(255,43,43).setTextColor(TextFormatting.DARK_RED));
		components.add(new ComponentMidnightBloom().setPrimaryColor(12,6,36).setSecondaryColor(18,18,18).setTextColor(TextFormatting.DARK_PURPLE));
		components.add(new ComponentFlareOrchid().setPrimaryColor(255,60,18).setSecondaryColor(255,60,18).setTextColor(TextFormatting.RED));
		components.add(new ComponentRadiantDaisy().setPrimaryColor(255,255,255).setSecondaryColor(255,255,255).setTextColor(TextFormatting.WHITE));
		
		recipes.add(new ComponentRecipe("rosebush")
					.addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,4))
					.addIngredient(new ItemStack(Items.FERMENTED_SPIDER_EYE,1))
					.addIngredient(new ItemStack(Items.BONE,1)));
		recipes.add(new ComponentRecipe("dandelion")
					.addIngredient(new ItemStack(Blocks.YELLOW_FLOWER,1))
					.addIngredient(new ItemStack(Items.STRING,1))
					.addIngredient(new ItemStack(Items.FEATHER,1)));
		recipes.add(new ComponentRecipe("chorus")
					.addIngredient(new ItemStack(Items.CHORUS_FRUIT,1))
					.addIngredient(new ItemStack(Items.ENDER_PEARL,1))
					.addIngredient(new ItemStack(Items.DYE,1,5))
					.addIngredient(new ItemStack(Items.CHORUS_FRUIT_POPPED,1)));
		recipes.add(new ComponentRecipe("netherwart")
					.addIngredient(new ItemStack(Items.NETHER_WART,1,0))
					.addIngredient(new ItemStack(Items.BLAZE_POWDER,1,0))
					.addIngredient(new ItemStack(Items.COAL,1,0))
					.addIngredient(new ItemStack(Items.COAL,1,1)));
		recipes.add(new ComponentRecipe("peony")
					.addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,5))
					.addIngredient(new ItemStack(Items.MELON,1))
					.addIngredient(new ItemStack(Items.DYE,1,1)));
		recipes.add(new ComponentRecipe("sunflower")
					.addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,0))
					.addIngredient(new ItemStack(Blocks.LIT_PUMPKIN,1))
					.addIngredient(new ItemStack(Items.DYE,1,15)));
		recipes.add(new ComponentRecipe("azurebluet")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,3))
					.addIngredient(new ItemStack(Items.IRON_INGOT,1))
					.addIngredient(new ItemStack(Blocks.SAPLING,1,1)));
		recipes.add(new ComponentRecipe("allium")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,2))
					.addIngredient(new ItemStack(Blocks.SOUL_SAND,1))
					.addIngredient(new ItemStack(Blocks.BROWN_MUSHROOM,1)));
		recipes.add(new ComponentRecipe("lilac")
					.addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,1))
					.addIngredient(new ItemStack(Items.WHEAT,1))
					.addIngredient(new ItemStack(Items.BEETROOT,1))
					.addIngredient(new ItemStack(Blocks.VINE,1)));
		recipes.add(new ComponentRecipe("whitetulip")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,6))
					.addIngredient(new ItemStack(Items.SNOWBALL,1))
					.addIngredient(new ItemStack(Blocks.SAPLING,1,2)));
		recipes.add(new ComponentRecipe("redtulip")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,4))
					.addIngredient(new ItemStack(Items.NETHERBRICK,1))
					.addIngredient(new ItemStack(Items.QUARTZ,1))
					.addIngredient(new ItemStack(Blocks.SAND,1,1)));
		recipes.add(new ComponentRecipe("blueorchid")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,1))
					.addIngredient(new ItemStack(Items.PRISMARINE_SHARD,1))
					.addIngredient(new ItemStack(Items.DYE,1,4))
					.addIngredient(new ItemStack(Blocks.VINE,1)));
		recipes.add(new ComponentRecipe("poppy")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,0))
					.addIngredient(new ItemStack(Blocks.RED_MUSHROOM,1))
					.addIngredient(new ItemStack(Items.BEEF,1)));
		recipes.add(new ComponentRecipe("poisonouspotato")
					.addIngredient(new ItemStack(Items.POISONOUS_POTATO,1,0))
					.addIngredient(new ItemStack(Blocks.GLASS,1))
					.addIngredient(new ItemStack(Blocks.GLOWSTONE,1))
					.addIngredient(new ItemStack(Items.REEDS,1)));
		recipes.add(new ComponentRecipe("orangetulip")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,5))
					.addIngredient(new ItemStack(Blocks.SPONGE,1))
					.addIngredient(new ItemStack(Items.LEATHER,1)));
		recipes.add(new ComponentRecipe("pinktulip")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,7))
					.addIngredient(new ItemStack(Items.MELON_SEEDS,1))
					.addIngredient(new ItemStack(Items.PORKCHOP,1))
					.addIngredient(new ItemStack(Blocks.STONE,1,1)));
		recipes.add(new ComponentRecipe("oxeyedaisy")
					.addIngredient(new ItemStack(Blocks.RED_FLOWER,1,8))
					.addIngredient(new ItemStack(Blocks.END_STONE,1))
					.addIngredient(new ItemStack(Items.GOLD_INGOT,1))
					.addIngredient(new ItemStack(Items.SUGAR,1)));
		recipes.add(new ComponentRecipe("lilypad")
					.addIngredient(new ItemStack(Blocks.WATERLILY,1))
					.addIngredient(new ItemStack(Blocks.SPONGE,1,1))
					.addIngredient(new ItemStack(Items.BEETROOT,1)));
		recipes.add(new ComponentRecipe("apple")
					.addIngredient(new ItemStack(Items.APPLE,1))
					.addIngredient(new ItemStack(Items.SPECKLED_MELON,1))
					.addIngredient(new ItemStack(Items.GOLDEN_APPLE,1)));
		recipes.add(new ComponentRecipe("midnightbloom")
				.addIngredient(new ItemStack(RegistryManager.midnightBloom,1))
				.addIngredient(new ItemStack(Blocks.OBSIDIAN,1))
				.addIngredient(new ItemStack(Items.DRAGON_BREATH,1))
				.addIngredient(new ItemStack(Items.DIAMOND,1)));
		recipes.add(new ComponentRecipe("flareorchid")
				.addIngredient(new ItemStack(RegistryManager.flareOrchid,1))
				.addIngredient(new ItemStack(Blocks.NETHERRACK,1))
				.addIngredient(new ItemStack(Items.MAGMA_CREAM,1))
				.addIngredient(new ItemStack(Items.BLAZE_ROD,1)));
		recipes.add(new ComponentRecipe("radiantdaisy")
				.addIngredient(new ItemStack(RegistryManager.radiantDaisy,1))
				.addIngredient(new ItemStack(Blocks.GLOWSTONE,1))
				.addIngredient(new ItemStack(Blocks.GLOWSTONE,1))
				.addIngredient(new ItemStack(Blocks.REDSTONE_BLOCK,1)));
	}
	
	public static boolean isValidEffectItem(ItemStack stack){
		for (int i = 0; i < components.size(); i ++){
			if (components.get(i).getItem() != null && stack != null){
				if (components.get(i).getItem().getItem() == stack.getItem()
					&& components.get(i).getItem().getMetadata() == stack.getMetadata()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static ComponentRecipe getRecipe(String name){
		for (int i = 0; i < recipes.size(); i ++){
			if (recipes.get(i).effectResult == name){
				return recipes.get(i);
			}
		}
		return null;
	}
	
	public static ComponentRecipe getRecipe(List<ItemStack> items){
		for (int i = 0; i < recipes.size(); i ++){
			if (recipes.get(i).matches(items)){
				return recipes.get(i);
			}
		}
		return null;
	}
	
	public static ItemStack getItemFromComponent(ComponentBase component){
		for (int i = 0; i < components.size(); i ++){
			if (components.get(i).getName() == component.getName()){
				return components.get(i).itemSource;
			}
		}
		return null;
	}
	
	public static ComponentBase getComponentFromName(String name){
		for (int i = 0; i < components.size(); i ++){
			if (components.get(i).getName().equals(name)){
				return components.get(i);
			}
		}
		return null;
	}
	
	public static ComponentBase getComponentFromItem(ItemStack stack){
		for (int i = 0; i < components.size(); i ++){
			if (components.get(i).getItem() != null && stack != null){
				if (components.get(i).getItem().getItem() == stack.getItem()
					&& components.get(i).getItem().getMetadata() == stack.getMetadata()){
					return components.get(i);
				}
			}
		}
		return null;
	}
}
