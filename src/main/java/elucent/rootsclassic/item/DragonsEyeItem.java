package elucent.rootsclassic.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DragonsEyeItem extends RootsFoodItem {

  public DragonsEyeItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
    super.finishUsingItem(stack, worldIn, entityLiving);
    if (!worldIn.isClientSide) {
      double d0 = entityLiving.getX();
      double d1 = entityLiving.getY();
      double d2 = entityLiving.getZ();
      for (int i = 0; i < 32; ++i) {
        double d3 = entityLiving.getX() + (entityLiving.getRandom().nextDouble() - 0.5D) * 32.0D;
        double d4 = MathHelper.clamp(entityLiving.getY() + (entityLiving.getRandom().nextInt(32) - 8), 0.0D, worldIn.getHeight() - 1);
        double d5 = entityLiving.getZ() + (entityLiving.getRandom().nextDouble() - 0.5D) * 32.0D;
        if (entityLiving.isPassenger()) {
          entityLiving.stopRiding();
        }
        if (entityLiving.randomTeleport(d3, d4, d5, true)) {
          worldIn.playSound((PlayerEntity) null, d0, d1, d2, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
          entityLiving.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
          break;
        }
      }
      if (entityLiving instanceof PlayerEntity) {
        ((PlayerEntity) entityLiving).getCooldowns().addCooldown(this, 20);
      }
    }
    return stack;
  }
}
