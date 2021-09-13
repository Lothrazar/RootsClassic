package elucent.rootsclassic.item;

import javax.annotation.Nullable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import net.minecraft.world.item.Item.Properties;

public class InfernalBulbItem extends Item {

  public InfernalBulbItem(Properties properties) {
    super(properties);
  }

  @Override
  public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
    return 2400;
  }
}
