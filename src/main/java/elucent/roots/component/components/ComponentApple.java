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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class ComponentApple extends ComponentBase{
	Random random = new Random();
	public ComponentApple(){
		super("apple","Nature's Cure",Items.APPLE,3);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			if (caster instanceof EntityPlayer){
				List<PotionEffect> effects = (List<PotionEffect>) ((EntityPlayer) caster).getActivePotionEffects();
				for (int i = 0; i < effects.size(); i ++){
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("slowness")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("speed"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("mining_fatigue")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("haste"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("poison")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("wither")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("blindness")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("nausea")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("hunger")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("saturation"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
					if (effects.get(i).getPotion() == Potion.getPotionFromResourceLocation("weakness")){
						effects.set(i, new PotionEffect(Potion.getPotionFromResourceLocation("strength"),effects.get(i).getDuration(),effects.get(i).getAmplifier()+(int)potency));
					}
				}
			}
		}
	}
}
