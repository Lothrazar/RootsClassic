package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class RootsDamageTypes {

  public static final ResourceKey<DamageType> CACTUS = register("cactus");
  public static final ResourceKey<DamageType> FIRE = register("fire"); //Bypasses armor and sets on fire
  public static final ResourceKey<DamageType> GENERIC = register("generic"); //Bypasses armor
  public static final ResourceKey<DamageType> WITHER = register("wither"); //Bypasses armor

  private static ResourceKey<DamageType> register(String name) {
    return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Const.MODID, name));
  }
}
