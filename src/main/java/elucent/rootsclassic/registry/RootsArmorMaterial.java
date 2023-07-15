package elucent.rootsclassic.registry;

import java.util.EnumMap;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("deprecation")
public enum RootsArmorMaterial implements ArmorMaterial {

  SYLVAN("rootsclassic:sylvan", 10, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
    map.put(ArmorItem.Type.BOOTS, 1);
    map.put(ArmorItem.Type.LEGGINGS, 5);
    map.put(ArmorItem.Type.CHESTPLATE, 6);
    map.put(ArmorItem.Type.HELMET, 2);
  }), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
    return Ingredient.EMPTY;
  }), WILDWOOD("rootsclassic:wildwood", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
    map.put(ArmorItem.Type.BOOTS, 2);
    map.put(ArmorItem.Type.LEGGINGS, 5);
    map.put(ArmorItem.Type.CHESTPLATE, 7);
    map.put(ArmorItem.Type.HELMET, 3);
  }), 10, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 0.0F, () -> {
    return Ingredient.EMPTY;
  });

  private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
    map.put(ArmorItem.Type.BOOTS, 13);
    map.put(ArmorItem.Type.LEGGINGS, 15);
    map.put(ArmorItem.Type.CHESTPLATE, 16);
    map.put(ArmorItem.Type.HELMET, 11);
  });
  private final String name;
  private final int durabilityMultiplier;
  private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
  private final int enchantability;
  private final SoundEvent soundEvent;
  private final float toughness;
  private final float knockbackResistance;
  private final LazyLoadedValue<Ingredient> repairMaterial;

  RootsArmorMaterial(String name, int maxDamageFactor, EnumMap<ArmorItem.Type, Integer> protectionFunctionForType, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
    this.name = name;
    this.durabilityMultiplier = maxDamageFactor;
    this.protectionFunctionForType = protectionFunctionForType;
    this.enchantability = enchantability;
    this.soundEvent = soundEvent;
    this.toughness = toughness;
    this.knockbackResistance = knockbackResistance;
    this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
  }

  public int getDurabilityForType(ArmorItem.Type type) {
    return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
  }

  public int getDefenseForType(ArmorItem.Type type) {
    return this.protectionFunctionForType.get(type);
  }

  public int getEnchantmentValue() {
    return this.enchantability;
  }

  public SoundEvent getEquipSound() {
    return this.soundEvent;
  }

  public Ingredient getRepairIngredient() {
    return this.repairMaterial.get();
  }

  @OnlyIn(Dist.CLIENT)
  public String getName() {
    return this.name;
  }

  public float getToughness() {
    return this.toughness;
  }

  /**
   * Gets the percentage of knockback resistance provided by armor of the material.
   */
  public float getKnockbackResistance() {
    return this.knockbackResistance;
  }
}