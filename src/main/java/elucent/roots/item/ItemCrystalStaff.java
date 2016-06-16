package elucent.roots.item;

import java.util.List;
import java.util.Random;

import net.minecraft.util.text.TextFormatting;

import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrystalStaff extends Item {
	Random random = new Random();
	
	public ItemCrystalStaff(){
		super();
		setUnlocalizedName("crystalStaff");
		setCreativeTab(Roots.tab);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack){
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack){
		return 72000;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft){
		if (timeLeft < (72000-20)){
			if (stack.hasTagCompound()){
				BlockPos pos = new BlockPos(player.posX,player.posY,player.posZ);
				boolean doEffect = false;
				if (Util.isNaturalBlock(world.getBlockState(pos).getBlock()) || Util.isNaturalBlock(world.getBlockState(pos.down()).getBlock()) || Util.isNaturalBlock(world.getBlockState(pos.down(2)).getBlock())){
					doEffect = true;
				}
				else {
					if (player.getHealth() > 1.0){
						player.setHealth(player.getHealth()-1.0f);
						doEffect = true;
					}
				}
				if (doEffect){
					ComponentBase comp = ComponentManager.getComponentFromName(this.getEffect(stack));
					if (comp != null){
						int potency = this.getPotency(stack)+1;
						int efficiency = this.getEfficiency(stack);
						int size = this.getSize(stack);
						if (((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.FEET) != null){
							if (((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemDruidRobes
								&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemDruidRobes
								&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemDruidRobes
								&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemDruidRobes){
								efficiency += 2;
							}
						}
						double xpCost = (comp.xpCost + potency)*(1.0-0.25*(double)efficiency);
						Random random = new Random();
						double cost = 1.0-(1.0/(comp.xpCost));
						if (random.nextDouble()*(1.0+0.5*efficiency) < cost){
							((EntityPlayer)player).getFoodStats().addExhaustion(random.nextInt(comp.xpCost));
						}
						comp.doEffect(world, player, EnumCastType.SPELL, player.posX+3.0*player.getLookVec().xCoord, player.posY+3.0*player.getLookVec().yCoord, player.posZ+3.0*player.getLookVec().zCoord, potency, efficiency, 3.0+2.0*size);
						for (int i = 0 ; i < 90; i ++){
							double offX = random.nextFloat()*0.5-0.25;
							double offY = random.nextFloat()*0.5-0.25;
							double offZ = random.nextFloat()*0.5-0.25;
							double coeff = (offX+offY+offZ)/1.5+0.5;
							double dx = (player.getLookVec().xCoord+offX)*coeff;
							double dy = (player.getLookVec().yCoord+offY)*coeff;
							double dz = (player.getLookVec().zCoord+offZ)*coeff;
							if (random.nextBoolean()){
								Roots.proxy.spawnParticleMagicFX(world, player.posX+dx, player.posY+1.5+dy, player.posZ+dz, dx, dy, dz, comp.primaryColor.xCoord, comp.primaryColor.yCoord, comp.primaryColor.zCoord);
							}
							else {
								Roots.proxy.spawnParticleMagicFX(world, player.posX+dx, player.posY+1.5+dy, player.posZ+dz, dx, dy, dz, comp.secondaryColor.xCoord, comp.secondaryColor.yCoord, comp.secondaryColor.zCoord);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public int getItemStackLimit(){
		return 1;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
		if (stack.hasTagCompound() && !player.isSneaking()){
			player.setActiveHand(hand);
			return new ActionResult(EnumActionResult.PASS, stack);
		}
		else if (stack.hasTagCompound()){
			stack.getTagCompound().setInteger("selected", stack.getTagCompound().getInteger("selected")+1);
			if (stack.getTagCompound().getInteger("selected") > 4){
				stack.getTagCompound().setInteger("selected", 1);
			}
			return new ActionResult(EnumActionResult.FAIL,stack);
		}
		return new ActionResult(EnumActionResult.FAIL,stack);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged){
		if (oldS.hasTagCompound() && newS.hasTagCompound()){
			if (this.getEffect(oldS) != this.getEffect(newS) || oldS.getTagCompound().getInteger("selected") != newS.getTagCompound().getInteger("selected") || slotChanged){
				return true;
			}
		}
		return slotChanged;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){
		if (stack.hasTagCompound()){
			ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString("effect"));
			int potency = stack.getTagCompound().getInteger("potency");
			int efficiency = stack.getTagCompound().getInteger("efficiency");
			int size = stack.getTagCompound().getInteger("size");
			if (comp != null){
				comp.castingAction((EntityPlayer) player, count, potency, efficiency, size);
				if (random.nextBoolean()){	
					Roots.proxy.spawnParticleMagicLineFX(player.getEntityWorld(), player.posX+2.0*(random.nextFloat()-0.5), player.posY+2.0*(random.nextFloat()-0.5)+1.0, player.posZ+2.0*(random.nextFloat()-0.5), player.posX, player.posY+1.0, player.posZ, comp.primaryColor.xCoord, comp.primaryColor.yCoord, comp.primaryColor.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicLineFX(player.getEntityWorld(), player.posX+2.0*(random.nextFloat()-0.5), player.posY+2.0*(random.nextFloat()-0.5)+1.0, player.posZ+2.0*(random.nextFloat()-0.5), player.posX, player.posY+1.0, player.posZ, comp.secondaryColor.xCoord, comp.secondaryColor.yCoord, comp.secondaryColor.zCoord);
				}
			}
		}
	}
	
	public static void createData(ItemStack stack){
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("selected", 1);
		stack.getTagCompound().setInteger("potency1", 0);
		stack.getTagCompound().setInteger("potency2", 0);
		stack.getTagCompound().setInteger("potency3", 0);
		stack.getTagCompound().setInteger("potency4", 0);
		stack.getTagCompound().setInteger("efficiency1", 0);
		stack.getTagCompound().setInteger("efficiency2", 0);
		stack.getTagCompound().setInteger("efficiency3", 0);
		stack.getTagCompound().setInteger("efficiency4", 0);
		stack.getTagCompound().setInteger("size1", 0);
		stack.getTagCompound().setInteger("size2", 0);
		stack.getTagCompound().setInteger("size3", 0);
		stack.getTagCompound().setInteger("size4", 0);
		stack.getTagCompound().setString("effect1", "");
		stack.getTagCompound().setString("effect2", "");
		stack.getTagCompound().setString("effect3", "");
		stack.getTagCompound().setString("effect4", "");
	}
	
	public static void addEffect(ItemStack stack, int slot, String effect, int potency, int efficiency, int size){
		stack.getTagCompound().setString("effect"+slot, effect);
		stack.getTagCompound().setInteger("potency"+slot, potency);
		stack.getTagCompound().setInteger("efficiency"+slot, efficiency);
		stack.getTagCompound().setInteger("size"+slot, size);
	}
	
	public static Integer getPotency(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getInteger("potency"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static Integer getEfficiency(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getInteger("efficiency"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static Integer getSize(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getInteger("size"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static String getEffect(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getString("effect"+stack.getTagCompound().getInteger("selected"));
		}
		return null;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			ComponentBase comp = ComponentManager.getComponentFromName(this.getEffect(stack));
			if (comp != null){
				tooltip.add(TextFormatting.GOLD + "Type: " + comp.getTextColor() + comp.getEffectName());
				tooltip.add(TextFormatting.RED + "  +" + this.getPotency(stack) + " potency.");
				tooltip.add(TextFormatting.RED + "  +" + this.getEfficiency(stack) + " efficiency.");
				tooltip.add(TextFormatting.RED + "  +" + this.getSize(stack) + " size.");
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
	
	public static class ColorHandler implements IItemColor {
		public ColorHandler(){
			//
		}
		@Override
		public int getColorFromItemstack(ItemStack stack, int layer) {
			if (stack.hasTagCompound()){
				if (layer == 2){
					ComponentBase comp = ComponentManager.getComponentFromName(ItemCrystalStaff.getEffect(stack));
					if (comp != null){
						return Util.intColor((int)comp.primaryColor.xCoord,(int)comp.primaryColor.yCoord,(int)comp.primaryColor.zCoord);
					}
				}
				if (layer == 1){
					ComponentBase comp = ComponentManager.getComponentFromName(ItemCrystalStaff.getEffect(stack));
					if (comp != null){
						return Util.intColor((int)comp.secondaryColor.xCoord,(int)comp.secondaryColor.yCoord,(int)comp.secondaryColor.zCoord);
					}
				}
			}
			return Util.intColor(255, 255, 255);
		}
	}
}
