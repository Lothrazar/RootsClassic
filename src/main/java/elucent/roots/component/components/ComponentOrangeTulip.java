package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import elucent.roots.PlayerManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentEffect;
import elucent.roots.component.EnumCastType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class ComponentOrangeTulip extends ComponentBase{
	Random random = new Random();
	public ComponentOrangeTulip(){
		super("orangetulip","Shielding",Blocks.RED_FLOWER,5);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		//
	}
	
	@Override
	public void castingAction(EntityPlayer player, int count, int potency, int efficiency, int size){
		super.castingAction(player, count, potency, efficiency, size);
		if (count % 1 == 0){
			List<Entity> entities = (List<Entity>)player.worldObj.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX-(1.0+(double)0.5*size),player.posY-(1.0+(double)0.5*size),player.posZ-(1.0+(double)0.5*size),player.posX+(1.0+(double)0.5*size),player.posY+(1.0+(double)0.5*size),player.posZ+(1.0+0.5*(double)size)));
			for (int i = 0; i < entities.size(); i ++){
				if (entities.get(i) instanceof EntityLivingBase || entities.get(i) instanceof EntityArrow){
					Vec3d v = new Vec3d(entities.get(i).posX-player.posX,entities.get(i).posY-(player.posY+1.0),entities.get(i).posZ-player.posZ);
					v.normalize();
					entities.get(i).motionX = v.xCoord*(0.05+0.05*(double)potency);
					entities.get(i).motionY = v.yCoord*(0.05+0.05*(double)potency);
					entities.get(i).motionZ = v.zCoord*(0.05+0.05*(double)potency);
				}
			}
		}
	}
}
