package elucent.rootsclassic.compat.jei.wrapper;

import java.util.List;
import java.util.Map;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.ritual.RitualPillars;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class RitualWrapper implements IRecipeCategoryExtension {

  private final RitualRecipe<?> ritual;

  public Map<BlockPos, Block> getPillars() {
    return RitualPillars.getRitualPillars(ritual.level);
  }

  public List<Ingredient> getIngredients() {
    return ritual.getIngredients();
  }

  public List<Ingredient> getIncenses() {
    return ritual.getIncenses();
  }

  public ItemStack getResult() {
    return ritual.getResultItem();
  }

  public RitualWrapper(RitualRecipe<?> ritual) {
    this.ritual = ritual;
  }

  public MutableComponent getInfoText() {
    return ritual.getInfoText();
  }
}
