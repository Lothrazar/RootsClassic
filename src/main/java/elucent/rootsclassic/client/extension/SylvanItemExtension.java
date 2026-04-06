package elucent.rootsclassic.client.extension;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SylvanItemExtension implements IClientItemExtensions {
  private static final SylvanArmorModel HEAD_MODEL = new SylvanArmorModel(SylvanArmorModel.createArmorDefinition().bakeRoot(), ArmorType.HELMET);
  private static final SylvanArmorModel CHEST_MODEL = new SylvanArmorModel(SylvanArmorModel.createArmorDefinition().bakeRoot(), ArmorType.CHESTPLATE);
  private static final SylvanArmorModel LEGGINGS_MODEL = new SylvanArmorModel(SylvanArmorModel.createArmorDefinition().bakeRoot(), ArmorType.LEGGINGS);
  private static final SylvanArmorModel FEET_MODEL = new SylvanArmorModel(SylvanArmorModel.createArmorDefinition().bakeRoot(), ArmorType.BOOTS);

  @Override
  public @Nullable Identifier getArmorTexture(@NonNull ItemStack stack, EquipmentClientInfo.@NonNull LayerType type,
                                              EquipmentClientInfo.@NonNull Layer layer, @NonNull Identifier _default) {
    return Const.modLoc("textures/models/armor/sylvan.png");
  }

  @Override
  public @NonNull Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.@NonNull LayerType layerType, @NonNull Model original) {
    Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
    EquipmentSlot slot = equippable != null ? equippable.slot() : null;

    SylvanArmorModel model = switch (slot) {
      case HEAD -> HEAD_MODEL;
      case CHEST -> CHEST_MODEL;
      case LEGS -> LEGGINGS_MODEL;
      case FEET -> FEET_MODEL;
      default -> null;
    };
    if (model == null) {
      return original;
    }

    model.head.visible = slot == EquipmentSlot.HEAD;
    model.body.visible = slot == EquipmentSlot.CHEST;
    model.rightArm.visible = slot == EquipmentSlot.CHEST;
    model.leftArm.visible = slot == EquipmentSlot.CHEST;
    model.rightLeg.visible = slot == EquipmentSlot.LEGS;
    model.leftLeg.visible = slot == EquipmentSlot.LEGS;
    model.rightFoot.visible = slot == EquipmentSlot.FEET;
    model.leftFoot.visible = slot == EquipmentSlot.FEET;

    model.updateRotations();
    return model;
  }
}
