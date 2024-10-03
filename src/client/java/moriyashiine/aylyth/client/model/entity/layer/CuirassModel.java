package moriyashiine.aylyth.client.model.entity.layer;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.Identifier;

import java.util.NoSuchElementException;

public class CuirassModel extends BipedEntityModel<AbstractClientPlayerEntity> {
    public static final EntityModelLayer LAYER_LOCATION_1 = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_cuirass_1"), "main");
    public static final EntityModelLayer LAYER_LOCATION_2 = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_cuirass_2"), "main");
    public static final EntityModelLayer LAYER_LOCATION_3 = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_cuirass_3"), "main");
    public static final EntityModelLayer LAYER_LOCATION_4 = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_cuirass_4"), "main");
    public static final EntityModelLayer LAYER_LOCATION_5 = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_cuirass_5"), "main");

    private ModelPart armorLeftArm;
    private ModelPart armorRightArm;

    public CuirassModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
        try {
            armorRightArm = rightArm.getChild("armorRightArm");
            armorLeftArm = leftArm.getChild("armorLeftArm");
        } catch (NoSuchElementException ignored) {
        }
    }

    private static ModelData getBaseData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));
        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(1.9F, 12.0F, 0.0F));
        return data;
    }

    public void adjustArmPivots(boolean slim) {
        if (armorRightArm != null) {
            armorRightArm.pivotX = slim ? 1.5F : 0.5F;
        }
        if (armorLeftArm != null) {
            armorLeftArm.pivotX = slim ? -1F : 0F;
        }
    }


    public static TexturedModelData createBodyLayer1() {
        ModelData meshdefinition = getBaseData();
        ModelPartData partdefinition = meshdefinition.getRoot();
        ModelPartData body = partdefinition.getChild(EntityModelPartNames.BODY);
        ModelPartData rArm = partdefinition.getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData lArm = partdefinition.getChild(EntityModelPartNames.LEFT_ARM);

        ModelPartData armorBody = body.addChild("armorBody", ModelPartBuilder.create().uv(37, 93).cuboid(-4.5F, -0.35F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData spine = armorBody.addChild("spine", ModelPartBuilder.create().uv(93, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 2.25F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r1 = spine.addChild("cube_r1", ModelPartBuilder.create().uv(112, 115).cuboid(0.0F, -2.75F, 1.0F, 0.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData coric_seed = armorBody.addChild("coric_seed", ModelPartBuilder.create().uv(93, 18).cuboid(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(-0.25F, 1.75F, -2.25F, -0.7854F, -0.7854F, 0.7854F));
        ModelPartData breastbone = armorBody.addChild("breastbone", ModelPartBuilder.create(), ModelTransform.pivot(0.25F, 1.0F, -2.25F));
        ModelPartData breastboneshadow_r1 = breastbone.addChild("breastboneshadow_r1", ModelPartBuilder.create().uv(91, 22).cuboid(-1.25F, -1.0F, -1.1F, 2.0F, 5.0F, 2.0F, new Dilation(-0.11F)).uv(104, 15).cuboid(-1.25F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData lFRibs = armorBody.addChild("lFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lRib01 = lFRibs.addChild("lRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 1.0F, -1.5F));
        ModelPartData cube_r2 = lRib01.addChild("cube_r2", ModelPartBuilder.create().uv(112, 0).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib02 = lFRibs.addChild("lRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r3 = lRib02.addChild("cube_r3", ModelPartBuilder.create().uv(113, 3).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lBRibs = armorBody.addChild("lBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData lbRib01 = lBRibs.addChild("lbRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 0.75F, -1.5F));
        ModelPartData cube_r4 = lbRib01.addChild("cube_r4", ModelPartBuilder.create().uv(115, 1).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib02 = lBRibs.addChild("lbRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r5 = lbRib02.addChild("cube_r5", ModelPartBuilder.create().uv(115, 4).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rBRibs = armorBody.addChild("rBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData rbRib01 = rBRibs.addChild("rbRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 0.75F, -1.5F));
        ModelPartData cube_r6 = rbRib01.addChild("cube_r6", ModelPartBuilder.create().uv(112, 1).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib02 = rBRibs.addChild("rbRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r7 = rbRib02.addChild("cube_r7", ModelPartBuilder.create().uv(112, 4).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rFRibs = armorBody.addChild("rFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rRib01 = rFRibs.addChild("rRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 1.0F, -1.5F));
        ModelPartData cube_r8 = rRib01.addChild("cube_r8", ModelPartBuilder.create().uv(112, 0).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib02 = rFRibs.addChild("rRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r9 = rRib02.addChild("cube_r9", ModelPartBuilder.create().uv(113, 3).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData armorRightArm = rArm.addChild("armorRightArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rShoulderPad = armorRightArm.addChild("rShoulderPad", ModelPartBuilder.create().uv(102, 23).mirrored().cuboid(-2.25F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false).uv(116, 113).mirrored().cuboid(-4.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, -1.0F, 0.0F));
        ModelPartData armorLeftArm = lArm.addChild("armorLeftArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lShoulderPad = armorLeftArm.addChild("lShoulderPad", ModelPartBuilder.create().uv(102, 23).cuboid(-2.75F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).uv(104, 112).cuboid(-2.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -1.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }

    public static TexturedModelData createBodyLayer2() {
        ModelData meshdefinition = getBaseData();
        ModelPartData partdefinition = meshdefinition.getRoot();
        ModelPartData body = partdefinition.getChild(EntityModelPartNames.BODY);
        ModelPartData rArm = partdefinition.getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData lArm = partdefinition.getChild(EntityModelPartNames.LEFT_ARM);

        ModelPartData armorBody = body.addChild("armorBody", ModelPartBuilder.create().uv(37, 72).cuboid(-4.5F, -0.35F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData spine = armorBody.addChild("spine", ModelPartBuilder.create().uv(93, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 2.25F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r1 = spine.addChild("cube_r1", ModelPartBuilder.create().uv(111, 95).cuboid(0.0F, -2.75F, 1.0F, 0.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData coric_seed = armorBody.addChild("coric_seed", ModelPartBuilder.create().uv(93, 18).cuboid(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(-0.25F, 1.75F, -2.25F, -0.7854F, -0.7854F, 0.7854F));
        ModelPartData breastbone = armorBody.addChild("breastbone", ModelPartBuilder.create(), ModelTransform.pivot(0.25F, 1.0F, -2.25F));
        ModelPartData breastboneshadow_r1 = breastbone.addChild("breastboneshadow_r1", ModelPartBuilder.create().uv(91, 22).cuboid(-1.25F, -1.0F, -1.1F, 2.0F, 5.0F, 2.0F, new Dilation(-0.11F)).uv(104, 15).cuboid(-1.25F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData lFRibs = armorBody.addChild("lFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lRib01 = lFRibs.addChild("lRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 1.0F, -1.5F));
        ModelPartData cube_r2 = lRib01.addChild("cube_r2", ModelPartBuilder.create().uv(112, 0).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib02 = lFRibs.addChild("lRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r3 = lRib02.addChild("cube_r3", ModelPartBuilder.create().uv(113, 3).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib03 = lFRibs.addChild("lRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.0F, -1.5F));
        ModelPartData cube_r4 = lRib03.addChild("cube_r4", ModelPartBuilder.create().uv(116, 6).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lBRibs = armorBody.addChild("lBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData lbRib01 = lBRibs.addChild("lbRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 0.75F, -1.5F));
        ModelPartData cube_r5 = lbRib01.addChild("cube_r5", ModelPartBuilder.create().uv(115, 1).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib02 = lBRibs.addChild("lbRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r6 = lbRib02.addChild("cube_r6", ModelPartBuilder.create().uv(115, 4).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib03 = lBRibs.addChild("lbRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.5F, -1.5F));
        ModelPartData cube_r7 = lbRib03.addChild("cube_r7", ModelPartBuilder.create().uv(118, 7).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rBRibs = armorBody.addChild("rBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData rbRib01 = rBRibs.addChild("rbRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 0.75F, -1.5F));
        ModelPartData cube_r8 = rbRib01.addChild("cube_r8", ModelPartBuilder.create().uv(112, 1).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib02 = rBRibs.addChild("rbRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r9 = rbRib02.addChild("cube_r9", ModelPartBuilder.create().uv(112, 4).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib03 = rBRibs.addChild("rbRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.5F, -1.5F));
        ModelPartData cube_r10 = rbRib03.addChild("cube_r10", ModelPartBuilder.create().uv(115, 7).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rFRibs = armorBody.addChild("rFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rRib01 = rFRibs.addChild("rRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 1.0F, -1.5F));
        ModelPartData cube_r11 = rRib01.addChild("cube_r11", ModelPartBuilder.create().uv(112, 0).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib02 = rFRibs.addChild("rRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r12 = rRib02.addChild("cube_r12", ModelPartBuilder.create().uv(113, 3).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib03 = rFRibs.addChild("rRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.0F, -1.5F));
        ModelPartData cube_r13 = rRib03.addChild("cube_r13", ModelPartBuilder.create().uv(116, 6).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData armorRightArm = rArm.addChild("armorRightArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rShoulderPad = armorRightArm.addChild("rShoulderPad", ModelPartBuilder.create().uv(102, 23).mirrored().cuboid(-2.25F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false).uv(116, 93).mirrored().cuboid(-4.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, -1.0F, 0.0F));
        ModelPartData armorLeftArm = lArm.addChild("armorLeftArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lShoulderPad = armorLeftArm.addChild("lShoulderPad", ModelPartBuilder.create().uv(102, 23).cuboid(-2.75F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).uv(104, 92).cuboid(-2.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -1.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }

    public static TexturedModelData createBodyLayer3() {
        ModelData meshdefinition = getBaseData();
        ModelPartData partdefinition = meshdefinition.getRoot();
        ModelPartData body = partdefinition.getChild(EntityModelPartNames.BODY);
        ModelPartData rArm = partdefinition.getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData lArm = partdefinition.getChild(EntityModelPartNames.LEFT_ARM);

        ModelPartData armorBody = body.addChild("armorBody", ModelPartBuilder.create().uv(0, 93).cuboid(-4.5F, -0.35F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData spine = armorBody.addChild("spine", ModelPartBuilder.create().uv(93, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 2.25F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r1 = spine.addChild("cube_r1", ModelPartBuilder.create().uv(111, 75).cuboid(0.0F, -2.75F, 1.0F, 0.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData coric_seed = armorBody.addChild("coric_seed", ModelPartBuilder.create().uv(93, 18).cuboid(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(-0.25F, 1.75F, -2.25F, -0.7854F, -0.7854F, 0.7854F));
        ModelPartData breastbone = armorBody.addChild("breastbone", ModelPartBuilder.create(), ModelTransform.pivot(0.25F, 1.0F, -2.25F));
        ModelPartData breastboneshadow_r1 = breastbone.addChild("breastboneshadow_r1", ModelPartBuilder.create().uv(91, 22).cuboid(-1.25F, -1.0F, -1.1F, 2.0F, 5.0F, 2.0F, new Dilation(-0.11F)).uv(104, 15).cuboid(-1.25F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData lFRibs = armorBody.addChild("lFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lRib01 = lFRibs.addChild("lRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 1.0F, -1.5F));
        ModelPartData cube_r2 = lRib01.addChild("cube_r2", ModelPartBuilder.create().uv(112, 0).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib02 = lFRibs.addChild("lRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r3 = lRib02.addChild("cube_r3", ModelPartBuilder.create().uv(113, 3).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib03 = lFRibs.addChild("lRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.0F, -1.5F));
        ModelPartData cube_r4 = lRib03.addChild("cube_r4", ModelPartBuilder.create().uv(116, 6).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib04 = lFRibs.addChild("lRib04", ModelPartBuilder.create(), ModelTransform.of(1.0F, 5.5F, -1.5F, 0.0F, 0.0F, 0.1309F));
        ModelPartData cube_r5 = lRib04.addChild("cube_r5", ModelPartBuilder.create().uv(113, 9).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lBRibs = armorBody.addChild("lBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData lbRib01 = lBRibs.addChild("lbRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 0.75F, -1.5F));
        ModelPartData cube_r6 = lbRib01.addChild("cube_r6", ModelPartBuilder.create().uv(115, 1).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib02 = lBRibs.addChild("lbRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r7 = lbRib02.addChild("cube_r7", ModelPartBuilder.create().uv(115, 4).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib03 = lBRibs.addChild("lbRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.5F, -1.5F));
        ModelPartData cube_r8 = lbRib03.addChild("cube_r8", ModelPartBuilder.create().uv(118, 7).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib04 = lBRibs.addChild("lbRib04", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 6.5F, -1.5F));
        ModelPartData cube_r9 = lbRib04.addChild("cube_r9", ModelPartBuilder.create().uv(115, 10).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rBRibs = armorBody.addChild("rBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData rbRib01 = rBRibs.addChild("rbRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 0.75F, -1.5F));
        ModelPartData cube_r10 = rbRib01.addChild("cube_r10", ModelPartBuilder.create().uv(112, 1).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib02 = rBRibs.addChild("rbRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r11 = rbRib02.addChild("cube_r11", ModelPartBuilder.create().uv(112, 4).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib03 = rBRibs.addChild("rbRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.5F, -1.5F));
        ModelPartData cube_r12 = rbRib03.addChild("cube_r12", ModelPartBuilder.create().uv(115, 7).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib04 = rBRibs.addChild("rbRib04", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 6.5F, -1.5F));
        ModelPartData cube_r13 = rbRib04.addChild("cube_r13", ModelPartBuilder.create().uv(112, 10).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rFRibs = armorBody.addChild("rFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rRib01 = rFRibs.addChild("rRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 1.0F, -1.5F));
        ModelPartData cube_r14 = rRib01.addChild("cube_r14", ModelPartBuilder.create().uv(112, 0).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib02 = rFRibs.addChild("rRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r15 = rRib02.addChild("cube_r15", ModelPartBuilder.create().uv(113, 3).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib03 = rFRibs.addChild("rRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.0F, -1.5F));
        ModelPartData cube_r16 = rRib03.addChild("cube_r16", ModelPartBuilder.create().uv(116, 6).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib04 = rFRibs.addChild("rRib04", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 5.5F, -1.5F, 0.0F, 0.0F, -0.1309F));
        ModelPartData cube_r17 = rRib04.addChild("cube_r17", ModelPartBuilder.create().uv(113, 9).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData armorRightArm = rArm.addChild("armorRightArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rShoulderPad = armorRightArm.addChild("rShoulderPad", ModelPartBuilder.create().uv(102, 23).mirrored().cuboid(-2.25F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false).uv(116, 73).mirrored().cuboid(-4.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, -1.0F, 0.0F));
        ModelPartData armorLeftArm = lArm.addChild("armorLeftArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lShoulderPad = armorLeftArm.addChild("lShoulderPad", ModelPartBuilder.create().uv(102, 23).cuboid(-2.75F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).uv(104, 72).cuboid(-2.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -1.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }

    public static TexturedModelData createBodyLayer4() {
        ModelData meshdefinition = getBaseData();
        ModelPartData partdefinition = meshdefinition.getRoot();
        ModelPartData body = partdefinition.getChild(EntityModelPartNames.BODY);
        ModelPartData rArm = partdefinition.getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData lArm = partdefinition.getChild(EntityModelPartNames.LEFT_ARM);

        ModelPartData armorBody = body.addChild("armorBody", ModelPartBuilder.create().uv(0, 72).cuboid(-4.5F, -0.35F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData spine = armorBody.addChild("spine", ModelPartBuilder.create().uv(93, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 2.25F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r1 = spine.addChild("cube_r1", ModelPartBuilder.create().uv(111, 56).cuboid(0.0F, -2.75F, 1.0F, 0.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData coric_seed = armorBody.addChild("coric_seed", ModelPartBuilder.create().uv(93, 18).cuboid(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(-0.25F, 1.75F, -2.25F, -0.7854F, -0.7854F, 0.7854F));
        ModelPartData breastbone = armorBody.addChild("breastbone", ModelPartBuilder.create(), ModelTransform.pivot(0.25F, 1.0F, -2.25F));
        ModelPartData breastboneshadow_r1 = breastbone.addChild("breastboneshadow_r1", ModelPartBuilder.create().uv(91, 22).cuboid(-1.25F, -1.0F, -1.1F, 2.0F, 5.0F, 2.0F, new Dilation(-0.11F)).uv(104, 15).cuboid(-1.25F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData lFRibs = armorBody.addChild("lFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lRib01 = lFRibs.addChild("lRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 1.0F, -1.5F));
        ModelPartData cube_r2 = lRib01.addChild("cube_r2", ModelPartBuilder.create().uv(112, 0).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib02 = lFRibs.addChild("lRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r3 = lRib02.addChild("cube_r3", ModelPartBuilder.create().uv(113, 3).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib03 = lFRibs.addChild("lRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.0F, -1.5F));
        ModelPartData cube_r4 = lRib03.addChild("cube_r4", ModelPartBuilder.create().uv(116, 6).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib04 = lFRibs.addChild("lRib04", ModelPartBuilder.create(), ModelTransform.of(1.0F, 5.5F, -1.5F, 0.0F, 0.0F, 0.1309F));
        ModelPartData cube_r5 = lRib04.addChild("cube_r5", ModelPartBuilder.create().uv(113, 9).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib05 = lFRibs.addChild("lRib05", ModelPartBuilder.create(), ModelTransform.of(1.25F, 6.25F, -1.5F, 0.0F, 0.0F, 0.4363F));
        ModelPartData cube_r6 = lRib05.addChild("cube_r6", ModelPartBuilder.create().uv(116, 15).cuboid(-1.25F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lBRibs = armorBody.addChild("lBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData lbRib01 = lBRibs.addChild("lbRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 0.75F, -1.5F));
        ModelPartData cube_r7 = lbRib01.addChild("cube_r7", ModelPartBuilder.create().uv(115, 1).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib02 = lBRibs.addChild("lbRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r8 = lbRib02.addChild("cube_r8", ModelPartBuilder.create().uv(115, 4).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib03 = lBRibs.addChild("lbRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.5F, -1.5F));
        ModelPartData cube_r9 = lbRib03.addChild("cube_r9", ModelPartBuilder.create().uv(118, 7).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib04 = lBRibs.addChild("lbRib04", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 6.5F, -1.5F));
        ModelPartData cube_r10 = lbRib04.addChild("cube_r10", ModelPartBuilder.create().uv(115, 10).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib05 = lBRibs.addChild("lbRib05", ModelPartBuilder.create(), ModelTransform.pivot(1.25F, 8.5F, -1.5F));
        ModelPartData cube_r11 = lbRib05.addChild("cube_r11", ModelPartBuilder.create().uv(117, 13).cuboid(-0.75F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rBRibs = armorBody.addChild("rBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData rbRib01 = rBRibs.addChild("rbRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 0.75F, -1.5F));
        ModelPartData cube_r12 = rbRib01.addChild("cube_r12", ModelPartBuilder.create().uv(112, 1).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib02 = rBRibs.addChild("rbRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r13 = rbRib02.addChild("cube_r13", ModelPartBuilder.create().uv(112, 4).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib03 = rBRibs.addChild("rbRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.5F, -1.5F));
        ModelPartData cube_r14 = rbRib03.addChild("cube_r14", ModelPartBuilder.create().uv(115, 7).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib04 = rBRibs.addChild("rbRib04", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 6.5F, -1.5F));
        ModelPartData cube_r15 = rbRib04.addChild("cube_r15", ModelPartBuilder.create().uv(112, 10).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib05 = rBRibs.addChild("rbRib05", ModelPartBuilder.create(), ModelTransform.pivot(-1.25F, 8.5F, -1.5F));
        ModelPartData cube_r16 = rbRib05.addChild("cube_r16", ModelPartBuilder.create().uv(114, 13).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rFRibs = armorBody.addChild("rFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rRib01 = rFRibs.addChild("rRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 1.0F, -1.5F));
        ModelPartData cube_r17 = rRib01.addChild("cube_r17", ModelPartBuilder.create().uv(112, 0).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib02 = rFRibs.addChild("rRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r18 = rRib02.addChild("cube_r18", ModelPartBuilder.create().uv(113, 3).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib03 = rFRibs.addChild("rRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.0F, -1.5F));
        ModelPartData cube_r19 = rRib03.addChild("cube_r19", ModelPartBuilder.create().uv(116, 6).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib04 = rFRibs.addChild("rRib04", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 5.5F, -1.5F, 0.0F, 0.0F, -0.1309F));
        ModelPartData cube_r20 = rRib04.addChild("cube_r20", ModelPartBuilder.create().uv(113, 9).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib05 = rFRibs.addChild("rRib05", ModelPartBuilder.create(), ModelTransform.of(-1.25F, 6.25F, -1.5F, 0.0F, 0.0F, -0.4363F));
        ModelPartData cube_r21 = rRib05.addChild("cube_r21", ModelPartBuilder.create().uv(116, 15).mirrored().cuboid(-3.75F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData armorRightArm = rArm.addChild("armorRightArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rShoulderPad = armorRightArm.addChild("rShoulderPad", ModelPartBuilder.create().uv(102, 23).mirrored().cuboid(-2.25F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false).uv(116, 53).mirrored().cuboid(-4.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, -1.0F, 0.0F));
        ModelPartData armorLeftArm = lArm.addChild("armorLeftArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lShoulderPad = armorLeftArm.addChild("lShoulderPad", ModelPartBuilder.create().uv(102, 23).cuboid(-2.75F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).uv(104, 53).cuboid(-2.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -1.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }

    public static TexturedModelData createBodyLayer5() {
        ModelData meshdefinition = getBaseData();
        ModelPartData partdefinition = meshdefinition.getRoot();
        ModelPartData body = partdefinition.getChild(EntityModelPartNames.BODY);
        ModelPartData rArm = partdefinition.getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData lArm = partdefinition.getChild(EntityModelPartNames.LEFT_ARM);

        ModelPartData armorBody = body.addChild("armorBody", ModelPartBuilder.create().uv(64, 0).cuboid(-4.5F, -0.35F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData spine = armorBody.addChild("spine", ModelPartBuilder.create().uv(93, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 2.25F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r1 = spine.addChild("cube_r1", ModelPartBuilder.create().uv(111, 35).cuboid(0.0F, -2.75F, 1.0F, 0.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData coric_seed = armorBody.addChild("coric_seed", ModelPartBuilder.create().uv(93, 18).cuboid(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(-0.25F, 1.75F, -2.25F, -0.7854F, -0.7854F, 0.7854F));
        ModelPartData breastbone = armorBody.addChild("breastbone", ModelPartBuilder.create(), ModelTransform.pivot(0.25F, 1.0F, -2.25F));
        ModelPartData breastboneshadow_r1 = breastbone.addChild("breastboneshadow_r1", ModelPartBuilder.create().uv(91, 22).cuboid(-1.25F, -1.0F, -1.1F, 2.0F, 5.0F, 2.0F, new Dilation(-0.11F)).uv(104, 15).cuboid(-1.25F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData lFRibs = armorBody.addChild("lFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lRib01 = lFRibs.addChild("lRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 1.0F, -1.5F));
        ModelPartData cube_r2 = lRib01.addChild("cube_r2", ModelPartBuilder.create().uv(112, 0).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib02 = lFRibs.addChild("lRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r3 = lRib02.addChild("cube_r3", ModelPartBuilder.create().uv(113, 3).cuboid(0.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib03 = lFRibs.addChild("lRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.0F, -1.5F));
        ModelPartData cube_r4 = lRib03.addChild("cube_r4", ModelPartBuilder.create().uv(116, 6).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib04 = lFRibs.addChild("lRib04", ModelPartBuilder.create(), ModelTransform.of(1.0F, 5.5F, -1.5F, 0.0F, 0.0F, 0.1309F));
        ModelPartData cube_r5 = lRib04.addChild("cube_r5", ModelPartBuilder.create().uv(113, 9).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib05 = lFRibs.addChild("lRib05", ModelPartBuilder.create(), ModelTransform.of(1.25F, 6.25F, -1.5F, 0.0F, 0.0F, 0.4363F));
        ModelPartData cube_r6 = lRib05.addChild("cube_r6", ModelPartBuilder.create().uv(116, 15).cuboid(-1.25F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lRib06 = lFRibs.addChild("lRib06", ModelPartBuilder.create(), ModelTransform.of(1.0F, 6.5F, -1.5F, 0.0F, 0.0F, 0.829F));
        ModelPartData cube_r7 = lRib06.addChild("cube_r7", ModelPartBuilder.create().uv(113, 15).cuboid(-1.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lBRibs = armorBody.addChild("lBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData lbRib01 = lBRibs.addChild("lbRib01", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 0.75F, -1.5F));
        ModelPartData cube_r8 = lbRib01.addChild("cube_r8", ModelPartBuilder.create().uv(115, 1).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib02 = lBRibs.addChild("lbRib02", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.5F, -1.5F));
        ModelPartData cube_r9 = lbRib02.addChild("cube_r9", ModelPartBuilder.create().uv(115, 4).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib03 = lBRibs.addChild("lbRib03", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.5F, -1.5F));
        ModelPartData cube_r10 = lbRib03.addChild("cube_r10", ModelPartBuilder.create().uv(118, 7).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib04 = lBRibs.addChild("lbRib04", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 6.5F, -1.5F));
        ModelPartData cube_r11 = lbRib04.addChild("cube_r11", ModelPartBuilder.create().uv(115, 10).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib05 = lBRibs.addChild("lbRib05", ModelPartBuilder.create(), ModelTransform.pivot(1.25F, 8.5F, -1.5F));
        ModelPartData cube_r12 = lbRib05.addChild("cube_r12", ModelPartBuilder.create().uv(117, 13).cuboid(-0.75F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData lbRib06 = lBRibs.addChild("lbRib06", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 10.5F, -1.5F));
        ModelPartData cube_r13 = lbRib06.addChild("cube_r13", ModelPartBuilder.create().uv(116, 16).cuboid(-0.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rBRibs = armorBody.addChild("rBRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));
        ModelPartData rbRib01 = rBRibs.addChild("rbRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 0.75F, -1.5F));
        ModelPartData cube_r14 = rbRib01.addChild("cube_r14", ModelPartBuilder.create().uv(112, 1).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib02 = rBRibs.addChild("rbRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r15 = rbRib02.addChild("cube_r15", ModelPartBuilder.create().uv(112, 4).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib03 = rBRibs.addChild("rbRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.5F, -1.5F));
        ModelPartData cube_r16 = rbRib03.addChild("cube_r16", ModelPartBuilder.create().uv(115, 7).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib04 = rBRibs.addChild("rbRib04", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 6.5F, -1.5F));
        ModelPartData cube_r17 = rbRib04.addChild("cube_r17", ModelPartBuilder.create().uv(112, 10).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib05 = rBRibs.addChild("rbRib05", ModelPartBuilder.create(), ModelTransform.pivot(-1.25F, 8.5F, -1.5F));
        ModelPartData cube_r18 = rbRib05.addChild("cube_r18", ModelPartBuilder.create().uv(114, 13).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rbRib06 = rBRibs.addChild("rbRib06", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 10.5F, -1.5F));
        ModelPartData cube_r19 = rbRib06.addChild("cube_r19", ModelPartBuilder.create().uv(113, 16).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rFRibs = armorBody.addChild("rFRibs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rRib01 = rFRibs.addChild("rRib01", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 1.0F, -1.5F));
        ModelPartData cube_r20 = rRib01.addChild("cube_r20", ModelPartBuilder.create().uv(112, 0).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib02 = rFRibs.addChild("rRib02", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 2.5F, -1.5F));
        ModelPartData cube_r21 = rRib02.addChild("cube_r21", ModelPartBuilder.create().uv(113, 3).mirrored().cuboid(-3.25F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib03 = rFRibs.addChild("rRib03", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.0F, -1.5F));
        ModelPartData cube_r22 = rRib03.addChild("cube_r22", ModelPartBuilder.create().uv(116, 6).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib04 = rFRibs.addChild("rRib04", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 5.5F, -1.5F, 0.0F, 0.0F, -0.1309F));
        ModelPartData cube_r23 = rRib04.addChild("cube_r23", ModelPartBuilder.create().uv(113, 9).mirrored().cuboid(-3.5F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib05 = rFRibs.addChild("rRib05", ModelPartBuilder.create(), ModelTransform.of(-1.25F, 6.25F, -1.5F, 0.0F, 0.0F, -0.4363F));
        ModelPartData cube_r24 = rRib05.addChild("cube_r24", ModelPartBuilder.create().uv(116, 15).mirrored().cuboid(-3.75F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData rRib06 = rFRibs.addChild("rRib06", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.5F, -1.5F, 0.0F, 0.0F, -0.829F));
        ModelPartData cube_r25 = rRib06.addChild("cube_r25", ModelPartBuilder.create().uv(113, 15).mirrored().cuboid(-5.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData armorRightArm = rArm.addChild("armorRightArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rShoulderPad = armorRightArm.addChild("rShoulderPad", ModelPartBuilder.create().uv(102, 23).mirrored().cuboid(-2.25F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false).uv(116, 32).mirrored().cuboid(-4.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, -1.0F, 0.0F));
        ModelPartData armorLeftArm = lArm.addChild("armorLeftArm", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData lShoulderPad = armorLeftArm.addChild("lShoulderPad", ModelPartBuilder.create().uv(102, 23).cuboid(-2.75F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)).uv(104, 32).cuboid(-2.0F, -5.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -1.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }
}
