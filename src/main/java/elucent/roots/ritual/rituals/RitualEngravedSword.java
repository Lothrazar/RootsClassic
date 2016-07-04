package elucent.roots.ritual.rituals;

import java.util.ArrayList;
import java.util.List;

import elucent.roots.RegistryManager;
import elucent.roots.Util;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualEngravedSword extends RitualCrafting{

	public RitualEngravedSword(String name, double r, double g, double b){
		super(name, r, g, b);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		List<Item> items = new ArrayList<Item>();
		for(ItemStack i: incenses){
			items.add(i.getItem());
		}
		if (Util.itemListsMatchWithSize(inventory, this.ingredients)){
			ItemStack toSpawn = result;
			if (!world.isRemote){
				int mods = 0;
				EntityItem item = new EntityItem(world,pos.getX()+0.5,pos.getY()+1.5,pos.getZ()+0.5,toSpawn);
				item.forceSpawn = true;
				world.spawnEntityInWorld(item);
				ItemStack stack = item.getEntityItem();
				stack.setTagCompound(new NBTTagCompound());
				for(Item i: items){
					if(i == (RegistryManager.acaciaTreeBark) && mods < 4){
						addMod(stack, "spikes");
					}
					if(i == RegistryManager.spruceTreeBark && mods < 4){
						addMod(stack, "forceful");
					}	
					if(i == RegistryManager.birchTreeBark && mods < 4){
						addMod(stack, "holy");
					}	
					if(i == RegistryManager.jungleTreeBark && mods < 4){
						addMod(stack, "aquatic");
					}	
					if(i == RegistryManager.darkOakTreeBark && mods < 4){
						addMod(stack, "shadowstep");
					}	
					mods++;
				}
			}
			inventory.clear();
			world.getTileEntity(pos).markDirty();
		}
	}
	
	public void addMod(ItemStack stack, String name){
			if(stack.getTagCompound().hasKey(name)){
				stack.getTagCompound().setInteger(name, stack.getTagCompound().getInteger(name) + 1);
			} else {
				stack.getTagCompound().setInteger(name, 1);
			}
	}

}
