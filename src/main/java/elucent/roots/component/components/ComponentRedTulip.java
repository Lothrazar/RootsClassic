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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class ComponentRedTulip extends ComponentBase{
	Random random = new Random();
	public ComponentRedTulip(){
		super("redtulip","Devil's Flower",Blocks.RED_FLOWER,6);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-size*2.4,y-size*2.4,z-size*2.4,x+size*2.4,y+size*2.4,z+size*2.4));
			if (targets.size() > 0 && !world.isRemote){
				EntitySkeleton skeleton = new EntitySkeleton(world);
				skeleton.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(x,y,z)), null);
				skeleton.setHeldItem(EnumHand.MAIN_HAND, null);
				skeleton.setDropItemsWhenDead(false);
				skeleton.getEntityData().setBoolean("RMOD_dropItems", false);
				skeleton.getEntityData().setLong("RMOD_dontTarget", caster.getUniqueID().getMostSignificantBits());
				skeleton.setPosition(x, y+2.0, z);
				skeleton.setAttackTarget(targets.get(random.nextInt(targets.size())));
				if (skeleton.getAttackTarget().getUniqueID() != caster.getUniqueID()){
					world.spawnEntityInWorld(skeleton);
				}
			}
		}
	}
}
