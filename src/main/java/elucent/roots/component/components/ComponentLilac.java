package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class ComponentLilac extends ComponentBase{
	Random random = new Random();
	public ComponentLilac(){
		super("lilac","Growth",Blocks.DOUBLE_PLANT,1,3);	
	}
	
	public void growBlockSafe(World world, BlockPos pos, int potency){
		if (world.getBlockState(pos).getBlock() instanceof IGrowable && random.nextInt(4-(int)potency) == 0){
			((IGrowable)world.getBlockState(pos).getBlock()).grow(world, random, pos, world.getBlockState(pos));
		}
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			if (caster instanceof EntityPlayer && !world.isRemote){
				BlockPos pos = Util.getRayTrace(world,(EntityPlayer)caster,4+2*(int)size);
				growBlockSafe(world, pos, (int)potency);
				if (random.nextBoolean()){
					growBlockSafe(world, pos.east(), (int)potency);
				}
				if (random.nextBoolean()){
					growBlockSafe(world, pos.west(), (int)potency);
				}
				if (random.nextBoolean()){
					growBlockSafe(world, pos.north(), (int)potency);
				}
				if (random.nextBoolean()){
					growBlockSafe(world, pos.south(), (int)potency);
				}
			}
		}
	}
}
