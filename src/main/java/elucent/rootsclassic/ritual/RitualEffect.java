package elucent.rootsclassic.ritual;

import java.util.List;
import com.google.gson.JsonObject;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class RitualEffect<C> {

  public abstract void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, C config);

  public ItemStack getResult(C config) {
    return ItemStack.EMPTY;
  }

  public MutableComponent getInfoText(C config) {
    var id = RitualBaseRegistry.RITUALS.get().getKey(this);
    if (id == null) return Component.empty();
    return Component.translatable(id.getNamespace() + ".jei.tooltip." + id.getPath());
  }

  abstract public C fromJSON(JsonObject object);

  abstract public void toNetwork(C config, FriendlyByteBuf buffer);

  abstract public C fromNetwork(FriendlyByteBuf buffer);
  
  public boolean incenseMatches(List<ItemStack> incensesFromNearby, RitualRecipe<C> recipe) {
    return RootsUtil.matchesIngredients(incensesFromNearby, recipe.getIncenses());
  }
}