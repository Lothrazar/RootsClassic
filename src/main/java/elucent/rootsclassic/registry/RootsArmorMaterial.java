package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class RootsArmorMaterial {
	public static final Holder<ArmorMaterial> SYLVAN = register("sylvan", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
		map.put(ArmorItem.Type.BOOTS, 1);
		map.put(ArmorItem.Type.LEGGINGS, 5);
		map.put(ArmorItem.Type.CHESTPLATE, 6);
		map.put(ArmorItem.Type.BODY, 6);
		map.put(ArmorItem.Type.HELMET, 2);
	}), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY);
	public static final Holder<ArmorMaterial> WILDWOOD = register("wildwood", Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
		map.put(ArmorItem.Type.BOOTS, 2);
		map.put(ArmorItem.Type.LEGGINGS, 5);
		map.put(ArmorItem.Type.CHESTPLATE, 7);
		map.put(ArmorItem.Type.BODY, 7);
		map.put(ArmorItem.Type.HELMET, 3);
	}), 10, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 0.0F, () -> Ingredient.EMPTY);

	private static Holder<ArmorMaterial> register(
		String pName,
		EnumMap<ArmorItem.Type, Integer> pDefense,
		int pEnchantmentValue,
		Holder<SoundEvent> pEquipSound,
		float pToughness,
		float pKnockbackResistance,
		Supplier<Ingredient> pRepairIngredient
	) {
		List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.tryParse(pName)));
		return register(pName, pDefense, pEnchantmentValue, pEquipSound, pToughness, pKnockbackResistance, pRepairIngredient, list);
	}

	private static Holder<ArmorMaterial> register(
		String pName,
		EnumMap<ArmorItem.Type, Integer> pDefense,
		int pEnchantmentValue,
		Holder<SoundEvent> pEquipSound,
		float pToughness,
		float pKnockbackResistance,
		Supplier<Ingredient> pRepairIngridient,
		List<ArmorMaterial.Layer> pLayers
	) {
		EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

		for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
			enummap.put(armoritem$type, pDefense.get(armoritem$type));
		}

		return Registry.registerForHolder(
			BuiltInRegistries.ARMOR_MATERIAL,
			Const.modLoc(pName),
			new ArmorMaterial(enummap, pEnchantmentValue, pEquipSound, pRepairIngridient, pLayers, pToughness, pKnockbackResistance)
		);
	}
}