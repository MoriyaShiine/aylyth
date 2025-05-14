package moriyashiine.aylyth.common.entity.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class RiderControls {
    public static final Codec<RiderControls> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("pressing_up").forGetter(RiderControls::isPressingUp),
                    Codec.BOOL.fieldOf("pressing_down").forGetter(RiderControls::isPressingDown)
            ).apply(instance, RiderControls::new)
    );
    public static final PacketCodec<? super RegistryByteBuf, RiderControls> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, RiderControls::isPressingUp,
            PacketCodecs.BOOLEAN, RiderControls::isPressingDown,
            RiderControls::new
    );
    private boolean pressingUp;
    private boolean pressingDown;

    public RiderControls() {
        this(false, false);
    }

    public RiderControls(boolean pressingUp, boolean pressingDown) {
        this.pressingUp = pressingUp;
        this.pressingDown = pressingDown;
    }

    public boolean isPressingUp() {
        return this.pressingUp;
    }

    public boolean isPressingDown() {
        return this.pressingDown;
    }

    public void setPressingUp(boolean pressingUp) {
        this.pressingUp = pressingUp;
    }

    public void setPressingDown(boolean pressingDown) {
        this.pressingDown = pressingDown;
    }
}