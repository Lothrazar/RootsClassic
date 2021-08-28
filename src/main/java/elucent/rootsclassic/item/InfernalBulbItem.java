package elucent.rootsclassic.item;

import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;

public class InfernalBulbItem extends Item {

  public InfernalBulbItem(Properties properties) {
    super(properties);
  }

  @Override
  public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
    return 2400;
  }
}
