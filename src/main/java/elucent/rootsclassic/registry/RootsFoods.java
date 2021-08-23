package elucent.rootsclassic.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RootsFoods {
	public static final Food OLD_ROOT = (new Food.Builder()).hunger(1).saturation(0.1F).build();
	public static final Food DRAGONS_EYE = (new Food.Builder()).hunger(2).saturation(0.1F).build();
	public static final Food NIGHTSHADE = (new Food.Builder()).hunger(2).saturation(0.1F).effect(() -> new EffectInstance(Effects.POISON, 320), 1.0F).build();
	public static final Food BLACKCURRANT = (new Food.Builder()).hunger(4).saturation(0.4F).build();
	public static final Food REDCURRANT = (new Food.Builder()).hunger(4).saturation(0.4F).build();
	public static final Food WHITECURRANT = (new Food.Builder()).hunger(4).saturation(0.4F).build();
	public static final Food ELDERBERRY = (new Food.Builder()).hunger(2).saturation(0.1F).build();
	public static final Food HEALING_POULTICE = (new Food.Builder()).hunger(0).saturation(0.0F).setAlwaysEdible().build();
	private static final float COOKED_BEEF_SATURATION = 0.8F;
	public static final Food ROOTY_STEW = (new Food.Builder()).hunger(7).saturation(0.2F + COOKED_BEEF_SATURATION).build();
	public static final Food FRUIT_SALAD = (new Food.Builder()).hunger(8).saturation(0.1F + COOKED_BEEF_SATURATION).build();

}
