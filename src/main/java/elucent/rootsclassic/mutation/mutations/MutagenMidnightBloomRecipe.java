package elucent.rootsclassic.mutation.mutations;

import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;

public class MutagenMidnightBloomRecipe extends MutagenRecipe {

  public MutagenMidnightBloomRecipe() {
    super(new ResourceLocation(Const.MODID, "midnight_bloom"), Blocks.POPPY.getDefaultState(), RootsRegistry.MIDNIGHT_BLOOM.get().getDefaultState());
    addIngredient(new ItemStack(Blocks.COAL_BLOCK, 1));
  }

  @Override
  public void onCrafted(World world, BlockPos pos, PlayerEntity player) {
    player.getPersistentData().putInt("RMOD_skipTicks", 200);
  }

  @Override
  public boolean matches(List<ItemStack> items, World world, BlockPos pos, PlayerEntity player) {
    if (super.matches(items, world, pos, player)) {
      return world.getDimensionKey() == World.THE_END && world.getBlockState(pos.down(2)).getBlock() == Blocks.OBSIDIAN && player.getActivePotionEffect(Effects.SLOWNESS) != null;
    }
    return false;
  }
}
