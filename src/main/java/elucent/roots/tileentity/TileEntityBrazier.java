package elucent.roots.tileentity;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import elucent.roots.Roots;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import elucent.roots.item.DustPetal;
import elucent.roots.item.ItemStaff;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityBrazier extends TEBase implements ITickable {
	public ItemStack heldItem = null;
	Random random = new Random();
	public int ticker = 0;
	public boolean burning = false;
	public int progress = 0;
	
	public TileEntityBrazier(){
		super();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if (tag.hasKey("heldItem")){
			heldItem = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("heldItem"));
		}
		if (tag.hasKey("burning")){
			burning = tag.getBoolean("burning");
		}
		if (tag.hasKey("progress")){
			progress = tag.getInteger("progress");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		if (heldItem != null){
			tag.setTag("heldItem", heldItem.writeToNBT(new NBTTagCompound()));
		}
		tag.setBoolean("burning",burning);
		tag.setInteger("progress", progress);
		return tag;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (heldItem != null && !burning){
			if (!world.isRemote){
				world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,heldItem));
			}
		}
		this.invalidate();
	}
	
	public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack playerItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (playerItem == null){
			if (heldItem != null && !burning){
				if (!world.isRemote){
					world.spawnEntityInWorld(new EntityItem(world, pos.getX()+0.5, pos.getY()+1.0, pos.getZ()+0.5, heldItem));
				}
				heldItem = null;
				markDirty();
				return true;
			}
			if (burning){
				if (player.isSneaking()){
					burning = false;
					progress = 0;
					heldItem = null;
					markDirty();
					return true;
				}
			}
		}
		else if (playerItem.getItem() == Items.FLINT_AND_STEEL){
			if (heldItem != null){
				burning = true;
				progress = 2400;
				markDirty();
				return true;
			}
		}
		else {
			if (heldItem == null){
				heldItem = new ItemStack(playerItem.getItem(),1,playerItem.getItemDamage());
				if (playerItem.hasTagCompound()){
					heldItem.setTagCompound(playerItem.getTagCompound());
				}
				playerItem.stackSize --;
				markDirty();
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		ticker += burning == true ? 12.0 : 3.0;
		if (progress > 0){
			progress --;
			if (progress % 2 == 0){
				getWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, getPos().getX()+0.5, getPos().getY()+0.5, getPos().getZ()+0.5, 0, random.nextDouble()*0.0625+0.0625, 0, 0);
			}
			if (progress % 20 == 0){
				getWorld().spawnParticle(EnumParticleTypes.FLAME, getPos().getX()+0.5, getPos().getY()+0.5, getPos().getZ()+0.5, 0, 0, 0, 0);
			}
			if (progress == 0){
				heldItem = null;
				markDirty();
			}
		}
		if (ticker > 360){
			ticker = 0;
		}
	}
}
