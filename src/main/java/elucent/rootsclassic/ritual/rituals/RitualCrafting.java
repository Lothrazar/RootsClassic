package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RitualCrafting extends RitualBase {

	public ItemStack result = ItemStack.EMPTY;

	public RitualCrafting(int level, double r, double g, double b) {
		super(level, r, g, b);
	}

	public RitualCrafting setResult(ItemStack stack) {
		this.result = stack;
		return this;
	}

	@Override
	public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
		// if (Util.itemListsMatchWithSize(inventory, this.ingredients)) {
		ItemStack toSpawn = result.copy();
		if (!levelAccessor.isClientSide) {
			ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
			levelAccessor.addFreshEntity(item);
		}
		inventory.clearContent();
		levelAccessor.getBlockEntity(pos).setChanged();
		//}
	}
}
