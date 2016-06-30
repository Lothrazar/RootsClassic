package elucent.roots.ritual.rituals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.ritual.RitualBase;

public class RitualGrow extends RitualBase {
	Random random = new Random();
	
	public RitualGrow(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		for (int i = -17; i < 18; i ++){
			for (int j = -4; j < 5; j ++){
				for (int k = -17; k < 18; k ++){
					if (world.getBlockState(pos.add(i,j,k)).getBlock() instanceof IGrowable && random.nextInt(12) == 0){
						((IGrowable)world.getBlockState(pos.add(i,j,k)).getBlock()).grow(world, random, pos.add(i,j,k), world.getBlockState(pos.add(i,j,k)));
					}
				}
			}
		}
	}
}
