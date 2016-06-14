package elucent.roots.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityDruidChalice extends TEBase{
	
	public TileEntityDruidChalice(){
		super();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		this.invalidate();
	}
	
}
