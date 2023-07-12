package elucent.rootsclassic.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;

import javax.annotation.Nullable;

public class InfernalBulbItem extends Item {

  public InfernalBulbItem(Properties properties) {
    super(properties);
  }

  @Override
  public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
    return 2400;
  }
}
