package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ComponentApple extends ComponentBase {

	public ComponentApple() {
		super(new ResourceLocation(Const.MODID, "apple"), Items.APPLE, 16);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			if (caster instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) caster;
				ArrayList<EffectInstance> effects = new ArrayList<>(player.getActivePotionEffects());
				player.clearActivePotions();
				for (EffectInstance effect : effects) {
					if (effect.getPotion().getName().equals("Soul Fray")) { //TODO: Check if Blood magic uses this name still
						player.addPotionEffect(effect);
					}
					if (effect.getPotion() == Effects.SLOWNESS) {
						player.addPotionEffect(new EffectInstance(Effects.SPEED, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
					if (effect.getPotion() == Effects.MINING_FATIGUE) {
						player.addPotionEffect(new EffectInstance(Effects.HASTE, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
					if (effect.getPotion() == Effects.POISON) {
						player.addPotionEffect(new EffectInstance(Effects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
					if (effect.getPotion() == Effects.WITHER) {
						player.addPotionEffect(new EffectInstance(Effects.REGENERATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
					if (effect.getPotion() == Effects.BLINDNESS) {
						player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
					if (effect.getPotion() == Effects.NAUSEA) {
						player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
					if (effect.getPotion() == Effects.HUNGER) {
						player.addPotionEffect(new EffectInstance(Effects.SATURATION, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
					if (effect.getPotion() == Effects.WEAKNESS) {
						player.addPotionEffect(new EffectInstance(Effects.STRENGTH, effect.getDuration(), effect.getAmplifier() + (int) potency));
					}
				}
			}
		}
	}
}
