package moriyashiine.aylyth.client.render.item;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.mixin.client.HeldItemRendererAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import org.joml.Matrix4f;

import java.util.List;

public class MysteriousSketchItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if ((mode.isFirstPerson() || mode == ModelTransformationMode.FIXED) && stack.hasNbt() && stack.getNbt().contains("PageId")) {
            Identifier pageId = new Identifier(stack.getNbt().getString("PageId"));
            RenderSystem.setShaderTexture(0, MinecraftClient.getInstance().player.getSkinTexture());
            PlayerEntityRenderer playerEntityRenderer = (PlayerEntityRenderer)MinecraftClient.getInstance().getEntityRenderDispatcher().<AbstractClientPlayerEntity>getRenderer(MinecraftClient.getInstance().player);
            Identifier pageTextureId = new Identifier("%s:textures/item/mysterious_sketches/%s.png".formatted(pageId.getNamespace(), pageId.getPath()));
            HeldItemRendererAccessor accessor = (HeldItemRendererAccessor)MinecraftClient.getInstance().gameRenderer.firstPersonRenderer;
            boolean hasEmptyOffhand = MinecraftClient.getInstance().player.getOffHandStack().isEmpty();
            switch (mode) {
                case FIRST_PERSON_LEFT_HAND -> {
                    matrices.push();
                    matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(45.0F));
                    matrices.translate(-0.065F, -0.3F, 0.75F);
                    playerEntityRenderer.renderLeftArm(matrices, vertexConsumers, light, MinecraftClient.getInstance().player);
                    matrices.pop();

//                    if (MinecraftClient.getInstance().player.getOffHandStack().isEmpty() || accessor.getEquipProgressMainHand() != 0) {
//                        matrices.push();
//                        matrices.translate(-0.565, 0.5225, 0);
//                        drawPage(mode, matrices, vertexConsumers.getBuffer(RenderLayer.getText(pageTextureId)), light);
//                        matrices.pop();
//                    } else {
//                        matrices.push();
//                        matrices.scale(0.65f, 0.65f, 0.65f);
//                        matrices.translate(0.35, 1.1, 0.6);
//                        drawPage(mode, matrices, vertexConsumers.getBuffer(RenderLayer.getText(pageTextureId)), light);
//                        matrices.pop();
//                    }
                }
                case FIRST_PERSON_RIGHT_HAND -> {
                    if (hasEmptyOffhand) {
                        matrices.push();
                        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(45.0F));
                        matrices.translate(-0.065F, -0.3F, 0.75F);
                        playerEntityRenderer.renderLeftArm(matrices, vertexConsumers, light, MinecraftClient.getInstance().player);
                        matrices.pop();
                    }

                    if (true) {
                        matrices.push();
//                        matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(45.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
//                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180f));
//                        matrices.translate(-0.065f, -0.3f, 0.75f);
                        playerEntityRenderer.renderRightArm(matrices, vertexConsumers, light, MinecraftClient.getInstance().player);
                        matrices.pop();
                    }/* else {
                        matrices.push();
                        matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(45f));
                        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180f));
                        matrices.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(5f));
                        matrices.translate(-0.25f, -0.35f, -0.65f);
                        playerEntityRenderer.renderRightArm(matrices, vertexConsumers, light, MinecraftClient.getInstance().player);
                        matrices.pop();
                    }*/

//                    matrices.push();
//                    matrices.scale(0.65f, 0.65f, 0.65f);
//                    matrices.translate(0.35, 1.1, 0.5);
//                    drawPage(mode, matrices, vertexConsumers.getBuffer(RenderLayer.getText(pageTextureId)), light);
//                    matrices.pop();
                }
                case FIXED -> {
                    matrices.push();
                    matrices.scale(1.495f, 1.495f, 1.495f);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                    matrices.translate(-0.835, -0.165, -0.325);
                    drawPage(mode, matrices, vertexConsumers.getBuffer(RenderLayer.getText(pageTextureId)), light);
                    matrices.pop();
                }
            }
        } else {
            BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(AylythUtil.id("mysterious_sketch_generated"), "inventory"));
            matrices.push();
            model.getTransformation().getTransformation(mode).apply(mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND, matrices);
            switch (mode) {
                case FIXED -> {
                    matrices.translate(-1, 0, -1);
                }
                case GROUND, THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND -> {
                    matrices.translate(0.5, 0.5, 0.5);
                }
                case FIRST_PERSON_RIGHT_HAND -> {
                    matrices.translate(0.5, 0, -1.25);
                }
                case FIRST_PERSON_LEFT_HAND -> {
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                    matrices.translate(0.5, 0, -1.225);
                }
            }

            Random rand = Random.create();
            RenderLayer layer = RenderLayers.getItemLayer(stack, true);
            VertexConsumer consumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, mode == ModelTransformationMode.GUI ? RenderLayer.getCutout() : layer, true, stack.hasGlint());
            for (Direction dir : Direction.values()) {
                rand.setSeed(42L);
                renderQuads(stack, model.getQuads(null, dir, rand), consumer, matrices, light, overlay);
            }
            rand.setSeed(42L);
            renderQuads(stack, model.getQuads(null, null, rand), consumer, matrices, light, overlay);
            matrices.pop();
        }
    }

    private void renderQuads(ItemStack stack, List<BakedQuad> quads, VertexConsumer consumer, MatrixStack matrices, int light, int overlay) {
        MatrixStack.Entry entry = matrices.peek();
        for (BakedQuad quad : quads) {
            float r = 1.0f;
            float g = 1.0f;
            float b = 1.0f;
            ItemColorProvider colorProvider = ColorProviderRegistry.ITEM.get(stack.getItem());
            if (quad.hasColor() && colorProvider != null) {
                r = ((colorProvider.getColor(stack, quad.getColorIndex()) >> 16) & 255) / 255f;
                g = ((colorProvider.getColor(stack, quad.getColorIndex()) >> 8) & 255) / 255f;
                b = ((colorProvider.getColor(stack, quad.getColorIndex())) & 255) / 255f;
            }
            consumer.quad(entry, quad, r, g, b, light, overlay);
        }
    }

    private void drawPage(ModelTransformationMode mode, MatrixStack matrices, VertexConsumer consumer, int light) {
        Matrix4f posMat = matrices.peek().getPositionMatrix();
            consumer.vertex(posMat, 0, 1, 0).color(255, 255, 255, 255).texture(0f, 0f).light(light).next();
            consumer.vertex(posMat, 0, 0, 0).color(255, 255, 255, 255).texture(0f, 1f).light(light).next();
            consumer.vertex(posMat, 1, 0, 0).color(255, 255, 255, 255).texture(1f, 1f).light(light).next();
            consumer.vertex(posMat, 1, 1, 0).color(255, 255, 255, 255).texture(1f, 0f).light(light).next();
    }
}
