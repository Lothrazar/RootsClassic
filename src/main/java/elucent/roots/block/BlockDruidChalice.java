package elucent.roots.block;

import elucent.roots.Roots;
import elucent.roots.tileentity.TileEntityDruidChalice;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDruidChalice extends Block/* extends TEBlockBase implements ITileEntityProvider*/{
	public BlockDruidChalice(){
		super(Material.WOOD);
		setUnlocalizedName("druidChalice");
		setHardness(0.5f);
		setCreativeTab(Roots.tab);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),0,new ModelResourceLocation(getRegistryName(),"inventory"));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	/*@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos){
		return new AxisAlignedBB(0.4,0,0,0.6,0,0.2);
	}*/
	
	@Override
	public boolean isFullCube(IBlockState state){
		return true;
	}
	/*
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta){
		return new TileEntityDruidChalice();
	}*/
}
