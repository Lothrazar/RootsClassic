package elucent.rootsclassic.mutation.mutations;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MutagenRadiantDaisyRecipe extends MutagenRecipe {

  public MutagenRadiantDaisyRecipe() {
    super(new ResourceLocation(Const.MODID, "radiant_daisy"), Blocks.OXEYE_DAISY.defaultBlockState(), RootsRegistry.RADIANT_DAISY.get().defaultBlockState());
    addIngredient(new ItemStack(Blocks.GLOWSTONE, 1));
    addIngredient(new ItemStack(Items.PRISMARINE_CRYSTALS, 1));
  }

  @Override
  public void onCrafted(World world, BlockPos pos, PlayerEntity player) {
    player.addEffect(new EffectInstance(Effects.BLINDNESS, 1200, 0));
  }

  @Override
  public boolean matches(List<ItemStack> items, World world, BlockPos pos, PlayerEntity player) {
    if (super.matches(items, world, pos, player)) {
      return world.dimension() == World.OVERWORLD && player.getEffect(Effects.NIGHT_VISION) != null && player.getCommandSenderWorld().getDayTime() > 5000 && player.getCommandSenderWorld().getDayTime() < 7000;
    }
    return false;
  }
}
