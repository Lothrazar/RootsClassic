package elucent.rootsclassic.mutation;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.mutation.mutations.MutagenFlareOrchidRecipe;
import elucent.rootsclassic.mutation.mutations.MutagenMidnightBloomRecipe;
import elucent.rootsclassic.mutation.mutations.MutagenRadiantDaisyRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MutagenManager {

  public static ArrayList<MutagenRecipe> recipes = new ArrayList<MutagenRecipe>();

  public static void init() {
    recipes.add(new MutagenMidnightBloomRecipe());
    recipes.add(new MutagenFlareOrchidRecipe());
    recipes.add(new MutagenRadiantDaisyRecipe());
  }

  public static MutagenRecipe getRecipe(List<ItemStack> items, World world, BlockPos pos, EntityPlayer player) {
    for (int i = 0; i < recipes.size(); i++) {
      if (recipes.get(i).matches(items, world, pos, player)) {
        return recipes.get(i);
      }
    }
    return null;
  }
}