package elucent.rootsclassic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import elucent.rootsclassic.block.imbuer.ImbuerBlockEntity;
import elucent.rootsclassic.client.renderer.block.state.ImbuerRenderState;
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
import org.jspecify.annotations.Nullable;

public class ImbuerBER implements BlockEntityRenderer<ImbuerBlockEntity, ImbuerRenderState> {
  private final ItemModelResolver itemModelResolver;

  public ImbuerBER(BlockEntityRendererProvider.Context context) {
    this.itemModelResolver = context.itemModelResolver();
  }

  @Override
  public ImbuerRenderState createRenderState() {
    return new ImbuerRenderState();
  }

  @Override
  public void extractRenderState(ImbuerBlockEntity blockEntity, ImbuerRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
    BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
    int seed = (int)blockEntity.getBlockPos().asLong();

    this.itemModelResolver
      .updateForTopItem(state.powderRenderState, blockEntity.getStick(),
        ItemDisplayContext.GROUND, blockEntity.getLevel(), null, seed);

    this.itemModelResolver
      .updateForTopItem(state.powderRenderState, blockEntity.getSpellPowder(),
        ItemDisplayContext.GROUND, blockEntity.getLevel(), null, seed);

    state.spin = blockEntity.spin;
  }

  @Override
  public void submit(ImbuerRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
    final ItemStackRenderState stickStack = state.stickRenderState;
    if (!stickStack.isEmpty()) {
      poseStack.pushPose();
      poseStack.translate(0.5, 0.3125, 0.5);
      poseStack.mulPose(Axis.YP.rotationDegrees(state.spin));
      stickStack.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
      poseStack.popPose();
    }
    final ItemStackRenderState dustStack = state.powderRenderState;
    if (!dustStack.isEmpty()) {
      poseStack.pushPose();
      poseStack.translate(0.5, 0.125, (1 / 16F) * 6);
      poseStack.mulPose(Axis.XP.rotationDegrees(90));
      dustStack.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
      poseStack.popPose();
    }
  }
}
