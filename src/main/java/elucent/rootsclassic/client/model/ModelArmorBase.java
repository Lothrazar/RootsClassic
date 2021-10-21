package elucent.rootsclassic.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelArmorBase extends BipedModel<LivingEntity> {

  public EquipmentSlotType slot;
  public float armorScale = 1.05f;
  public ModelRenderer headPart;
  public ModelRenderer chest;
  public ModelRenderer armR;
  public ModelRenderer armL;
  public ModelRenderer legR;
  public ModelRenderer legL;
  public ModelRenderer bootR;
  public ModelRenderer bootL;

  public ModelArmorBase(EquipmentSlotType slot) {
    super(0.0F, 1.0f, 64, 64);
    this.texHeight = 64;
    this.texWidth = 64;
    this.slot = slot;
    this.young = false;
    headPart = new ModelRenderer(this);
    chest = new ModelRenderer(this);
    armR = new ModelRenderer(this);
    armL = new ModelRenderer(this);
    legR = new ModelRenderer(this);
    legL = new ModelRenderer(this);
    bootR = new ModelRenderer(this);
    bootL = new ModelRenderer(this);
  }

  @Override
  public void setupAnim(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    if (!(entityIn instanceof ArmorStandEntity)) {
      super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      return;
    }
    if (entityIn instanceof ArmorStandEntity) {
      ArmorStandEntity armorStand = (ArmorStandEntity) entityIn;
      this.headPart.xRot = ((float) Math.PI / 180F) * armorStand.getHeadPose().getX();
      this.headPart.yRot = ((float) Math.PI / 180F) * armorStand.getHeadPose().getY();
      this.headPart.zRot = ((float) Math.PI / 180F) * armorStand.getHeadPose().getZ();
      this.headPart.setPos(0.0F, 1.0F, 0.0F);
      this.body.xRot = ((float) Math.PI / 180F) * armorStand.getBodyPose().getX();
      this.body.yRot = ((float) Math.PI / 180F) * armorStand.getBodyPose().getY();
      this.body.zRot = ((float) Math.PI / 180F) * armorStand.getBodyPose().getZ();
      this.leftArm.xRot = ((float) Math.PI / 180F) * armorStand.getLeftArmPose().getX();
      this.leftArm.yRot = ((float) Math.PI / 180F) * armorStand.getLeftArmPose().getY();
      this.leftArm.zRot = ((float) Math.PI / 180F) * armorStand.getLeftArmPose().getZ();
      this.rightArm.xRot = ((float) Math.PI / 180F) * armorStand.getRightArmPose().getX();
      this.rightArm.yRot = ((float) Math.PI / 180F) * armorStand.getRightArmPose().getY();
      this.rightArm.zRot = ((float) Math.PI / 180F) * armorStand.getRightArmPose().getZ();
      this.leftLeg.xRot = ((float) Math.PI / 180F) * armorStand.getLeftLegPose().getX();
      this.leftLeg.yRot = ((float) Math.PI / 180F) * armorStand.getLeftLegPose().getY();
      this.leftLeg.zRot = ((float) Math.PI / 180F) * armorStand.getLeftLegPose().getZ();
      this.leftLeg.setPos(1.9F, 11.0F, 0.0F);
      this.rightLeg.xRot = ((float) Math.PI / 180F) * armorStand.getRightLegPose().getX();
      this.rightLeg.yRot = ((float) Math.PI / 180F) * armorStand.getRightLegPose().getY();
      this.rightLeg.zRot = ((float) Math.PI / 180F) * armorStand.getRightLegPose().getZ();
      this.rightLeg.setPos(-1.9F, 11.0F, 0.0F);
      this.hat.copyFrom(this.headPart);
    }
  }

  @Override
  public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    matrixStackIn.pushPose();
    matrixStackIn.scale(armorScale, armorScale, armorScale);
    this.setHeadRotation();
    this.setChestRotation();
    this.setLegsRotation();
    this.setBootRotation();
    headPart.visible = slot == EquipmentSlotType.HEAD;
    chest.visible = slot == EquipmentSlotType.CHEST;
    armR.visible = slot == EquipmentSlotType.CHEST;
    armL.visible = slot == EquipmentSlotType.CHEST;
    legR.visible = slot == EquipmentSlotType.LEGS;
    legL.visible = slot == EquipmentSlotType.LEGS;
    bootR.visible = slot == EquipmentSlotType.FEET;
    bootL.visible = slot == EquipmentSlotType.FEET;
    if (this.young) {
      float f = 2.0F;
      matrixStackIn.scale(1.5F / f, 1.5F / f, 1.5F / f);
      matrixStackIn.translate(0.0F, 16.0F * 1, 0.0F);
      headPart.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      matrixStackIn.popPose();
      matrixStackIn.pushPose();
      matrixStackIn.scale(1.0F / f, 1.0F / f, 1.0F / f);
      matrixStackIn.translate(0.0F, 24.0F * 1, 0.0F);
      chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
    else {
      if (crouching) {
        matrixStackIn.translate(0.0F, 0.2F, 0.0F);
      }
      headPart.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      armR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      armL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
    matrixStackIn.translate(0.0F, 1.2F, 0.0F);
    legR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    legL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    bootR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    bootL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    matrixStackIn.popPose();
  }

  public void setHeadRotation() {
    headPart.x = head.x;
    headPart.y = head.y;
    headPart.z = head.z;
    setRotation(headPart, head.xRot, head.yRot, head.zRot);
  }

  public void setChestRotation() {
    /* if (e instanceof EntityPlayer){ ((EntityPlayer)e).get } */
    chest.x = body.x;
    chest.y = body.y - 1;
    chest.z = body.z;
    chest.yTexOffs -= 0.125;
    armR.x = rightArm.x + 5;
    armR.y = rightArm.y - 1;
    armR.z = rightArm.z;
    armR.yTexOffs -= 0;
    armL.x = leftArm.x - 5;
    armL.y = leftArm.y - 1;
    armL.z = leftArm.z;
    armL.yTexOffs -= 0;
    setRotation(chest, body.xRot, body.yRot, body.zRot);
    setRotation(armR, rightArm.xRot, rightArm.yRot, rightArm.zRot);
    setRotation(armL, leftArm.xRot, leftArm.yRot, leftArm.zRot);
  }

  public void setLegsRotation() {
    legR.x = rightLeg.x + 2;
    legR.y = rightLeg.y - 22;
    legR.z = rightLeg.z;
    legL.x = leftLeg.x - 2;
    legL.y = leftLeg.y - 22;
    legL.z = leftLeg.z;
    setRotation(legR, rightLeg.xRot, rightLeg.yRot, rightLeg.zRot);
    setRotation(legL, leftLeg.xRot, leftLeg.yRot, leftLeg.zRot);
  }

  public void setBootRotation() {
    bootR.x = rightLeg.x + 2;
    bootR.y = rightLeg.y - 22;
    bootR.z = rightLeg.z;
    bootL.x = leftLeg.x - 2;
    bootL.y = leftLeg.y - 22;
    bootL.z = leftLeg.z;
    setRotation(bootR, rightLeg.xRot, rightLeg.yRot, rightLeg.zRot);
    setRotation(bootL, leftLeg.xRot, leftLeg.yRot, leftLeg.zRot);
  }

  public static void setRotation(ModelRenderer model, float x, float y, float z) {
    model.xRot = x;
    model.yRot = y;
    model.zRot = z;
  }
}
