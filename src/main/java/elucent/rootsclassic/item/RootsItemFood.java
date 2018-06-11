package elucent.rootsclassic.item;

import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RootsItemFood extends ItemFood
{
	public RootsItemFood(String name, int amount, float saturation, boolean isWolFFood){
		super(amount, saturation, isWolFFood);
		this.setCreativeTab(Roots.tab);
		this.setUnlocalizedName(name);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving){
		 super.onItemUseFinish(stack, worldIn, entityLiving);
		 if(stack.getItem() == RegistryManager.redCurrant){
			 entityLiving.heal(2F); 
		 }
		 if(stack.getItem() == RegistryManager.elderBerry){
			 if(!worldIn.isRemote){
				 entityLiving.clearActivePotions();
			 }
		 }
		 if (stack.getItem() == RegistryManager.healingPoultice){
			 entityLiving.heal(5F);
		 }
		 return stack;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
