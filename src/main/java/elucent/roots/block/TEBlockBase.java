package elucent.roots.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.tileentity.TEBase;

public class TEBlockBase extends Block {
	public TEBlockBase(Material mat){
		super(mat);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (world.getTileEntity(pos) instanceof TEBase){
			((TEBase)world.getTileEntity(pos)).breakBlock(world, pos, state, player);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (world.getTileEntity(pos) instanceof TEBase){
			return ((TEBase)world.getTileEntity(pos)).activate(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
		}
		return false;
	}
}
