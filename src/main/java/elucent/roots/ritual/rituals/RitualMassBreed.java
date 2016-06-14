package elucent.roots.ritual.rituals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

public class RitualMassBreed extends RitualBase {
	public RitualMassBreed(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	@Override
	public boolean matches(World world, BlockPos pos){
		if (super.matches(world, pos)){
			if (world.getWorldInfo().isRaining() == false){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		inventory.clear();
		List<EntityAnimal> animals = (List<EntityAnimal>)world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(pos.getX()-22,pos.getY()-8,pos.getZ()-22,pos.getX()+23,pos.getY()+9,pos.getZ()+23));
		if (animals.size() > 0){
			for (int i = 0; i < animals.size(); i ++){
				animals.get(i).getEntityData().setInteger("InLove", 400);
			}
		}
	}
}
