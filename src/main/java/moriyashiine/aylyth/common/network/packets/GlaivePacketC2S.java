package moriyashiine.aylyth.common.network.packets;

import moriyashiine.aylyth.common.network.AylythPacketTypes;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record GlaivePacketC2S(int entityId) implements FabricPacket {
    public static GlaivePacketC2S create(PacketByteBuf buf) {
        return new GlaivePacketC2S(buf.readInt());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Override
    public PacketType<?> getType() {
        return AylythPacketTypes.GLAIVE_SPECIAL_PACKET;
    }
}