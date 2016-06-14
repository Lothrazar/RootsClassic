package elucent.roots.research;

import java.util.ArrayList;

import elucent.roots.component.ComponentRecipe;
import elucent.roots.ritual.RitualBase;
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
	
	public ResearchPage(String title){
		this.title = title;
	}
	
	public ResearchPage addInfo(String s){
		info.add(s);
		return this;
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
