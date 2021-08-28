package elucent.rootsclassic.mutation;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

	public static MutagenRecipe getRecipe(List<ItemStack> items, World world, BlockPos pos, PlayerEntity player) {
		for (MutagenRecipe recipe : recipes) {
			if (recipe.matches(items, world, pos, player)) {
				return recipe;
			}
		}
		return null;
	}
}