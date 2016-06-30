package elucent.roots.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemDragonsEye extends RootsItemFood{

	public ItemDragonsEye(String name, int amount, float saturation, boolean isWolFFood){
		super(name, amount, saturation, isWolFFood);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving){
		 super.onItemUseFinish(stack, worldIn, entityLiving);
		 if (!worldIn.isRemote){
	            double d0 = entityLiving.posX;
	            double d1 = entityLiving.posY;
	            double d2 = entityLiving.posZ;

	            for (int i = 0; i < 32; ++i){
	                double d3 = entityLiving.posX + (entityLiving.getRNG().nextDouble() - 0.5D) * 32.0D;
	                double d4 = MathHelper.clamp_double(entityLiving.posY + (double)(entityLiving.getRNG().nextInt(32) - 8), 0.0D, (double)(worldIn.getActualHeight() - 1));
	                double d5 = entityLiving.posZ + (entityLiving.getRNG().nextDouble() - 0.5D) * 32.0D;

	                if (entityLiving.attemptTeleport(d3, d4, d5)){
	                    worldIn.playSound((EntityPlayer)null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
	                    entityLiving.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
	                    break;
	                }
	            }

	            if (entityLiving instanceof EntityPlayer){
	                ((EntityPlayer)entityLiving).getCooldownTracker().setCooldown(this, 20);
	            }
	        }
		 return stack;
	}

}
