package elucent.rootsclassic.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum RootsArmorMaterial implements ArmorMaterial {
  SYLVAN("rootsclassic:sylvan", 10, new int[] { 1, 5, 6, 2 }, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
    return null;
  }), WILDWOOD("rootsclassic:wildwood", 15, new int[] { 2, 5, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 0.0F, () -> {
    return null;
  });

  private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
  private final String name;
  private final int maxDamageFactor;
  private final int[] damageReductionAmountArray;
  private final int enchantability;
  private final SoundEvent soundEvent;
  private final float toughness;
  private final float knockbackResistance;
  private final LazyLoadedValue<Ingredient> repairMaterial;

  RootsArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
    this.name = name;
    this.maxDamageFactor = maxDamageFactor;
    this.damageReductionAmountArray = damageReductionAmountArray;
    this.enchantability = enchantability;
    this.soundEvent = soundEvent;
    this.toughness = toughness;
    this.knockbackResistance = knockbackResistance;
    this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
  }

  public int getDurabilityForSlot(EquipmentSlot slotIn) {
    return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
  }

  public int getDefenseForSlot(EquipmentSlot slotIn) {
    return this.damageReductionAmountArray[slotIn.getIndex()];
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