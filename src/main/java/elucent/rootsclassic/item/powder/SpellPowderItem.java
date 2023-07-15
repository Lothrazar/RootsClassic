package elucent.rootsclassic.item.powder;

import java.util.List;
import javax.annotation.Nullable;
import com.lothrazar.library.item.ItemFlib;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SpellPowderItem extends ItemFlib {

  public SpellPowderItem(Properties properties) {
    super(properties);
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
    return slotChanged;
  }

  public static void createData(ItemStack stack, ResourceLocation effect, Container inventory) {
    CompoundTag nbt = new CompoundTag();
    int potency = 0;
    int efficiency = 0;
    int size = 0;
    nbt.putString(Const.NBT_EFFECT, effect.toString());
    if (inventory != null) {
      for (int i = 0; i < inventory.getContainerSize(); i++) {
        ItemStack itemStack = inventory.getItem(i);
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
    nbt.putInt(Const.NBT_POTENCY, potency);
    nbt.putInt(Const.NBT_EFFICIENCY, efficiency);
    nbt.putInt(Const.NBT_SIZE, size);
    stack.setTag(nbt);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level levelAccessor, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, levelAccessor, tooltip, flagIn);
    if (stack.hasTag()) {
      CompoundTag tag = stack.getTag();
      ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
      if (compName != null) {
        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get().getValue(compName);
        if (comp != null) {
          tooltip.add(Component.translatable("rootsclassic.tooltip.spelltypeheading")
              .append(": ").withStyle(ChatFormatting.GOLD).append(comp.getEffectName().withStyle(comp.getTextColor())));
        }
      }
      tooltip.add(Component.translatable("  +" + tag.getInt(Const.NBT_POTENCY) + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellpotency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + tag.getInt(Const.NBT_EFFICIENCY) + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellefficiency")).append(".").withStyle(ChatFormatting.RED));
      tooltip.add(Component.translatable("  +" + tag.getInt(Const.NBT_SIZE) + " ")
          .append(Component.translatable("rootsclassic.tooltip.spellsize")).append(".").withStyle(ChatFormatting.RED));
    }
    else {
      tooltip.add(Component.translatable("rootsclassic.error.unset").withStyle(ChatFormatting.GRAY));
    }
  }
}
