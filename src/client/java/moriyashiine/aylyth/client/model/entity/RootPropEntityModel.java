package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class RootPropEntityModel <T extends Entity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "elder_aylythian_root_attack"), "main");
    private final ModelPart mainRoot;

    public RootPropEntityModel(ModelPart root) {
        this.mainRoot = root.getChild("mainRoot");
    }

    public static TexturedModelData createBodyLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData mainRoot = partdefinition.addChild("mainRoot", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -8.5F, -4.0F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-1.5F, 7.5F, -4.0F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
        ModelPartData cube_r1 = mainRoot.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -9.5F, -3.0F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        ModelPartData cube_r2 = mainRoot.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-1.5F, -10.25F, -3.25F, 3.0F, 3.0F, 8.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));
        ModelPartData cube_r3 = mainRoot.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-1.5F, -8.75F, -3.25F, 3.0F, 3.0F, 8.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
        ModelPartData claws = mainRoot.addChild("claws", ModelPartBuilder.create(), ModelTransform.of(0.0F, -7.0F, -4.0F, -0.48F, 0.0F, 0.0F));
        ModelPartData cube_r4 = claws.addChild("cube_r4", ModelPartBuilder.create().uv(0, 11).cuboid(-2.0F, -1.5F, -6.5F, 4.0F, 4.0F, 8.0F, new Dilation(-0.3F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData centralClaws = claws.addChild("centralClaws", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData cube_r5 = centralClaws.addChild("cube_r5", ModelPartBuilder.create().uv(8, 5).cuboid(-2.25F, 0.25F, -12.0F, 5.0F, 0.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        ModelPartData cube_r6 = centralClaws.addChild("cube_r6", ModelPartBuilder.create().uv(8, 5).cuboid(-2.25F, 0.25F, -12.0F, 5.0F, 0.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        return TexturedModelData.of(meshdefinition, 32, 32);
    }


    @Override
    public ModelPart getPart() {
        return mainRoot;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float f = limbAngle * 2.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        /*
        f = 1.0F - f * f * f;
        this.upperJaw.roll = 3.1415927F - f * 0.35F * 3.1415927F;
        this.lowerJaw.roll = 3.1415927F + f * 0.35F * 3.1415927F;
        float g = (limbAngle + MathHelper.sin(limbAngle * 2.7F)) * 0.6F * 12.0F;
        this.upperJaw.pivotY = 24.0F - g;
        this.lowerJaw.pivotY = this.upperJaw.pivotY;
        this.mainRoot.pivotY = this.upperJaw.pivotY;

         */
    }
}
