package elucent.rootsclassic.client;

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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		ClientRegistry.bindTileEntityRenderer(RootsRegistry.MORTAR_TILE.get(), MortarTESR::new);
		ClientRegistry.bindTileEntityRenderer(RootsRegistry.IMBUER_TILE.get(), ImbuerTESR::new);
		ClientRegistry.bindTileEntityRenderer(RootsRegistry.ALTAR_TILE.get(), AltarTESR::new);
		ClientRegistry.bindTileEntityRenderer(RootsRegistry.BRAZIER_TILE.get(), BrazierTESR::new);

		RenderTypeLookup.setRenderLayer(RootsRegistry.MIDNIGHT_BLOOM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RootsRegistry.FLARE_ORCHID.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RootsRegistry.RADIANT_DAISY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RootsRegistry.ALTAR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RootsRegistry.BRAZIER.get(), RenderType.getCutout());

		RenderingRegistry.registerEntityRenderingHandler(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);


		ItemModelsProperties.registerProperty(RootsRegistry.STAFF.get(), new ResourceLocation("imbued"), (stack, world, livingEntity) ->
				stack.getTag() != null && stack.getTag().contains(Const.NBT_EFFECT) ? 1.0F : 0.0F);
	}

	public static void registerItemColors(final ColorHandlerEvent.Item event) {
		ItemColors colors = event.getItemColors();

		colors.register((stack, tintIndex) -> {
			if (stack.hasTag() && stack.getItem() instanceof StaffItem) {
				CompoundNBT tag = stack.getTag();
				ResourceLocation compName = ResourceLocation.tryCreate(tag.getString(Const.NBT_EFFECT));
				if(compName != null) {
					ComponentBase comp = ComponentManager.getComponentFromName(compName);
					if(comp != null) {
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
				if(effect != null) {
					ResourceLocation compName = ResourceLocation.tryCreate(effect);
					if(compName != null) {
						ComponentBase comp = ComponentManager.getComponentFromName(compName);
						if(comp != null) {
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
