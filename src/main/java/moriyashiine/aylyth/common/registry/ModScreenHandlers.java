package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.screenhandler.TulpaScreenHandler;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static void init() {}

    public static final ScreenHandlerType<TulpaScreenHandler> TULPA_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, AylythUtil.id("tulpa_screen"), new ExtendedScreenHandlerType<>(TulpaScreenHandler::new));
}
