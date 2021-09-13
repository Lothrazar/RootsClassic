package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import elucent.rootsclassic.registry.RootsRegistry;

import net.minecraft.world.item.Item.Properties;

public class RootsFoodItem extends Item {

  private static final int HEAL_LARGE = 5;
  private static final int HEAL_SMALL = 2;

  public RootsFoodItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
    super.finishUsingItem(stack, worldIn, entityLiving);
    if (stack.getItem() == RootsRegistry.REDCURRANT.get()) {
      entityLiving.heal(HEAL_SMALL);
    }
    if (stack.getItem() == RootsRegistry.ELDERBERRY.get()) {
      entityLiving.removeAllEffects();
    }
    if (stack.getItem() == RootsRegistry.HEALING_POULTICE.get()) {
      entityLiving.heal(HEAL_LARGE);
    }
    return stack;
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    if (stack.getItem() == RootsRegistry.REDCURRANT.get()) {
      tooltip.add(new TranslatableComponent("rootsclassic.healingitem.tooltip").withStyle(ChatFormatting.GRAY));
    }
    if (stack.getItem() == RootsRegistry.ELDERBERRY.get()) {
      tooltip.add(new TranslatableComponent("rootsclassic.clearpotionsitem.tooltip").withStyle(ChatFormatting.GRAY));
    }
    if (stack.getItem() == RootsRegistry.HEALING_POULTICE.get()) {
      tooltip.add(new TranslatableComponent("rootsclassic.healingitem.tooltip", HEAL_LARGE).withStyle(ChatFormatting.GRAY));
    }
    if (stack.getItem() == RootsRegistry.NIGHTSHADE.get()) {
      tooltip.add(new TranslatableComponent("rootsclassic.poisonitem.tooltip").withStyle(ChatFormatting.GRAY));
    }
  }
}
