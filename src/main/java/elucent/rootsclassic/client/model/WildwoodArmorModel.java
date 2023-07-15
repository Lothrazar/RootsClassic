package elucent.rootsclassic.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.item.ArmorItem;

public class WildwoodArmorModel extends ModelArmorBase {

  public WildwoodArmorModel(ModelPart root, ArmorItem.Type type) {
    super(root, type);
    this.armorScale = 1.2f;
  }

  public static LayerDefinition createArmorDefinition() {
    MeshDefinition meshdefinition = ModelArmorBase.createArmorMesh();
    PartDefinition partdefinition = meshdefinition.getRoot();
    PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
    head.addOrReplaceChild("head_1",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, -8F, -1F, 2, 8, 2).mirror(),
        PartPose.offsetAndRotation(0F, -1F, -6F, 0.1308997F, 0F, 0F));
    head.addOrReplaceChild("head_2",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -8F, -1F, 2, 8, 2).mirror(),
        PartPose.offsetAndRotation(2F, -6.5F, -5F, -1.047198F, 0.2617994F, 0F));
    head.addOrReplaceChild("head_3",
        CubeListBuilder.create()
            .texOffs(0, 32)
            .addBox(-1F, -1F, -1F, 2, 2, 2).mirror(),
        PartPose.offsetAndRotation(-2F, -1F, -5F, 0F, 0F, 0F));
    head.addOrReplaceChild("head_4",
        CubeListBuilder.create()
            .texOffs(0, 32)
            .addBox(-1F, -1F, -1F, 2, 2, 2).mirror(),
        PartPose.offsetAndRotation(0F, -0F, -6F, 0F, 0F, 0F));
    head.addOrReplaceChild("head_5",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, -4F, -1F, 2, 6, 2).mirror(),
        PartPose.offsetAndRotation(-3.8F, -3F, -1F, 0.1308997F, 1.308997F, -0.5235988F));
    head.addOrReplaceChild("head_6",
        CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-6F, -1F, -2F, 6, 2, 4).mirror(),
        PartPose.offsetAndRotation(0F, -7F, -5F, 0F, 1.570796F, 0.7853982F));
    head.addOrReplaceChild("head_7",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, -6F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2F, -1F, -5F, 0.1308997F, 0.2617994F, 0F));
    head.addOrReplaceChild("head_8",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, -7F, -1F, 2, 7, 2).mirror(),
        PartPose.offsetAndRotation(2.933333F, -1F, -4F, 0.1308997F, -0.7853982F, 0F));
    head.addOrReplaceChild("head_9",
        CubeListBuilder.create()
            .texOffs(0, 32)
            .addBox(-1F, -1F, -1F, 2, 2, 2).mirror(),
        PartPose.offsetAndRotation(2F, -1F, -5F, 0F, 0F, 0F));
    head.addOrReplaceChild("head_10",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -6F, -1F, 2, 6, 2).mirror(),
        PartPose.offsetAndRotation(4F, -1.5F, -1F, -1.308997F, 0.1308997F, 0F));
    head.addOrReplaceChild("head_11",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, -7F, -1F, 2, 7, 2).mirror(),
        PartPose.offsetAndRotation(-3F, -1F, -4F, 0.1308997F, 0.7853982F, 0F));
    head.addOrReplaceChild("head_12",
        CubeListBuilder.create()
            .texOffs(56, 16)
            .addBox(-1F, -6F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-3.5F, 1F, -3F, 0.1308997F, 1.047198F, -0.2617994F));
    head.addOrReplaceChild("head_13",
        CubeListBuilder.create()
            .texOffs(56, 16)
            .addBox(-1F, -6F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(3.5F, 1F, -3F, 0.1308997F, -1.047198F, 0.2617994F));
    head.addOrReplaceChild("head_14",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, -4F, -1F, 2, 6, 2).mirror(),
        PartPose.offsetAndRotation(3.75F, -3F, -1F, 0.1308997F, -1.308997F, 0.5235988F));
    head.addOrReplaceChild("head_15",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, -6F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2F, -1F, -5F, 0.1308997F, -0.2617994F, 0F));
    head.addOrReplaceChild("head_16",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -6F, -1F, 2, 6, 2).mirror(),
        PartPose.offsetAndRotation(-4F, -1.5F, -1F, -1.308997F, -0.1308997F, 0F));
    head.addOrReplaceChild("head_17",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -8F, -1F, 2, 8, 2).mirror(),
        PartPose.offsetAndRotation(-2F, -6.5F, -5F, -1.047198F, -0.2617994F, 0F));
    //body
    PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
    body.addOrReplaceChild("body_1",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(3F, 11F, 0F, 0.5235988F, -1.570796F, 0F));
    body.addOrReplaceChild("body_2",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -8F, -1F, 4, 8, 2).mirror(),
        PartPose.offsetAndRotation(-1F, 10F, 2F, 0.1308997F, 2.879793F, 0.3926991F));
    body.addOrReplaceChild("body_3",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -8F, -1F, 4, 8, 2).mirror(),
        PartPose.offsetAndRotation(1F, 10F, 2F, 0.1308997F, -2.879793F, -0.3926991F));
    body.addOrReplaceChild("body_4",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(-3F, 11F, 0F, 0.5235988F, 1.570796F, 0F));
    body.addOrReplaceChild("body_5",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -8F, -1F, 4, 8, 2).mirror(),
        PartPose.offsetAndRotation(-1F, 10F, -2F, 0.1308997F, 0.2617994F, -0.3926991F));
    body.addOrReplaceChild("body_6",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -8F, -1F, 4, 8, 2).mirror(),
        PartPose.offsetAndRotation(0F, 9F, -2F, 0.2617994F, 0F, 0F));
    body.addOrReplaceChild("body_7",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(0F, 11F, 2F, 0.5235988F, 3.141593F, 0F));
    body.addOrReplaceChild("body_8",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -8F, -1F, 4, 8, 2).mirror(),
        PartPose.offsetAndRotation(1F, 10F, -2F, 0.1308997F, -0.2617994F, 0.3926991F));
    body.addOrReplaceChild("body_9",
        CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(0F, 11F, -2F, 0.5235988F, 0F, 0F));
    body.addOrReplaceChild("body_10",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2.5F, 7F, -1F, -1.832596F, -2.879793F, 0F));
    body.addOrReplaceChild("body_11",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2.5F, 5F, -2F, -1.308997F, -2.879793F, 0F));
    body.addOrReplaceChild("body_12",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2.5F, 5F, -2F, -1.308997F, 2.879793F, 0F));
    body.addOrReplaceChild("body_13",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2.5F, 7F, -1F, -1.832596F, 2.879793F, 0F));
    PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
    right_arm.addOrReplaceChild("right_arm1",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(-7.5F, 8F, 0F, 0F, 1.570796F, 0F));
    right_arm.addOrReplaceChild("right_arm2",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-6F, 0F, 0F, -0.7853982F, 0F, -0.3926991F));
    right_arm.addOrReplaceChild("right_arm3",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-6F, 0F, -1F, -0.2617994F, 0F, -0.1308997F));
    right_arm.addOrReplaceChild("right_arm4",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, 0F, -1F, 2, 7, 2).mirror(),
        PartPose.offsetAndRotation(-7F, 9F, 0F, 0.1308997F, 1.570796F, 3.141593F));
    right_arm.addOrReplaceChild("right_arm5",
        CubeListBuilder.create()
            .texOffs(16, 32)
            .addBox(-2F, -4F, -2F, 4, 4, 4).mirror(),
        PartPose.offsetAndRotation(-5F, 3F, 0F, 0.2617994F, 1.570796F, 0F));
    right_arm.addOrReplaceChild("right_arm6",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -6F, -1F, 2, 6, 2).mirror(),
        PartPose.offsetAndRotation(-6.5F, 4F, 0F, -1.047198F, -0.3926991F, 0F));
    PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
    left_arm.addOrReplaceChild("left_arm1",
        CubeListBuilder.create()
            .texOffs(16, 32)
            .addBox(-2F, -4F, -2F, 4, 4, 4).mirror(),
        PartPose.offsetAndRotation(5F, 3F, 0F, -0.2617994F, 1.570796F, 0F));
    left_arm.addOrReplaceChild("left_arm2",
        CubeListBuilder.create()
            .texOffs(48, 16)
            .addBox(-1F, 0F, -1F, 2, 7, 2).mirror(),
        PartPose.offsetAndRotation(7F, 9F, 0F, -0.1308997F, 1.570796F, 3.141593F));
    left_arm.addOrReplaceChild("left_arm3",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(7.5F, 8F, 0F, 0F, -1.570796F, 0F));
    left_arm.addOrReplaceChild("left_arm4",
        CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-1F, -6F, -1F, 2, 6, 2).mirror(),
        PartPose.offsetAndRotation(6.5F, 4F, 0F, -1.047198F, 0.3926991F, 0F));
    left_arm.addOrReplaceChild("left_arm5",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(6F, 0F, 0F, -0.7853982F, 0F, 0.3926991F));
    left_arm.addOrReplaceChild("left_arm6",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(6F, 0F, -1F, -0.2617994F, 0F, 0.1308997F));
    PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    right_leg.addOrReplaceChild("right_leg1",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2.5F, 2F, -2.5F, 0.1308997F, 0F, 0F));
    right_leg.addOrReplaceChild("right_leg2",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2.5F, 7F, -2F, 0F, 0F, 3.141593F));
    right_leg.addOrReplaceChild("right_leg3",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-2.5F, 1F, -2F, 0.1308997F, 0F, 0F));
    right_leg.addOrReplaceChild("right_leg4",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-3F, 0F, -2F, -1.047198F, -2.617994F, 0F));
    right_leg.addOrReplaceChild("right_leg5",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-3F, 0F, -1F, -1.047198F, -1.832596F, 0F));
    right_leg.addOrReplaceChild("right_leg6",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-2F, 0F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(-4F, 0F, -0.5F, -0.2617994F, 1.570796F, 0F));
    PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    left_leg.addOrReplaceChild("left_leg1",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-2F, -4F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(2.5F, 4F, -2.5F, 0.1308997F, 0F, 0F));
    left_leg.addOrReplaceChild("left_leg2",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, 0F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2.5F, 7F, -2F, 0F, 0F, 3.141593F));
    left_leg.addOrReplaceChild("left_leg3",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(2.5F, 1F, -2F, 0.1308997F, 0F, 0F));
    left_leg.addOrReplaceChild("left_leg4",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(3F, 0F, -2F, -1.047198F, 2.617994F, 0F));
    left_leg.addOrReplaceChild("left_leg5",
        CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(3F, 0F, -1F, -1.047198F, 1.832596F, 0F));
    left_leg.addOrReplaceChild("left_leg6",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-2F, 0F, -1F, 4, 4, 2).mirror(),
        PartPose.offsetAndRotation(4F, 0F, -0.5F, -0.2617994F, -1.570796F, 0F));
    PartDefinition right_foot = partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    right_foot.addOrReplaceChild("right_foot1",
        CubeListBuilder.create()
            .texOffs(16, 32)
            .addBox(-2F, -4F, -2F, 4, 4, 4).mirror(),
        PartPose.offsetAndRotation(-2F, 10F, -2F, 0F, 0F, 0F));
    right_foot.addOrReplaceChild("right_foot2",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-3F, 7F, -1F, -1.047198F, -0.5235988F, 0F));
    right_foot.addOrReplaceChild("right_foot3",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(-3F, 7F, -1F, -1.832596F, -0.7853982F, 0F));
    PartDefinition left_foot = partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(0, -12, 0));
    left_foot.addOrReplaceChild("left_foot1",
        CubeListBuilder.create()
            .texOffs(16, 32)
            .addBox(-2F, -4F, -2F, 4, 4, 4).mirror(),
        PartPose.offsetAndRotation(2F, 10F, -2F, 0F, 0F, 0F));
    left_foot.addOrReplaceChild("left_foot2",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(3F, 7F, -1F, -1.047198F, 0.5235988F, 0F));
    left_foot.addOrReplaceChild("left_foot3",
        CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1F, -4F, -1F, 2, 4, 2).mirror(),
        PartPose.offsetAndRotation(3F, 7F, -1F, -1.832596F, 0.7853982F, 0F));
    return LayerDefinition.create(meshdefinition, 64, 64);
  }
}
