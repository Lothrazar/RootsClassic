package elucent.rootsclassic.item.powder;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.registry.RootsComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class SpellPowderItem extends Item {

  public SpellPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    return slotChanged;
  }

  public static void createData(ItemStack stack, ResourceLocation effect, RecipeInput recipeInput) {
    CompoundTag nbt = new CompoundTag();
    int potency = 0;
    int efficiency = 0;
    int size = 0;
    if (recipeInput != null) {
      for (int i = 0; i < recipeInput.size(); i++) {
        ItemStack itemStack = recipeInput.getItem(i);
        if (!itemStack.isEmpty()) {
          //TODO: Make this into a tag
          if (itemStack.getItem() == Items.GLOWSTONE_DUST) {
            potency++;
          }
          if (itemStack.getItem() == Items.REDSTONE) {
            efficiency++;
          }
          if (itemStack.getItem() == Items.GUNPOWDER) {
            size++;
          }
        }
      }
    }
		stack.set(RootsComponents.SPELL, new SpellData(potency, efficiency, size, effect.toString()));
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
	  super.appendHoverText(stack, context, tooltip, tooltipFlag);
    if (stack.has(RootsComponents.SPELL)) {
	    SpellData spell = stack.get(RootsComponents.SPELL);
      ResourceLocation compName = ResourceLocation.tryParse(spell.effect());
      if (compName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get(compName);
        if (comp != null) {
          tooltip.add(Component.translatable("rootsclassic.tooltip.spelltypeheading")
              .append(": ").withStyle(ChatFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
        }
      }
      tooltip.add(Component.translatable("  +" + spell.potency() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + spell.efficiency() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + spell.size() + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
    }
    else {
      tooltip.add(Component.translatable("rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
    }
  }
}
