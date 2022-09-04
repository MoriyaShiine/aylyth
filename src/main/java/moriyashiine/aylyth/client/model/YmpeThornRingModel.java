package moriyashiine.aylyth.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class YmpeThornRingModel extends EntityModel<Entity> {
	private final ModelPart thornWheel;

	public YmpeThornRingModel(ModelPart root) {
		this.thornWheel = root.getChild("thorn_wheel");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData thorn_wheel = root.addChild("thorn_wheel", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -3.5F, 0.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 18.5F, 0.0F));
		ModelPartData cube_r1 = thorn_wheel.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -8.0F, 0.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.3787F, -0.3787F, 0.0F, 0.0F, 0.0F, -1.5708F));
		ModelPartData cube_r2 = thorn_wheel.addChild("cube_r2", ModelPartBuilder.create().uv(3, 0).cuboid(-2.0F, -8.0F, 0.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -4.2574F, 0.0F, 0.0F, 0.0F, -3.1416F));
		ModelPartData cube_r3 = thorn_wheel.addChild("cube_r3", ModelPartBuilder.create().uv(8, 0).cuboid(-2.0F, -8.0F, 0.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.3787F, 0.6213F, 0.0F, 0.0F, 0.0F, 1.5708F));
		ModelPartData cube_r4 = thorn_wheel.addChild("cube_r4", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 6.0F, 0.01F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0355F, -2.6213F, 0.0F, 0.0F, 0.0F, -0.7854F));
		ModelPartData cube_r5 = thorn_wheel.addChild("cube_r5", ModelPartBuilder.create().uv(8, 0).cuboid(-2.0F, -8.0F, 0.01F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.4497F, 2.864F, 0.0F, 0.0F, 0.0F, -0.7854F));
		ModelPartData cube_r6 = thorn_wheel.addChild("cube_r6", ModelPartBuilder.create().uv(6, 0).cuboid(0.0F, 6.0F, 0.01F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.3284F, -3.3284F, 0.0F, 0.0F, 0.0F, 0.7854F));
		ModelPartData cube_r7 = thorn_wheel.addChild("cube_r7", ModelPartBuilder.create().uv(4, 0).cuboid(-2.0F, -8.0F, 0.01F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.7426F, 3.5711F, 0.0F, 0.0F, 0.0F, 0.7854F));
		ModelPartData outer_thorns = thorn_wheel.addChild("outer_thorns", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData thorns01 = outer_thorns.addChild("thorns01", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData cube_r8 = thorns01.addChild("cube_r8", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		ModelPartData thorns02 = outer_thorns.addChild("thorns02", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		ModelPartData cube_r9 = thorns02.addChild("cube_r9", ModelPartBuilder.create().uv(0, 12).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		ModelPartData thorns03 = outer_thorns.addChild("thorns03", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
		ModelPartData cube_r10 = thorns03.addChild("cube_r10", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		ModelPartData thorns04 = outer_thorns.addChild("thorns04", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.3562F));
		ModelPartData cube_r11 = thorns04.addChild("cube_r11", ModelPartBuilder.create().uv(0, 4).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		ModelPartData thorns05 = outer_thorns.addChild("thorns05", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));
		ModelPartData cube_r12 = thorns05.addChild("cube_r12", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		ModelPartData thorns06 = outer_thorns.addChild("thorns06", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.3562F));
		ModelPartData cube_r13 = thorns06.addChild("cube_r13", ModelPartBuilder.create().uv(0, 4).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		ModelPartData thorns07 = outer_thorns.addChild("thorns07", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
		ModelPartData cube_r14 = thorns07.addChild("cube_r14", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		ModelPartData thorns08 = outer_thorns.addChild("thorns08", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		ModelPartData cube_r15 = thorns08.addChild("cube_r15", ModelPartBuilder.create().uv(0, 12).cuboid(-2.0F, -4.25F, -3.5F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		return TexturedModelData.of(data, 16, 16);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		thornWheel.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}
