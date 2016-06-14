package elucent.roots.item;

import java.util.ArrayList;
import java.util.List;

import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.mutation.MutagenManager;
import elucent.roots.mutation.MutagenRecipe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMutagen extends Item {
	public ItemMutagen(){
		super();
		setUnlocalizedName("mutagen");
		setCreativeTab(Roots.tab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
		for (int i = 0; i < 40; i ++){
			double velX = (player.getLookVec().xCoord*0.75)+0.5*(itemRand.nextDouble()-0.5);
			double velY = (player.getLookVec().yCoord*0.75)+0.5*(itemRand.nextDouble()-0.5);
			double velZ = (player.getLookVec().zCoord*0.75)+0.5*(itemRand.nextDouble()-0.5);
			Roots.proxy.spawnParticleMagicFX(world, player.posX+0.5*player.getLookVec().xCoord, player.posY+1.5+0.5*player.getLookVec().yCoord, player.posZ+0.5*player.getLookVec().zCoord, velX, velY, velZ, 142, 62, 56);
		}
		BlockPos pos = Util.getRayTrace(world,player,4);
		List<EntityItem> entityItems = (List<EntityItem>) world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX()-2,pos.getY()-2,pos.getZ()-2,pos.getX()+3,pos.getY()+3,pos.getZ()+3));
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = 0; i < entityItems.size(); i ++){
			items.add((entityItems).get(i).getEntityItem());
		}
		if (items.size() > 0){
			MutagenRecipe recipe = MutagenManager.getRecipe(items, world, pos, player);
			if (recipe != null){
				world.setBlockState(pos, recipe.result);
				for (int i = 0; i < 100; i ++){
					double velX = 1.5*(itemRand.nextDouble()-0.5);
					double velY = 1.5*(itemRand.nextDouble()-0.5);
					double velZ = 1.5*(itemRand.nextDouble()-0.5);
					Roots.proxy.spawnParticleMagicAuraFX(world, pos.getX()+itemRand.nextDouble(), pos.getY()+itemRand.nextDouble(), pos.getZ()+itemRand.nextDouble(), velX, velY, velZ, 142, 62, 56);
				}
				for (int i = 0; i < entityItems.size(); i ++){
					world.removeEntity(entityItems.get(i));
				}
				recipe.onCrafted(world, pos, player);
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
