package elucent.rootsclassic.item;

import javax.annotation.Nullable;
import com.lothrazar.library.item.ItemFlib;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class InfernalBulbItem extends ItemFlib {

  public InfernalBulbItem(Properties properties) {
    super(properties);
  }

  @Override
  public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
    return 2400;
  }
}
