package elucent.roots.ritual.rituals;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.item.ItemCrystalStaff;
import elucent.roots.ritual.RitualBase;

public class RitualImbuer extends RitualBase {

	public RitualImbuer() {
		super("imbuer", 255, 255, 255);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		ItemStack toSpawn = new ItemStack(RegistryManager.crystalStaff, 1);
		ItemCrystalStaff.createData(toSpawn);
		for (int i = 0; i < incenses.size() && i < 4; i ++){
			if (incenses.get(i).getItem() == RegistryManager.dustPetal && incenses.get(i).hasTagCompound()){
				NBTTagCompound tag = incenses.get(i).getTagCompound();
				ItemCrystalStaff.addEffect(toSpawn, i+1, tag.getString("effect"), tag.getInteger("potency"), tag.getInteger("efficiency"), tag.getInteger("size"));
			}
		}
		if (!world.isRemote){
			world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+1.5,pos.getZ()+0.5,toSpawn));
		}
		inventory.clear();
		world.getTileEntity(pos).markDirty();
	}
}
