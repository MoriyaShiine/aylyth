package moriyashiine.aylyth.api.interfaces;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;

public interface AylythGameHud {
    Identifier YMPE_OUTLINE_0_TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/gui/ympe_outline_0.png");
    Identifier YMPE_OUTLINE_1_TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/gui/ympe_outline_1.png");
    Identifier YMPE_OUTLINE_2_TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/gui/ympe_outline_2.png");
    Identifier YMPE_HEALTH_TEXTURES = new Identifier(Aylyth.MOD_ID, "textures/gui/icons.png");
    Identifier SEEP_OVERLAY = Aylyth.id("textures/environment/seep_overlay.png");
    Identifier HEARTS = Aylyth.id("textures/gui/aylyth_hearts.png");
}
