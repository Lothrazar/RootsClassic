package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import com.google.gson.JsonObject;
import elucent.rootsclassic.ritual.RitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class RitualCrafting extends RitualEffect<RitualCrafting.RitualCraftingConfig> {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses, RitualCraftingConfig config) {
    // if (Util.itemListsMatchWithSize(inventory, this.ingredients)) {
    ItemStack toSpawn = config.result.copy();
    if (!levelAccessor.isClientSide) {
      ItemEntity item = new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, toSpawn);
      levelAccessor.addFreshEntity(item);
    }
    inventory.clearContent();
    levelAccessor.getBlockEntity(pos).setChanged();
    //}
  }

  @Override
  public RitualCraftingConfig fromJSON(JsonObject object) {
    var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(object, "result"));
    return new RitualCraftingConfig(result);
  }

  @Override
  public ItemStack getResult(RitualCraftingConfig config) {
    return config.result;
  }

  @Override
  public void toNetwork(RitualCraftingConfig config, FriendlyByteBuf buffer) {
    buffer.writeItem(config.result);
  }

  @Override
  public RitualCraftingConfig fromNetwork(FriendlyByteBuf buffer) {
    return new RitualCraftingConfig(buffer.readItem());
  }

  public record RitualCraftingConfig(ItemStack result) {}
}
