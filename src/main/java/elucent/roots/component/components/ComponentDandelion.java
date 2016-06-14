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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;


public class ComponentDandelion extends ComponentBase{
	Random rnd = new Random();
	public ComponentDandelion(){
		super("dandelion","Dandelion Winds",Blocks.YELLOW_FLOWER,2);
		
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-size,y-size,z-size,x+size,y+size,z+size));
			for(int i = 0; i < targets.size();i++){
				if(targets.get(i).getUniqueID() != caster.getUniqueID()){
					targets.get(i).motionX = caster.getLookVec().xCoord;
					targets.get(i).motionY = (float)(potency==0?1.0:1.0+0.5*potency);
					targets.get(i).motionZ = caster.getLookVec().zCoord;
				}
			}
		}
	}
}