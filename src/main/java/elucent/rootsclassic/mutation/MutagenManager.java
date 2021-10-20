package elucent.rootsclassic.mutation;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.mutation.mutations.MutagenFlareOrchidRecipe;
import elucent.rootsclassic.mutation.mutations.MutagenMidnightBloomRecipe;
import elucent.rootsclassic.mutation.mutations.MutagenRadiantDaisyRecipe;

public class MutagenManager {

  public static ArrayList<MutagenRecipe> recipes = new ArrayList<>();

  public static void reload() {
    recipes.add(new MutagenMidnightBloomRecipe());
    recipes.add(new MutagenFlareOrchidRecipe());
    recipes.add(new MutagenRadiantDaisyRecipe());
  }

  public static MutagenRecipe getRecipe(List<ItemStack> items, Level world, BlockPos pos, Player player) {
    for (MutagenRecipe recipe : recipes) {
      if (recipe.matches(items, world, pos, player)) {
        return recipe;
      }
    }
    return null;
  }
}