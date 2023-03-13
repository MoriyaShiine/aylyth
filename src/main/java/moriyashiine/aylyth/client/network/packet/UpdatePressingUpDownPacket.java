package moriyashiine.aylyth.client.network.packet;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class UpdatePressingUpDownPacket {
    public static final Identifier ID = new Identifier(Aylyth.MOD_ID, "toggle_pressing_up_down");

    public UpdatePressingUpDownPacket() {
    }

    public static void send(boolean pressingUp, boolean pressingDown) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(pressingUp);
        buf.writeBoolean(pressingDown);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        boolean pressingUp = buf.readBoolean();
        boolean pressingDown = buf.readBoolean();
        server.execute(() -> {
            ModComponents.RIDER_COMPONENT.get(player).setPressingUp(pressingUp);
            ModComponents.RIDER_COMPONENT.get(player).setPressingDown(pressingDown);
        });
    }
}