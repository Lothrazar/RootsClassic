package elucent.rootsclassic.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RootsFoods {

  public static final Food OLD_ROOT = (new Food.Builder()).nutrition(1).saturationMod(0.1F).build();
  public static final Food DRAGONS_EYE = (new Food.Builder()).nutrition(2).saturationMod(0.1F).build();
  public static final Food NIGHTSHADE = (new Food.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new EffectInstance(Effects.POISON, 320), 1.0F).build();
  public static final Food BLACKCURRANT = (new Food.Builder()).nutrition(4).saturationMod(0.4F).build();
  public static final Food REDCURRANT = (new Food.Builder()).nutrition(4).saturationMod(0.4F).build();
  public static final Food WHITECURRANT = (new Food.Builder()).nutrition(4).saturationMod(0.4F).build();
  public static final Food ELDERBERRY = (new Food.Builder()).nutrition(2).saturationMod(0.1F).build();
  public static final Food HEALING_POULTICE = (new Food.Builder()).nutrition(0).saturationMod(0.0F).alwaysEat().build();
  private static final float COOKED_BEEF_SATURATION = 0.8F;
  public static final Food ROOTY_STEW = (new Food.Builder()).nutrition(7).saturationMod(0.2F + COOKED_BEEF_SATURATION).build();
  public static final Food FRUIT_SALAD = (new Food.Builder()).nutrition(8).saturationMod(0.1F + COOKED_BEEF_SATURATION).build();
}
