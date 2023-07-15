package elucent.rootsclassic.client;

import com.lothrazar.library.util.RenderUtil;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.client.model.WildwoodArmorModel;
import elucent.rootsclassic.client.particles.MagicAltarLineParticleData;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.client.renderer.block.AltarBER;
import elucent.rootsclassic.client.renderer.block.BrazierBER;
import elucent.rootsclassic.client.renderer.block.ImbuerBER;
import elucent.rootsclassic.client.renderer.block.MortarBER;
import elucent.rootsclassic.client.renderer.entity.AcceleratorRenderer;
import elucent.rootsclassic.client.renderer.entity.PhantomSkeletonRenderer;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {

  public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "sylvan_armor");
  public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "wildwood_armor");

  public static void onClientSetup(final FMLClientSetupEvent event) {
    ItemProperties.register(RootsRegistry.STAFF.get(), new ResourceLocation("imbued"), (stack, world, livingEntity, unused) -> stack.getTag() != null && stack.getTag().contains(Const.NBT_EFFECT) ? 1.0F : 0.0F);
  }

  public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(RootsRegistry.MORTAR_TILE.get(), MortarBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.IMBUER_TILE.get(), ImbuerBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.ALTAR_TILE.get(), AltarBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.BRAZIER_TILE.get(), BrazierBER::new);
    event.registerEntityRenderer(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);
    event.registerEntityRenderer(RootsEntities.ENTITY_ACCELERATOR.get(), AcceleratorRenderer::new);
    event.registerEntityRenderer(RootsEntities.TILE_ACCELERATOR.get(), AcceleratorRenderer::new);
  }

  public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(SYLVAN_ARMOR, SylvanArmorModel::createArmorDefinition);
    event.registerLayerDefinition(WILDWOOD_ARMOR, WildwoodArmorModel::createArmorDefinition);
  }

  public static void registerItemColors(final RegisterColorHandlersEvent.Item event) {
    event.register((stack, tintIndex) -> {
      if (stack.hasTag() && stack.getItem() instanceof StaffItem) {
        CompoundTag tag = stack.getTag();
        ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
        if (compName != null) {
          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get().getValue(compName);
          if (comp != null) {
            if (tintIndex == 2) {
              return RenderUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
            }
            if (tintIndex == 1) {
              return RenderUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
            }
          }
        }
      }
      return RenderUtil.intColor(255, 255, 255);
    }, RootsRegistry.STAFF.get());
    event.register((stack, tintIndex) -> {
      if (stack.getItem() instanceof CrystalStaffItem && stack.hasTag()) {
        String effect = CrystalStaffItem.getEffect(stack);
        if (effect != null) {
          ResourceLocation compName = ResourceLocation.tryParse(effect);
          if (compName != null) {
            ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get().getValue(compName);
            if (comp != null) {
              if (tintIndex == 2) {
                return RenderUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
              }
              if (tintIndex == 1) {
                return RenderUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
              }
            }
          }
        }
      }
      return RenderUtil.intColor(255, 255, 255);
    }, RootsRegistry.CRYSTAL_STAFF.get());
  }

  public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(ParticleRegistry.MAGIC_TYPE.get(), MagicParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_TYPE.get(), MagicAltarParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_LINE_TYPE.get(), MagicAltarLineParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
  }
}
