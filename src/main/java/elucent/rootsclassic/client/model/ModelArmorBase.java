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
  public static ModelRenderer head;
  public static ModelRenderer chest;
  public static ModelRenderer armR;
  public static ModelRenderer armL;
  public static ModelRenderer legR;
  public static ModelRenderer legL;
  public static ModelRenderer bootR;
  public static ModelRenderer bootL;

  public ModelArmorBase(EquipmentSlotType slot) {
    super(0.0F, 1.0f, 64, 64);
    this.textureHeight = 64;
    this.textureWidth = 64;
    this.slot = slot;
    this.isChild = false;
    head = new ModelRenderer(this);
    chest = new ModelRenderer(this);
    armR = new ModelRenderer(this);
    armL = new ModelRenderer(this);
    legR = new ModelRenderer(this);
    legL = new ModelRenderer(this);
    bootR = new ModelRenderer(this);
    bootL = new ModelRenderer(this);
  }

  @Override
  public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    if (!(entityIn instanceof ArmorStandEntity)) {
      super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      return;
    }
    if (entityIn instanceof ArmorStandEntity) {
      ArmorStandEntity armorStand = (ArmorStandEntity) entityIn;
      this.bipedHead.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getHeadRotation().getX();
      this.bipedHead.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getHeadRotation().getY();
      this.bipedHead.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getHeadRotation().getZ();
      this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
      this.bipedBody.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getBodyRotation().getX();
      this.bipedBody.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getBodyRotation().getY();
      this.bipedBody.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getBodyRotation().getZ();
      this.bipedLeftArm.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getLeftArmRotation().getX();
      this.bipedLeftArm.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getLeftArmRotation().getY();
      this.bipedLeftArm.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getLeftArmRotation().getZ();
      this.bipedRightArm.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getRightArmRotation().getX();
      this.bipedRightArm.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getRightArmRotation().getY();
      this.bipedRightArm.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getRightArmRotation().getZ();
      this.bipedLeftLeg.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getLeftLegRotation().getX();
      this.bipedLeftLeg.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getLeftLegRotation().getY();
      this.bipedLeftLeg.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getLeftLegRotation().getZ();
      this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
      this.bipedRightLeg.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getRightLegRotation().getX();
      this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getRightLegRotation().getY();
      this.bipedRightLeg.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getRightLegRotation().getZ();
      this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
      this.bipedHeadwear.copyModelAngles(this.bipedHead);
    }
  }

  @Override
  public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    matrixStackIn.push();
    matrixStackIn.scale(armorScale, armorScale, armorScale);
    this.setHeadRotation();
    this.setChestRotation();
    this.setLegsRotation();
    this.setBootRotation();
    head.showModel = slot == EquipmentSlotType.HEAD;
    chest.showModel = slot == EquipmentSlotType.CHEST;
    armR.showModel = slot == EquipmentSlotType.CHEST;
    armL.showModel = slot == EquipmentSlotType.CHEST;
    legR.showModel = slot == EquipmentSlotType.LEGS;
    legL.showModel = slot == EquipmentSlotType.LEGS;
    bootR.showModel = slot == EquipmentSlotType.FEET;
    bootL.showModel = slot == EquipmentSlotType.FEET;
    if (this.isChild) {
      float f = 2.0F;
      matrixStackIn.scale(1.5F / f, 1.5F / f, 1.5F / f);
      matrixStackIn.translate(0.0F, 16.0F * 1, 0.0F);
      head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      matrixStackIn.pop();
      matrixStackIn.push();
      matrixStackIn.scale(1.0F / f, 1.0F / f, 1.0F / f);
      matrixStackIn.translate(0.0F, 24.0F * 1, 0.0F);
      chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
    else {
      if (isSneak) {
        matrixStackIn.translate(0.0F, 0.2F, 0.0F);
      }
      head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      armR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      armL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
    matrixStackIn.translate(0.0F, 1.2F, 0.0F);
    legR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    legL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    bootR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    bootL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    matrixStackIn.pop();
  }

  public void setHeadRotation() {
    head.rotationPointX = bipedHead.rotationPointX;
    head.rotationPointY = bipedHead.rotationPointY;
    head.rotationPointZ = bipedHead.rotationPointZ;
    setRotation(head, bipedHead.rotateAngleX, bipedHead.rotateAngleY, bipedHead.rotateAngleZ);
  }

  public void setChestRotation() {
    /* if (e instanceof EntityPlayer){ ((EntityPlayer)e).get } */
    chest.rotationPointX = bipedBody.rotationPointX;
    chest.rotationPointY = bipedBody.rotationPointY - 1;
    chest.rotationPointZ = bipedBody.rotationPointZ;
    chest.textureOffsetY -= 0.125;
    armR.rotationPointX = bipedRightArm.rotationPointX + 5;
    armR.rotationPointY = bipedRightArm.rotationPointY - 1;
    armR.rotationPointZ = bipedRightArm.rotationPointZ;
    armR.textureOffsetY -= 0;
    armL.rotationPointX = bipedLeftArm.rotationPointX - 5;
    armL.rotationPointY = bipedLeftArm.rotationPointY - 1;
    armL.rotationPointZ = bipedLeftArm.rotationPointZ;
    armL.textureOffsetY -= 0;
    setRotation(chest, bipedBody.rotateAngleX, bipedBody.rotateAngleY, bipedBody.rotateAngleZ);
    setRotation(armR, bipedRightArm.rotateAngleX, bipedRightArm.rotateAngleY, bipedRightArm.rotateAngleZ);
    setRotation(armL, bipedLeftArm.rotateAngleX, bipedLeftArm.rotateAngleY, bipedLeftArm.rotateAngleZ);
  }

  public void setLegsRotation() {
    legR.rotationPointX = bipedRightLeg.rotationPointX + 2;
    legR.rotationPointY = bipedRightLeg.rotationPointY - 22;
    legR.rotationPointZ = bipedRightLeg.rotationPointZ;
    legL.rotationPointX = bipedLeftLeg.rotationPointX - 2;
    legL.rotationPointY = bipedLeftLeg.rotationPointY - 22;
    legL.rotationPointZ = bipedLeftLeg.rotationPointZ;
    setRotation(legR, bipedRightLeg.rotateAngleX, bipedRightLeg.rotateAngleY, bipedRightLeg.rotateAngleZ);
    setRotation(legL, bipedLeftLeg.rotateAngleX, bipedLeftLeg.rotateAngleY, bipedLeftLeg.rotateAngleZ);
  }

  public void setBootRotation() {
    bootR.rotationPointX = bipedRightLeg.rotationPointX + 2;
    bootR.rotationPointY = bipedRightLeg.rotationPointY - 22;
    bootR.rotationPointZ = bipedRightLeg.rotationPointZ;
    bootL.rotationPointX = bipedLeftLeg.rotationPointX - 2;
    bootL.rotationPointY = bipedLeftLeg.rotationPointY - 22;
    bootL.rotationPointZ = bipedLeftLeg.rotationPointZ;
    setRotation(bootR, bipedRightLeg.rotateAngleX, bipedRightLeg.rotateAngleY, bipedRightLeg.rotateAngleZ);
    setRotation(bootL, bipedLeftLeg.rotateAngleX, bipedLeftLeg.rotateAngleY, bipedLeftLeg.rotateAngleZ);
  }

  public static void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
}
