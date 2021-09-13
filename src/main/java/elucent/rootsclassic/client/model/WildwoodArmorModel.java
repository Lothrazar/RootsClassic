package elucent.rootsclassic.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public class WildwoodArmorModel extends ModelArmorBase {

  public WildwoodArmorModel(ModelPart mp,EquipmentSlot slot) {
    super(mp, slot);
    this.armorScale = 1.2f;
//    ModelPart head1 = new ModelPart(this, 48, 16);
//    head1.addBox(-1F, -8F, -1F, 2, 8, 2);
//    head1.setPos(0F, -1F, -6F);
//    head1.setTexSize(64, 64);
//    head1.mirror = true;
//    setRotation(head1, 0.1308997F, 0F, 0F);
//    ModelPart head2 = new ModelPart(this, 0, 16);
//    head2.addBox(-1F, -8F, -1F, 2, 8, 2);
//    head2.setPos(2F, -6.5F, -5F);
//    head2.setTexSize(64, 64);
//    head2.mirror = true;
//    setRotation(head2, -1.047198F, 0.2617994F, 0F);
//    ModelPart head3 = new ModelPart(this, 0, 32);
//    head3.addBox(-1F, -1F, -1F, 2, 2, 2);
//    head3.setPos(-2F, -1F, -5F);
//    head3.setTexSize(64, 64);
//    head3.mirror = true;
//    setRotation(head3, 0F, 0F, 0F);
//    ModelPart head4 = new ModelPart(this, 0, 32);
//    head4.addBox(-1F, -1F, -1F, 2, 2, 2);
//    head4.setPos(0F, -0F, -6F);
//    head4.setTexSize(64, 64);
//    head4.mirror = true;
//    setRotation(head4, 0F, 0F, 0F);
//    ModelPart head5 = new ModelPart(this, 48, 16);
//    head5.addBox(-1F, -4F, -1F, 2, 6, 2);
//    head5.setPos(-3.8F, -3F, -1F);
//    head5.setTexSize(64, 64);
//    head5.mirror = true;
//    setRotation(head5, 0.1308997F, 1.308997F, -0.5235988F);
//    ModelPart head6 = new ModelPart(this, 32, 0);
//    head6.addBox(-6F, -1F, -2F, 6, 2, 4);
//    head6.setPos(0F, -7F, -5F);
//    head6.setTexSize(64, 64);
//    head6.mirror = true;
//    setRotation(head6, 0F, 1.570796F, 0.7853982F);
//    ModelPart head7 = new ModelPart(this, 48, 16);
//    head7.addBox(-1F, -6F, -1F, 2, 4, 2);
//    head7.setPos(-2F, -1F, -5F);
//    head7.setTexSize(64, 64);
//    head7.mirror = true;
//    setRotation(head7, 0.1308997F, 0.2617994F, 0F);
//    ModelPart head8 = new ModelPart(this, 48, 16);
//    head8.addBox(-1F, -7F, -1F, 2, 7, 2);
//    head8.setPos(2.933333F, -1F, -4F);
//    head8.setTexSize(64, 64);
//    head8.mirror = true;
//    setRotation(head8, 0.1308997F, -0.7853982F, 0F);
//    ModelPart head9 = new ModelPart(this, 0, 32);
//    head9.addBox(-1F, -1F, -1F, 2, 2, 2);
//    head9.setPos(2F, -1F, -5F);
//    head9.setTexSize(64, 64);
//    head9.mirror = true;
//    setRotation(head9, 0F, 0F, 0F);
//    ModelPart head10 = new ModelPart(this, 0, 16);
//    head10.addBox(-1F, -6F, -1F, 2, 6, 2);
//    head10.setPos(4F, -1.5F, -1F);
//    head10.setTexSize(64, 64);
//    head10.mirror = true;
//    setRotation(head10, -1.308997F, 0.1308997F, 0F);
//    ModelPart head11 = new ModelPart(this, 48, 16);
//    head11.addBox(-1F, -7F, -1F, 2, 7, 2);
//    head11.setPos(-3F, -1F, -4F);
//    head11.setTexSize(64, 64);
//    head11.mirror = true;
//    setRotation(head11, 0.1308997F, 0.7853982F, 0F);
//    ModelPart head12 = new ModelPart(this, 56, 16);
//    head12.addBox(-1F, -6F, -1F, 2, 4, 2);
//    head12.setPos(-3.5F, 1F, -3F);
//    head12.setTexSize(64, 64);
//    head12.mirror = true;
//    setRotation(head12, 0.1308997F, 1.047198F, -0.2617994F);
//    ModelPart head13 = new ModelPart(this, 56, 16);
//    head13.addBox(-1F, -6F, -1F, 2, 4, 2);
//    head13.setPos(3.5F, 1F, -3F);
//    head13.setTexSize(64, 64);
//    head13.mirror = true;
//    setRotation(head13, 0.1308997F, -1.047198F, 0.2617994F);
//    ModelPart head14 = new ModelPart(this, 48, 16);
//    head14.addBox(-1F, -4F, -1F, 2, 6, 2);
//    head14.setPos(3.75F, -3F, -1F);
//    head14.setTexSize(64, 64);
//    head14.mirror = true;
//    setRotation(head14, 0.1308997F, -1.308997F, 0.5235988F);
//    ModelPart head15 = new ModelPart(this, 48, 16);
//    head15.addBox(-1F, -6F, -1F, 2, 4, 2);
//    head15.setPos(2F, -1F, -5F);
//    head15.setTexSize(64, 64);
//    head15.mirror = true;
//    setRotation(head15, 0.1308997F, -0.2617994F, 0F);
//    ModelPart head16 = new ModelPart(this, 0, 16);
//    head16.addBox(-1F, -6F, -1F, 2, 6, 2);
//    head16.setPos(-4F, -1.5F, -1F);
//    head16.setTexSize(64, 64);
//    head16.mirror = true;
//    setRotation(head16, -1.308997F, -0.1308997F, 0F);
//    ModelPart head17 = new ModelPart(this, 0, 16);
//    head17.addBox(-1F, -8F, -1F, 2, 8, 2);
//    head17.setPos(-2F, -6.5F, -5F);
//    head17.setTexSize(64, 64);
//    head17.mirror = true;
//    setRotation(head17, -1.047198F, -0.2617994F, 0F);
//    ModelPart chest1 = new ModelPart(this, 16, 16);
//    chest1.addBox(-2F, -4F, -1F, 4, 4, 2);
//    chest1.setPos(3F, 11F, 0F);
//    chest1.setTexSize(64, 64);
//    chest1.mirror = true;
//    setRotation(chest1, 0.5235988F, -1.570796F, 0F);
//    ModelPart chest2 = new ModelPart(this, 16, 16);
//    chest2.addBox(-2F, -8F, -1F, 4, 8, 2);
//    chest2.setPos(-1F, 10F, 2F);
//    chest2.setTexSize(64, 64);
//    chest2.mirror = true;
//    setRotation(chest2, 0.1308997F, 2.879793F, 0.3926991F);
//    ModelPart chest3 = new ModelPart(this, 16, 16);
//    chest3.addBox(-2F, -8F, -1F, 4, 8, 2);
//    chest3.setPos(1F, 10F, 2F);
//    chest3.setTexSize(64, 64);
//    chest3.mirror = true;
//    setRotation(chest3, 0.1308997F, -2.879793F, -0.3926991F);
//    ModelPart chest4 = new ModelPart(this, 16, 16);
//    chest4.addBox(-2F, -4F, -1F, 4, 4, 2);
//    chest4.setPos(-3F, 11F, 0F);
//    chest4.setTexSize(64, 64);
//    chest4.mirror = true;
//    setRotation(chest4, 0.5235988F, 1.570796F, 0F);
//    ModelPart chest5 = new ModelPart(this, 16, 16);
//    chest5.addBox(-2F, -8F, -1F, 4, 8, 2);
//    chest5.setPos(-1F, 10F, -2F);
//    chest5.setTexSize(64, 64);
//    chest5.mirror = true;
//    setRotation(chest5, 0.1308997F, 0.2617994F, -0.3926991F);
//    ModelPart chest6 = new ModelPart(this, 16, 16);
//    chest6.addBox(-2F, -8F, -1F, 4, 8, 2);
//    chest6.setPos(0F, 9F, -2F);
//    chest6.setTexSize(64, 64);
//    chest6.mirror = true;
//    setRotation(chest6, 0.2617994F, 0F, 0F);
//    ModelPart chest7 = new ModelPart(this, 16, 16);
//    chest7.addBox(-2F, -4F, -1F, 4, 4, 2);
//    chest7.setPos(0F, 11F, 2F);
//    chest7.setTexSize(64, 64);
//    chest7.mirror = true;
//    setRotation(chest7, 0.5235988F, 3.141593F, 0F);
//    ModelPart chest8 = new ModelPart(this, 16, 16);
//    chest8.addBox(-2F, -8F, -1F, 4, 8, 2);
//    chest8.setPos(1F, 10F, -2F);
//    chest8.setTexSize(64, 64);
//    chest8.mirror = true;
//    setRotation(chest8, 0.1308997F, -0.2617994F, 0.3926991F);
//    ModelPart chest9 = new ModelPart(this, 16, 16);
//    chest9.addBox(-2F, -4F, -1F, 4, 4, 2);
//    chest9.setPos(0F, 11F, -2F);
//    chest9.setTexSize(64, 64);
//    chest9.mirror = true;
//    setRotation(chest9, 0.5235988F, 0F, 0F);
//    ModelPart chest10 = new ModelPart(this, 8, 16);
//    chest10.addBox(-1F, -4F, -1F, 2, 4, 2);
//    chest10.setPos(-2.5F, 7F, -1F);
//    chest10.setTexSize(64, 64);
//    chest10.mirror = true;
//    setRotation(chest10, -1.832596F, -2.879793F, 0F);
//    ModelPart chest11 = new ModelPart(this, 8, 16);
//    chest11.addBox(-1F, -4F, -1F, 2, 4, 2);
//    chest11.setPos(-2.5F, 5F, -2F);
//    chest11.setTexSize(64, 64);
//    chest11.mirror = true;
//    setRotation(chest11, -1.308997F, -2.879793F, 0F);
//    ModelPart chest12 = new ModelPart(this, 8, 16);
//    chest12.addBox(-1F, -4F, -1F, 2, 4, 2);
//    chest12.setPos(2.5F, 5F, -2F);
//    chest12.setTexSize(64, 64);
//    chest12.mirror = true;
//    setRotation(chest12, -1.308997F, 2.879793F, 0F);
//    ModelPart chest13 = new ModelPart(this, 8, 16);
//    chest13.addBox(-1F, -4F, -1F, 2, 4, 2);
//    chest13.setPos(2.5F, 7F, -1F);
//    chest13.setTexSize(64, 64);
//    chest13.mirror = true;
//    setRotation(chest13, -1.832596F, 2.879793F, 0F);
//    ModelPart armR1 = new ModelPart(this, 32, 16);
//    armR1.addBox(-2F, -4F, -1F, 4, 4, 2);
//    armR1.setPos(-7.5F, 8F, 0F);
//    armR1.setTexSize(64, 64);
//    armR1.mirror = true;
//    setRotation(armR1, 0F, 1.570796F, 0F);
//    ModelPart armR2 = new ModelPart(this, 8, 16);
//    armR2.addBox(-1F, -4F, -1F, 2, 4, 2);
//    armR2.setPos(-6F, 0F, 0F);
//    armR2.setTexSize(64, 64);
//    armR2.mirror = true;
//    setRotation(armR2, -0.7853982F, 0F, -0.3926991F);
//    ModelPart armR3 = new ModelPart(this, 8, 16);
//    armR3.addBox(-1F, -4F, -1F, 2, 4, 2);
//    armR3.setPos(-6F, 0F, -1F);
//    armR3.setTexSize(64, 64);
//    armR3.mirror = true;
//    setRotation(armR3, -0.2617994F, 0F, -0.1308997F);
//    ModelPart armR4 = new ModelPart(this, 48, 16);
//    armR4.addBox(-1F, 0F, -1F, 2, 7, 2);
//    armR4.setPos(-7F, 9F, 0F);
//    armR4.setTexSize(64, 64);
//    armR4.mirror = true;
//    setRotation(armR4, 0.1308997F, 1.570796F, 3.141593F);
//    ModelPart armR5 = new ModelPart(this, 16, 32);
//    armR5.addBox(-2F, -4F, -2F, 4, 4, 4);
//    armR5.setPos(-5F, 3F, 0F);
//    armR5.setTexSize(64, 64);
//    armR5.mirror = true;
//    setRotation(armR5, 0.2617994F, 1.570796F, 0F);
//    ModelPart armR6 = new ModelPart(this, 0, 16);
//    armR6.addBox(-1F, -6F, -1F, 2, 6, 2);
//    armR6.setPos(-6.5F, 4F, 0F);
//    armR6.setTexSize(64, 64);
//    armR6.mirror = true;
//    setRotation(armR6, -1.047198F, -0.3926991F, 0F);
//    ModelPart armL1 = new ModelPart(this, 16, 32);
//    armL1.addBox(-2F, -4F, -2F, 4, 4, 4);
//    armL1.setPos(5F, 3F, 0F);
//    armL1.setTexSize(64, 64);
//    armL1.mirror = true;
//    setRotation(armL1, -0.2617994F, 1.570796F, 0F);
//    ModelPart armL2 = new ModelPart(this, 48, 16);
//    armL2.addBox(-1F, 0F, -1F, 2, 7, 2);
//    armL2.setPos(7F, 9F, 0F);
//    armL2.setTexSize(64, 64);
//    armL2.mirror = true;
//    setRotation(armL2, -0.1308997F, 1.570796F, 3.141593F);
//    ModelPart armL3 = new ModelPart(this, 32, 16);
//    armL3.addBox(-2F, -4F, -1F, 4, 4, 2);
//    armL3.setPos(7.5F, 8F, 0F);
//    armL3.setTexSize(64, 64);
//    armL3.mirror = true;
//    setRotation(armL3, 0F, -1.570796F, 0F);
//    ModelPart armL4 = new ModelPart(this, 0, 16);
//    armL4.addBox(-1F, -6F, -1F, 2, 6, 2);
//    armL4.setPos(6.5F, 4F, 0F);
//    armL4.setTexSize(64, 64);
//    armL4.mirror = true;
//    setRotation(armL4, -1.047198F, 0.3926991F, 0F);
//    ModelPart armL5 = new ModelPart(this, 8, 16);
//    armL5.addBox(-1F, -4F, -1F, 2, 4, 2);
//    armL5.setPos(6F, 0F, 0F);
//    armL5.setTexSize(64, 64);
//    armL5.mirror = true;
//    setRotation(armL5, -0.7853982F, 0F, 0.3926991F);
//    ModelPart armL6 = new ModelPart(this, 8, 16);
//    armL6.addBox(-1F, -4F, -1F, 2, 4, 2);
//    armL6.setPos(6F, 0F, -1F);
//    armL6.setTexSize(64, 64);
//    armL6.mirror = true;
//    setRotation(armL6, -0.2617994F, 0F, 0.1308997F);
//    ModelPart legR1 = new ModelPart(this, 32, 16);
//    legR1.addBox(-2F, -4F, -1F, 4, 4, 2);
//    legR1.setPos(-2.5F, 16F, -2.5F);
//    legR1.setTexSize(64, 64);
//    legR1.mirror = true;
//    setRotation(legR1, 0.1308997F, 0F, 0F);
//    ModelPart legR2 = new ModelPart(this, 32, 16);
//    legR2.addBox(-1F, 0F, -1F, 2, 4, 2);
//    legR2.setPos(-2.5F, 19F, -2F);
//    legR2.setTexSize(64, 64);
//    legR2.mirror = true;
//    setRotation(legR2, 0F, 0F, 3.141593F);
//    ModelPart legR3 = new ModelPart(this, 32, 16);
//    legR3.addBox(-1F, -4F, -1F, 2, 4, 2);
//    legR3.setPos(-2.5F, 13F, -2F);
//    legR3.setTexSize(64, 64);
//    legR3.mirror = true;
//    setRotation(legR3, 0.1308997F, 0F, 0F);
//    ModelPart legR4 = new ModelPart(this, 8, 16);
//    legR4.addBox(-1F, -4F, -1F, 2, 4, 2);
//    legR4.setPos(-3F, 12F, -2F);
//    legR4.setTexSize(64, 64);
//    legR4.mirror = true;
//    setRotation(legR4, -1.047198F, -2.617994F, 0F);
//    ModelPart legR5 = new ModelPart(this, 8, 16);
//    legR5.addBox(-1F, -4F, -1F, 2, 4, 2);
//    legR5.setPos(-3F, 12F, -1F);
//    legR5.setTexSize(64, 64);
//    legR5.mirror = true;
//    setRotation(legR5, -1.047198F, -1.832596F, 0F);
//    ModelPart legR6 = new ModelPart(this, 32, 16);
//    legR6.addBox(-2F, 0F, -1F, 4, 4, 2);
//    legR6.setPos(-4F, 12F, -0.5F);
//    legR6.setTexSize(64, 64);
//    legR6.mirror = true;
//    setRotation(legR6, -0.2617994F, 1.570796F, 0F);
//    ModelPart legL1 = new ModelPart(this, 32, 16);
//    legL1.addBox(-2F, -4F, -1F, 4, 4, 2);
//    legL1.setPos(2.5F, 16F, -2.5F);
//    legL1.setTexSize(64, 64);
//    legL1.mirror = true;
//    setRotation(legL1, 0.1308997F, 0F, 0F);
//    ModelPart legL2 = new ModelPart(this, 32, 16);
//    legL2.addBox(-1F, 0F, -1F, 2, 4, 2);
//    legL2.setPos(2.5F, 19F, -2F);
//    legL2.setTexSize(64, 64);
//    legL2.mirror = true;
//    setRotation(legL2, 0F, 0F, 3.141593F);
//    ModelPart legL3 = new ModelPart(this, 32, 16);
//    legL3.addBox(-1F, -4F, -1F, 2, 4, 2);
//    legL3.setPos(2.5F, 13F, -2F);
//    legL3.setTexSize(64, 64);
//    legL3.mirror = true;
//    setRotation(legL3, 0.1308997F, 0F, 0F);
//    ModelPart legL4 = new ModelPart(this, 8, 16);
//    legL4.addBox(-1F, -4F, -1F, 2, 4, 2);
//    legL4.setPos(3F, 12F, -2F);
//    legL4.setTexSize(64, 64);
//    legL4.mirror = true;
//    setRotation(legL4, -1.047198F, 2.617994F, 0F);
//    ModelPart legL5 = new ModelPart(this, 8, 16);
//    legL5.addBox(-1F, -4F, -1F, 2, 4, 2);
//    legL5.setPos(3F, 12F, -1F);
//    legL5.setTexSize(64, 64);
//    legL5.mirror = true;
//    setRotation(legL5, -1.047198F, 1.832596F, 0F);
//    ModelPart legL6 = new ModelPart(this, 32, 16);
//    legL6.addBox(-2F, 0F, -1F, 4, 4, 2);
//    legL6.setPos(4F, 12F, -0.5F);
//    legL6.setTexSize(64, 64);
//    legL6.mirror = true;
//    setRotation(legL6, -0.2617994F, -1.570796F, 0F);
//    ModelPart bootR1 = new ModelPart(this, 16, 32);
//    bootR1.addBox(-2F, -4F, -2F, 4, 4, 4);
//    bootR1.setPos(-2F, 22F, -2F);
//    bootR1.setTexSize(64, 64);
//    bootR1.mirror = true;
//    setRotation(bootR1, 0F, 0F, 0F);
//    ModelPart bootR2 = new ModelPart(this, 32, 16);
//    bootR2.addBox(-1F, -4F, -1F, 2, 4, 2);
//    bootR2.setPos(-3F, 19F, -1F);
//    bootR2.setTexSize(64, 64);
//    bootR2.mirror = true;
//    setRotation(bootR2, -1.047198F, -0.5235988F, 0F);
//    ModelPart bootR3 = new ModelPart(this, 32, 16);
//    bootR3.addBox(-1F, -4F, -1F, 2, 4, 2);
//    bootR3.setPos(-3F, 19F, -1F);
//    bootR3.setTexSize(64, 64);
//    bootR3.mirror = true;
//    setRotation(bootR3, -1.832596F, -0.7853982F, 0F);
//    ModelPart bootL1 = new ModelPart(this, 16, 32);
//    bootL1.addBox(-2F, -4F, -2F, 4, 4, 4);
//    bootL1.setPos(2F, 22F, -2F);
//    bootL1.setTexSize(64, 64);
//    bootL1.mirror = true;
//    setRotation(bootL1, 0F, 0F, 0F);
//    ModelPart bootL2 = new ModelPart(this, 32, 16);
//    bootL2.addBox(-1F, -4F, -1F, 2, 4, 2);
//    bootL2.setPos(3F, 19F, -1F);
//    bootL2.setTexSize(64, 64);
//    bootL2.mirror = true;
//    setRotation(bootL2, -1.047198F, 0.5235988F, 0F);
//    ModelPart bootL3 = new ModelPart(this, 32, 16);
//    bootL3.addBox(-1F, -4F, -1F, 2, 4, 2);
//    bootL3.setPos(3F, 19F, -1F);
//    bootL3.setTexSize(64, 64);
//    bootL3.mirror = true;
//    setRotation(bootL3, -1.832596F, 0.7853982F, 0F);
//    head = new ModelPart(this);
//    head.addChild(head1);
//    head.addChild(head2);
//    head.addChild(head3);
//    head.addChild(head4);
//    head.addChild(head5);
//    head.addChild(head6);
//    head.addChild(head7);
//    head.addChild(head8);
//    head.addChild(head9);
//    head.addChild(head10);
//    head.addChild(head11);
//    head.addChild(head12);
//    head.addChild(head13);
//    head.addChild(head14);
//    head.addChild(head15);
//    head.addChild(head16);
//    head.addChild(head17);
//    chest = new ModelPart(this);
//    chest.addChild(chest1);
//    chest.addChild(chest2);
//    chest.addChild(chest3);
//    chest.addChild(chest4);
//    chest.addChild(chest5);
//    chest.addChild(chest6);
//    chest.addChild(chest7);
//    chest.addChild(chest8);
//    chest.addChild(chest9);
//    chest.addChild(chest10);
//    chest.addChild(chest11);
//    chest.addChild(chest12);
//    chest.addChild(chest13);
//    for (int i = 0; i < chest.children.size(); i++) {
//      chest.children.get(i).y -= 1;
//    }
//    armR = new ModelPart(this);
//    armR.addChild(armR1);
//    armR.addChild(armR2);
//    armR.addChild(armR3);
//    armR.addChild(armR4);
//    armR.addChild(armR5);
//    armR.addChild(armR6);
//    armL = new ModelPart(this);
//    armL.addChild(armL1);
//    armL.addChild(armL2);
//    armL.addChild(armL3);
//    armL.addChild(armL4);
//    armL.addChild(armL5);
//    armL.addChild(armL6);
//    legR = new ModelPart(this);
//    legR.setPos(0, -12, 0);
//    legR.addChild(legR1);
//    legR.addChild(legR2);
//    legR.addChild(legR3);
//    legR.addChild(legR4);
//    legR.addChild(legR5);
//    legR.addChild(legR6);
//    for (int i = 0; i < legR.children.size(); i++) {
//      legR.children.get(i).y -= 12;
//    }
//    legL = new ModelPart(this);
//    legL.setPos(0, -12, 0);
//    legL.addChild(legL1);
//    legL.addChild(legL2);
//    legL.addChild(legL3);
//    legL.addChild(legL4);
//    legL.addChild(legL5);
//    legL.addChild(legL6);
//    for (int i = 0; i < legL.children.size(); i++) {
//      legL.children.get(i).y -= 12;
//    }
//    bootR = new ModelPart(this);
//    bootR.setPos(0, -12, 0);
//    bootR.addChild(bootR1);
//    bootR.addChild(bootR2);
//    bootR.addChild(bootR3);
//    for (int i = 0; i < bootR.children.size(); i++) {
//      bootR.children.get(i).y -= 12;
//    }
//    bootL = new ModelPart(this);
//    bootL.setPos(0, -12, 0);
//    bootL.addChild(bootL1);
//    bootL.addChild(bootL2);
//    bootL.addChild(bootL3);
//    for (int i = 0; i < bootL.children.size(); i++) {
//      bootL.children.get(i).y -= 12;
//    }
  }
}
