package elucent.rootsclassic.mutation.mutations;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class MutagenRadiantDaisyRecipe extends MutagenRecipe {

  public MutagenRadiantDaisyRecipe() {
    super(Const.modLoc("radiant_daisy"), Blocks.OXEYE_DAISY.defaultBlockState(),
	    RootsRegistry.RADIANT_DAISY.get().defaultBlockState());
    addIngredient(new ItemStack(Blocks.GLOWSTONE, 1));
    addIngredient(new ItemStack(Items.PRISMARINE_CRYSTALS, 1));
  }

  @Override
  public void onCrafted(Level levelAccessor, BlockPos pos, Player player) {
    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200, 0));
  }

  @Override
  public boolean matches(List<ItemStack> items, Level level, BlockPos pos, Player player) {
    if (super.matches(items, level, pos, player) && level instanceof ServerLevel serverLevel) {
      long time = serverLevel.getDefaultClockTime();
      return level.dimension() == Level.OVERWORLD && player.getEffect(MobEffects.NIGHT_VISION) != null &&
        time > 5000 && time < 7000;
    }
    return false;
  }
}
