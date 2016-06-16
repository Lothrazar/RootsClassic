package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Collection;
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
				ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>(((EntityPlayer)caster).getActivePotionEffects());
				((EntityPlayer)caster).clearActivePotions();
				for (int i = 0; i < effects.size(); i ++){
					PotionEffect effect = effects.get(i);
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("slowness")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("speed"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("mining_fatigue")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("haste"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("poison")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("wither")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("blindness")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("nausea")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("hunger")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("saturation"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
					if (effect.getPotion() == Potion.getPotionFromResourceLocation("weakness")){
						((EntityPlayer)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("strength"),effect.getDuration(),effect.getAmplifier()+(int)potency));
					}
				}
			}
		}
	}
}
