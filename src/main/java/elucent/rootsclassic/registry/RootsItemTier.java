package elucent.rootsclassic.registry;

import java.util.function.Supplier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum RootsItemTier implements IItemTier {

  LIVING(2, 192, 6.0f, 2.0f, 18, () -> null),
  ENGRAVED(2, 1050, 5F, 8.0F, 5, () -> null);

  private final int harvestLevel;
  private final int maxUses;
  private final float efficiency;
  private final float attackDamage;
  private final int enchantability;
  private final LazyValue<Ingredient> repairMaterial;

  RootsItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
    this.harvestLevel = harvestLevelIn;
    this.maxUses = maxUsesIn;
    this.efficiency = efficiencyIn;
    this.attackDamage = attackDamageIn;
    this.enchantability = enchantabilityIn;
    this.repairMaterial = new LazyValue<>(repairMaterialIn);
  }

  public int getUses() {
    return this.maxUses;
  }

  public float getSpeed() {
    return this.efficiency;
  }

  public float getAttackDamageBonus() {
    return this.attackDamage;
  }

  public int getLevel() {
    return this.harvestLevel;
  }

  public int getEnchantmentValue() {
    return this.enchantability;
  }

  public Ingredient getRepairIngredient() {
    return this.repairMaterial.get();
  }
}