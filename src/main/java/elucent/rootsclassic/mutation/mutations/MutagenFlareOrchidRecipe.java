package elucent.rootsclassic.mutation.mutations;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class MutagenFlareOrchidRecipe extends MutagenRecipe {

  public MutagenFlareOrchidRecipe() {
    super(new ResourceLocation(Const.MODID, "flare_orchid"), Blocks.BLUE_ORCHID.defaultBlockState(), RootsRegistry.FLARE_ORCHID.get().defaultBlockState());
    addIngredient(new ItemStack(Items.BLAZE_ROD, 1));
    addIngredient(new ItemStack(Items.LAVA_BUCKET, 1));
  }

  @Override
  public void onCrafted(Level world, BlockPos pos, Player player) {
    player.setSecondsOnFire(20);
  }

  @Override
  public boolean matches(List<ItemStack> items, Level world, BlockPos pos, Player player) {
    if (super.matches(items, world, pos, player)) {
      return world.dimension() == Level.NETHER && player.getEffect(MobEffects.FIRE_RESISTANCE) != null
          && world.getBlockState(pos.east()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.west()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.north()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.south()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.east().north()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.west().south()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.north().west()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.south().east()).getBlock() == Blocks.NETHERRACK;
    }
    return false;
  }
}
