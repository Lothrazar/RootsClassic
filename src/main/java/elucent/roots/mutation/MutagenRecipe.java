package elucent.roots.mutation;

import java.util.ArrayList;
import java.util.List;

import elucent.roots.RegistryManager;
import elucent.roots.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MutagenRecipe {
	ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
	public IBlockState result = null;
	IBlockState plantBlock = Blocks.AIR.getDefaultState();
	String name = "";
	
	public MutagenRecipe(String name, IBlockState block, IBlockState result){
		this.name = name;
		this.plantBlock = block;
		this.result = result;
	}
	
	public void onCrafted(World world, BlockPos pos, EntityPlayer player){
		//
	}
	
	public MutagenRecipe addIngredient(ItemStack stack){
		inputs.add(stack);
		return this;
	}
	
	public boolean matches(List<ItemStack> items, World world, BlockPos pos, EntityPlayer player){
		if (world.getBlockState(pos).getBlock() == plantBlock.getBlock() && world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) == plantBlock.getBlock().getMetaFromState(plantBlock)){
			ArrayList<ItemStack> tempItems = new ArrayList<ItemStack>();
			tempItems.addAll(items);
			for (int i = 0; i < inputs.size(); i ++){
				boolean endIteration = false;
				for (int j = 0; j < tempItems.size() && !endIteration; j ++){
					if (Util.oreDictMatches(inputs.get(i), tempItems.get(j))){
						tempItems.remove(j);
						endIteration = true;
					}
				}
			}
			return tempItems.size() == 0;
		}
		return false;
	}
}