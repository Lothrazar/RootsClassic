package elucent.rootsclassic.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class RootsFoods {

  public static final FoodProperties OLD_ROOT = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).build();
  public static final FoodProperties DRAGONS_EYE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
  public static final FoodProperties NIGHTSHADE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 320), 1.0F).build();
  public static final FoodProperties BLACKCURRANT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.4F).build();
  public static final FoodProperties REDCURRANT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.4F).build();
  public static final FoodProperties WHITECURRANT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.4F).build();
  public static final FoodProperties ELDERBERRY = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
  public static final FoodProperties HEALING_POULTICE = (new FoodProperties.Builder()).nutrition(0).saturationMod(0.0F).alwaysEat().build();
  private static final float COOKED_BEEF_SATURATION = 0.8F;
  public static final FoodProperties ROOTY_STEW = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.2F + COOKED_BEEF_SATURATION).build();
  public static final FoodProperties FRUIT_SALAD = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.1F + COOKED_BEEF_SATURATION).build();
}
