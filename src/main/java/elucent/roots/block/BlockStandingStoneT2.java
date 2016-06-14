package elucent.roots.block;

import java.util.List;

import elucent.roots.Roots;
import elucent.roots.tileentity.TileEntityMortar;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStandingStoneT2 extends Block {
	public static final PropertyEnum topState = PropertyEnum.create("topq", BlockStandingStoneT2.isTop.class);

	public enum isTop implements IStringSerializable {
		enumBottom(0,"bottom"),
	    enumTop(1, "top");
	
	    private int ID;
	    private String name;
	    
	    private isTop(int ID, String name) {
	        this.ID = ID;
	        this.name = name;
	    }
	    
	    public static isTop fromMeta(int meta){
	    	switch(meta){
	    		default: return enumBottom;
	    		case 0: return enumBottom;
	    		case 1: return enumTop;
	    	}
	    }
	    
	    @Override
	    public String getName() {
	        return name;
	    }
	
	    public int getID() {
	        return ID;
	    }
	}
	
	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[]{topState});
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
		isTop TYPE = (isTop)state.getValue(topState);
		return TYPE.getID();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta){
		return getDefaultState().withProperty(topState,isTop.fromMeta(meta));
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state){
		if (this.getMetaFromState(state) == 0){
			world.setBlockState(pos.up(), this.getDefaultState().withProperty(topState, isTop.enumTop));
		}
		else {
			world.setBlockState(pos.down(), this.getStateFromMeta(0));
		}
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		return null;
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (!world.isRemote && !player.capabilities.isCreativeMode){
			world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,new ItemStack(this,1,0)));
		}
		if (this.getMetaFromState(state) == 0){
			world.setBlockToAir(pos.up());
		}
		else {
			world.setBlockToAir(pos.down());
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos){
		if (world.getBlockState(pos.up()) == Blocks.AIR.getDefaultState()){
			return true;
		}
		return false;
	}
	
	public BlockStandingStoneT2(){
		super(Material.ROCK);
		setUnlocalizedName("standingStoneT2");
		setCreativeTab(Roots.tab);
		setHardness(1.0f);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos){
		if (this.getMetaFromState(state) == 0){
			return new AxisAlignedBB(0.25,0,0.25,0.75,1.0,0.75);
		}
		else {
			return new AxisAlignedBB(0.25,0,0.25,0.75,0.75,0.75);
		}
	}
	
	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
}
