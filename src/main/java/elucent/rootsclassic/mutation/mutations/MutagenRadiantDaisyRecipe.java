package elucent.rootsclassic.mutation.mutations;

import java.util.List;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class MutagenRadiantDaisyRecipe extends MutagenRecipe {

  public MutagenRadiantDaisyRecipe() {
    super(new ResourceLocation(Const.MODID, "radiant_daisy"), Blocks.OXEYE_DAISY.defaultBlockState(), RootsRegistry.RADIANT_DAISY.get().defaultBlockState());
    addIngredient(new ItemStack(Blocks.GLOWSTONE, 1));
    addIngredient(new ItemStack(Items.PRISMARINE_CRYSTALS, 1));
  }

  @Override
  public void onCrafted(Level levelAccessor, BlockPos pos, Player player) {
    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200, 0));
  }

  @Override
  public boolean matches(List<ItemStack> items, Level levelAccessor, BlockPos pos, Player player) {
    if (super.matches(items, levelAccessor, pos, player)) {
      return levelAccessor.dimension() == Level.OVERWORLD && player.getEffect(MobEffects.NIGHT_VISION) != null && player.getCommandSenderWorld().getDayTime() > 5000 && player.getCommandSenderWorld().getDayTime() < 7000;
    }
    return false;
  }
}
