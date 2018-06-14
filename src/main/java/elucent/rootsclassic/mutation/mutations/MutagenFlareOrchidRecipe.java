package elucent.rootsclassic.mutation.mutations;

import java.util.List;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.mutation.MutagenRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class MutagenFlareOrchidRecipe extends MutagenRecipe {

  public MutagenFlareOrchidRecipe() {
    super("flareOrchid", Blocks.RED_FLOWER.getStateFromMeta(1), RegistryManager.flareOrchid.getDefaultState());
    addIngredient(new ItemStack(Items.BLAZE_ROD, 1));
    addIngredient(new ItemStack(Items.LAVA_BUCKET, 1));
  }

  @Override
  public void onCrafted(World world, BlockPos pos, EntityPlayer player) {
    player.setFire(20);
  }

  @Override
  public boolean matches(List<ItemStack> items, World world, BlockPos pos, EntityPlayer player) {
    if (super.matches(items, world, pos, player)) {
      if (world.provider.getDimensionType() == DimensionType.NETHER && player.getActivePotionEffect(Potion.getPotionFromResourceLocation("fire_resistance")) != null
          && world.getBlockState(pos.east()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.west()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.north()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.south()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.east().north()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.west().south()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.north().west()).getBlock() == Blocks.NETHERRACK
          && world.getBlockState(pos.south().east()).getBlock() == Blocks.NETHERRACK) {
        return true;
      }
    }
    return false;
  }
}
