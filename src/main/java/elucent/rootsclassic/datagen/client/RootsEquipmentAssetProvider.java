package elucent.rootsclassic.datagen.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsArmorMaterial;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

public class RootsEquipmentAssetProvider extends EquipmentAssetProvider {
  public RootsEquipmentAssetProvider(PackOutput output) {
    super(output);
  }

  @Override
  protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
    output.accept(RootsArmorMaterial.WILDWOOD.assetId(), onlyHumanoid("wildwood"));
    output.accept(RootsArmorMaterial.SYLVAN.assetId(), onlyHumanoid("sylvan"));
  }

  public static EquipmentClientInfo onlyHumanoid(String name) {
    return EquipmentClientInfo.builder()
      .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
        EquipmentClientInfo.Layer.leatherDyeable(Const.modLoc(name), false)
      ).build();
  }
}
