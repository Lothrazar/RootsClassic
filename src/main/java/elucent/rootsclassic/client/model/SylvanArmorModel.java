package elucent.rootsclassic.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.item.ArmorItem;

public class SylvanArmorModel extends ModelArmorBase {

  public SylvanArmorModel(ModelPart root, ArmorItem.Type slot) {
    super(root, slot);
    this.armorScale = 1.1f;
  }

  public static LayerDefinition createArmorDefinition() {
    MeshDefinition meshdefinition = ModelArmorBase.createArmorMesh();
    PartDefinition partdefinition = meshdefinition.getRoot();
    PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
    head.addOrReplaceChild("head_1",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-3F, 0F, 0F, 6, 4, 2).mirror(),
        PartPose.offsetAndRotation(0F, -9F, -4F, -0.2617994F, 0F, 0F));
    head.addOrReplaceChild("head_2",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-4F, 0F, 0F, 8, 2, 7).mirror(),
        PartPose.offsetAndRotation(0F, -8F, -3F, 0F, 0F, 0F));
    head.addOrReplaceChild("head_3",
        CubeListBuilder.create()
            .texOffs(0, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(3.5F, -8F, 3F, -0.2617994F, -1.963495F, 0F));
    head.addOrReplaceChild("head_4",
        CubeListBuilder.create()
            .texOffs(0, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(-3.5F, -8F, 3F, -0.2617994F, 1.963495F, 0F));
    head.addOrReplaceChild("head_5",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(0F, -8F, 4F, -0.2617994F, 3.141593F, 0F));
    head.addOrReplaceChild("head_6",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, -7F, 2F, -0.7853982F, -0.2617994F, 0F));
    head.addOrReplaceChild("head_7",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, -7F, 2F, -0.7853982F, 0.2617994F, 0F));
    head.addOrReplaceChild("head_8",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -8F, -1F, 2, 8, 2).mirror(),
        PartPose.offsetAndRotation(-2F, -7F, -2F, -0.5235988F, -0.5235988F, 0F));
    head.addOrReplaceChild("head_9",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -8F, -1F, 2, 8, 2).mirror(),
        PartPose.offsetAndRotation(2F, -7F, -2F, -0.5235988F, 0.5235988F, 0F));
    head.addOrReplaceChild("head_10",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, 0F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(0F, -10F, 2F, -1.047198F, 3.141593F, 0F));
    head.addOrReplaceChild("head_11",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, 0F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(0F, -8F, -3F, -2.094395F, 3.141593F, 0F));
    head.addOrReplaceChild("head_12",
        CubeListBuilder.create()
            .texOffs(16, 50)
            .addBox(-2F, 0F, 0F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(0F, -8F, -4F, -2.530727F, 3.141593F, 0F));
    head.addOrReplaceChild("head_13",
        CubeListBuilder.create()
            .texOffs(0, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(-4F, -8F, 0F, -0.2617994F, 1.308997F, 0F));
    head.addOrReplaceChild("head_14",
        CubeListBuilder.create()
            .texOffs(0, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(4F, -8F, 0F, -0.2617994F, -1.308997F, 0F));
    PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
    body.addOrReplaceChild("body1",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-4F, -1F, -7F, 8, 2, 7).mirror(),
        PartPose.offsetAndRotation(0F, 0F, -1F, 1.308997F, 0F, 0F));
    body.addOrReplaceChild("body2",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-4F, -1F, -7F, 8, 2, 7).mirror(),
        PartPose.offsetAndRotation(0F, 0F, 1F, 1.308997F, -3.141593F, 0F));
    body.addOrReplaceChild("body3",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-3F, -1F, -4F, 6, 2, 4).mirror(),
        PartPose.offsetAndRotation(0F, 6F, -2F, 1.570796F, 0F, 0F));
    body.addOrReplaceChild("body4",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, 0F, 0F, 4, 2, 4).mirror(),
        PartPose.offsetAndRotation(0F, 12F, -2.5F, 1.570796F, 0F, 0F));
    body.addOrReplaceChild("body5",
        CubeListBuilder.create()
            .texOffs(48, 0)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(0F, 0F, 2F, -0.2617994F, 3.141593F, 0F));
    body.addOrReplaceChild("body6",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 2F, 1.5F, -0.7853982F, -0.2617994F, 0F));
    body.addOrReplaceChild("body7",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, 2F, 1.5F, -0.7853982F, 0.2617994F, 0F));
    body.addOrReplaceChild("body8",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 5F, 2F, -1.047198F, -0.2617994F, 0F));
    body.addOrReplaceChild("body9",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, 5F, 2F, -1.047198F, 0.2617994F, 0F));
    PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
    right_arm.addOrReplaceChild("right_arm1",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, 0F, -2F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(-5F, 8F, 0F, -1.570796F, 1.570796F, 0F));
    right_arm.addOrReplaceChild("right_arm2",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-7F, 6F, -1F, 3.141593F, 1.570796F, 0.2617994F));
    right_arm.addOrReplaceChild("right_arm3",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-7F, 6F, 1F, 3.141593F, 1.570796F, -0.2617994F));
    right_arm.addOrReplaceChild("right_arm4",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-8F, 6F, 0F, 3.403392F, 1.570796F, 0F));
    right_arm.addOrReplaceChild("right_arm5",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, 0F, 0F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(-4F, -3.5F, 0F, -1.308997F, 1.570796F, 0F));
    PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
    left_arm.addOrReplaceChild("left_arm1",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, 0F, -2F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(9F, 8F, 0F, -1.570796F, 1.570796F, 0F));
    left_arm.addOrReplaceChild("left_arm2",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(8F, 6F, 0F, 2.879793F, 1.570796F, 0F));
    left_arm.addOrReplaceChild("left_arm3",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(7F, 6F, -1F, 3.141593F, 1.570796F, 0.2617994F));
    left_arm.addOrReplaceChild("left_arm4",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(7F, 6F, 1F, 3.141593F, 1.570796F, -0.2617994F));
    left_arm.addOrReplaceChild("left_arm5",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, 0F, 0F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(4F, -3.5F, 0F, -1.308997F, -1.570796F, 0F));
    PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    left_leg.addOrReplaceChild("left_leg1",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(2F, 0F, -2F, 0.2617994F, 3.141593F, 0F));
    left_leg.addOrReplaceChild("left_leg2",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(2F, 0F, 2F, -0.2617994F, 3.141593F, 0F));
    left_leg.addOrReplaceChild("left_leg3",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(4F, 0F, 0F, -0.2617994F, -1.570796F, 0F));
    left_leg.addOrReplaceChild("left_leg4",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -2F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(4F, 2F, 2F, -0.7853982F, 0.5235988F, 0F));
    PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    right_leg.addOrReplaceChild("right_leg1",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(-4F, 0F, 0F, -0.2617994F, 1.570796F, 0F));
    right_leg.addOrReplaceChild("right_leg2",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 0F, 2F, -0.2617994F, 3.141593F, 0F));
    right_leg.addOrReplaceChild("right_leg3",
        CubeListBuilder.create()
            .texOffs(16, 48)
            .addBox(-2F, 0F, -1F, 4, 6, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 0F, -2F, 0.2617994F, 3.141593F, 0F));
    right_leg.addOrReplaceChild("right_leg4",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-4F, 2F, 2F, -0.7853982F, -0.5235988F, 0F));
    PartDefinition right_foot = partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    right_foot.addOrReplaceChild("right_foot1",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, -2F, 0F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 10F, -2F, -1.570796F, 1.570796F, 0F));
    right_foot.addOrReplaceChild("right_foot2",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, -2F, 0F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 10F, 0F, -1.570796F, 1.570796F, 0F));
    right_foot.addOrReplaceChild("right_foot3",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 10F, 1F, 0.2617994F, 0F, 3.141593F));
    right_foot.addOrReplaceChild("right_foot4",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, 10F, -2F, -0.2617994F, 0F, 3.141593F));
    right_foot.addOrReplaceChild("right_foot5",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-1F, 10F, -1F, -0.2617994F, 1.570796F, 3.141593F));
    right_foot.addOrReplaceChild("right_foot6",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-3F, 10F, -1F, 0.2617994F, 1.570796F, 3.141593F));
    PartDefinition left_foot = partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    left_foot.addOrReplaceChild("left_foot1",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, -2F, 0F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, 10F, -2F, -1.570796F, 1.570796F, 0F));
    left_foot.addOrReplaceChild("left_foot2",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-2F, -2F, 0F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, 10F, 0F, -1.570796F, 1.570796F, 0F));
    left_foot.addOrReplaceChild("left_foot3",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(1F, 10F, -1F, 0.2617994F, 1.570796F, 3.141593F));
    left_foot.addOrReplaceChild("left_foot4",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, 10F, 1F, 0.2617994F, 0F, 3.141593F));
    left_foot.addOrReplaceChild("left_foot5",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, 10F, -2F, -0.2617994F, 0F, 3.141593F));
    left_foot.addOrReplaceChild("left_foot6",
        CubeListBuilder.create()
            .texOffs(32, 48)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(3F, 10F, -1F, -0.2617994F, 1.570796F, 3.141593F));
    return LayerDefinition.create(meshdefinition, 64, 64);
  }
}
