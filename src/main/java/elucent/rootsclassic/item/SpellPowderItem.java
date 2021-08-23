package elucent.rootsclassic.item;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SpellPowderItem extends Item {

	public SpellPowderItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged) {
		return slotChanged;
	}

	public static void createData(ItemStack stack, ResourceLocation effect, IInventory inventory) {
		CompoundNBT nbt = new CompoundNBT();
		int potency = 0;
		int efficiency = 0;
		int size = 0;
		nbt.putString(Const.NBT_EFFECT, effect.toString());
		if(inventory != null) {
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack itemStack = inventory.getStackInSlot(i);
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.hasTag()) {
			CompoundNBT tag = stack.getTag();
			ResourceLocation compName = ResourceLocation.tryCreate(tag.getString(Const.NBT_EFFECT));
			if(compName != null) {
				ComponentBase comp = ComponentManager.getComponentFromName(compName);
				if(comp != null) {
					tooltip.add(new TranslationTextComponent("rootsclassic.tooltip.spelltypeheading")
							.appendString(": ").mergeStyle(TextFormatting.GOLD).appendSibling(comp.getEffectName().mergeStyle(comp.getTextColor())));
				}
			}

			tooltip.add(new StringTextComponent("  +" + tag.getInt(Const.NBT_POTENCY) + " ")
					.appendSibling(new TranslationTextComponent("rootsclassic.tooltip.spellpotency")).appendString(".").mergeStyle(TextFormatting.RED));
			tooltip.add(new StringTextComponent("  +" + tag.getInt(Const.NBT_EFFICIENCY) + " ")
					.appendSibling(new TranslationTextComponent("rootsclassic.tooltip.spellefficiency")).appendString(".").mergeStyle(TextFormatting.RED));
			tooltip.add(new StringTextComponent("  +" + tag.getInt(Const.NBT_SIZE) + " ")
					.appendSibling(new TranslationTextComponent("rootsclassic.tooltip.spellsize")).appendString(".").mergeStyle(TextFormatting.RED));
		} else {
			tooltip.add(new TranslationTextComponent("rootsclassic.error.unset").mergeStyle(TextFormatting.GRAY));
		}
	}
}
