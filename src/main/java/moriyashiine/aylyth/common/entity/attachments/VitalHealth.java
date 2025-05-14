package moriyashiine.aylyth.common.entity.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class VitalHealth {
    public static final Codec<VitalHealth> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("vital_health").forGetter(VitalHealth::getValue)
            ).apply(instance, VitalHealth::new)
    );
    public static final PacketCodec<? super RegistryByteBuf, VitalHealth> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, VitalHealth::getValue,
            VitalHealth::new
    );
    private float vitalHealth;

    public VitalHealth() {
        this(0);
    }

    public VitalHealth(float vitalHealth) {
        this.vitalHealth = vitalHealth;
    }

    public float getValue() {
        return vitalHealth;
    }

    public void setValue(float vital) {
        vitalHealth = vital;
    }
}
