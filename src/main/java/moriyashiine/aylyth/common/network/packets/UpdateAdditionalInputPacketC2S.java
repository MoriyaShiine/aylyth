package moriyashiine.aylyth.common.network.packets;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.attachments.AdditionalPlayerInput;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record UpdateAdditionalInputPacketC2S(AdditionalPlayerInput input) implements CustomPayload {
    public static final CustomPayload.Id<UpdateAdditionalInputPacketC2S> ID = new Id<>(Aylyth.id("toggle_pressing_up_down"));
    public static final PacketCodec<? super RegistryByteBuf, UpdateAdditionalInputPacketC2S> PACKET_CODEC = PacketCodec.tuple(
            AdditionalPlayerInput.PACKET_CODEC, UpdateAdditionalInputPacketC2S::input,
            UpdateAdditionalInputPacketC2S::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}