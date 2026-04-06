package elucent.rootsclassic.datagen.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.client.tintsource.CrystalStaffSource;
import elucent.rootsclassic.client.tintsource.StaffSource;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.NonNull;

public class RootsModelProvider extends ModelProvider {
  public RootsModelProvider(PackOutput output) {
    super(output, Const.MODID);
  }

  public static final ModelTemplate THREE_LAYERED_ITEM = ModelTemplates.createItem("handheld", TextureSlot.LAYER0, TextureSlot.LAYER1, TextureSlot.LAYER2);

  @SuppressWarnings("deprecation")
  @Override
  protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
    // Blocks
    blockModels.createNonTemplateModelBlock(RootsRegistry.MORTAR.get());
    itemModels.generateFlatItem(RootsRegistry.MORTAR_ITEM.get(), ModelTemplates.FLAT_ITEM);
    blockModels.createNonTemplateModelBlock(RootsRegistry.ALTAR.get());
    blockModels.registerSimpleItemModel(RootsRegistry.ALTAR_ITEM.get(), ModelLocationUtils.getModelLocation(RootsRegistry.ALTAR.get()));
    blockModels.createNonTemplateModelBlock(RootsRegistry.BRAZIER.get());
    blockModels.registerSimpleItemModel(RootsRegistry.BRAZIER_ITEM.get(), ModelLocationUtils.getModelLocation(RootsRegistry.BRAZIER.get()));
    blockModels.createNonTemplateModelBlock(RootsRegistry.IMBUER.get());
    itemModels.generateFlatItem(RootsRegistry.IMBUER_ITEM.get(), ModelTemplates.FLAT_ITEM);
    blockModels.createNonTemplateModelBlock(RootsRegistry.MUNDANE_STANDING_STONE.get());
    blockModels.registerSimpleItemModel(RootsRegistry.MUNDANE_STANDING_STONE_ITEM.get(), ModelLocationUtils.getModelLocation(RootsRegistry.MUNDANE_STANDING_STONE_ITEM.get()));
    createAttunedStandingStone(blockModels, RootsRegistry.ACCELERATOR_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.AESTHETIC_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.ATTUNED_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.ENTANGLER_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.GROWER_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.HEALER_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.IGNITER_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.REPULSOR_STANDING_STONE);
    createAttunedStandingStone(blockModels, RootsRegistry.VACUUM_STANDING_STONE);
    blockModels.createCrossBlockWithDefaultItem(RootsRegistry.MIDNIGHT_BLOOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
    blockModels.createCrossBlockWithDefaultItem(RootsRegistry.FLARE_ORCHID.get(), BlockModelGenerators.PlantType.NOT_TINTED);
    blockModels.createCrossBlockWithDefaultItem(RootsRegistry.RADIANT_DAISY.get(), BlockModelGenerators.PlantType.NOT_TINTED);

    // Items
    for (DeferredHolder<Item, ? extends Item> holder : RootsRegistry.ITEMS.getEntries()) {
      if (holder.get() instanceof BlockItem) continue; // BlockItems are handled by block model generation

      if (holder.is(RootsRegistry.BARK_KNIFE) || holder.is(RootsRegistry.ENGRAVED_BLADE) ||
        holder.is(RootsRegistry.LIVING_SWORD) || holder.is(RootsRegistry.LIVING_SHOVEL) ||
        holder.is(RootsRegistry.LIVING_PICKAXE) || holder.is(RootsRegistry.LIVING_AXE) ||
        holder.is(RootsRegistry.LIVING_HOE)
      ) {
        itemModels.generateFlatItem(holder.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
      } else if (holder.is(RootsRegistry.CRYSTAL_STAFF)) {
        Identifier model = THREE_LAYERED_ITEM.create(holder.get(), TextureMapping.layered(
          TextureMapping.getItemTexture(holder.get()),
          TextureMapping.getItemTexture(holder.get(), "_glow1"),
          TextureMapping.getItemTexture(holder.get(), "_glow2")), itemModels.modelOutput);
        itemModels.itemModelOutput.accept(holder.get(), ItemModelUtils.tintedModel(model, ItemModelGenerators.BLANK_LAYER, new CrystalStaffSource(), new CrystalStaffSource(1)));
      } else if (holder.is(RootsRegistry.STAFF)) {
        ItemModel.Unbaked baseModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(holder.get(), "_base", ModelTemplates.FLAT_ITEM));

        Identifier staff = THREE_LAYERED_ITEM.create(holder.get(), TextureMapping.layered(
          TextureMapping.getItemTexture(holder.get(), "_base"),
          TextureMapping.getItemTexture(holder.get(), "_glow2"),
          TextureMapping.getItemTexture(holder.get(), "_glow1")), itemModels.modelOutput);
        ItemModel.Unbaked model = ItemModelUtils.tintedModel(staff, ItemModelGenerators.BLANK_LAYER, new StaffSource(), new StaffSource(1));

        itemModels.itemModelOutput
          .accept(
            holder.get(),
            ItemModelUtils.conditional(
              ItemModelUtils.hasComponent(RootsComponents.SPELL.get()),
              model,
              baseModel
            )
          );
      } else {
        itemModels.generateFlatItem(holder.get(), ModelTemplates.FLAT_ITEM);
      }
    }
  }

  private final ModelTemplate BASE_POWERED_STANDING_STONE = ModelTemplates.create("rootsclassic:base_powered_standing_stone", TextureSlot.LAYER0, TextureSlot.PARTICLE);
  private final ModelTemplate STANDING_STONE_ITEM = ModelTemplates.createItem("rootsclassic:standing_stone_item", TextureSlot.LAYER0, TextureSlot.PARTICLE);

  private void createAttunedStandingStone(BlockModelGenerators blockModels, DeferredBlock<? extends AttunedStandingStoneBlock> block) {
    Material texture = TextureMapping.getBlockTexture(block.get());
    TextureMapping mapping = new TextureMapping();
    mapping.put(TextureSlot.LAYER0, texture);
    mapping.put(TextureSlot.PARTICLE, texture);

    Identifier model = BASE_POWERED_STANDING_STONE.create(block.get(), mapping, blockModels.modelOutput);

    blockModels.createDoubleBlock(block.get(),
      BlockModelGenerators.plainVariant(model),
      BlockModelGenerators.plainVariant(Const.modLoc("block/attuned_standing_stone_bottom"))
    );

    TextureMapping itemMapping = new TextureMapping();
    itemMapping.put(TextureSlot.LAYER0, texture);
    itemMapping.put(TextureSlot.PARTICLE, texture);
    Identifier baseModel = STANDING_STONE_ITEM.create(block.asItem(), itemMapping, blockModels.modelOutput);
    blockModels.itemModelOutput.accept(block.get().asItem(), ItemModelUtils.plainModel(baseModel));
  }
}
