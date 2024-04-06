package moriyashiine.aylyth.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;

public class RenderUtils {

    /**
     * Modified version of {@link InventoryScreen#drawEntity} to accommodate for REI and EMI
     * @param x x
     * @param y y
     * @param size size of drawn entity
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param entity entity to draw
     */
    public static void drawEntity(DrawContext context, int x, int y, int size, float mouseX, float mouseY, Entity entity) {
        float f = (float) Math.atan((x - mouseX) / 40.0F);
        float g = (float) Math.atan((y - mouseY) / 40.0F);
        var stack = context.getMatrices();
        stack.push();
        stack.translate(x, y, 1000.0);
        stack.scale((float)size, (float)size, -(float)size);
        var quaternion = RotationAxis.POSITIVE_Z.rotationDegrees(180.0F);
        var quaternion2 = RotationAxis.POSITIVE_X.rotationDegrees(g * 20.0F);
        quaternion.mul(quaternion2);
        stack.multiply(quaternion);
        float h = entity.getBodyYaw();
        float i = entity.getYaw();
        float j = entity.getPitch();
        float l = entity.getHeadYaw();
        entity.setBodyYaw(180.0F + f * 20.0F);
        entity.setYaw(180.0F + f * 40.0F);
        entity.setPitch(-g * 20.0F);
        entity.setHeadYaw(entity.getYaw());
        float k = 0;
        if (entity instanceof LivingEntity living) {
            k = living.prevHeadYaw;
            living.prevHeadYaw = entity.getYaw();
        }
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, stack, context.getVertexConsumers(), LightmapTextureManager.MAX_LIGHT_COORDINATE));
        context.draw();
        entityRenderDispatcher.setRenderShadows(true);
        entity.setBodyYaw(h);
        entity.setYaw(i);
        entity.setPitch(j);
        if (entity instanceof LivingEntity living) {
            living.prevHeadYaw = k;
        }
        entity.setHeadYaw(l);
        stack.pop();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
