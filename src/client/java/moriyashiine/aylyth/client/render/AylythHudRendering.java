package moriyashiine.aylyth.client.render;

import moriyashiine.aylyth.api.interfaces.AylythGameHud;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.YmpeInfestation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public interface AylythHudRendering extends AylythGameHud {
    static void ympeInfestation(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        YmpeInfestation infestation = client.player.getAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION);
        if (infestation != null) {
            int stage = infestation.getStage();
            if (stage >= 3) {
                renderOverlay(drawContext, INFESTATION_OVERLAY_1, stage == 3 ? (float) infestation.getInfestationTimer() / YmpeInfestation.TIME_UNTIL_STAGE_INCREASES : 1);
            }
            if (stage >= 2) {
                renderOverlay(drawContext, INFESTATION_OVERLAY_0, stage == 2 ? (float) infestation.getInfestationTimer() / YmpeInfestation.TIME_UNTIL_STAGE_INCREASES : 1);
            }
            if (stage >= 5) {
                renderOverlay(drawContext, INFESTATION_OVERLAY_2, stage == 5 ? (float) infestation.getInfestationTimer() / YmpeInfestation.TIME_UNTIL_STAGE_INCREASES : 1);
            }
        }
    }

    private static void renderOverlay(DrawContext context, Identifier texture, float alpha) {
        context.drawTexture(
                RenderLayer::getGuiTexturedOverlay,
                texture,
                0,
                0,
                0.0F,
                0.0F,
                context.getScaledWindowWidth(),
                context.getScaledWindowHeight(),
                context.getScaledWindowWidth(),
                context.getScaledWindowHeight(),
                ColorHelper.getWhite(alpha)
        );
    }
}
