package moriyashiine.aylyth.common.network.packets;

import moriyashiine.aylyth.common.network.AylythPacketTypes;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record UpdatePressingUpDownPacketC2S(boolean pressingUp, boolean pressingDown) implements FabricPacket {
    public static UpdatePressingUpDownPacketC2S create(PacketByteBuf buf) {
        return new UpdatePressingUpDownPacketC2S(buf.readBoolean(), buf.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(pressingUp);
        buf.writeBoolean(pressingDown);
    }

    @Override
    public PacketType<?> getType() {
        return AylythPacketTypes.UPDATE_RIDER_PACKET;
    }
}