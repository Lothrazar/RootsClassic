package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.UUID;

import elucent.roots.ConfigManager;
import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentRose extends ComponentBase {
	public ComponentRose(){
		super("rosebush", "Rose's Thorns", Blocks.DOUBLE_PLANT,4,14);
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-size,y-size,z-size,x+size,y+size,z+size));
			int damageDealt = 0;
			for (int i = 0; i < targets.size(); i ++){
				if (targets.get(i).getUniqueID() != caster.getUniqueID()){
					if (targets.get(i) instanceof EntityPlayer && ConfigManager.disablePVP){
						
					}
					else {
						if (caster instanceof EntityPlayer){
							if (!((EntityPlayer)caster).hasAchievement(RegistryManager.achieveSpellRose)){
								PlayerManager.addAchievement(((EntityPlayer)caster), RegistryManager.achieveSpellRose);
							}
						}
						targets.get(i).attackEntityFrom(DamageSource.cactus, (int)(7+4*potency));
						targets.get(i).attackEntityAsMob(caster);
						targets.get(i).setLastAttacker(caster);
						targets.get(i).setRevengeTarget((EntityLivingBase)caster);
					}
				}
			}
			if (damageDealt > 80){
				if (caster instanceof EntityPlayer){
					if (!((EntityPlayer)caster).hasAchievement(RegistryManager.achieveLotsDamage)){
						PlayerManager.addAchievement(((EntityPlayer)caster), RegistryManager.achieveLotsDamage);
					}
				}
			}
		}
	}
}
