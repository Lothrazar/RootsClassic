package elucent.roots.mutation.mutations;

import java.util.List;

import elucent.roots.RegistryManager;
import elucent.roots.mutation.MutagenRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class MutagenMidnightBloomRecipe extends MutagenRecipe {
	public MutagenMidnightBloomRecipe() {
		super("midnightBloom",Blocks.RED_FLOWER.getStateFromMeta(0),RegistryManager.midnightBloom.getDefaultState());
		addIngredient(new ItemStack(Blocks.COAL_BLOCK,1));
	}
	
	@Override
	public void onCrafted(World world, BlockPos pos, EntityPlayer player){
		player.getEntityData().setInteger("RMOD_skipTicks", 200);
	}
	
	@Override
	public boolean matches(List<ItemStack> items, World world, BlockPos pos, EntityPlayer player){
		if (super.matches(items, world, pos, player)){
			if (world.provider.getDimensionType() == DimensionType.THE_END && world.getBlockState(pos.down(2)).getBlock() == Blocks.OBSIDIAN && player.getActivePotionEffect(Potion.getPotionFromResourceLocation("slowness")) != null){
				return true;
			}
		}
		return false;
	}
	
}
