package moriyashiine.aylyth.client.model.entity.armor;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17+ for Yarn
public class HeartwoodArmour extends BipedEntityModel<LivingEntity> {
    private final ModelPart armorHead;
    private final ModelPart frogmouth;
    private final ModelPart armorBody;
    private final ModelPart apron;
    private final ModelPart apron2;
    private final ModelPart leftTasset;
    private final ModelPart rightTasset;
    private final ModelPart armorRightArm;
    private final ModelPart armorLeftArm;
    private final ModelPart armorRightLeg;
    private final ModelPart armorRightBoot;
    private final ModelPart armorLeftLeg;
    private final ModelPart armorLeftBoot;

    public HeartwoodArmour(ModelPart root) {
        super(root);
        this.armorHead = this.head.getChild("armorHead");
        this.frogmouth = this.armorHead.getChild("frogmouth");
        this.armorBody = this.body.getChild("armorBody");
        this.apron = this.armorBody.getChild("apron");
        this.apron2 = this.armorBody.getChild("apron2");
        this.leftTasset = this.armorBody.getChild("leftTasset");
        this.rightTasset = this.armorBody.getChild("rightTasset");
        this.armorRightArm = this.rightArm.getChild("armorRightArm");
        this.armorLeftArm = this.leftArm.getChild("armorLeftArm");
        this.armorRightLeg = this.rightLeg.getChild("armorRightLeg");
        this.armorRightBoot = this.rightLeg.getChild("armorRightBoot");
        this.armorLeftLeg = this.leftLeg.getChild("armorLeftLeg");
        this.armorLeftBoot = this.armorLeftLeg.getChild("armorLeftBoot");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData armorHead = modelPartData.getChild(EntityModelPartNames.HEAD).addChild("armorHead", ModelPartBuilder.create().uv(48, 59).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData frogmouth = armorHead.addChild("frogmouth", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 1.0F, 0.5236F, 0.0F, 0.0F));

        ModelPartData frogmouth_r1 = frogmouth.addChild("frogmouth_r1", ModelPartBuilder.create().uv(72, 26).cuboid(-5.5F, -16.5F, -5.5F, 14.0F, 9.0F, 14.0F, new Dilation(0.0F))
                .uv(48, 41).cuboid(-6.0F, -7.5F, -6.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.05F, 1.7F, 0.0F, -0.7854F, 0.0F));

        ModelPartData armorBody = modelPartData.getChild(EntityModelPartNames.BODY).addChild("armorBody", ModelPartBuilder.create().uv(0, 64).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, new Dilation(0.4F))
                .uv(96, 79).cuboid(-5.0F, 0.0F, -3.3F, 10.0F, 4.0F, 6.0F, new Dilation(0.6F))
                .uv(24, 64).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.3F))
                .uv(102, 89).cuboid(-1.0F, 4.0F, 1.25F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(94, 62).cuboid(-5.0F, 1.0F, -4.0F, 10.0F, 10.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData breastplate_2_r1 = armorBody.addChild("breastplate_2_r1", ModelPartBuilder.create().uv(0, 86).cuboid(-4.5F, 2.8F, -4.9F, 9.0F, 5.0F, 3.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        ModelPartData breastplate_1_r1 = armorBody.addChild("breastplate_1_r1", ModelPartBuilder.create().uv(0, 78).cuboid(-4.5F, 0.0F, -2.65F, 9.0F, 5.0F, 3.0F, new Dilation(-0.01F))
                .uv(20, 90).cuboid(-4.5F, 9.0F, -0.1F, 9.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData backfauld_r1 = armorBody.addChild("backfauld_r1", ModelPartBuilder.create().uv(46, 90).cuboid(-4.5F, 9.0F, -3.85F, 9.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData apron = armorBody.addChild("apron", ModelPartBuilder.create().uv(48, 75).mirrored().cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 9.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 11.0326F, -2.7521F, -0.1745F, 0.0F, 0.0F));

        ModelPartData apron2 = armorBody.addChild("apron2", ModelPartBuilder.create().uv(62, 75).mirrored().cuboid(-3.0F, 0.0F, -1.0F, 6.0F, 9.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(0.0F, 10.8292F, 2.4907F, 0.1745F, 0.0F, 0.0F));

        ModelPartData leftTasset = armorBody.addChild("leftTasset", ModelPartBuilder.create().uv(28, 97).mirrored().cuboid(-2.5F, 0.0F, -0.4F, 4.0F, 4.0F, 4.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(3.0F, 11.5F, -3.0F, -0.0436F, 0.0F, 0.0F));

        ModelPartData rightTasset = armorBody.addChild("rightTasset", ModelPartBuilder.create().uv(28, 97).cuboid(-1.5F, 0.0F, -0.4F, 4.0F, 4.0F, 4.0F, new Dilation(0.1F)), ModelTransform.of(-3.0F, 11.5F, -3.0F, -0.0436F, 0.0F, 0.0F));

        ModelPartData armorRightArm = modelPartData.getChild(EntityModelPartNames.RIGHT_ARM).addChild("armorRightArm", ModelPartBuilder.create().uv(0, 104).cuboid(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.35F))
                .uv(0, 94).cuboid(-4.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.45F))
                .uv(16, 104).cuboid(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.33F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_plate_rim_r1 = armorRightArm.addChild("left_plate_rim_r1", ModelPartBuilder.create().uv(106, 8).cuboid(-1.75F, -5.0F, -5.5F, 0.0F, 6.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        ModelPartData left_besagew_r1 = armorRightArm.addChild("left_besagew_r1", ModelPartBuilder.create().uv(114, 12).mirrored().cuboid(-3.25F, -2.25F, -4.3F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        ModelPartData left_besagew_r2 = armorRightArm.addChild("left_besagew_r2", ModelPartBuilder.create().uv(114, 12).mirrored().cuboid(-3.25F, -2.25F, 2.3F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

        ModelPartData armorLeftArm = modelPartData.getChild(EntityModelPartNames.LEFT_ARM).addChild("armorLeftArm", ModelPartBuilder.create().uv(0, 104).mirrored().cuboid(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.35F)).mirrored(false)
                .uv(0, 94).mirrored().cuboid(0.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.45F)).mirrored(false)
                .uv(16, 104).mirrored().cuboid(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.33F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_plate_rim_r2 = armorLeftArm.addChild("left_plate_rim_r2", ModelPartBuilder.create().uv(106, 8).cuboid(1.75F, -5.0F, -5.5F, 0.0F, 6.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        ModelPartData left_besagew_r3 = armorLeftArm.addChild("left_besagew_r3", ModelPartBuilder.create().uv(114, 12).cuboid(-1.75F, -2.25F, 2.3F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        ModelPartData left_besagew_r4 = armorLeftArm.addChild("left_besagew_r4", ModelPartBuilder.create().uv(114, 12).cuboid(-1.75F, -2.25F, -4.3F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

        ModelPartData left_couter_r1 = armorLeftArm.addChild("left_couter_r1", ModelPartBuilder.create().uv(24, 80).cuboid(-0.5F, 0.1F, 3.5F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData armorRightLeg = modelPartData.getChild(EntityModelPartNames.RIGHT_LEG).addChild("armorRightLeg", ModelPartBuilder.create().uv(32, 105).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.36F)).mirrored(false)
                .uv(48, 105).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_poleyn_r1 = armorRightLeg.addChild("right_poleyn_r1", ModelPartBuilder.create().uv(24, 84).mirrored().cuboid(1.5F, 4.45F, 1.15F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData armorRightBoot = modelPartData.getChild(EntityModelPartNames.RIGHT_LEG).addChild("armorRightBoot", ModelPartBuilder.create().uv(12, 56).cuboid(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.45F))
                .uv(64, 116).cuboid(-2.5F, 5.0F, -2.5F, 5.0F, 7.0F, 5.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_sabaton_r1 = armorRightBoot.addChild("right_sabaton_r1", ModelPartBuilder.create().uv(28, 60).cuboid(-3.25F, 11.25F, -3.25F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData armorLeftLeg = modelPartData.getChild(EntityModelPartNames.LEFT_LEG).addChild("armorLeftLeg", ModelPartBuilder.create().uv(32, 105).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.36F))
                .uv(48, 105).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_poleyn_r1 = armorLeftLeg.addChild("left_poleyn_r1", ModelPartBuilder.create().uv(24, 84).cuboid(-6.5F, 4.45F, 1.15F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData armorLeftBoot = modelPartData.getChild(EntityModelPartNames.LEFT_LEG).addChild("armorLeftBoot", ModelPartBuilder.create().uv(12, 56).cuboid(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.45F))
                .uv(84, 116).cuboid(-2.5F, 5.0F, -2.5F, 5.0F, 7.0F, 5.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_sabaton_r1 = armorLeftBoot.addChild("left_sabaton_r1", ModelPartBuilder.create().uv(28, 60).cuboid(-3.25F, 11.25F, -3.25F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
}