package elucent.rootsclassic.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.extension.SylvanItemExtension;
import elucent.rootsclassic.client.extension.WildwoodItemExtension;
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
import elucent.rootsclassic.client.tintsource.CrystalStaffSource;
import elucent.rootsclassic.client.tintsource.StaffSource;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {

  public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(Const.modLoc("main"), "sylvan_armor");
  public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(Const.modLoc("main"), "wildwood_armor");

  @SubscribeEvent
  public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(RootsRegistry.MORTAR_TILE.get(), MortarBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.IMBUER_TILE.get(), ImbuerBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.ALTAR_TILE.get(), AltarBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.BRAZIER_TILE.get(), BrazierBER::new);
    event.registerEntityRenderer(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);
    event.registerEntityRenderer(RootsEntities.ENTITY_ACCELERATOR.get(), AcceleratorRenderer::new);
    event.registerEntityRenderer(RootsEntities.TILE_ACCELERATOR.get(), AcceleratorRenderer::new);
  }

  @SubscribeEvent
  public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(SYLVAN_ARMOR, SylvanArmorModel::createArmorDefinition);
    event.registerLayerDefinition(WILDWOOD_ARMOR, WildwoodArmorModel::createArmorDefinition);
  }

  @SubscribeEvent
  public static void registerItemColors(final RegisterColorHandlersEvent.ItemTintSources event) {
    event.register(Const.modLoc("staff"), StaffSource.MAP_CODEC);
    event.register(Const.modLoc("crystal_staff"), CrystalStaffSource.MAP_CODEC);
  }

  @SubscribeEvent
  public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(ParticleRegistry.MAGIC_TYPE.get(), MagicParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_TYPE.get(), MagicAltarParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_LINE_TYPE.get(), MagicAltarLineParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
  }

  @SubscribeEvent
  public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
    event.registerItem(new WildwoodItemExtension(), RootsRegistry.WILDWOOD_BOOTS, RootsRegistry.WILDWOOD_LEGGINGS,
      RootsRegistry.WILDWOOD_PLATE, RootsRegistry.WILDWOOD_MASK);
    event.registerItem(new SylvanItemExtension(), RootsRegistry.SYLVAN_BOOTS, RootsRegistry.SYLVAN_TUNIC,
      RootsRegistry.SYLVAN_ROBE, RootsRegistry.SYLVAN_HOOD);
  }
}
