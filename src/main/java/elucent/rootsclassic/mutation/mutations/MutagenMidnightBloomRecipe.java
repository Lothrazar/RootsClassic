package elucent.rootsclassic.mutation.mutations;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MutagenMidnightBloomRecipe extends MutagenRecipe {

  public MutagenMidnightBloomRecipe() {
    super(new ResourceLocation(Const.MODID, "midnight_bloom"), Blocks.POPPY.defaultBlockState(), RootsRegistry.MIDNIGHT_BLOOM.get().defaultBlockState());
    addIngredient(new ItemStack(Blocks.COAL_BLOCK, 1));
  }

  @Override
  public void onCrafted(World world, BlockPos pos, PlayerEntity player) {
    player.getPersistentData().putInt(Const.NBT_SKIP_TICKS, 200);
  }

  @Override
  public boolean matches(List<ItemStack> items, World world, BlockPos pos, PlayerEntity player) {
    if (super.matches(items, world, pos, player)) {
      return world.dimension() == World.END && world.getBlockState(pos.below(2)).getBlock() == Blocks.OBSIDIAN && player.getEffect(Effects.MOVEMENT_SLOWDOWN) != null;
    }
    return false;
  }
}
