package moriyashiine.aylyth.common.network.packets;

import io.netty.buffer.ByteBuf;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record UpdatePressingUpDownPacketC2S(boolean pressingUp, boolean pressingDown) implements CustomPayload {
    public static final CustomPayload.Id<UpdatePressingUpDownPacketC2S> ID = new Id<>(Aylyth.id("toggle_pressing_up_down"));
    public static final PacketCodec<? extends ByteBuf, UpdatePressingUpDownPacketC2S> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, UpdatePressingUpDownPacketC2S::pressingUp,
            PacketCodecs.BOOLEAN, UpdatePressingUpDownPacketC2S::pressingDown,
            UpdatePressingUpDownPacketC2S::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}