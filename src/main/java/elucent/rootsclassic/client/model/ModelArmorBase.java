package elucent.rootsclassic.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.equipment.ArmorType;

public class ModelArmorBase extends HumanoidModel<HumanoidRenderState> {

  public final ArmorType slot;
  public float armorScale = 1.05f;
  public final ModelPart rightFoot;
  public final ModelPart leftFoot;

  public ModelArmorBase(ModelPart root, ArmorType armorType) {
    super(root);
    this.rightFoot = root.getChild("right_foot");
    this.leftFoot = root.getChild("left_foot");
    this.slot = armorType;
  }

  public static MeshDefinition createArmorMesh() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();
    partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.ZERO);
    partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.ZERO);
    return meshdefinition;
  }

  @Override
  public void setupAnim(HumanoidRenderState state) {
    super.setupAnim(state);
  }

//  @Override
//  public void renderToBuffer(PoseStack poseStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
//    poseStack.pushPose();
//    poseStack.scale(armorScale, armorScale, armorScale);
//    this.setHeadRotation();
//    this.setChestRotation();
//    this.setLegsRotation();
//    this.setBootRotation();
//    head.visible = slot == ArmorType.HELMET;
//    body.visible = slot == ArmorType.CHESTPLATE;
//    rightArm.visible = slot == ArmorType.CHESTPLATE;
//    leftArm.visible = slot == ArmorType.CHESTPLATE;
//    rightLeg.visible = slot == ArmorType.LEGGINGS;
//    leftLeg.visible = slot == ArmorType.LEGGINGS;
//    rightFoot.visible = slot == ArmorType.BOOTS;
//    leftFoot.visible = slot == ArmorType.BOOTS;
//    if (this.young) {
//      float f = 2.0F;
//      poseStack.pushPose();
//      poseStack.scale(1.5F / f, 1.5F / f, 1.5F / f);
//      poseStack.translate(0.0F, 16.0F * 1, 0.0F);
//      head.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//      poseStack.popPose();
//      poseStack.pushPose();
//      poseStack.scale(1.0F / f, 1.0F / f, 1.0F / f);
//      poseStack.translate(0.0F, 24.0F * 1, 0.0F);
//      body.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//      poseStack.popPose();
//    }
//    else {
//      head.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//      if (crouching) {
//        poseStack.translate(0.0F, 0.2F, 0.0F);
//      }
//      body.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//      poseStack.pushPose();
//      if (crouching) {
//        poseStack.translate(0.0F, -0.15F, 0.0F);
//      }
//      rightArm.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//      leftArm.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//      poseStack.popPose();
//    }
//    poseStack.translate(0.0F, 1.25F, 0.0F);
//    if (crouching) {
//      poseStack.translate(0.0F, -0.15F, 0.05F);
//    }
//    rightLeg.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//    leftLeg.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//    rightFoot.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//    leftFoot.render(poseStack, bufferIn, packedLightIn, packedOverlayIn, color);
//    poseStack.popPose();
//  }
//
//  public void setHeadRotation() {
//    setRotation(head, head.xRot, head.yRot, head.zRot);
//  }
//
//  public void setChestRotation() {
//    /* if (e instanceof EntityPlayer){ ((EntityPlayer)e).get } */
//    this.body.y = body.y - 1;
//    this.rightArm.x = rightArm.x + 5;
//    this.rightArm.y = rightArm.y - 1;
//    this.leftArm.x = leftArm.x - 5;
//    this.leftArm.y = leftArm.y - 1;
//    setRotation(body, body.xRot, body.yRot, body.zRot);
//    setRotation(rightArm, rightArm.xRot, rightArm.yRot, rightArm.zRot);
//    setRotation(leftArm, leftArm.xRot, leftArm.yRot, leftArm.zRot);
//  }
//
//  public void setLegsRotation() {
//    this.rightLeg.x = rightLeg.x + 2;
//    this.rightLeg.y = rightLeg.y - 22;
//    this.leftLeg.x = leftLeg.x - 2;
//    this.leftLeg.y = leftLeg.y - 22;
//    setRotation(rightLeg, rightLeg.xRot, rightLeg.yRot, rightLeg.zRot);
//    setRotation(leftLeg, leftLeg.xRot, leftLeg.yRot, leftLeg.zRot);
//  }
//
//  public void setBootRotation() {
//    this.rightFoot.y = rightLeg.y - 0;
//    this.rightFoot.z = rightLeg.z;
//    this.leftFoot.y = leftLeg.y - 0;
//    this.leftFoot.z = leftLeg.z;
//    setRotation(rightFoot, rightLeg.xRot, rightLeg.yRot, rightLeg.zRot);
//    setRotation(leftFoot, leftLeg.xRot, leftLeg.yRot, leftLeg.zRot);
//  }
//
//  public static void setRotation(ModelPart model, float x, float y, float z) {
//    model.xRot = x;
//    model.yRot = y;
//    model.zRot = z;
//  }
}
