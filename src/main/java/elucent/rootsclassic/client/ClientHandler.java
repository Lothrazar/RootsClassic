package elucent.rootsclassic.client;

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
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.datacomponent.SpellDataList;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class ClientHandler {

  public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(Const.modLoc("main"), "sylvan_armor");
  public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(Const.modLoc("main"), "wildwood_armor");

  public static void onClientSetup(final FMLClientSetupEvent event) {
//    ItemProperties.register(RootsRegistry.STAFF.get(), Identifier.withDefaultNamespace("imbued"), (stack, world, livingEntity, unused) ->
//	    stack.has(RootsComponents.SPELL) ? 1.0F : 0.0F); TODO: re-implement imbued state for STAFF
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

  public static void registerItemColors(final RegisterColorHandlersEvent.ItemTintSources event) {
    event.register(Const.modLoc("spell_data"), SpellData.MAP_CODEC);
    event.register(Const.modLoc("spell_data_list"), SpellDataList.MAP_CODEC);
//    event.register((stack, tintIndex) -> {
//      if (stack.has(RootsComponents.SPELL) && stack.getItem() instanceof StaffItem) {
//	      SpellData data = stack.get(RootsComponents.SPELL);
//        Identifier compName = Identifier.tryParse(data.effect());
//        if (compName != null) {
//          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
//          if (comp != null) {
//            if (tintIndex == 2) {
//              return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
//            }
//            if (tintIndex == 1) {
//              return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
//            }
//          }
//        }
//      }
//      return RootsUtil.intColor(255, 255, 255);
//    }, RootsRegistry.STAFF.get());
//    event.register((stack, tintIndex) -> {
//      if (stack.getItem() instanceof CrystalStaffItem && stack.has(RootsComponents.SPELLS)) {
//				SpellData selectedSpell = CrystalStaffItem.getSelectedSpell(stack);
//        String effect = selectedSpell.effect();
//        if (effect != null) {
//          Identifier compName = Identifier.tryParse(effect);
//          if (compName != null) {
//            ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
//            if (comp != null) {
//              if (tintIndex == 2) {
//                return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
//              }
//              if (tintIndex == 1) {
//                return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
//              }
//            }
//          }
//        }
//      }
//      return RootsUtil.intColor(255, 255, 255);
//    }, RootsRegistry.CRYSTAL_STAFF.get());
  }

  public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(ParticleRegistry.MAGIC_TYPE.get(), MagicParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_TYPE.get(), MagicAltarParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_ALTAR_LINE_TYPE.get(), MagicAltarLineParticleData::new);
    event.registerSpriteSet(ParticleRegistry.MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
  }
}
