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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityAltar extends TEBase implements ITickable {
	public ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
	public ArrayList<ItemStack> incenses = new ArrayList<ItemStack>();
	Random random = new Random();
	int ticker = 0;
	int progress = 0;
	String ritualName = null;
	RitualBase ritual = null;
	ItemStack resultItem = null;
	
	public TileEntityAltar(){
		super();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		inventory = new ArrayList<ItemStack>();
		if (tag.hasKey("inventory")){
			NBTTagList list = tag.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); i ++){
				inventory.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
			}
		}
		incenses = new ArrayList<ItemStack>();
		if (tag.hasKey("incenses")){
			NBTTagList list = tag.getTagList("incenses", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); i ++){
				incenses.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
			}
		}
		if (tag.hasKey("ritualName")){
			ritualName = tag.getString("ritualName");
			ritual = RitualManager.getRitualFromName(ritualName);
			System.out.println("Ritual name: " + ritualName);
		}
		if (tag.hasKey("progress")){
			progress = tag.getInteger("progress");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		if (inventory.size() > 0){
			NBTTagList list = new NBTTagList();
			for (int i = 0; i < inventory.size(); i ++){;
				if (inventory.get(i) != null){
					list.appendTag(inventory.get(i).writeToNBT(new NBTTagCompound()));
				}
			}
			tag.setTag("inventory",list);
		}
		if (incenses.size() > 0){
			NBTTagList list = new NBTTagList();
			for (int i = 0; i < incenses.size(); i ++){;
				if (incenses.get(i) != null){
					list.appendTag(incenses.get(i).writeToNBT(new NBTTagCompound()));
				}
			}
			tag.setTag("incenses",list);
		}
		if (ritualName != null){
			tag.setString("ritualName", ritualName);
		}
		tag.setInteger("progress", progress);
		return tag;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		for (int i = 0; i < inventory.size(); i ++){
			if (!world.isRemote){
				world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,inventory.get(i)));
			}
		}
		this.invalidate();
	}
	
	public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (heldItem == null && !player.isSneaking()){
			if (inventory.size() > 0){
				if (!world.isRemote){
					world.spawnEntityInWorld(new EntityItem(world, pos.getX()+0.5, pos.getY()+1.0, pos.getZ()+0.5, inventory.remove(inventory.size()-1)));
				}
				else {
					inventory.remove(inventory.size()-1);
				}
				markDirty();
				return true;
			}
		}
		else if (player.isSneaking() && heldItem == null){
			ritualName = null;
			ritual = null;
			for (int i = 0; i < RitualManager.rituals.size(); i ++){
				if (RitualManager.rituals.get(i).matches(getWorld(), getPos())){
					ritualName = RitualManager.rituals.get(i).name;
					ritual = RitualManager.rituals.get(i);
					incenses = RitualManager.getIncenses(world, getPos());
					progress = 200;
					markDirty();
					return true;
				}
			}
			if (ritualName == null){
				if (world.isRemote){
					player.addChatMessage(new TextComponentString(TextFormatting.RED+"No valid ritual structure found!"));
				}
				markDirty();
				return true;
			}
		}
		else {
			if (inventory.size() < 3){
				ItemStack toAdd = new ItemStack(heldItem.getItem(),1,heldItem.getItemDamage());
				if (heldItem.hasTagCompound()){
					toAdd.setTagCompound(heldItem.getTagCompound());
				}
				inventory.add(toAdd);
				heldItem.stackSize --;
				markDirty();
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		ticker += 3.0;
		if (ticker > 360){
			ticker = 0;
		}
		if (progress > 0 && ritual != null){
			progress --;
			if (getWorld().isRemote){
				if (ritual.positions.size() > 0){
					BlockPos pos = ritual.positions.get(random.nextInt(ritual.positions.size())).up().add(getPos().getX(),getPos().getY(),getPos().getZ());
					Roots.proxy.spawnParticleMagicAltarLineFX(getWorld(), pos.getX()+0.5, pos.getY()+0.125, pos.getZ()+0.5, getPos().getX()+0.5, getPos().getY()+0.875, getPos().getZ()+0.5, ritual.color.xCoord, ritual.color.yCoord, ritual.color.zCoord);
				}
				Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX()+0.5, getPos().getY()+0.875, getPos().getZ()+0.5, 0.125*Math.sin(Math.toRadians(360.0*(progress % 100)/100.0)), 0, 0.125*Math.cos(Math.toRadians(360.0*(progress % 100)/100.0)), ritual.color.xCoord, ritual.color.yCoord, ritual.color.zCoord);
				Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX()+0.5, getPos().getY()+0.875, getPos().getZ()+0.5, 0.125*Math.sin(Math.toRadians(90.0+360.0*(progress % 100)/100.0)), 0, 0.125*Math.cos(Math.toRadians(90.0+360.0*(progress % 100)/100.0)), ritual.color.xCoord, ritual.color.yCoord, ritual.color.zCoord);
				Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX()+0.5, getPos().getY()+0.875, getPos().getZ()+0.5, 0.125*Math.sin(Math.toRadians(180.0+360.0*(progress % 100)/100.0)), 0, 0.125*Math.cos(Math.toRadians(180.0+360.0*(progress % 100)/100.0)), ritual.color.xCoord, ritual.color.yCoord, ritual.color.zCoord);
				Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX()+0.5, getPos().getY()+0.875, getPos().getZ()+0.5, 0.125*Math.sin(Math.toRadians(270.0+360.0*(progress % 100)/100.0)), 0, 0.125*Math.cos(Math.toRadians(270.0+360.0*(progress % 100)/100.0)), ritual.color.xCoord, ritual.color.yCoord, ritual.color.zCoord);
			}
			if (progress % 40 == 0){
				incenses = RitualManager.getIncenses(getWorld(), getPos());
				boolean doesMatch = false;
				for (int i = 0; i < RitualManager.rituals.size(); i ++){
					if (RitualManager.rituals.get(i).matches(getWorld(), getPos())){
						doesMatch = true;
					}
				}
				if (!doesMatch){
					ritual = null;
					ritualName = null;
				}
			}
			if (progress == 0){
				ritual.doEffect(getWorld(),getPos(),inventory,incenses);
				ritualName = null;
				ritual = null;
				markDirty();
			}
		}
	}
}
