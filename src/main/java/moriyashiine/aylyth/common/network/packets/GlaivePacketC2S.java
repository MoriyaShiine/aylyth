package moriyashiine.aylyth.common.network.packets;

import io.netty.buffer.ByteBuf;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record GlaivePacketC2S(int entityId) implements CustomPayload {
    public static final CustomPayload.Id<GlaivePacketC2S> ID = new Id<>(Aylyth.id("glaive"));
    public static final PacketCodec<? extends ByteBuf, GlaivePacketC2S> PACKET_CODEC = PacketCodecs.INTEGER.xmap(
            GlaivePacketC2S::new, GlaivePacketC2S::entityId
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}