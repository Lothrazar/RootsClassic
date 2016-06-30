package elucent.roots.item;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRootyStew extends ItemFood
{
	public ItemRootyStew(){
		super(7, 1.0F, false);
		this.setCreativeTab(Roots.tab);
		this.setUnlocalizedName("rootyStew");
	}
	
	@Override
	public int getItemStackLimit(){
		return 1;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving){
		 super.onItemUseFinish(stack, worldIn, entityLiving);
		 stack.stackSize = 1;
		 stack.setItem(Items.BOWL);
		 return stack;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
