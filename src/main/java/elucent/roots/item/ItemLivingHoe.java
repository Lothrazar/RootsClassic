package elucent.roots.item;

import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLivingHoe extends ItemHoe {
	Random random = new Random();
	public ItemLivingHoe(){
		super(RegistryManager.livingMaterial);
		setUnlocalizedName("livingHoe");
		setCreativeTab(Roots.tab);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected){
		if (random.nextInt(40) == 0){
			if (stack.getItemDamage() > 0){
				stack.setItemDamage(stack.getItemDamage()-1);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
	
}
