package elucent.roots.research;

import java.util.ArrayList;

import elucent.roots.component.ComponentRecipe;
import elucent.roots.ritual.RitualBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class ResearchPage {
	public ArrayList<ItemStack> craftingRecipe = new ArrayList<ItemStack>();
	public ComponentRecipe mortarRecipe = null;
	public RitualBase altarRecipe = null;
	public ArrayList<ItemStack> smeltingRecipe = new ArrayList<ItemStack>();
	public EnumRecipeType recipe = EnumRecipeType.TYPE_NULL;
	public ItemStack displayItem = null;
	public ArrayList<String> info = new ArrayList<String>();
	public String title = "";
	
	public ResearchPage(){
	}
	
	public ArrayList<String> makeLines(String s){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();
		String temp = "";
		int counter = 0;
		for (int i = 0; i < s.length(); i ++){
			temp += s.charAt(i);
			if (s.charAt(i) == ' '){
				words.add(temp);
				temp = "";
			}
		}
		words.add(temp);
		temp = "";
		for (int i = 0; i < words.size(); i ++){
			counter += Minecraft.getMinecraft().fontRendererObj.getStringWidth(words.get(i));
			if (counter > 160){
				list.add(temp);
				temp = words.get(i);
				counter = Minecraft.getMinecraft().fontRendererObj.getStringWidth(words.get(i));
			}
			else {
				temp += words.get(i);
			}
		}
		list.add(temp);
		return list;
	}
	
	public ResearchPage addCraftingRecipe(ItemStack stack1, ItemStack stack2, ItemStack stack3, ItemStack stack4, ItemStack stack5, ItemStack stack6, ItemStack stack7, ItemStack stack8, ItemStack stack9, ItemStack result){
		recipe = EnumRecipeType.TYPE_CRAFTING;
		craftingRecipe.add(stack1);
		craftingRecipe.add(stack2);
		craftingRecipe.add(stack3);
		craftingRecipe.add(stack4);
		craftingRecipe.add(stack5);
		craftingRecipe.add(stack6);
		craftingRecipe.add(stack7);
		craftingRecipe.add(stack8);
		craftingRecipe.add(stack9);
		craftingRecipe.add(result);
		return this;
	}
	
	public ResearchPage addDisplayItem(ItemStack stack){
		recipe = EnumRecipeType.TYPE_DISPLAY;
		displayItem = stack;
		return this;
	}
	
	public ResearchPage addSmeltingRecipe(ItemStack input, ItemStack result){
		recipe = EnumRecipeType.TYPE_SMELTING;
		smeltingRecipe.add(input);
		smeltingRecipe.add(result);
		return this;
	}
	
	public ResearchPage addMortarRecipe(ComponentRecipe component){
		recipe = EnumRecipeType.TYPE_MORTAR;
		mortarRecipe = component;
		return this;
	}
	
	public ResearchPage addAltarRecipe(RitualBase ritual){
		recipe = EnumRecipeType.TYPE_ALTAR;
		altarRecipe = ritual;
		return this;
	}
}
