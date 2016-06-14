package elucent.roots.item;

import elucent.roots.Roots;
import elucent.roots.Util;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGrowthSalve extends Item {
	public ItemGrowthSalve(){
		super();
		setUnlocalizedName("growthSalve");
		setCreativeTab(Roots.tab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
		for (int i = 0; i < 40; i ++){
			double velX = (player.getLookVec().xCoord*0.75)+0.5*(itemRand.nextDouble()-0.5);
			double velY = (player.getLookVec().yCoord*0.75)+0.5*(itemRand.nextDouble()-0.5);
			double velZ = (player.getLookVec().zCoord*0.75)+0.5*(itemRand.nextDouble()-0.5);
			Roots.proxy.spawnParticleMagicFX(world, player.posX+0.5*player.getLookVec().xCoord, player.posY+1.5+0.5*player.getLookVec().yCoord, player.posZ+0.5*player.getLookVec().zCoord, velX, velY, velZ, 39, 232, 55);
		}
		BlockPos pos = Util.getRayTrace(world,player,4);
		for (int i = -2; i < 3; i ++){
			for (int j = -2; j < 3; j ++){
				BlockPos temp = pos.add(i,0,j);
				if (world.getBlockState(temp).getBlock() == Blocks.DIRT){
					if (i == 0 && j == 0){
						world.setBlockState(temp, Blocks.GRASS.getDefaultState());
					}
					else if (itemRand.nextInt(Math.abs(i)+Math.abs(j)) == 0){
						world.setBlockState(temp, Blocks.GRASS.getDefaultState());
					}
				}
			}
		}
		if (!player.capabilities.isCreativeMode){
			stack.stackSize --;
		}
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
