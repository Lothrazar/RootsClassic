package elucent.roots.item;

import java.util.List;
import java.util.Random;

import net.minecraft.util.text.TextFormatting;
import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.capability.RootsCapabilityManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStaff extends Item implements IManaRelatedItem {
	Random random = new Random();
	
	public ItemStaff(){
		super();
		setUnlocalizedName("staff");
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
	public double getDurabilityForDisplay(ItemStack stack){
		if (stack.hasTagCompound()){
			return 1.0-(double)stack.getTagCompound().getInteger("uses")/(double)stack.getTagCompound().getInteger("maxUses");
		}
		return 1.0;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getInteger("uses") < stack.getTagCompound().getInteger("maxUses")){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft){
		if (timeLeft < (72000-12)){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().getInteger("uses") >= 0){
					stack.getTagCompound().setInteger("uses", stack.getTagCompound().getInteger("uses") - 1);
					ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString("effect"));
					int potency = stack.getTagCompound().getInteger("potency");
					int efficiency = stack.getTagCompound().getInteger("efficiency");
					int size = stack.getTagCompound().getInteger("size");
					if (((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null
						&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null
						&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null
						&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.FEET) != null){
						if (((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemDruidRobes
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemDruidRobes
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemDruidRobes
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemDruidRobes){
							potency += 1;
						}
					}
					if (((EntityPlayer)player).hasCapability(RootsCapabilityManager.manaCapability, null) && ((EntityPlayer)player).getCapability(RootsCapabilityManager.manaCapability, null).getMana() >= ((float)comp.xpCost)/(efficiency+1)){
						((EntityPlayer)player).getCapability(RootsCapabilityManager.manaCapability, null).setMana(((EntityPlayer)player).getCapability(RootsCapabilityManager.manaCapability, null).getMana()-(((float)comp.xpCost)/(efficiency+1)));
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
		if (Minecraft.getMinecraft().currentScreen == null){
			player.setActiveHand(hand);
			return new ActionResult(EnumActionResult.PASS, stack);
		}
		return new ActionResult(EnumActionResult.FAIL, stack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getInteger("uses") <= 0){
				if (entity instanceof EntityPlayer){
					((EntityPlayer)entity).inventory.setInventorySlotContents(slot, null);
				}
			}
		}
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged){
		if (oldS.hasTagCompound() && newS.hasTagCompound()){
			if (oldS.getTagCompound().getString("effect") != newS.getTagCompound().getString("effect")){
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
	
	public static void createData(ItemStack stack, String effect, int potency, int efficiency, int size){
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("effect", effect);
		stack.getTagCompound().setInteger("potency", potency);
		stack.getTagCompound().setInteger("efficiency", efficiency);
		stack.getTagCompound().setInteger("size", size);
		stack.getTagCompound().setInteger("uses", 65+32*efficiency);
		stack.getTagCompound().setInteger("maxUses", 65+32*efficiency);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString("effect"));
			tooltip.add(TextFormatting.GOLD + I18n.format("roots.tooltip.spelltypeheading.name") + ": " + comp.getTextColor() + comp.getEffectName());
			tooltip.add(TextFormatting.RED + "  +" + stack.getTagCompound().getInteger("potency") + " " + I18n.format("roots.tooltip.spellpotency.name") + ".");
			tooltip.add(TextFormatting.RED + "  +" + stack.getTagCompound().getInteger("efficiency") + " " + I18n.format("roots.tooltip.spellefficiency.name") + ".");
			tooltip.add(TextFormatting.RED + "  +" + stack.getTagCompound().getInteger("size") + " " + I18n.format("roots.tooltip.spellsize.name") + ".");
			tooltip.add("");
			tooltip.add(TextFormatting.GOLD + Integer.toString(stack.getTagCompound().getInteger("uses")) + " " + I18n.format("roots.tooltip.usesremaining.name") + ".");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName()+"_0","inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName()+"_1","inventory"));
	}
	
	public static class ColorHandler implements IItemColor {
		public ColorHandler(){
			//
		}
		@Override
		public int getColorFromItemstack(ItemStack stack, int layer) {
			if (stack.hasTagCompound() && stack.getItem() instanceof ItemStaff){
				if (layer == 2){
					ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString("effect"));
					return Util.intColor((int)comp.primaryColor.xCoord,(int)comp.primaryColor.yCoord,(int)comp.primaryColor.zCoord);
				}
				if (layer == 1){
					ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString("effect"));
					return Util.intColor((int)comp.secondaryColor.xCoord,(int)comp.secondaryColor.yCoord,(int)comp.secondaryColor.zCoord);
				}
			}
			return Util.intColor(255, 255, 255);
		}
	}
}
