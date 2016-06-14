package elucent.roots.ritual.rituals;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.ritual.RitualBase;

public class RitualCrafting extends RitualBase {
	public ItemStack result = null;
	
	public RitualCrafting setResult(ItemStack stack){
		this.result = stack;
		return this;
	}

	public RitualCrafting(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		if (Util.itemListsMatchWithSize(inventory, this.ingredients)){
			ItemStack toSpawn = result;
			if (!world.isRemote){
				world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+1.5,pos.getZ()+0.5,toSpawn));
			}
			inventory.clear();
			world.getTileEntity(pos).markDirty();
		}
	}
}
