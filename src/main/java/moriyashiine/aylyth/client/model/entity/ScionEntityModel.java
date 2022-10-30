package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ScionEntityModel<T extends ScionEntity> extends BipedEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "scion"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public ScionEntityModel(ModelPart root) {
		super(root);
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.body = root.getChild(EntityModelPartNames.BODY);
		this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
		this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
		this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
	}

	public static TexturedModelData createBodyLayer() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData ModelPartData = data.getRoot();

		ModelPartData head = ModelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(80, 110).cuboid(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		//ModelPartData leftArm = ModelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(108, 79).cuboid(-4.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 2.0F, 0.0F));

		ModelPartData crown = head.addChild("crown", ModelPartBuilder.create().uv(84, 98).cuboid(-3.5F, -7.0F, -1.0F, 7.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.4F, -4.5F, -0.8727F, 0.0F, 0.0F));
		ModelPartData lFaceWood = head.addChild("lFaceWood", ModelPartBuilder.create(), ModelTransform.of(4.0F, -5.25F, -1.0F, 0.0F, 0.1309F, 0.0F));
		ModelPartData cube_r1 = lFaceWood.addChild("cube_r1", ModelPartBuilder.create().uv(72, 105).cuboid(-1.5F, -3.0F, -1.0F, 2.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1309F, 0.2182F, 0.0F));
		ModelPartData rFaceWood = head.addChild("rFaceWood", ModelPartBuilder.create(), ModelTransform.of(-4.0F, -5.25F, -1.0F, 0.0F, -0.1309F, 0.0F));
		ModelPartData cube_r2 = rFaceWood.addChild("cube_r2", ModelPartBuilder.create().uv(72, 105).mirrored().cuboid(-0.5F, -3.0F, -1.0F, 2.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1309F, -0.2182F, 0.0F));
		ModelPartData lUpperAntler01 = head.addChild("lUpperAntler01", ModelPartBuilder.create().uv(102, 4).mirrored().cuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(3.4F, -8.6F, -1.0F, 0.6458F, 0.1396F, 0.6109F));
		ModelPartData lUpperAntler02a = lUpperAntler01.addChild("lUpperAntler02a", ModelPartBuilder.create().uv(102, 0).mirrored().cuboid(-0.8F, -1.05F, -0.5F, 2.0F, 2.0F, 4.0F, new Dilation(-0.2F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 1.2F, 0.0524F, -0.3403F, 0.0F));
		ModelPartData lUpperAntler07Leaf_r1 = lUpperAntler02a.addChild("lUpperAntler07Leaf_r1", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-1.6F, -0.1F, -0.25F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.4F, -0.9F, 3.0F, 0.3491F, 0.0F, 0.5672F));
		ModelPartData lUpperAntler03 = lUpperAntler02a.addChild("lUpperAntler03", ModelPartBuilder.create().uv(104, 0).mirrored().cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 3.5F, -0.1745F, 0.3491F, 0.0F));
		ModelPartData lUpperAntler04 = lUpperAntler03.addChild("lUpperAntler04", ModelPartBuilder.create().uv(105, 8).mirrored().cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 2.3F, 0.1396F, -0.576F, 0.0F));
		ModelPartData lUpperAntler05 = lUpperAntler04.addChild("lUpperAntler05", ModelPartBuilder.create().uv(108, 5).mirrored().cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(-0.2F)).mirrored(false), ModelTransform.of(-0.2F, 0.1F, 2.0F, 0.0F, -0.6981F, 0.0F));
		ModelPartData lUpperAntler06 = lUpperAntler04.addChild("lUpperAntler06", ModelPartBuilder.create().uv(105, 11).mirrored().cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(-0.2F)).mirrored(false), ModelTransform.of(0.1F, 0.3F, 1.4F, -0.1745F, 0.3142F, 0.0F));
		ModelPartData lUpperAntler06Leaf_r1 = lUpperAntler06.addChild("lUpperAntler06Leaf_r1", ModelPartBuilder.create().uv(0, 0).cuboid(0.5F, -0.25F, -0.25F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.0F, 0.0F, 0.48F, 0.0F));
		ModelPartData lUpperAntler07 = lUpperAntler02a.addChild("lUpperAntler07", ModelPartBuilder.create().uv(107, 0).mirrored().cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.4F, 0.0F, 2.0F, 0.0F, -0.7767F, 0.0F));
		ModelPartData lUpperAntler08 = lUpperAntler07.addChild("lUpperAntler08", ModelPartBuilder.create().uv(108, 0).mirrored().cuboid(-0.5F, -0.49F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 2.45F, 0.0F, 0.3491F, 0.0F));
		ModelPartData rUpperAntler01 = head.addChild("rUpperAntler01", ModelPartBuilder.create().uv(102, 4).cuboid(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.4F, -8.6F, -1.0F, 0.6458F, -0.1396F, -0.6109F));
		ModelPartData rUpperAntler01Leaf_r1 = rUpperAntler01.addChild("rUpperAntler01Leaf_r1", ModelPartBuilder.create().uv(93, 17).cuboid(-2.0F, -1.75F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, -0.3927F, 0.0F, 0.3054F));
		ModelPartData rUpperAntler02a = rUpperAntler01.addChild("rUpperAntler02a", ModelPartBuilder.create().uv(102, 0).cuboid(-1.2F, -1.05F, -0.5F, 2.0F, 2.0F, 4.0F, new Dilation(-0.2F)), ModelTransform.of(0.0F, 0.0F, 1.2F, 0.0524F, 0.3403F, 0.0F));
		ModelPartData rUpperAntler02Leaf_r1 = rUpperAntler02a.addChild("rUpperAntler02Leaf_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, 3.0F, -0.6109F, 0.0F, 0.0F));
		ModelPartData rUpperAntler03 = rUpperAntler02a.addChild("rUpperAntler03", ModelPartBuilder.create().uv(104, 0).cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 3.5F, -0.1745F, -0.3491F, 0.0F));
		ModelPartData rUpperAntler04 = rUpperAntler03.addChild("rUpperAntler04", ModelPartBuilder.create().uv(105, 8).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.3F, 0.1396F, 0.576F, 0.0F));
		ModelPartData rUpperAntler04Leaf_r1 = rUpperAntler04.addChild("rUpperAntler04Leaf_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.0F, -1.309F, 0.3927F, 0.3491F));
		ModelPartData rUpperAntler05 = rUpperAntler04.addChild("rUpperAntler05", ModelPartBuilder.create().uv(108, 5).cuboid(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F, new Dilation(-0.2F)), ModelTransform.of(0.2F, 0.1F, 2.0F, 0.0F, 0.6981F, 0.0F));
		ModelPartData rUpperAntler09 = rUpperAntler04.addChild("rUpperAntler09", ModelPartBuilder.create().uv(105, 11).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(-0.2F)), ModelTransform.of(-0.1F, 0.3F, 1.4F, -0.1745F, -0.3142F, 0.0F));
		ModelPartData rUpperAntler06 = rUpperAntler02a.addChild("rUpperAntler06", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.4F, 0.0F, 2.0F, 0.0F, 0.7767F, 0.0F));
		ModelPartData rUpperAntler07 = rUpperAntler06.addChild("rUpperAntler07", ModelPartBuilder.create().uv(108, 0).cuboid(-0.5F, -0.49F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 0.0F, 2.45F, 0.0F, -0.3491F, 0.0F));
		ModelPartData body = ModelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(78, 79).cuboid(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData cube_r3 = body.addChild("cube_r3", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, 4.5F, 2.5F, -2.2689F, -0.7418F, 0.4363F));
		ModelPartData cube_r4 = body.addChild("cube_r4", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 7.5F, 2.5F, -2.0071F, -0.6109F, 2.2689F));
		ModelPartData cube_r5 = body.addChild("cube_r5", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 5.5F, 2.5F, -0.7854F, -2.0944F, 0.0F));
		ModelPartData cube_r6 = body.addChild("cube_r6", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 7.5F, -2.5F, -1.4835F, 0.5236F, -1.9199F));
		ModelPartData cube_r7 = body.addChild("cube_r7", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 3.5F, -2.5F, -1.5708F, 0.5672F, -2.9671F));
		ModelPartData cube_r8 = body.addChild("cube_r8", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 5.5F, -2.5F, -1.4835F, 0.5236F, 1.0472F));
		ModelPartData cube_r9 = body.addChild("cube_r9", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, 2.5F, -2.5F, -1.4835F, 0.5236F, 0.0F));
		ModelPartData cube_r10 = body.addChild("cube_r10", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 4.5F, -2.5F, -1.4835F, 0.5236F, -0.9599F));
		ModelPartData leftArm = ModelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(108, 99).cuboid(0F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 2.0F, 0.0F));



		ModelPartData cube_r11 = leftArm.addChild("cube_r11", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-0.25F, -0.5F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(3.25F, -3.0F, 1.5F, 0.0F, 0.0F, -1.9199F));
		ModelPartData cube_r12 = leftArm.addChild("cube_r12", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-0.25F, -0.25F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.5F, -1.0F, -0.5F, 0.0F, 0.0F, -0.9599F));
		ModelPartData cube_r13 = leftArm.addChild("cube_r13", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-0.25F, -0.25F, -0.25F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.5F, 6.0F, -2.5F, -0.3054F, 0.3054F, -1.789F));
		ModelPartData rightArm = ModelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(108, 79).cuboid(-4.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 2.0F, 0.0F));
		ModelPartData cube_r14 = rightArm.addChild("cube_r14", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 5.0F, -2.5F, -0.5236F, -0.6981F, -2.0944F));
		ModelPartData cube_r15 = rightArm.addChild("cube_r15", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 2.0F, -2.5F, -0.5236F, -0.6981F, -2.8798F));
		ModelPartData cube_r16 = rightArm.addChild("cube_r16", ModelPartBuilder.create().uv(0, 4).cuboid(-1.75F, -0.25F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 3.0F, -2.5F, -0.0436F, -0.5672F, 1.2654F));
		ModelPartData cube_r17 = rightArm.addChild("cube_r17", ModelPartBuilder.create().uv(0, 0).cuboid(-1.75F, -0.25F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 4.0F, 2.5F, 0.4363F, 0.6109F, 1.1345F));
		ModelPartData cube_r18 = rightArm.addChild("cube_r18", ModelPartBuilder.create().uv(0, 4).cuboid(-1.75F, -0.25F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 1.0F, 2.5F, 0.2618F, 0.2182F, 1.2654F));
		ModelPartData cube_r19 = rightArm.addChild("cube_r19", ModelPartBuilder.create().uv(0, 0).cuboid(-2.25F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -2.0F, -2.0F, -0.0436F, 0.0F, 2.1817F));
		ModelPartData cube_r20 = rightArm.addChild("cube_r20", ModelPartBuilder.create().uv(0, 0).cuboid(-1.75F, -0.25F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-4.5F, 1.0F, -0.5F, 0.0F, 0.0F, 0.5236F));
		ModelPartData cube_r21 = rightArm.addChild("cube_r21", ModelPartBuilder.create().uv(0, 4).cuboid(-2.25F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -2.0F, 0.0F, 0.0F, 0.0F, 1.2654F));
		ModelPartData leftLeg = ModelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(92, 6).cuboid(1.0F, 5.5F, -1.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)).uv(92, 8).cuboid(-1.0F, 7.5F, 2.0F, 4.0F, 5.0F, 1.0F, new Dilation(0.0F)).uv(92, 11).cuboid(-1.0F, 9.5F, -3.0F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)).uv(108, 58).cuboid(-2.2F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		ModelPartData cube_r22 = leftLeg.addChild("cube_r22", ModelPartBuilder.create().uv(0, 4).cuboid(-0.25F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.25F, 4.5F, -2.5F, -1.2248F, 0.2848F, -0.8469F));
		ModelPartData cube_r23 = leftLeg.addChild("cube_r23", ModelPartBuilder.create().uv(0, 4).cuboid(-0.25F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.75F, 2.5F, -1.5F, -0.9163F, -1.0036F, -0.1745F));
		ModelPartData rightLeg = ModelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(87, 5).cuboid(-3.0F, 5.5F, -1.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).uv(104, 3).cuboid(-3.0F, 10.5F, -3.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).uv(96, 10).cuboid(-3.0F, 7.5F, -3.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)).uv(84, 57).cuboid(-2.75F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		ModelPartData cube_r24 = rightLeg.addChild("cube_r24", ModelPartBuilder.create().uv(0, 4).cuboid(-0.25F, 0.0F, -1.75F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.25F, 3.5F, 2.5F, -0.9599F, -2.9234F, 1.4835F));
		ModelPartData cube_r25 = rightLeg.addChild("cube_r25", ModelPartBuilder.create().uv(0, 4).cuboid(-0.25F, 0.0F, -1.75F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.75F, 4.5F, 2.5F, -0.9599F, -2.9234F, 1.1781F));
		ModelPartData cube_r26 = rightLeg.addChild("cube_r26", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 0.0F, -1.75F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.75F, 2.5F, -1.5F, 2.3562F, -0.1222F, 2.7489F));
		ModelPartData cube_r27 = rightLeg.addChild("cube_r27", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 0.0F, -1.75F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.75F, 4.5F, 0.5F, 2.3562F, -0.9076F, 2.138F));
		ModelPartData cube_r28 = rightLeg.addChild("cube_r28", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 0.25F, -1.75F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 2.5F, -2.5F, -1.3526F, 0.3927F, -0.5672F));
		ModelPartData cube_r29 = rightLeg.addChild("cube_r29", ModelPartBuilder.create().uv(0, 4).cuboid(-0.25F, 0.0F, -1.75F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 8.5F, -2.5F, -1.2217F, 0.2618F, -0.5672F));
		ModelPartData cube_r30 = rightLeg.addChild("cube_r30", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 5.0F, -2.5F, -1.0908F, 0.4363F, -1.0036F));

		return TexturedModelData.of(data, 128, 128);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
	}


	private void copyRotation(ModelPart to, ModelPart from) {
		to.pitch = from.pitch;
		to.yaw = from.yaw;
		to.roll = from.roll;
	}

}