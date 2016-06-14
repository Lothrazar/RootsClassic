package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentPeony extends ComponentBase{
	Random rnd = new Random();
	public ComponentPeony(){
		super("peony", "Regeneration", Blocks.DOUBLE_PLANT,5,5);
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			((EntityLivingBase) caster).heal((float)(3+potency));
		}
	}	
}