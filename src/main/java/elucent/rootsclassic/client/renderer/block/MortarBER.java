package elucent.rootsclassic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import elucent.rootsclassic.block.mortar.MortarBlockEntity;
import elucent.rootsclassic.client.renderer.block.state.MortarRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class MortarBER implements BlockEntityRenderer<MortarBlockEntity, MortarRenderState> {
  private final ItemModelResolver itemModelResolver;

  public MortarBER(BlockEntityRendererProvider.Context context) {
    this.itemModelResolver = context.itemModelResolver();
  }

  @Override
  public MortarRenderState createRenderState() {
    return new MortarRenderState();
  }

  @Override
  public void extractRenderState(MortarBlockEntity blockEntity, MortarRenderState state, float partialTicks,
                                 Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
    BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
    int seed = (int)blockEntity.getBlockPos().asLong();
    state.items = new ArrayList<>();

    ResourceHandler<ItemResource> inventory = blockEntity.inventory;
    for (int slot = 0; slot < inventory.size(); slot++) {
      ItemStackRenderState itemState = new ItemStackRenderState();
      this.itemModelResolver
        .updateForTopItem(itemState, inventory.getResource(slot).toStack(inventory.getAmountAsInt(slot)),
          ItemDisplayContext.GROUND, blockEntity.getLevel(), null, seed + slot);
      state.items.add(itemState);
    }
  }

  @Override
  public void submit(MortarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
    for (int i = 0; i < state.items.size(); i++) {
      ItemStackRenderState itemState = state.items.get(i);
      poseStack.pushPose();
      Random random = new Random(i);
      poseStack.translate(0.475 + random.nextFloat() / 20.0, 0.05 + random.nextFloat() / 20.0, 0.475 + random.nextFloat() / 20.0);
      poseStack.scale(0.65F, 0.65F, 0.65F);
      poseStack.mulPose(Axis.YP.rotationDegrees(random.nextInt(360)));
      itemState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
      poseStack.popPose();
    }
  }
}
