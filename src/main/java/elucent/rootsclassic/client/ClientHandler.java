package elucent.rootsclassic.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.renderer.entity.PhantomSkeletonRenderer;
import elucent.rootsclassic.client.renderer.tile.AltarTESR;
import elucent.rootsclassic.client.renderer.tile.BrazierTESR;
import elucent.rootsclassic.client.renderer.tile.ImbuerTESR;
import elucent.rootsclassic.client.renderer.tile.MortarTESR;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class ClientHandler {

  public static void onClientSetup(final FMLClientSetupEvent event) {
    BlockEntityRenderers.register(RootsRegistry.MORTAR_TILE.get(), MortarTESR::new);
    ClientRegistry.bindTileEntityRenderer(RootsRegistry.IMBUER_TILE.get(), ImbuerTESR::new);
    ClientRegistry.bindTileEntityRenderer(RootsRegistry.ALTAR_TILE.get(), AltarTESR::new);
    ClientRegistry.bindTileEntityRenderer(RootsRegistry.BRAZIER_TILE.get(), BrazierTESR::new);
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.MIDNIGHT_BLOOM.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.FLARE_ORCHID.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.RADIANT_DAISY.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.ALTAR.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.BRAZIER.get(), RenderType.cutout());
    RenderingRegistry.registerEntityRenderingHandler(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);
//    ItemProperties.register(RootsRegistry.STAFF.get(), new ResourceLocation("imbued"), (stack, world, livingEntity) -> stack.getTag() != null && stack.getTag().contains(Const.NBT_EFFECT) ? 1.0F : 0.0F);
  }

  public static void registerItemColors(final ColorHandlerEvent.Item event) {
    ItemColors colors = event.getItemColors();
    colors.register((stack, tintIndex) -> {
      if (stack.hasTag() && stack.getItem() instanceof StaffItem) {
        CompoundTag tag = stack.getTag();
        ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
        if (compName != null) {
          ComponentBase comp = ComponentManager.getComponentFromName(compName);
          if (comp != null) {
            if (tintIndex == 2) {
              return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
            }
            if (tintIndex == 1) {
              return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
            }
          }
        }
      }
      return RootsUtil.intColor(255, 255, 255);
    }, RootsRegistry.STAFF.get());
    colors.register((stack, tintIndex) -> {
      if (stack.getItem() instanceof CrystalStaffItem && stack.hasTag()) {
        String effect = CrystalStaffItem.getEffect(stack);
        if (effect != null) {
          ResourceLocation compName = ResourceLocation.tryParse(effect);
          if (compName != null) {
            ComponentBase comp = ComponentManager.getComponentFromName(compName);
            if (comp != null) {
              if (tintIndex == 2) {
                return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
              }
              if (tintIndex == 1) {
                return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
              }
            }
          }
        }
      }
      return RootsUtil.intColor(255, 255, 255);
    }, RootsRegistry.CRYSTAL_STAFF.get());
  }
}
