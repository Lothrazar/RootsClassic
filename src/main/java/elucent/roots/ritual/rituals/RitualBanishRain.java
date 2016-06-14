package elucent.roots.ritual.rituals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.ritual.RitualBase;

public class RitualBanishRain extends RitualBase {
	public RitualBanishRain(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	@Override
	public boolean matches(World world, BlockPos pos){
		if (super.matches(world, pos)){
			if (world.getWorldInfo().isRaining() == true){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		inventory.clear();
		world.getWorldInfo().setRaining(false);
	}
}
