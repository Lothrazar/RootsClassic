package elucent.roots.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import elucent.roots.RegistryManager;
import elucent.roots.Util;

public class ComponentRecipe {
	public String effectResult = "";
	public ArrayList<ItemStack> materials = new ArrayList<ItemStack>();
	
	public static int getModifierCapacity(List<ItemStack> items){
		int maxCapacity = -1;
		for (int i = 0; i < items.size(); i ++){
			if (items.get(i).getItem() == RegistryManager.oldRoot && maxCapacity < 0){
				maxCapacity = 0;
			}
			if (items.get(i).getItem() == RegistryManager.verdantSprig && maxCapacity < 1){
				maxCapacity = 1;
			}
			if (items.get(i).getItem() == RegistryManager.infernalStem && maxCapacity < 2){
				maxCapacity = 2;
			}
			if (items.get(i).getItem() == RegistryManager.dragonsEye && maxCapacity < 3){
				maxCapacity = 3;
			}
		}
		return maxCapacity;
	}
	
	public ComponentRecipe(String result){
		this.effectResult = result;
	}
	
	public ComponentRecipe addIngredient(ItemStack stack){
		this.materials.add(stack);
		return this;
	}
	
	public static int getModifierCount(List<ItemStack> items){
		int count = 0;
		for (int i = 0; i < items.size(); i ++){
			if (items.get(i) != null){
				if (items.get(i).getItem() == Items.GLOWSTONE_DUST){
					count ++;
				}
				if (items.get(i).getItem() == Items.REDSTONE){
					count ++;
				}
				if (items.get(i).getItem() == Items.GUNPOWDER){
					count ++;
				}
			}
		}
		return count;
	}
	
	public boolean matches(List<ItemStack> items){
		ArrayList<ItemStack> tempItems = new ArrayList<ItemStack>();
		tempItems.addAll(items);
		for (int i = 0; i < tempItems.size(); i ++){
			if (tempItems.get(i).getItem() == RegistryManager.oldRoot ||
				tempItems.get(i).getItem() == RegistryManager.verdantSprig ||
				tempItems.get(i).getItem() == RegistryManager.infernalStem ||
				tempItems.get(i).getItem() == RegistryManager.dragonsEye ||
				tempItems.get(i).getItem() == Items.GLOWSTONE_DUST ||
				tempItems.get(i).getItem() == Items.REDSTONE ||
				tempItems.get(i).getItem() == Items.GUNPOWDER){
				tempItems.remove(i);
				i --;
			}
		}
		for (int i = 0; i < materials.size(); i ++){
			boolean endIteration = false;
			for (int j = 0; j < tempItems.size() && !endIteration; j ++){
				if (Util.oreDictMatches(materials.get(i), tempItems.get(j))){
					tempItems.remove(j);
					endIteration = true;
				}
			}
		}
		return tempItems.size() == 0;
	}
}
