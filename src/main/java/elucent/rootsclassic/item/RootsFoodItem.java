package elucent.rootsclassic.item;

import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RootsFoodItem extends Item {

  private static final int HEAL_LARGE = 5;
  private static final int HEAL_SMALL = 2;

  public RootsFoodItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level levelAccessor, LivingEntity entityLiving) {
    Item item = stack.getItem();
    super.finishUsingItem(stack, levelAccessor, entityLiving);
    if (item == RootsRegistry.REDCURRANT.get()) {
      entityLiving.heal(HEAL_SMALL);
    }
    if (item == RootsRegistry.ELDERBERRY.get()) {
      entityLiving.removeAllEffects();
    }
    if (item == RootsRegistry.HEALING_POULTICE.get()) {
      entityLiving.heal(HEAL_LARGE);
    }
    return stack;
  }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltip, tooltipFlag);
    if (stack.is(RootsRegistry.REDCURRANT.get())) {
      tooltip.add(Component.translatable("rootsclassic.healingitem.tooltip").withStyle(ChatFormatting.GRAY));
    }
    if (stack.is(RootsRegistry.ELDERBERRY.get())) {
      tooltip.add(Component.translatable("rootsclassic.clearpotionsitem.tooltip").withStyle(ChatFormatting.GRAY));
    }
    if (stack.is(RootsRegistry.HEALING_POULTICE.get())) {
      tooltip.add(Component.translatable("rootsclassic.healingitem.tooltip").withStyle(ChatFormatting.GRAY));
    }
    if (stack.is(RootsRegistry.NIGHTSHADE.get())) {
      tooltip.add(Component.translatable("rootsclassic.poisonitem.tooltip").withStyle(ChatFormatting.GRAY));
    }
  }
}
