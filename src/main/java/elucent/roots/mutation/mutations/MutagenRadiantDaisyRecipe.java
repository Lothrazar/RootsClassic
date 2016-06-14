package elucent.roots.mutation.mutations;

import java.util.List;

import elucent.roots.RegistryManager;
import elucent.roots.mutation.MutagenRecipe;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class MutagenRadiantDaisyRecipe extends MutagenRecipe {
	public MutagenRadiantDaisyRecipe() {
		super("radiantDaisy",Blocks.RED_FLOWER.getStateFromMeta(8),RegistryManager.radiantDaisy.getDefaultState());
		addIngredient(new ItemStack(Blocks.GLOWSTONE,1));
		addIngredient(new ItemStack(Items.PRISMARINE_CRYSTALS,1));
	}
	
	@Override
	public void onCrafted(World world, BlockPos pos, EntityPlayer player){
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("blindness"),1200,0));
	}
	
	@Override
	public boolean matches(List<ItemStack> items, World world, BlockPos pos, EntityPlayer player){
		if (super.matches(items, world, pos, player)){
			if (world.provider.getDimensionType() == DimensionType.OVERWORLD && player.getActivePotionEffect(Potion.getPotionFromResourceLocation("night_vision")) != null && player.getEntityWorld().getWorldTime() > 5000 && player.getEntityWorld().getWorldTime() < 7000){
				return true;
			}
		}
		return false;
	}
	
}
