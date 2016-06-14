package elucent.roots.item;

import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDruidKnife extends Item{
	
	Random rnd = new Random();
	
	public ItemDruidKnife(){
		super();
		setUnlocalizedName("druidKnife");
		setCreativeTab(Roots.tab);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
	
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.getBlockState(pos).getBlock() == Blocks.LOG ){
        	if(!worldIn.isRemote){
        		switch(worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos))){
        		case 0:
	        	case 4:
	        	case 8:
        			playerIn.entityDropItem(new ItemStack(RegistryManager.oakTreeBark), 1.f);
        			stack.getTagCompound().setInteger("durability",stack.getTagCompound().getInteger("durability")+1);
        			if(rnd.nextDouble() < 0.3){
        				worldIn.destroyBlock(pos, false);	
        			}
        			break;
        		case 1:
        		case 5:
        		case 9:
        			playerIn.entityDropItem(new ItemStack(RegistryManager.spruceTreeBark), 1.f);
        			stack.getTagCompound().setInteger("durability",stack.getTagCompound().getInteger("durability")+1);
        			if(rnd.nextDouble() < 0.3){
        				worldIn.destroyBlock(pos, false);	
        			}
        			break;
        		case 2:
        		case 6:
        		case 10:
        			playerIn.entityDropItem(new ItemStack(RegistryManager.birchTreeBark), 1.f);
        			stack.getTagCompound().setInteger("durability",stack.getTagCompound().getInteger("durability")+1);
        			if(rnd.nextDouble() < 0.3){
        				worldIn.destroyBlock(pos, false);	
        			}
        			break;
        		case 3:
        		case 7:
        		case 11:
        			playerIn.entityDropItem(new ItemStack(RegistryManager.jungleTreeBark), 1.f);
        			stack.getTagCompound().setInteger("durability",stack.getTagCompound().getInteger("durability")+1);
        			if(rnd.nextDouble() < 0.3){
        				worldIn.destroyBlock(pos, false);	
        			}
        			break;
        		}
        	}
        }else if(worldIn.getBlockState(pos).getBlock() == Blocks.LOG2){
        	if(!worldIn.isRemote){
        		switch(worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos))){
        		case 0:
	        	case 4:
	        	case 8:
	    			playerIn.entityDropItem(new ItemStack(RegistryManager.acaciaTreeBark), 1.f);
	    			stack.getTagCompound().setInteger("durability",stack.getTagCompound().getInteger("durability")+1);
	    			if(rnd.nextDouble() < 0.3){
        				worldIn.destroyBlock(pos, false);	
        			}
        			break;
	        	case 1:
        		case 5:
        		case 9:
	    			playerIn.entityDropItem(new ItemStack(RegistryManager.darkOakTreeBark), 1.f);
	    			stack.getTagCompound().setInteger("durability",stack.getTagCompound().getInteger("durability")+1);
	    			if(rnd.nextDouble() < 0.3){
        				worldIn.destroyBlock(pos, false);	
        			}
        			break;
	        	}
        	}
        }
        
		return EnumActionResult.SUCCESS;
    }
	
	@Override
	public int getItemStackLimit(){
		return 1;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack){
		
		if(stack.hasTagCompound()){
			if(stack.getTagCompound().hasKey("durability")){
				return stack.getTagCompound().getInteger("durability") == 0?1.D:(double)(stack.getTagCompound().getInteger("durability"))*100/64/100;
			}else{
				return 1.D;
			}
		}else{
			return 1.D;
		}
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack){
		return this.getDurabilityForDisplay(stack) == 1.D?false:true;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("durability", 0);
		}
		
		if(stack.getTagCompound().getInteger("durability") == 64){
			EntityPlayer playerIn = (EntityPlayer) entityIn;
			if(playerIn != null){
				playerIn.inventory.deleteStack(stack);
			}
		}
	}
	
}
