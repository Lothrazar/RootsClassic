package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import elucent.rootsclassic.RegistryManager;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRootsFood extends ItemFood {

  private static final int HEAL_LARGE = 5;
  private static final int HEAL_SMALL = 2;

  public ItemRootsFood(int amount, float saturation, boolean isWolFFood) {
    super(amount, saturation, isWolFFood);
  }

  @Override
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    super.onItemUseFinish(stack, worldIn, entityLiving);
    if (stack.getItem() == RegistryManager.redCurrant) {
      entityLiving.heal(HEAL_SMALL);
    }
    if (stack.getItem() == RegistryManager.elderBerry) {
      entityLiving.clearActivePotions();
    }
    if (stack.getItem() == RegistryManager.healingPoultice) {
      entityLiving.heal(HEAL_LARGE);
    }
    return stack;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (stack.getItem() == RegistryManager.redCurrant) {
      tooltip.add(I18n.translateToLocalFormatted("roots.healingitem.tooltip"));
    }
    if (stack.getItem() == RegistryManager.elderBerry) {
      tooltip.add(I18n.translateToLocalFormatted("roots.clearpotionsitem.tooltip"));
    }
    if (stack.getItem() == RegistryManager.healingPoultice) {
      tooltip.add(I18n.translateToLocalFormatted("roots.healingitem.tooltip", HEAL_LARGE));
    }
    if (stack.getItem() == RegistryManager.nightshade) {
      tooltip.add(I18n.translateToLocalFormatted("roots.poisonitem.tooltip"));
    }
  }
}
