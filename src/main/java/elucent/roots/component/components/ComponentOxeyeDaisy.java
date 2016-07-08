package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import elucent.roots.PlayerManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentEffect;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.EntityAccelerator;
import elucent.roots.entity.EntityTileAccelerator;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class ComponentOxeyeDaisy extends ComponentBase{
	Random random = new Random();
	public ComponentOxeyeDaisy(){
		super("oxeyedaisy","Acceleration",Blocks.RED_FLOWER,8,14);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			if (caster instanceof EntityPlayer){
				Entity entity = Util.getRayTraceEntity(world, (EntityPlayer)caster, 4+2*(int)size);
				if (entity != null){
					if (!world.isRemote){
						if (world.getEntitiesWithinAABB(EntityAccelerator.class, new AxisAlignedBB(entity.posX-0.1,entity.posY-0.1,entity.posZ-0.1,entity.posX+0.1,entity.posY+0.1,entity.posZ+0.1)).size() == 0){
							EntityAccelerator a = new EntityAccelerator(world,entity,(int)potency,(int)size);
							world.spawnEntityInWorld(a);
						}
					}
				}
				else {
					BlockPos pos = Util.getRayTrace(world,(EntityPlayer)caster,4+2*(int)size);
					if (world.getTileEntity(pos) != null && !world.isRemote){
						if (world.getEntitiesWithinAABB(EntityTileAccelerator.class, new AxisAlignedBB(pos.getX()-0.1,pos.getY()-0.1,pos.getZ()-0.1,pos.getX()+0.1,pos.getY()+0.1,pos.getZ()+0.1)).size() == 0){
							EntityTileAccelerator a = new EntityTileAccelerator(world,pos,(int)potency,(int)size);
							world.spawnEntityInWorld(a);
						}
					}
				}
			}
		}
	}
}
