package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;


public class ComponentChorus extends ComponentBase{
	Random random = new Random();
	public ComponentChorus(){
		super("chorus","Ender Warp",Items.CHORUS_FRUIT,4);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			if (caster instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer)caster;
				player.setPosition(player.posX + player.getLookVec().xCoord*(8.0+8.0*potency), player.posY + player.getLookVec().yCoord*(8.0+8.0*potency), player.posZ + player.getLookVec().zCoord*(8.0+8.0*potency));
			}
		}
	}
}
