package moriyashiine.aylyth.common.entity.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record AdditionalPlayerInput(boolean ascending, boolean descending) {
    public static final Codec<AdditionalPlayerInput> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("ascending").forGetter(AdditionalPlayerInput::ascending),
                    Codec.BOOL.fieldOf("descending").forGetter(AdditionalPlayerInput::descending)
            ).apply(instance, AdditionalPlayerInput::new)
    );
    public static final PacketCodec<? super RegistryByteBuf, AdditionalPlayerInput> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, AdditionalPlayerInput::ascending,
            PacketCodecs.BOOLEAN, AdditionalPlayerInput::descending,
            AdditionalPlayerInput::new
    );
    public static final AdditionalPlayerInput DEFAULT = new AdditionalPlayerInput(false, false);
}