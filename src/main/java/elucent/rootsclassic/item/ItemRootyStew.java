package elucent.rootsclassic.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRootyStew extends ItemFood {

  public ItemRootyStew() {
    super(7, 1.0F, false);

    this.setMaxStackSize(1);
  }


  @Override
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    super.onItemUseFinish(stack, worldIn, entityLiving);
    stack = new ItemStack(Items.BOWL);
    stack.setCount(1);
    return stack;
  }

}
