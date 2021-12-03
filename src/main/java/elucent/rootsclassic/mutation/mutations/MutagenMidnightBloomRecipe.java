package elucent.rootsclassic.mutation.mutations;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class MutagenMidnightBloomRecipe extends MutagenRecipe {

  public MutagenMidnightBloomRecipe() {
    super(new ResourceLocation(Const.MODID, "midnight_bloom"), Blocks.POPPY.defaultBlockState(), RootsRegistry.MIDNIGHT_BLOOM.get().defaultBlockState());
    addIngredient(new ItemStack(Blocks.COAL_BLOCK, 1));
  }

  @Override
  public void onCrafted(Level world, BlockPos pos, Player player) {
    player.getPersistentData().putInt(Const.NBT_SKIP_TICKS, 200);
  }

  @Override
  public boolean matches(List<ItemStack> items, Level world, BlockPos pos, Player player) {
    if (super.matches(items, world, pos, player)) {
      return world.dimension() == Level.END && world.getBlockState(pos.below(2)).getBlock() == Blocks.OBSIDIAN && player.getEffect(MobEffects.MOVEMENT_SLOWDOWN) != null;
    }
    return false;
  }
}
