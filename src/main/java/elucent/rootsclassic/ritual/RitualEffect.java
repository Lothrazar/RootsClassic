package elucent.rootsclassic.ritual;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class RitualEffect<C>  {

	public abstract void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, C config);

	public ItemStack getResult(C config) {
		return ItemStack.EMPTY;
	}

	abstract public C fromJSON(JsonObject object);

	abstract public void toNetwork(C config, FriendlyByteBuf buffer);

	abstract public C fromNetwork(FriendlyByteBuf buffer);

}
