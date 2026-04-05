package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class RootsArmorMaterial {
	public static final Holder<ArmorMaterial> SYLVAN = register("sylvan", Util.make(new EnumMap<>(ArmorType.class), (map) -> {
		map.put(ArmorType.BOOTS, 1);
		map.put(ArmorType.LEGGINGS, 5);
		map.put(ArmorType.CHESTPLATE, 6);
		map.put(ArmorType.BODY, 6);
		map.put(ArmorType.HELMET, 2);
	}), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY);
	public static final Holder<ArmorMaterial> WILDWOOD = register("wildwood", Util.make(new EnumMap<>(ArmorType.class), (map) -> {
		map.put(ArmorType.BOOTS, 2);
		map.put(ArmorType.LEGGINGS, 5);
		map.put(ArmorType.CHESTPLATE, 7);
		map.put(ArmorType.BODY, 7);
		map.put(ArmorType.HELMET, 3);
	}), 10, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 0.0F, () -> Ingredient.EMPTY);

	private static Holder<ArmorMaterial> register(
		String pName,
		EnumMap<ArmorType, Integer> pDefense,
		int pEnchantmentValue,
		Holder<SoundEvent> pEquipSound,
		float pToughness,
		float pKnockbackResistance,
		Supplier<Ingredient> pRepairIngredient
	) {
		List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Identifier.tryParse(pName)));
		return register(pName, pDefense, pEnchantmentValue, pEquipSound, pToughness, pKnockbackResistance, pRepairIngredient, list);
	}

	private static Holder<ArmorMaterial> register(
		String pName,
		EnumMap<ArmorType, Integer> pDefense,
		int pEnchantmentValue,
		Holder<SoundEvent> pEquipSound,
		float pToughness,
		float pKnockbackResistance,
		Supplier<Ingredient> pRepairIngridient,
		List<ArmorMaterial.Layer> pLayers
	) {
		EnumMap<ArmorType, Integer> enummap = new EnumMap<>(ArmorType.class);

		for (ArmorType armoritem$type : ArmorType.values()) {
			enummap.put(armoritem$type, pDefense.get(armoritem$type));
		}

		return Registry.registerForHolder(
			BuiltInRegistries.ARMOR_MATERIAL,
			Const.modLoc(pName),
			new ArmorMaterial(enummap, pEnchantmentValue, pEquipSound, pRepairIngridient, pLayers, pToughness, pKnockbackResistance)
		);
	}
}