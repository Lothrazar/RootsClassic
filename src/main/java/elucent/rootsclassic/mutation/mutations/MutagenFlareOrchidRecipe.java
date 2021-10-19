package elucent.rootsclassic.mutation.mutations;

import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;

public class MutagenFlareOrchidRecipe extends MutagenRecipe {

  public MutagenFlareOrchidRecipe() {
    super(new ResourceLocation(Const.MODID, "flare_orchid"), Blocks.BLUE_ORCHID.defaultBlockState(), RootsRegistry.FLARE_ORCHID.get().defaultBlockState());
    addIngredient(new ItemStack(Items.BLAZE_ROD, 1));
    addIngredient(new ItemStack(Items.LAVA_BUCKET, 1));
  }

  @Override
  public void onCrafted(World world, BlockPos pos, PlayerEntity player) {
    player.setSecondsOnFire(20);
  }

  @Override
  public boolean matches(List<ItemStack> items, World world, BlockPos pos, PlayerEntity player) {
    if (super.matches(items, world, pos, player)) {
      return world.dimension() == World.NETHER && player.getEffect(Effects.FIRE_RESISTANCE) != null
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
