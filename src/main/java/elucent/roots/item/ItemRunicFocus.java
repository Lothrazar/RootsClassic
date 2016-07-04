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

public class ItemRunicFocus extends Item implements IManaRelatedItem {
	Random random = new Random();
	
	public ItemRunicFocus(){
		super();
		setUnlocalizedName("runicFocus_0");
		this.setCreativeTab(Roots.tab);
	}
	
	@Override
	public int getItemStackLimit(){
		return 1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		if (stack.getItemDamage() == 0){
			return "runicFocus_0";
		}
		if (stack.getItemDamage() == 1){
			return "runicFocus_1";
		}
		return "runicFocus_0";
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName()+"_0","inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName()+"_1","inventory"));
	}
}
