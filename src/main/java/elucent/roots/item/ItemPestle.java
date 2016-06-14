package elucent.roots.item;

import elucent.roots.Roots;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPestle extends Item {
	public ItemPestle(){
		super();
		setUnlocalizedName("pestle");
		setCreativeTab(Roots.tab);
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack){
		return new ItemStack(this,1);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
