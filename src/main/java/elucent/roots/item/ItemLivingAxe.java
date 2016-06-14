package elucent.roots.item;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLivingAxe extends ItemTool {
	Random random = new Random();
	public ItemLivingAxe(){
		super(RegistryManager.livingMaterial,Sets.newHashSet(new Block[]{Blocks.PLANKS}));
		setUnlocalizedName("livingAxe");
		setCreativeTab(Roots.tab);
		setHarvestLevel("axe",this.toolMaterial.getHarvestLevel());
		this.damageVsEntity = this.toolMaterial.getDamageVsEntity() + 3.0f;
		this.attackSpeed = -3.1f;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected){
		if (random.nextInt(20) == 0){
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
