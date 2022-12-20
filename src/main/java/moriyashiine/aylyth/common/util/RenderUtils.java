package moriyashiine.aylyth.common.util;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.math.Rectangle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

public class RenderUtils {

    /**
     * Modified version of {@link InventoryScreen#drawEntity} to accommodate for REI and EMI
     * @param x x
     * @param y y
     * @param size size of drawn entity
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param entity entity to draw
     * @param bounds REI supported parameter
     */
    public static void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity, @Nullable Rectangle bounds) {
        float f;
        float g;
        if(bounds != null){
            f = (float) Math.atan((bounds.getCenterX() - mouseX) / 40.0F);
            g = (float) Math.atan((bounds.getCenterY() - mouseY) / 40.0F);
        }else{
            f = (float) Math.atan((x - mouseX) / 40.0F);
            g = (float) Math.atan((y - mouseY) / 40.0F);
        }
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        if(bounds != null) {
            matrixStack.translate(bounds.getCenterX(), bounds.getCenterY() + 20, 1050.0);
        }else{
            matrixStack.translate(x, y + 20, 1050.0);
        }
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0, 0.0, 1000.0);
        matrixStack2.scale((float)size, (float)size, (float)size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
        quaternion.hamiltonProduct(quaternion2);
        matrixStack2.multiply(quaternion);
        float h = entity.bodyYaw;
        float i = entity.getYaw();
        float j = entity.getPitch();
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        entity.bodyYaw = 180.0F + f * 20.0F;
        entity.setYaw(180.0F + f * 40.0F);
        entity.setPitch(-g * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, matrixStack2, immediate, 15728880));
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        entity.bodyYaw = h;
        entity.setYaw(i);
        entity.setPitch(j);
        entity.prevHeadYaw = k;
        entity.headYaw = l;
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
