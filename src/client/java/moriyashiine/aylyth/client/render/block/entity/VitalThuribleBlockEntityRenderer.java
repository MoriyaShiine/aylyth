package moriyashiine.aylyth.client.render.block.entity;

import moriyashiine.aylyth.common.block.entity.type.VitalThuribleBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector4f;
import org.joml.Vector4fc;

public class VitalThuribleBlockEntityRenderer implements BlockEntityRenderer<VitalThuribleBlockEntity> {
    private static final Vector4fc[] POS = {
            new Vector4f(-0.1F, 0.03F, -0.1F, 25 * MathHelper.RADIANS_PER_DEGREE),
            new Vector4f(0.1F, 0.06F, -0.1F, -45 * MathHelper.RADIANS_PER_DEGREE),
            new Vector4f(0.1F, 0.01F, 0.1F, 15 * MathHelper.RADIANS_PER_DEGREE),
            new Vector4f(-0.1F, 0.07F, 0.1F, 65 * MathHelper.RADIANS_PER_DEGREE),
            new Vector4f(0.05F, 0.1F, 0.05F, -35 * MathHelper.RADIANS_PER_DEGREE)
    };

    public VitalThuribleBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(VitalThuribleBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.translate(0.5, 0.25, 0.5);
        for (int i = 0; i < entity.getStack(0).getCount(); i++) {
            Vector4fc transform = POS[i];
            ItemStack stack = entity.getStack(0);
            matrices.push();
            matrices.translate(transform.x(), transform.y(), transform.z());
            matrices.scale(1 / 2.5f, 1 / 2.5f, 1 / 2.5f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(transform.w()));
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), 42);
            matrices.pop();
        }
    }
}
