package moriyashiine.aylyth.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.entity.type.mob.TulpaEntity;
import moriyashiine.aylyth.common.screenhandler.TulpaScreenHandler;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TulpaScreen extends HandledScreen<TulpaScreenHandler> {
    private static final Identifier TULPA_GUI_TEXTURES = AylythUtil.id("textures/gui/tulpa_inventory.png");

    private final PlayerEntity player;
    private final TulpaEntity tulpaEntity;
    private float mousePosX;
    private float mousePosY;
    private boolean buttonPressed;

    public TulpaScreen(TulpaScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleX = 80;
        this.playerInventoryTitleX = 100;
        this.player = inventory.player;
        this.tulpaEntity = handler.tulpaEntity;
    }

    @Override
    protected void drawForeground(DrawContext matrixStack, int x, int y) {
        super.drawForeground(matrixStack, x, y);
    }

    @Override
    protected void drawBackground(DrawContext matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        matrices.drawTexture(TULPA_GUI_TEXTURES, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        InventoryScreen.drawEntity(matrices, i + 51, j + 75, 30, (float) (i + 51) - this.mousePosX, (float) (j + 75 - 50) - this.mousePosY, this.tulpaEntity);

    }

    @Override
    public void render(DrawContext matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.mousePosX = (float) mouseX;
        this.mousePosY = (float) mouseY;
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(matrixStack, mouseX, mouseY);
    }
}
