package moriyashiine.aylyth.client.screen;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity;
import moriyashiine.aylyth.common.screenhandler.TulpaScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TulpaScreen extends HandledScreen<TulpaScreenHandler> {
    private static final Identifier TULPA_GUI_TEXTURES = Aylyth.id("textures/gui/tulpa_inventory.png");

    private final PlayerEntity player;
    private final TulpaEntity tulpaEntity;

    public TulpaScreen(TulpaScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleX = 80;
        this.playerInventoryTitleX = 100;
        this.player = inventory.player;
        this.tulpaEntity = handler.tulpaEntity;
    }

    @Override
    protected void drawForeground(DrawContext drawContext, int x, int y) {
        super.drawForeground(drawContext, x, y);
    }

    @Override
    protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        drawContext.drawTexture(RenderLayer::getGuiTextured, TULPA_GUI_TEXTURES, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
        // TODO: Fix entity draw
//        InventoryScreen.drawEntity(drawContext, i + 51, j + 75, 30, (float) (i + 51) - this.mousePosX, (float) (j + 75 - 50) - this.mousePosY, this.tulpaEntity);
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(drawContext, mouseX, mouseY, partialTicks);
        super.render(drawContext, mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }
}
