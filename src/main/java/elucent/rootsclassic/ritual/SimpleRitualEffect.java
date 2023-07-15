package elucent.rootsclassic.ritual;

import java.util.List;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class SimpleRitualEffect extends RitualEffect<Void> {

  @Override
  public final Void fromJSON(JsonObject object) {
    return null;
  }

  @Override
  public final void toNetwork(Void config, FriendlyByteBuf buffer) {}

  @Override
  public final Void fromNetwork(FriendlyByteBuf buffer) {
    return null;
  }

  @Override
  public final void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, Void config) {
    doEffect(levelAccessor, pos, inventory, incenses);
  }

  public abstract void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses);
}