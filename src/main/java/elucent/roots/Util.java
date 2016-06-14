package elucent.roots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Util {
	public static Random random = new Random();
	public static ArrayList<IBlockState> oreList = new ArrayList<IBlockState>();
	public static ArrayList<Block> naturalBlocks = new ArrayList<Block>();
	
	public static BlockPos getRayTrace(World world, EntityPlayer player, int reachDistance){
		double x = player.posX;
		double y = player.posY + player.getEyeHeight();
		double z = player.posZ;
		for (int i = 0; i < reachDistance*10.0; i ++){
			x += player.getLookVec().xCoord*0.1;
			y += player.getLookVec().yCoord*0.1;
			z += player.getLookVec().zCoord*0.1;
			if (world.getBlockState(new BlockPos(x,y,z)).getBlock() != Blocks.AIR){
				return new BlockPos(x,y,z);
			}
		}
		return new BlockPos(x,y,z);
	}
	
	public static void initOres(){
		oreList.add(Blocks.IRON_ORE.getDefaultState());
		oreList.add(Blocks.GOLD_ORE.getDefaultState());
		oreList.add(Blocks.DIAMOND_ORE.getDefaultState());
		oreList.add(Blocks.REDSTONE_ORE.getDefaultState());
		oreList.add(Blocks.LAPIS_ORE.getDefaultState());
		oreList.add(Blocks.COAL_ORE.getDefaultState());
	}
	
	public static void initNaturalBlocks(){
		naturalBlocks.add(Blocks.TALLGRASS);
		naturalBlocks.add(Blocks.GRASS);
		naturalBlocks.add(Blocks.GRASS_PATH);
		naturalBlocks.add(Blocks.LEAVES);
		naturalBlocks.add(Blocks.LOG);
		naturalBlocks.add(Blocks.LOG2);
		naturalBlocks.add(Blocks.PLANKS);
		naturalBlocks.add(Blocks.CACTUS);
		naturalBlocks.add(Blocks.WATERLILY);
		naturalBlocks.add(Blocks.WATER);
		naturalBlocks.add(Blocks.FLOWING_WATER);
		naturalBlocks.add(Blocks.RED_FLOWER);
		naturalBlocks.add(Blocks.YELLOW_FLOWER);
	}
	
	public static boolean containsItem(List<ItemStack> list, Item item){
		for (int i = 0; i < list.size(); i ++){
			if (list.get(i) != null){
				if (list.get(i).getItem() == item){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean containsItem(List<ItemStack> list, Block item){
		for (int i = 0; i < list.size(); i ++){
			if (list.get(i) != null){
				if (Block.getBlockFromItem(list.get(i).getItem()) == item){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean containsItem(List<ItemStack> list, Item item, int meta){
		for (int i = 0; i < list.size(); i ++){
			if (list.get(i) != null){
				if (list.get(i).getItem() == item && list.get(i).getMetadata() == meta){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean containsItem(List<ItemStack> list, Block item, int meta){
		for (int i = 0; i < list.size(); i ++){
			if (list.get(i) != null){
				if (Block.getBlockFromItem(list.get(i).getItem()) == item && list.get(i).getMetadata() == meta){
					return true;
				}
			}
		}
		return false;
	}
	
	public static IBlockState getRandomOre(){
		return oreList.get(random.nextInt(oreList.size()));
	}
	
	public static boolean oreDictMatches(ItemStack stack1, ItemStack stack2){
		if (OreDictionary.itemMatches(stack1, stack2, true)){
			return true;
		}
		else {
			int[] oreIds = OreDictionary.getOreIDs(stack1);
			for (int i = 0; i < oreIds.length; i ++){
				if (OreDictionary.containsMatch(true, OreDictionary.getOres(OreDictionary.getOreName(oreIds[i])), stack2)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static int intColor(int r, int g, int b){
		return (r*65536 + g*256 + b);
	}

	public static boolean isNaturalBlock(Block block) {
		for (int i = 0; i < naturalBlocks.size(); i ++){
			if (naturalBlocks.get(i) == block){
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemListsMatchWithSize(List<ItemStack> i1, List<ItemStack> i2){
		ArrayList<ItemStack> recipe = new ArrayList<ItemStack>();
		ArrayList<ItemStack> available = new ArrayList<ItemStack>();
		recipe.addAll(i1);
		available.addAll(i2);
		for (int i = 0; i < recipe.size(); i ++){
			if (recipe.get(i) == null){
				recipe.remove(i);
				i --;
			}
		}
		for (int i = 0; i < available.size(); i ++){
			if (available.get(i) == null){
				available.remove(i);
				i --;
			}
		}
		if (available.size() == recipe.size()){
			for (int j = 0; j < available.size(); j ++){
				boolean endIteration = false;
				for (int i = 0; i < recipe.size() && !endIteration; i ++){
					if (oreDictMatches(available.get(j),recipe.get(i))){
						recipe.remove(i);
						endIteration = true;
					}
				}
			}
		}
		return recipe.size() == 0;
	}
	
	public static boolean itemListsMatch(List<ItemStack> i1, List<ItemStack> i2){
		ArrayList<ItemStack> recipe = new ArrayList<ItemStack>();
		ArrayList<ItemStack> available = new ArrayList<ItemStack>();
		recipe.addAll(i1);
		available.addAll(i2);
		for (int i = 0; i < recipe.size(); i ++){
			if (recipe.get(i) == null){
				recipe.remove(i);
				i --;
			}
		}
		for (int i = 0; i < available.size(); i ++){
			if (available.get(i) == null){
				available.remove(i);
				i --;
			}
		}
		if (available.size() >= recipe.size()){
			for (int j = 0; j < available.size(); j ++){
				boolean endIteration = false;
				for (int i = 0; i < recipe.size() && !endIteration; i ++){
					if (oreDictMatches(available.get(j),recipe.get(i))){
						recipe.remove(i);
						endIteration = true;
					}
				}
			}
		}
		return recipe.size() == 0;
	}
}
