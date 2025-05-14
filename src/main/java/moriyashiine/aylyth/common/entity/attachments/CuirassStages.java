package moriyashiine.aylyth.common.entity.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class CuirassStages {
    public static final Codec<CuirassStages> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("stage").forGetter(CuirassStages::getStage),
                    Codec.INT.fieldOf("stage_timer").forGetter(CuirassStages::getStageTimer)
            ).apply(instance, CuirassStages::new)
    );
    public static final PacketCodec<? super RegistryByteBuf, CuirassStages> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, CuirassStages::getStage,
            PacketCodecs.INTEGER, CuirassStages::getStageTimer,
            CuirassStages::new
    );
    //Under the hood value but in reality should be 5 stages of growth times 2 for each heart and 2 again for halved damage thingy
    public static final float MAX_STAGE = 20;
    private int stage;
    private int stageTimer;

    public CuirassStages() {
        this(0, 0);
    }

    public CuirassStages(int stage, int stageTimer) {
        this.stage = stage;
        this.stageTimer = stageTimer;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getStageTimer() {
        return stageTimer;
    }

    public void setStageTimer(int stageTimer) {
        this.stageTimer = stageTimer;
    }
}
