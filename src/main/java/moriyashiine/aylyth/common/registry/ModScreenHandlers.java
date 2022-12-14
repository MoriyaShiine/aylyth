package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.screenhandler.TulpaScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    public static final ScreenHandlerType<TulpaScreenHandler> TULPA_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerExtended(AylythUtil.id("tulpa_screen"),
            TulpaScreenHandler::new);


}
