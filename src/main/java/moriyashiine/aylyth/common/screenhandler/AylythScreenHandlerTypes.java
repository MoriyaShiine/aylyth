package moriyashiine.aylyth.common.screenhandler;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public interface AylythScreenHandlerTypes {

    ScreenHandlerType<TulpaScreenHandler> TULPA = register("tulpa", TulpaScreenHandler::new);

    private static <H extends ScreenHandler> ScreenHandlerType<H> register(String name, ExtendedFactory<H> handlerFactory) {
        return Registry.register(Registries.SCREEN_HANDLER, AylythUtil.id(name), new ExtendedScreenHandlerType<>(handlerFactory));
    }

    static void register() {}
}
