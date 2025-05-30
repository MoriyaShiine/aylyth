package moriyashiine.aylyth.api.interfaces;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;

public interface AylythGameHud {
    Identifier INFESTATION_OVERLAY_0 = Aylyth.id("textures/misc/infestation_overlay_0.png");
    Identifier INFESTATION_OVERLAY_1 = Aylyth.id("textures/misc/infestation_overlay_1.png");
    Identifier INFESTATION_OVERLAY_2 = Aylyth.id("textures/misc/infestation_overlay_2.png");
    Identifier SEEP_OVERLAY = Aylyth.id("textures/environment/seep_overlay.png");
    Identifier VITAL_FULL = Aylyth.id("hud/hearts/vital_full");
    Identifier VITAL_HALF = Aylyth.id("hud/hearts/vital_half");
    Identifier VITAL_CONTAINER_FULL = Aylyth.id("hud/hearts/vital_container_full");
    Identifier VITAL_CONTAINER_HALF = Aylyth.id("hud/hearts/vital_container_half");
    Identifier BRANCHES_FULL = Aylyth.id("hud/hearts/branches_full");
    Identifier BRANCHES_HALF = Aylyth.id("hud/hearts/branches_half");
    Identifier BRANCHES_CONTAINER_FULL = Aylyth.id("hud/hearts/branches_container_full");
    Identifier BRANCHES_CONTAINER_HALF = Aylyth.id("hud/hearts/branches_container_half");
}
