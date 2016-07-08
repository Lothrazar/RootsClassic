package elucent.roots.tileentity;

import java.util.Random;

import elucent.roots.Roots;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityAestheticStandingStone extends TEBase implements ITickable {
	int ticker = 0;
	int r = 0;
	int g = 0;
	int b = 0;
	
	Random random = new Random();
	public TileEntityAestheticStandingStone(){
		super();
	}
	
	public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if(heldItem != null){
			int amount = 5;		
			if(heldItem.getItem() == Items.DYE && heldItem.getItemDamage() == 1){
				if(r < 255){
					r += amount;
					return true;
				}
			}
			if(heldItem.getItem() == Items.DYE && heldItem.getItemDamage() == 2){
				if(g < 255){
					g += amount;
					return true;
				}
			}
			if(heldItem.getItem() == Items.DYE && heldItem.getItemDamage() == 4){
				if(b < 255){
					b += amount;
					return true;
				}
			}
			if(heldItem.getItem() == Items.DYE && heldItem.getItemDamage() == 15){
				r = 0;
				g = 0;
				b = 0;
				return true;
			}
		}
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		this.r = tag.getInteger("red");
		this.b = tag.getInteger("blue");
		this.g = tag.getInteger("green");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setInteger("red", r);
		tag.setInteger("blue", b);
		tag.setInteger("green", g);
		return tag;
	}
	@Override
	public void update() {
		ticker ++;
		if (ticker % 5 == 0){
			for (double i = 0; i < 720; i += 45.0){
				double xShift = 0.5*Math.sin(Math.PI*(i/360.0));
				double zShift = 0.5*Math.cos(Math.PI*(i/360.0));
				Roots.proxy.spawnParticleMagicAuraFX(this.getWorld(), this.getPos().getX()+0.5+xShift, this.getPos().getY()+0.5, this.getPos().getZ()+0.5+zShift, 0, 0, 0, r, g, b);
			}
		}
	}

}
