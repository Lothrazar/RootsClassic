package elucent.roots.tileentity;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import net.minecraft.util.text.TextFormatting;

import elucent.roots.Roots;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import elucent.roots.item.DustPetal;
import elucent.roots.item.ItemStaff;
import elucent.roots.ritual.RitualBase;
import elucent.roots.ritual.RitualManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityStandingStoneVacuum extends TEBase implements ITickable {
	int ticker = 0;
	Random random = new Random();
	public TileEntityStandingStoneVacuum(){
		super();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		return tag;
	}

	@Override
	public void update() {
		ticker ++;
		if (ticker % 5 == 0){
			for (double i = 0; i < 720; i += 45.0){
				double xShift = 0.5*Math.sin(Math.PI*(i/360.0));
				double zShift = 0.5*Math.cos(Math.PI*(i/360.0));
				Roots.proxy.spawnParticleMagicAuraFX(this.getWorld(), this.getPos().getX()+0.5+xShift, this.getPos().getY()+0.5, this.getPos().getZ()+0.5+zShift, 0, 0, 0, 255, 32, 160);
			}
		}
		ArrayList<EntityItem> nearbyItems = (ArrayList<EntityItem>)this.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.getPos().getX()-5,this.getPos().getY()-5,this.getPos().getZ()-5,this.getPos().getX()+6,this.getPos().getY()+6,this.getPos().getZ()+6));
		if (nearbyItems.size() > 0){
			for (int i = 0; i < nearbyItems.size(); i ++){
				if (Math.max(Math.abs(nearbyItems.get(i).posX-(this.getPos().getX()+0.5)),Math.abs(nearbyItems.get(i).posZ-(this.getPos().getZ()+0.5))) > 1.0){
					Vec3d v = new Vec3d(nearbyItems.get(i).posX-(this.getPos().getX()+0.5),0,nearbyItems.get(i).posZ-(this.getPos().getZ()+0.5));
					v.normalize();
					nearbyItems.get(i).motionX = -v.xCoord*0.05;
					nearbyItems.get(i).motionZ = -v.zCoord*0.05;
				}
			}
		}
	}
}
