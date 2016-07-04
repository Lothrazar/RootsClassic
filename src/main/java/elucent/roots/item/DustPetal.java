package elucent.roots.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.text.TextFormatting;
import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DustPetal extends Item {
	Random random = new Random();
	
	public DustPetal(){
		super();
		setUnlocalizedName("dustPetal");
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged){
		if (slotChanged){
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void createData(ItemStack stack, EntityPlayer player, String effect, ArrayList<ItemStack> items){
		stack.setTagCompound(new NBTTagCompound());
		int potency = 0;
		int efficiency = 0;
		int size = 0;
		stack.getTagCompound().setString("effect", effect);
		for (int i = 0; i < items.size(); i ++){
			if (items.get(i) != null){
				if (items.get(i).getItem() == Items.GLOWSTONE_DUST){
					potency ++;
				}
				if (items.get(i).getItem() == Items.REDSTONE){
					efficiency ++;
				}
				if (items.get(i).getItem() == Items.GUNPOWDER){
					size ++;
				}
			}
		}
		if (potency >= 3 || efficiency >= 3 || size >= 3){
			if (!(player).hasAchievement(RegistryManager.achieveMaxModifiers)){
				PlayerManager.addAchievement(player, RegistryManager.achieveMaxModifiers);
			}
		}
		stack.getTagCompound().setInteger("potency", potency);
		stack.getTagCompound().setInteger("efficiency", efficiency);
		stack.getTagCompound().setInteger("size", size);
	}
	
	@Override
	public int getItemStackLimit(){
		return 1;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			ComponentBase comp = ComponentManager.getComponentFromName(stack.getTagCompound().getString("effect"));
			tooltip.add(TextFormatting.GOLD + I18n.format("roots.tooltip.spelltypeheading.name") + ": " + comp.getTextColor() + comp.getEffectName());
			tooltip.add(TextFormatting.RED + "  +" + stack.getTagCompound().getInteger("potency") + " " + I18n.format("roots.tooltip.spellpotency.name") + ".");
			tooltip.add(TextFormatting.RED + "  +" + stack.getTagCompound().getInteger("efficiency") + " " + I18n.format("roots.tooltip.spellefficiency.name") + ".");
			tooltip.add(TextFormatting.RED + "  +" + stack.getTagCompound().getInteger("size") + " " + I18n.format("roots.tooltip.spellsize.name") + ".");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
