package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

public class RootsArmorMaterial {

  public static final ArmorMaterial SYLVAN = new ArmorMaterial(6, Util.make(new EnumMap<>(ArmorType.class), map -> {
    map.put(ArmorType.BOOTS, 1);
    map.put(ArmorType.LEGGINGS, 5);
    map.put(ArmorType.CHESTPLATE, 6);
    map.put(ArmorType.BODY, 6);
    map.put(ArmorType.HELMET, 2);
  }),
    20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, RootsTags.REPAIRS_SYLVAN,
    createAsset("sylvan"));

  public static final ArmorMaterial WILDWOOD = new ArmorMaterial(6, Util.make(new EnumMap<>(ArmorType.class), map -> {
    map.put(ArmorType.BOOTS, 2);
    map.put(ArmorType.LEGGINGS, 5);
    map.put(ArmorType.CHESTPLATE, 7);
    map.put(ArmorType.BODY, 7);
    map.put(ArmorType.HELMET, 3);
  }),
    10, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 0.0F, RootsTags.REPAIRS_WILDWOOD,
    createAsset("wildwood"));

  private static ResourceKey<EquipmentAsset> createAsset(String name) {
    return ResourceKey.create(EquipmentAssets.ROOT_ID, Const.modLoc(name));
  }
}