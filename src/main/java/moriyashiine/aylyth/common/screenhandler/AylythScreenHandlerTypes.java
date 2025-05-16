package moriyashiine.aylyth.common.screenhandler;

import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public interface AylythScreenHandlerTypes {

    ScreenHandlerType<TulpaScreenHandler> TULPA = register("tulpa", TulpaScreenHandler::new, PacketCodecs.INTEGER);

    private static <H extends ScreenHandler, D> ScreenHandlerType<H> register(
            String name,
            ExtendedFactory<H, D> handlerFactory,
            PacketCodec<? super RegistryByteBuf, D> packetCodec
    ) {
        return Registry.register(Registries.SCREEN_HANDLER, Aylyth.id(name), new ExtendedScreenHandlerType<>(handlerFactory, packetCodec));
    }

    static void register() {}
}
