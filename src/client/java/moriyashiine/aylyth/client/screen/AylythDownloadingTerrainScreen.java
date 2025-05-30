package moriyashiine.aylyth.client.screen;

import moriyashiine.aylyth.client.integration.iris.IrisCompat;
import moriyashiine.aylyth.client.render.AylythHudRendering;
import moriyashiine.aylyth.client.render.AylythRenderLayers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.render.RenderLayer;

import java.util.function.BooleanSupplier;

public class AylythDownloadingTerrainScreen extends DownloadingTerrainScreen {
    public AylythDownloadingTerrainScreen(BooleanSupplier shouldClose, WorldEntryReason worldEntryReason) {
        super(shouldClose, worldEntryReason);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        if (FabricLoader.getInstance().isModLoaded("iris") && IrisCompat.isShaderPackInUse()) {
            context.drawTexture(RenderLayer::getGuiOpaqueTexturedBackground, AylythHudRendering.SEEP_OVERLAY, 0, 0, 0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), 256, 256);
        } else {
            context.fillWithLayer(AylythRenderLayers.SEEP, 0, 0, this.width, this.height, 0);
        }
    }
}
