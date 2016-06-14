package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.Util;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class ComponentRadiantDaisy extends ComponentBase{
	Random random = new Random();
	public ComponentRadiantDaisy(){
		super("radiantdaisy","Radiance",RegistryManager.radiantDaisy,5);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (!world.isRemote){
			EntityPlayer player = (EntityPlayer)caster;
			double posX = player.posX+player.getLookVec().xCoord*0.5;
			double posY = player.posY+1.5+player.getLookVec().yCoord*0.5;
			double posZ = player.posZ+player.getLookVec().zCoord*0.5;
			for (int i = 0; i < 100+50*size; i ++){
				boolean didHit = false;
				Roots.proxy.spawnParticleMagicAuraFX(player.getEntityWorld(), posX, posY, posZ, 0, 0, 0, 255, 255, 255);
				posX += player.getLookVec().xCoord*0.25;
				posY += player.getLookVec().yCoord*0.25;
				posZ += player.getLookVec().zCoord*0.25;
				List<EntityLivingBase> targets = (List<EntityLivingBase>)player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX-0.25,posY-0.25,posZ-0.25,posX+0.25,posY+0.25,posZ+0.25));
				if (targets.size() > 0){
					for (int j = 0; j < targets.size() && !didHit; j ++){
						if (targets.get(j).getUniqueID() != player.getUniqueID()){
							didHit = true;
							targets.get(j).attackEntityFrom(DamageSource.magic, (float) (12+3.0*potency));
							targets.get(j).setLastAttacker(player);
							targets.get(j).setRevengeTarget((EntityLivingBase)player);
						}
					}
				}
				if (player.getEntityWorld().getBlockState(new BlockPos(posX,posY,posZ)).isFullCube()){
					didHit = true;
				}
				if (didHit){
					return;
				}
			}
		}
	}
}
